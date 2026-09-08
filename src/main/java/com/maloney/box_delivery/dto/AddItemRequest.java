package com.maloney.box_delivery.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemRequest {

    @NotBlank(message = "Item name is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9_-]+$",
            message = "Name can only contain letters, numbers, hyphens and underscores"
    )
    private String name;

    @NotNull(message = "Item weight is required")
    @Min(value = 1, message = "Item weight must be greater than 0")
    private Double weight;

    @NotBlank(message = "Item code is required")
    @Pattern(
            regexp = "^[A-Z0-9_]+$",
            message = "Code can only contain uppercase letters, numbers and underscores"
    )
    private String code;
}
