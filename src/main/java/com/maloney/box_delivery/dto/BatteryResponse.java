package com.maloney.box_delivery.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BatteryResponse {

    private Long boxId;

    private String trackerId;

    private Integer batteryCapacity;
}
