package com.example.ecommerce.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class ProductDTO {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Positive
    private Double price;

    private String imageUrl;
}
