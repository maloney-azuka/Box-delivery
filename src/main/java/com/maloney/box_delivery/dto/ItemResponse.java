package com.maloney.box_delivery.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponse {

    private Long id;

    private String name;

    private Double weight;

    private String code;
}
