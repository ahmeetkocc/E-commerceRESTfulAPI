package com.example.ecommerce.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class OrderItemDTO {
    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;
}
