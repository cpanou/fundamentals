package com.company.eshop.services;

import com.company.eshop.application.MyApplication;
import com.company.eshop.exceptions.ApplicationError;
import com.company.eshop.exceptions.ApplicationException;
import com.company.eshop.model.Cart;
import com.company.eshop.model.Product;
import com.company.eshop.model.User;
import com.company.eshop.repository.CartStore;
import com.company.eshop.repository.ProductRepository;
import com.company.eshop.repository.UserRepository;

import javax.ws.rs.Produces;
import java.util.logging.Logger;

public class CartService {
    private static final Logger log = Logger.getLogger(MyApplication.class.getName());

    //Gets a reference to the Repositories
    private ProductRepository productRepository = ProductRepository.getInstance();

    private CartStore cartStore = CartStore.getInstance();


    public Cart getUserCart(long userId) {
        Cart userCart = cartStore.getCart(userId);
        if (userCart == null || userCart.getProductList().isEmpty())
            throw new ApplicationException(ApplicationError.CART_EMPTY);
        return userCart;
    }


    public Cart addToCart(long userId, Product product) {
        Product product1 = productRepository.getProduct(product.getProductId());
        if (product1 == null)
            throw new ApplicationException(ApplicationError.PRODUCT_NOT_FOUND);
        log.info("Adding product " + product.getProductName() + " to " + userId);
        return cartStore.addToCart(userId, product);
    }

    public Cart deleteFromCart(long userId, long productId) {
        Product product1 = productRepository.getProduct(productId);
        if (product1 == null)
            throw new ApplicationException(ApplicationError.PRODUCT_NOT_FOUND);

        Cart cart = cartStore.removeFromCart(userId, productId);
        if (cart == null)
            throw new ApplicationException(ApplicationError.PRODUCT_NOT_IN_CART);

        if (cart.getProductList().isEmpty())
            throw new ApplicationException(ApplicationError.CART_EMPTY);

        return cart;
    }

}
