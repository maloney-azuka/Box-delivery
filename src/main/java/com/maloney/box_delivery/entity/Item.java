package com.maloney.box_delivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Item name is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9_-]+$",
            message = "Name can only contain letters, numbers, hyphens and underscores"
    )
    @Column(nullable = false)
    private String name;

    @Min(value = 1, message = "Weight must be greater than 0")
    @Column(nullable = false)
    private Double weight;

    @NotBlank(message = "Item code is required")
    @Pattern(
            regexp = "^[A-Z0-9_]+$",
            message = "Code can only contain uppercase letters, numbers and underscores"
    )
    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    @JsonIgnore
    private Box box;
}
