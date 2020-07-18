package com.company.eshop.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getProductList(@RequestParam(value = "search", required = false)String search){
        if( search != null )
            return ResponseEntity.ok(service.searchProducts(search));
        return ResponseEntity.ok(service.getProducts());
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addProducts(@RequestBody List<Product> products) {
        return ResponseEntity.ok(service.addProducts(products));
    }
}
