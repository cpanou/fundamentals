package com.company.eshop.product;


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
}
