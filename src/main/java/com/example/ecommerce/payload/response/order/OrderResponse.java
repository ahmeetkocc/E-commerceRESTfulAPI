package com.example.ecommerce.payload.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String status;
    private String username;
    private List<OrderItemResponse> items;
}
