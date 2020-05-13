package com.company.eshop.services;

import com.company.eshop.application.MyApplication;
import com.company.eshop.model.Product;
import com.company.eshop.repository.ProductRepository;

import java.util.List;
import java.util.logging.Logger;

public class ProductService {
    private static final Logger log = Logger.getLogger(ProductService.class.getSimpleName());

    private ProductRepository repository = ProductRepository.getInstance();

    public List<Product> getProducts() {
        return repository.getProducts();
    }

    public Product createProduct(Product product) {
        return repository.addProduct(product);
    }

    public Product getProduct(long productId) {
        return repository.getProduct(productId);
    }


}
