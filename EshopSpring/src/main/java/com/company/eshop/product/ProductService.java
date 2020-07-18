package com.company.eshop.product;


import org.apache.catalina.startup.Catalina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;


    public List<Product> getProducts() {
        return repository.findAll();
    }

    public List<Product> addProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    public List<Product> searchProducts(String search) {
        return repository.findAllByCategoryContainsIgnoreCaseOrTypeContainsIgnoreCaseOrProductNameContainsIgnoreCase(search, search, search);
    }
}
