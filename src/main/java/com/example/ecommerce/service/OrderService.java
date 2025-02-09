package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.payload.request.OrderDTO;
import com.example.ecommerce.payload.response.order.OrderResponse;
import com.example.ecommerce.payload.response.order.OrderItemResponse;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(orderDTO.getStatus() != null ? orderDTO.getStatus() : "PENDING");

        Set<OrderItem> orderItems = orderDTO.getItems().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toSet());

        order.setOrderItems(orderItems);
        order.setTotalAmount(orderItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum());

        Order savedOrder = orderRepository.save(order);
        return convertToOrderResponse(savedOrder);
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return orderRepository.findByUser(user).stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return convertToOrderResponse(order);
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(status);
        return convertToOrderResponse(orderRepository.save(order));
    }

    private OrderResponse convertToOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .username(order.getUser().getUsername())
                .items(order.getOrderItems().stream()
                        .map(this::convertToOrderItemResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private OrderItemResponse convertToOrderItemResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getProduct().getPrice())
                .build();
    }
}