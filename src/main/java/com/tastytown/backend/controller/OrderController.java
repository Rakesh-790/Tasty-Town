package com.tastytown.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tastytown.backend.constants.OrderStatus;
import com.tastytown.backend.dto.BillingInfoDTO;
import com.tastytown.backend.dto.OrderDTO;
import com.tastytown.backend.service.IOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final IOrderService iOrderService;

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@RequestAttribute String userId,
            @RequestBody BillingInfoDTO billingInfo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iOrderService.placeOrder(billingInfo, userId));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(iOrderService.getAllOrders());
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@RequestAttribute String userId) {
        return ResponseEntity.ok(iOrderService.getOrdersByUsers(userId));
    }

    @PutMapping("/{orderCode}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable String orderCode, @RequestParam OrderStatus status) {
        return ResponseEntity.ok(iOrderService.updateOrderStatus(orderCode, status));
    }
}
