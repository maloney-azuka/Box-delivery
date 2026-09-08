package com.maloney.box_delivery.entity;

import com.maloney.box_delivery.enums.BoxState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boxes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tracker ID is required")
    @Size(max = 20, message = "Tracker ID cannot exceed 20 characters")
    @Column(nullable = false, unique = true, length = 20)
    private String trackerId;

    @Min(value = 1, message = "Weight limit must be greater than 0")
    @Max(value = 500, message = "Weight limit cannot exceed 500g")
    @Column(nullable = false)
    private Double weightLimit;

    @Min(value = 0, message = "Battery capacity cannot be less than 0")
    @Max(value = 100, message = "Battery capacity cannot exceed 100")
    @Column(nullable = false)
    private Integer batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoxState state;

    @OneToMany(
            mappedBy = "box",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Item> items = new ArrayList<>();
}
