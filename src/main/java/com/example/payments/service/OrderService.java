package com.example.payments.service;



import com.example.payments.dto.OrderRequest;
import com.example.payments.entity.Order;
import com.example.payments.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    //helper function
    public Order DTOToEtityConversion (OrderRequest orderRequest) {
        /*
    ORDER REQ DTO(INPUT) -> ORDER
    */
    Order order = new Order();
    order.setUserId(orderRequest.userId());
    return order;
    }

    // CREATE ORDER + CREATE PAYMENT WITH PENDING
    public Order createOrder(OrderRequest req) {
        var order = DTOToEtityConversion(req);
        orderRepo.save(order);
        return order;
    }

//    public Order getOrderById(Long id) {
//        return orderRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//    }
//
//    public String cancelOrder(Long id) {
//
//        Order order = getOrderById(id);
//
//        order.setStatus(OrderStatus.CANCELLED);
//        orderRepo.save(order);
//
//        return "Order Cancelled";
//    }
}

