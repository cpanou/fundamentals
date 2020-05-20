package com.company.eshop.repository;

import com.company.eshop.model.Cart;
import com.company.eshop.model.Product;

import java.util.HashMap;
import java.util.Map;

public class CartStore {
    private static CartStore instance;
    private Map<Long, Cart> cartMap = new HashMap<>();
    private CartStore() {
    }
    public static void init() {
        instance = new CartStore();
    }
    public static CartStore getInstance() {
        return instance;
    }
    public Cart getCart(long id) {
        return cartMap.get(id);
    }

    public Cart addToCart(long id, Product product) {
        if (cartMap.containsKey(id)) {
            cartMap.get(id)//Cart object
                    .getProductList()//cart Product List
                    .add(product);
            return cartMap.get(id);
        }
        Cart newCart = new Cart();
        newCart.getProductList().add(product);
        cartMap.put(id, newCart);
        return cartMap.get(id);
    }

    public Cart removeFromCart(long id, long productId) {
        Cart cart = cartMap.get(id);
        if (cart == null)
            return null;
        cart.getProductList().removeIf(product -> product.getProductId() == productId);
        return cart;
    }

    public void clearCart(long id) {
        cartMap.get(id).getProductList().clear();
    }
}
