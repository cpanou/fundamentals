package com.company.eshop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> productList;

    public Cart() {
        productList = new ArrayList<>();
    }

    public Cart(List<Product> products) {
        this.productList = products;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

}
