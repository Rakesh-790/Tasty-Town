package com.tastytown.backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tastytown.backend.Helper.CartServiceHelper;
import com.tastytown.backend.Helper.UserServiceHelper;
import com.tastytown.backend.constants.OrderStatus;
import com.tastytown.backend.dto.BillingInfoDTO;
import com.tastytown.backend.dto.OrderDTO;
import com.tastytown.backend.service.IOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService{
    private final UserServiceHelper userServiceHelper;
    private final CartServiceHelper cartServiceHelper;

    // @Override
    // public OrderDTO placeOrder(BillingInfoDTO info, String userId) {
    //     var user = helper.getUserById(userId);
    //     var cart = cartServiceHelper.getCartByUser(user);

    //     if (cart.getItems().isEmpty()) {
    //         throw new RuntimeException("Cart is empty");
    //     }
    // }

    @Override
    public List<OrderDTO> getOrdersByUsers(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrdersByUsers'");
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
    }

    @Override
    public OrderDTO updateOrderStatus(String orderCode, OrderStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderStatus'");
    }

    // private Order createOrderFromCart(Cart cart, BillingInfoDTO billingInfo, User user){
    //     var order = new Order();
    //     order.setUser(user);
    //     order.setOrderDateTime(LocalDateTime.now());

    // }
}
