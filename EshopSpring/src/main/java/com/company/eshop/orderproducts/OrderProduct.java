package com.company.eshop.orderproducts;


import com.company.eshop.order.Order;
import com.company.eshop.product.Product;


//The Order has a Many to Many Relationship with the Products
//The OrderProduct Entity is used to represent the relationship as an entity
//As in the E-R Diagram the OrderProduct Entity transforms the ManyToMany relationship
//in a one to Many relationship with each other entity.

//      Order <---ManyToMany---> Products

//      Order --OneToMany--> OrderProduct
//      Product --OneToMany--> Product

//Annotate the OrderProduct Class
//(1) Make the OrderProduct an Entity
//(2) Provide Setters, Getters and Constructors
//(3) set an Id column with a generated key
public class OrderProduct {

    private Long id;

    //(4) Map The relationship with the Order class
    //Changes in the Order class:
        //no longer has an List<Product> but a List<OrderProduct>
    //Changes in the Service class: the List of products provided needs
        // to be mapped to a List of OrderProducts before saving the new Order in the database
        // The List of OrderProducts needs to have an entry for each Product provided and the new order
    private Order order;

    //(5) Map The relationship with the product class
    //Change the Product class Accordingly
        //no longer has an List<Order> but a List<OrderProduct>
    private Product product;

}
