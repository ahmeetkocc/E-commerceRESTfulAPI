package com.example.ecommerce.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    @NotNull
    private Long userId;

    private List<OrderItemDTO> items;

    private String status;
}
