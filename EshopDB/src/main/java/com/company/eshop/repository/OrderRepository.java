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
    public List<Order> getOrders(){
        log.info("fetching all orders");
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseUtils.createConnection();
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

    public Order getOrder(long orderId) {
        Order order = null;
        log.info("fetching order with id: " + orderId);
        try (Connection connection = DatabaseUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(OrderTemplate.QUERY_SELECT_ORDER_ID)){

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
               order = parseOrderFromDB(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("order with id : " + orderId + " not found");
        return order;
    }

    public Order addOrder(Order order) {
        log.info("Creating order: " + order.getOrderId());
        try (Connection connection = DatabaseUtils.createConnection();
             PreparedStatement statement = connection.prepareStatement(OrderTemplate.QUERY_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement orderProductsStatement = connection.prepareStatement(OrderProductTemplate.QUERY_INSERT_ORDER_PRODUCTS)) {

            connection.setAutoCommit(false);

            statement.setLong(1, order.getUserId());
            statement.setString(2, order.getStatus().name());
            statement.setTimestamp(3, Timestamp.valueOf(order.getSubmittedDate()));

            int result = statement.executeUpdate();
            if( result == 0 ){
                connection.rollback();
                return null;
            }
            ResultSet keySet = statement.getGeneratedKeys();
            if(!keySet.next()) {
                keySet.close();
                connection.rollback();
                return null;
            }

            order.setOrderId(keySet.getLong(1));
            keySet.close();

            for(Product product : order.getProducts() ) {
                orderProductsStatement.setLong(1, order.getOrderId());
                orderProductsStatement.setLong(2, product.getProductId());
                orderProductsStatement.addBatch();
            }
            int[] batchResult = orderProductsStatement.executeBatch();
            for ( int res : batchResult) {
                if (res == 0){
                    connection.rollback();
                    return null;
                }
            }
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
        try (Connection connection = DatabaseUtils.createConnection();
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
