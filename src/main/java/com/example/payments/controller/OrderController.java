package com.example.payments.controller;



import com.example.payments.dto.OrderRequest;
import com.example.payments.entity.Order;
import com.example.payments.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. CREATE ORDER
    @PostMapping("/order")
    public Order createOrder(
            @RequestBody OrderRequest request
    ) {
        return orderService.createOrder(request);
    }

}
