package com.maloney.box_delivery.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBoxRequest {

    @NotBlank(message = "Tracker ID is required")
    @Size(max = 20, message = "Tracker ID cannot exceed 20 characters")
    private String trackerId;

    @NotNull(message = "Weight limit is required")
    @Min(value = 1, message = "Weight limit must be at least 1g")
    @Max(value = 500, message = "Weight limit cannot exceed 500g")
    private Double weightLimit;

    @NotNull(message = "Battery capacity is required")
    @Min(value = 0, message = "Battery capacity cannot be less than 0")
    @Max(value = 100, message = "Battery capacity cannot exceed 100")
    private Integer batteryCapacity;

}
