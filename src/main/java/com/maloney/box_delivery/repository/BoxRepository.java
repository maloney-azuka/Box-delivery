package com.maloney.box_delivery.repository;


import com.maloney.box_delivery.entity.Box;
import com.maloney.box_delivery.enums.BoxState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoxRepository extends JpaRepository<Box, Long> {

    Optional<Box> findByTrackerId(String trackerId);

    List<Box> findByState(BoxState state);

    List<Box> findByStateAndBatteryCapacityGreaterThanEqual(
            BoxState state,
            Integer batteryCapacity
    );
}