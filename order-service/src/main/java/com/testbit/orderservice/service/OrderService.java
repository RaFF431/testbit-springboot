package com.testbit.orderservice.service;

import com.testbit.orderservice.dto.CreateOrderRequest;
import com.testbit.orderservice.dto.ProductResponse;
import com.testbit.orderservice.entity.Order;
import com.testbit.orderservice.entity.OrderStatus;
import com.testbit.orderservice.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final WebClient webClient;

    public OrderService(OrderRepository repository, WebClient.Builder builder) {
        this.repository = repository;
        this.webClient = builder.baseUrl("http://localhost:8081").build();
    }

    public List<Order> findAll(){
        return repository.findAll();
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) {

        ProductResponse product = webClient.get()
                .uri("/api/products/" + request.getProductId())
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();
        if(product == null){
            throw new RuntimeException("Product not found");
        }

        if(product.getStock() < request.getQuantity()){
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Not enough stock"
            );
        }

        webClient.patch()
                .uri("/api/products/" + request.getProductId() +
                        "/stock?qty=" + request.getQuantity())
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        return repository.save(order);
    }
}
