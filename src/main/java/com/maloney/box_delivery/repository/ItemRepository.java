package com.maloney.box_delivery.repository;


import com.maloney.box_delivery.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByBoxId(Long boxId);

    boolean existsByCode(String code);
}