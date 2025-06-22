package com.tastytown.backend.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tastytown.backend.dto.CartItemRequestDTO;
import com.tastytown.backend.dto.CartResponseDTO;
import com.tastytown.backend.entity.Cart;
import com.tastytown.backend.entity.CartItem;
import com.tastytown.backend.mapper.CartMapper;
import com.tastytown.backend.repository.CartRepository;
import com.tastytown.backend.repository.FoodRepository;
import com.tastytown.backend.repository.UserRepository;
import com.tastytown.backend.service.ICartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Override
    public CartResponseDTO addItemToCart(String userId, CartItemRequestDTO cartItemRequestDTO) {
        var user = userRepository.findById(userId).orElseThrow();

        var cart = cartRepository.findByUser(user).orElseGet(
                () -> {
                    var newCart = Cart.builder().user(user).build();
                    return cartRepository.save(newCart);
                });

        var food = foodRepository.findById(cartItemRequestDTO.foodId()).orElseThrow();

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

    // @Override
    // public CartResponseDTO getCartByUserId(String userId) {

    // }

    // @Override
    // public CartResponseDTO updateItemQuantity(String userId, CartItemRequestDTO
    // cartItemRequestDTO) {

    // }

    // @Override
    // public CartResponseDTO removeItemFromCart(String userId, String foodId) {

    // }

    // @Override
    // public CartResponseDTO clearCartItem(String userId) {

    // }

}
