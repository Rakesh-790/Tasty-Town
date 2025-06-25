package com.tastytown.backend.Helper;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import com.tastytown.backend.entity.Cart;
import com.tastytown.backend.entity.CartItem;
import com.tastytown.backend.entity.User;
import com.tastytown.backend.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CartServiceHelper {
    private final CartRepository cartRepository;

    public Cart getCartByUser(User user){
        return cartRepository.findByUser(user).orElseThrow(() -> new NoSuchElementException("cart not found with user"));
    }

    public Cart getOrCreateCartForUser(User user) {
        return cartRepository.findByUser(user).orElseGet(
                () -> {
                    var newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    public CartItem getMatchedCartItemOfAnUser(Cart cart, String foodId) {
        return cart.getItems().stream()
                .filter(item -> item.getFood().getFoodId().equals(foodId)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Food not found in the Cart"));
    }
}
