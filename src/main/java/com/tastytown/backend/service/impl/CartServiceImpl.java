package com.tastytown.backend.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tastytown.backend.Helper.CartServiceHelper;
import com.tastytown.backend.Helper.FoodServiceHelper;
import com.tastytown.backend.Helper.UserServiceHelper;
import com.tastytown.backend.dto.CartItemRequestDTO;
import com.tastytown.backend.dto.CartResponseDTO;
import com.tastytown.backend.entity.CartItem;
import com.tastytown.backend.mapper.CartMapper;
import com.tastytown.backend.repository.CartRepository;
import com.tastytown.backend.service.ICartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final UserServiceHelper userServiceHelper;
    private final CartServiceHelper cartServiceHelper;
    private final FoodServiceHelper foodServiceHelper;

    @Override
    public CartResponseDTO addItemToCart(String userId, CartItemRequestDTO cartItemRequestDTO) {
        var user = userServiceHelper.getUserById(userId);

        var cart = cartServiceHelper.getOrCreateCartForUser(user);

        var food = foodServiceHelper.getFoodById(cartItemRequestDTO.foodId());

        // check if item already exists in the cart
        Optional<CartItem> exitstingCartItemOpt = cart.getItems().stream()
                .filter(item -> item.getFood().getFoodId().equals(food.getFoodId())).findFirst();

        if (exitstingCartItemOpt.isPresent()) {
            // update quantity if that is present
            CartItem existingCartItem = exitstingCartItemOpt.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequestDTO.quantity());
        } else {
            // create a new cart item if not exist
            CartItem newCartItem = CartItem.builder()
                    .cart(cart)
                    .food(food)
                    .quantity(cartItemRequestDTO.quantity())
                    .build();
            cart.getItems().add(newCartItem);
        }
        var savedCart = cartRepository.save(cart);
        return CartMapper.convertToCartResponseDTO(savedCart);
    }

    @Override
    public CartResponseDTO getCartByUserId(String userId) {
        var user = userServiceHelper.getUserById(userId);
        var cartOfUser = cartServiceHelper.getOrCreateCartForUser(user);
        return CartMapper.convertToCartResponseDTO(cartOfUser);
    }

    @Override
    public CartResponseDTO updateItemQuantity(String userId, CartItemRequestDTO cartItemRequestDTO) {

        var user = userServiceHelper.getUserById(userId);
        var cart = cartServiceHelper.getOrCreateCartForUser(user);

        var cartItem = cartServiceHelper.getMatchedCartItemOfAnUser(cart, cartItemRequestDTO.foodId());

        if (cartItemRequestDTO.quantity() <= 0) {
            cart.getItems().remove(cartItem);
        } else {
            cartItem.setQuantity(cartItemRequestDTO.quantity());
        }
        var savedCart = cartRepository.save(cart);
        return CartMapper.convertToCartResponseDTO(savedCart);
    }

    @Override
    public CartResponseDTO removeItemFromCart(String userId, String foodId) { // Delete cart.
        var user = userServiceHelper.getUserById(userId);
        var cart = cartServiceHelper.getOrCreateCartForUser(user);
        var cartItem = cartServiceHelper.getMatchedCartItemOfAnUser(cart, foodId);

        cart.getItems().remove(cartItem);

        var savedCart = cartRepository.save(cart);
        return CartMapper.convertToCartResponseDTO(savedCart);
    }

    @Override
    public void clearCartItem(String userId) { // clear the cart item after the order.
        var user = userServiceHelper.getUserById(userId);
        cartRepository.deleteByUser(user);
    }
}
