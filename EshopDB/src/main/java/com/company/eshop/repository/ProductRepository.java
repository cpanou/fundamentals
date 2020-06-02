package com.company.eshop.repository;

import com.company.eshop.application.MyApplication;
import com.company.eshop.model.Product;
import com.company.eshop.repository.templates.ProductTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProductRepository {
    private static final Logger log = Logger.getLogger(MyApplication.class.getName());
    //Singleton Pattern
    private static ProductRepository instance;

    private ProductRepository() {
    }

    public static void init() {
        instance = new ProductRepository();
    }

    public static ProductRepository getInstance() {
        return instance;
    }
    //END Singleton

    /*
     * CRUD OPERATIONS
     *
     * CREATE -> add
     * READ -> get
     * UPDATE -> get -> edit
     * DELETE -> remove
     *
     * */
    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        log.info("fetching all products");
        try (Connection connection = DataBaseUtils.createConnection();
             Statement selectProducts = connection.createStatement()) {

            ResultSet resultSet = selectProducts.executeQuery(ProductTemplate.QUERY_SELECT_ALL_PRODUCTS);
            while (resultSet.next()) {
                Product product = parseProductFromDb(resultSet);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Product getProduct(long productId) {
        Product product = null;
        log.info("fetching product with id: " + productId);
        try (Connection connection = DataBaseUtils.createConnection();
             PreparedStatement selectProduct = connection.prepareStatement(ProductTemplate.QUERY_SELECT_PRODUCT_ID)) {

            selectProduct.setLong(1, productId);
            ResultSet resultSet = selectProduct.executeQuery();
            while (resultSet.next()) {
                product = parseProductFromDb(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public Product addProduct(Product product) {
        log.info("Creating a Product");
        try (Connection connection = DataBaseUtils.createConnection();
             PreparedStatement insertProduct = connection.prepareStatement(ProductTemplate.QUERY_INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement selectProduct = connection.prepareStatement(ProductTemplate.QUERY_SELECT_PRODUCT_ID)) {

            insertProduct.setString(1, product.getProductName());
            insertProduct.setDouble(2, product.getPrice());

            int result = insertProduct.executeUpdate();
            if (result != 1)
                return null;
            ResultSet keySet = insertProduct.getGeneratedKeys();
            if (!keySet.next())
                return null;

            long productId = keySet.getLong(1);
            keySet.close();

            selectProduct.setLong(1, productId);
            ResultSet resultSet = selectProduct.executeQuery();
            while (resultSet.next()) {
                product = parseProductFromDb(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    private Product parseProductFromDb(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setProductName(resultSet.getString(ProductTemplate.COLUMN_PRODUCT_NAME));
        product.setProductId(resultSet.getLong(ProductTemplate.COLUMN_PRODUCT_ID));
        product.setPrice(resultSet.getDouble(ProductTemplate.COLUMN_PRICE));
        return product;
    }

    public Product deleteProduct(long productId) {
        log.info("Deleting user with id: " + productId);
        Product product = getProduct(productId);
        if (product == null) return null;
        log.info("product with id : " + productId + " deleted");
        return product;
    }

}
