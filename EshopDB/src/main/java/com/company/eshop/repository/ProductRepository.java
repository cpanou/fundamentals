package com.company.eshop.repository;

import com.company.eshop.application.MyApplication;
import com.company.eshop.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProductRepository {
    private static final Logger log = Logger.getLogger(MyApplication.class.getName());
    //Singleton Pattern
    private static ProductRepository instance;

    private List<Product> products = new ArrayList<>();

    private ProductRepository(){
    }

    public static void init(){
        instance = new ProductRepository();
        instance.products.add(new Product( instance.getNewProductId(),"Xiaomi 1 ", 200));
        instance.products.add(new Product( instance.getNewProductId(),"Xiaomi 2 ", 300));
        instance.products.add(new Product( instance.getNewProductId(),"Xiaomi 3 ", 400));
        instance.products.add(new Product( instance.getNewProductId(),"Xiaomi 4 ", 500));
    }

    public static ProductRepository getInstance() {
        return instance;
    }
    //END Singleton


    private long getNewProductId() {
        return products.size() + 1;
    }

    /*
    * CRUD OPERATIONS
    *
    * CREATE -> add
    * READ -> get
    * UPDATE -> get -> edit
    * DELETE -> remove
    *
    * */

    public List<Product> getProducts(){
        log.info("fetching all products");
        //(1) Establish Connection using the DataBaseUtils.

        //(2) Create a Statement object to execute a query from the template.
        // The query we must use needs to return all products from the database.

        //(3) execute the query and get the ResultSet.

        //(4) parse the result set to a List of products.

        //(5) Handle the resources with the try with resources block
        return products;
    }

    public Product getProduct(long productId) {
        log.info("fetching product with id: "+ productId);
        //(1) Establish Connection using the DataBaseUtils.

        //(2) Create a PreparedStatement object to execute a query.
        // The query we must return a product from the database based on its productId.

        //(3) Pass the productId to the statement.

        //(4) execute the query and get the result.

        //(4) parse the result set to a product.

        //(6) Handle the resources with the try with resources block
        return null;
    }

    public Product addProduct(Product product) {
        log.info("Creating a Product");
        //(1) Establish Connection using the DataBaseUtils.

        //(2) Create a PreparedStatement object to execute a query.
        // The query we must use needs to insert a new product.

        //(3) Pass the product attributes to the statement, in the correct positions.

        //(4) execute the query and get the result.

        //(5) check the result for any possible failures.

        //(6) retrieve the newly created product.
        // we need to execute a select query with the product id generated in the database
        // hints - statement.getGeneratedKeys(), another statement to execute the select

        //(7) Handle the resources with the try with resources block
        return product;
    }

    public Product deleteProduct(long productId) {
        log.info("Deleting user with id: " + productId);
        Product product = getProduct(productId);
        if(product == null) return null;
        products.remove(product);
        log.info("product with id : " + productId + " deleted");
        return product;
    }


}
