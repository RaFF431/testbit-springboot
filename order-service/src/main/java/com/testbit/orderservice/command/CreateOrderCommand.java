package com.testbit.orderservice.command;

import com.testbit.orderservice.dto.CreateOrderRequest;
import com.testbit.orderservice.service.OrderService;

public class CreateOrderCommand implements OrderCommand{
    private final OrderService service;
    private final CreateOrderRequest request;

    public CreateOrderCommand(OrderService service, CreateOrderRequest request) {
        this.service = service;
        this.request = request;
    }

    @Override
    public void execute(){
        service.createOrder(request);
    }
}
