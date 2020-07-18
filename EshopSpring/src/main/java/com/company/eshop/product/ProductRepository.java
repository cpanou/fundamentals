package com.company.eshop.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByProductNameContains(String contains);
    List<Product> findAllByCategoryLikeOrTypeLikeOrProductNameLike(String category, String type, String name);
    List<Product> findAllByCategoryContainsIgnoreCaseOrTypeContainsIgnoreCaseOrProductNameContainsIgnoreCase(String category, String type, String name);

}
