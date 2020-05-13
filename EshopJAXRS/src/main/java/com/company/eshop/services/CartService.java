package com.company.eshop.services;

import com.company.eshop.application.MyApplication;
import com.company.eshop.exceptions.ApplicationError;
import com.company.eshop.exceptions.ApplicationException;
import com.company.eshop.model.Cart;
import com.company.eshop.model.Product;
import com.company.eshop.model.User;
import com.company.eshop.repository.ProductRepository;
import com.company.eshop.repository.UserRepository;

import java.util.logging.Logger;

public class CartService {
    private static final Logger log = Logger.getLogger(MyApplication.class.getName());

    //Gets a reference to the Repositories
    private UserRepository userRepository = UserRepository.getInstance();
    private ProductRepository productRepository = ProductRepository.getInstance();


    public Cart getUserCart(long userId) {
        User user = userRepository.getUser(userId);
        if(user.getCart().getProductList().isEmpty())
            throw new ApplicationException(ApplicationError.CART_EMPTY);
        return user.getCart();
    }


    public Cart addToCart(long userId, Product product) {
        User user = userRepository.getUser(userId);

        log.info("Adding product " + product.getProductName() + " to " + user.getUsername());
        user.getCart().getProductList().add(product);

        return user.getCart();
    }

    public Cart deleteFromCart(long userId, long productId) {
        User user = userRepository.getUser(userId);
        Product product1 = productRepository.getProduct(productId);
        if (product1 == null)
            throw new ApplicationException(ApplicationError.PRODUCT_NOT_FOUND);

        for (Product product : user.getCart().getProductList()) {
            if (product.getProductId() == productId) {
                user.getCart().getProductList().remove(product);
                return user.getCart();
            }
        }
        throw new ApplicationException(ApplicationError.PRODUCT_NOT_IN_CART);
    }

}
