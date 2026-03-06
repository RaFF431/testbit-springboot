package com.testbit.orderservice.controller;

import com.testbit.orderservice.command.CreateOrderCommand;
import com.testbit.orderservice.command.OrderCommandInvoker;
import com.testbit.orderservice.dto.CreateOrderRequest;
import com.testbit.orderservice.entity.Order;
import com.testbit.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    private final OrderCommandInvoker invoker;

    public OrderController(OrderService service) {
        this.service = service;
        this.invoker = new OrderCommandInvoker();
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody CreateOrderRequest request) {

        CreateOrderCommand command = new CreateOrderCommand(service, request);

        invoker.executeCommand(command);

        return "Order created successfully";
    }
}
