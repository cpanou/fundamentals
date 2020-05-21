package com.company.eshop.repository;

import com.company.eshop.application.MyApplication;
import com.company.eshop.model.Order;
import com.company.eshop.model.Product;
import com.company.eshop.repository.templates.OrderProductTemplate;
import com.company.eshop.repository.templates.OrderTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderRepository {
    private static final Logger log = Logger.getLogger(MyApplication.class.getName());
    //Singleton Pattern
    private static OrderRepository instance;
    private List<Order> orders = new ArrayList<>();
    private OrderRepository(){
    }
    public static void init(){
        instance = new OrderRepository();
    }
    public static OrderRepository getInstance() {
        return instance;
    }
    private long getNewOrderId() {
        return orders.size() + 1;
    }
    //END Singleton


    /* CRUD OPERATIONS
     *
     * CREATE -> add
     * READ -> get
     * UPDATE -> get -> edit
     * DELETE -> remove
     *
     * */
    //(1) get All orders from db
    public List<Order> getOrders(){
        log.info("fetching all orders");
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DataBaseUtils.createConnection();
             Statement statement = connection.createStatement()){

            ResultSet resultSet = statement.executeQuery(OrderTemplate.QUERY_SELECT_ALL_ORDERS);
            while (resultSet.next()) {
                Order order = parseOrderFromDB(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    //(1) get An order from db by its orderId.
    //(2) we want to retrieve ALL the products for the order.
    //(3) the only input is the orderId so in order to retrieve an order and all its products
    // we need to operate on the orders, orderproducts and products table:
    //          1. The orders table to fetch the order by its id
    //          2. The orderproducts table to fetch the productIds associated with the order
    //          3. The products table to fetch the actual products.
    public Order getOrder(long orderId) {
        Order order = null;
        log.info("fetching order with id: " + orderId);
        //(4) We need to define the queries for operated on each table mentioned above
        // 1. Select an order by its orderId
        // 2. Select * from orderproducts by the order id
        // 3. Select * from products for each retrieved product id
        try (Connection connection = DataBaseUtils.createConnection();
             PreparedStatement selectOrder = connection.prepareStatement(OrderTemplate.QUERY_SELECT_ORDER_ID);
             PreparedStatement selectOrderProducts = connection.prepareStatement(OrderProductTemplate.QUERY_SELECT_ORDER_ID);
             PreparedStatement selectProduct = connection.prepareStatement("SELECT * FROM eshop.products WHERE productId = ?;")){

            //SELECT ORDER

            //(5) we pass the orderId the select order statement and execute
            selectOrder.setLong(1,orderId);
            ResultSet orderResult = selectOrder.executeQuery();
            while (orderResult.next()) {
               order = parseOrderFromDB(orderResult);
            }
            //(6) check if the order is null it means the select query did not fetch any data
            if(order == null)
                return null;

            //SELECT ORDERPRODUCTS

            //(5) we pass the orderId to the select orderproducts statement and execute
            selectOrderProducts.setLong(1, orderId);
            ResultSet orderProductResult = selectOrderProducts.executeQuery();
            //(6) we only need all the product ids so we will use a temporary list to hold them
            List<Long> productIdList = new ArrayList<>();
            while (orderProductResult.next()) {
                productIdList.add(orderProductResult.getLong(OrderProductTemplate.COLUMN_PRODUCT_ID));
            }
            //(7) if the list is empty there were no products
            if(productIdList.isEmpty())
                return null;

            //SELECT MULTIPLE PRODUCTS

            //(8) We need a List to hold the products as we retrieve them
            List<Product> productList = new ArrayList<>();
            //(9) for each productId in the List we execute a the select Statement to fetch the product
            for( Long productId : productIdList){
                selectProduct.setLong(1, productId);
                ResultSet productResult = selectProduct.executeQuery();

                //(10) we parse the product from the db to a java object
                while (productResult.next()) {
                    Product product = new Product();
                    product.setProductId(productResult.getLong("productId"));
                    product.setPrice(productResult.getLong("price"));
                    product.setProductName(productResult.getString("productName"));
                    productList.add(product);
                }
                selectProduct.clearParameters();
            }
            //(11) Finally we have retrieved the Order from the Database and all its products by using the
            // orderproducts relationship and the products table.
            order.setProducts(productList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("order with id : " + orderId + " not found");
        return order;
    }

    
    // Add A new ORDER in the Database
    //(1) SHOWCASES TRANSACTIONS
    //(2) SHOWCASES BATCH STATEMENTS ( update statements ONLY (UPDATE, INSERT, DELETE) executed many times )
    public Order addOrder(Order order) {
        log.info("Creating order: " + order.getOrderId());
        //(3) we will need to insert the new order in the orders table and to associate it
        //  with all the products it contains we will create an entry in the orderproducts table
        //  for each product in the productsList
        try (Connection connection = DataBaseUtils.createConnection();
             PreparedStatement selectOrderStatement = connection.prepareStatement(OrderTemplate.QUERY_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement orderProductsStatement = connection.prepareStatement(OrderProductTemplate.QUERY_INSERT_ORDER_PRODUCTS)) {
            //1.1 - Since we want to execute multiple inserts we will
            // turn off autocommit so we can undo all executed inserts if anything goes wrong in the process
            connection.setAutoCommit(false);

            //3.1 - We pass the order attributes to the parameters of the statement
            selectOrderStatement.setLong(1, order.getUserId());
            selectOrderStatement.setString(2, order.getStatus().name());
            selectOrderStatement.setTimestamp(3, Timestamp.valueOf(order.getSubmittedDate()));

            //3.2 - We execute the statement and check the result
            int result = selectOrderStatement.executeUpdate();
            if( result == 0 ) {
                return null;
            }
            //3.3 - we retrieve the order id generated in the database
            // we will need it to create the orderproducts entries
            ResultSet keySet = selectOrderStatement.getGeneratedKeys();
            if(!keySet.next()) {
                //1.2 - If we cannot retrieve the key we call the connection.rollback()
                // method to undo the insert we previously executed and return
                connection.rollback();
                keySet.close();
                return null;
            }

            order.setOrderId(keySet.getLong(1));
            keySet.close();
            //2.1 - for each product in the order we create an insert statement and add it to a batch of statements to be
            // executed in the database
            for(Product product : order.getProducts()) {
                //2.2 - we pass the values to the parameters of the statement
                orderProductsStatement.setLong(1, order.getOrderId());
                orderProductsStatement.setLong(2, product.getProductId());
                //2.3 - the add batch method adds the set of parameters above to be used when we execute the query
                orderProductsStatement.addBatch();
            }
            //2.4 - when we call the executebatch() method the same statement is executed
            // for each set of parameters we added in the step above
            int[] batchResult = orderProductsStatement.executeBatch();
            //2.5 the result is an array that contains the number of rows changed by each execution
            // (e.g.) provided we executed the statement 3 times and the result is:
            //          batchResult[0]--> 1  : the first execution updated 1 row
            //          batchResult[1]--> 2  : the second execution updated 2 rows
            //          batchResult[2]--> 0  : the third execution updated 0 rows
            // since we execute an insert statement a 0 row count is an error so we need to check each
            // result for errors.
            for ( int res : batchResult) {
                //for each result we check its value ( row count)
                if (res == 0){
                    //1.3 - If any row count is 0 we rollback ALL the changes and return
                    connection.rollback();
                    return null;
                }
            }
            //1.4 - If the execution reaches this point the Order and its associations in the orderproducts
            //  table were added successfully so we call the connection.commit() method to save the changes in the db.
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        log.info("Order " + order.getOrderId() + " created");
        return order;
    }

    public Order deleteOrder(long orderId) {
        log.info("Deleting order with id: " + orderId);
        Order order = null;
        try (Connection connection = DataBaseUtils.createConnection();
             Statement selectOrderStatement = connection.createStatement();
             PreparedStatement statement = connection.prepareStatement(OrderTemplate.QUERY_DELETE_ORDER)){

            connection.setAutoCommit(false);

            ResultSet resultSet = selectOrderStatement.executeQuery(OrderTemplate.QUERY_SELECT_ORDER_ID);
            if (!resultSet.next()) {
                connection.rollback();
                resultSet.close();
                return null;
            }
            int result = statement.executeUpdate();
            if(result == 0){
                connection.rollback();
                return null;
            }

            order = parseOrderFromDB(resultSet);
            resultSet.close();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("order with id : " + orderId + " deleted");
        return order;
    }

    public Order updateOrder(Order order) {
        log.info("updating order with id: " + order.getOrderId());
        deleteOrder(order.getOrderId());
        addOrder(order);
        log.info("order with id : " + order.getOrderId() + " deleted");
        return order;
    }

    private Order parseOrderFromDB(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getLong(OrderTemplate.COLUMN_ORDER_ID));
        order.setUserId(resultSet.getLong(OrderTemplate.COLUMN_USER_ID));
        order.setStatus(resultSet.getString(OrderTemplate.COLUMN_STATUS));
        order.setProcessedDate(resultSet.getTimestamp(OrderTemplate.COLUMN_PROCESSEDDATE).toLocalDateTime());
        order.setSubmittedDate(resultSet.getTimestamp(OrderTemplate.COLUMN_SUBMITDATE).toLocalDateTime());
        return order;
    }

}
