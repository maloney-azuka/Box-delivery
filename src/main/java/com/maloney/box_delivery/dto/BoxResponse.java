package com.maloney.box_delivery.dto;

import com.maloney.box_delivery.enums.BoxState;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoxResponse {

    private Long id;

    private String trackerId;

    private Double weightLimit;

    private Integer batteryCapacity;

    private BoxState state;
}
