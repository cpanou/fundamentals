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
        return products;
    }

    public Product addProduct(Product product) {
        log.info("Creating product: " + product.getProductName());
        product.setProductId(getNewProductId());
        products.add(product);
        log.info("Product " + product.getProductName() + " created");
        return product;
    }

    public Product getProduct(long productId) {
        log.info("fetching product with id: " + productId);
        for( Product product: products) {
            if( product.getProductId() == productId)
                return product;
        }
        log.info("product with id : " + productId + " not found");
        return null;
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
