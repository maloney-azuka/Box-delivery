package com.maloney.box_delivery.service;


import com.maloney.box_delivery.dto.*;
import com.maloney.box_delivery.entity.Box;
import com.maloney.box_delivery.entity.Item;
import com.maloney.box_delivery.enums.BoxState;
import com.maloney.box_delivery.exception.BadRequestException;
import com.maloney.box_delivery.exception.ResourceNotFoundException;
import com.maloney.box_delivery.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.maloney.box_delivery.repository.BoxRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoxService {

    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;

    public BoxResponse createBox(CreateBoxRequest request) {

        if (boxRepository.findByTrackerId(request.getTrackerId()).isPresent()) {
            throw new BadRequestException("A box with this tracker ID already exists");
        }

        Box box = Box.builder()
                .trackerId(request.getTrackerId())
                .weightLimit(request.getWeightLimit())
                .batteryCapacity(request.getBatteryCapacity())
                .state(BoxState.IDLE)
                .build();

        Box savedBox = boxRepository.save(box);

        return mapToResponse(savedBox);
    }

    @Transactional
    public List<ItemResponse> loadItems(
            Long boxId,
            List<AddItemRequest> requests) {

        Box box = boxRepository.findById(boxId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Box with ID " + boxId + " not found"
                        )
                );


        if (box.getBatteryCapacity() < 25) {
            throw new BadRequestException(
                    "Box battery must be at least 25% before loading items"
            );
        }

        if (box.getState() != BoxState.IDLE
                && box.getState() != BoxState.LOADING) {

            throw new BadRequestException(
                    "Items can only be loaded into an IDLE or LOADING box"
            );
        }

        // weight of box items
        double currentWeight = box.getItems()
                .stream()
                .mapToDouble(Item::getWeight)
                .sum();

        //weight of new items
        double newItemsWeight = requests.stream()
                .mapToDouble(AddItemRequest::getWeight)
                .sum();

        // Check weight limit
        if (currentWeight + newItemsWeight > box.getWeightLimit()) {
            throw new BadRequestException(
                    "Items exceed the box weight limit"
            );
        }

        Set<String> itemCodes = new HashSet<>();

        for (AddItemRequest request : requests) {

            if (!itemCodes.add(request.getCode())) {
                throw new BadRequestException(
                        "Duplicate item code found in the request: "
                                + request.getCode()
                );
            }

            // To Check if code already exists in the database
            if (itemRepository.existsByCode(request.getCode())) {
                throw new BadRequestException(
                        "Item with code " + request.getCode()
                                + " already exists"
                );
            }
        }

        List<Item> items = requests.stream()
                .map(request -> Item.builder()
                        .name(request.getName())
                        .weight(request.getWeight())
                        .code(request.getCode())
                        .box(box)
                        .build()
                )
                .toList();

        List<Item> savedItems = itemRepository.saveAll(items);

        box.setState(BoxState.LOADED);
        boxRepository.save(box);


        return savedItems.stream()
                .map(this::mapToItemResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getBoxItems(Long boxId) {

        if (!boxRepository.existsById(boxId)) {
            throw new ResourceNotFoundException(
                    "Box with ID " + boxId + " not found"
            );
        }

        List<Item> items = itemRepository.findByBoxId(boxId);

        return items.stream()
                .map(this::mapToItemResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BoxResponse> getAvailableBoxes() {

        List<Box> boxes =
                boxRepository.findByStateAndBatteryCapacityGreaterThanEqual(
                        BoxState.IDLE,
                        25
                );

        return boxes.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BatteryResponse getBoxBattery(Long boxId) {

        Box box = boxRepository.findById(boxId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Box with ID " + boxId + " not found"
                        )
                );

        return BatteryResponse.builder()
                .boxId(box.getId())
                .trackerId(box.getTrackerId())
                .batteryCapacity(box.getBatteryCapacity())
                .build();
    }

    @Transactional(readOnly = true)
    public List<BoxResponse> getAllBoxes() {

        return boxRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BoxResponse getBoxById(Long boxId) {

        Box box = boxRepository.findById(boxId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Box with ID " + boxId + " not found"
                        )
                );

        return mapToResponse(box);
    }

    private ItemResponse mapToItemResponse(Item item) {

        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .weight(item.getWeight())
                .code(item.getCode())
                .build();
    }

    private BoxResponse mapToResponse(Box box) {

        return BoxResponse.builder()
                .id(box.getId())
                .trackerId(box.getTrackerId())
                .weightLimit(box.getWeightLimit())
                .batteryCapacity(box.getBatteryCapacity())
                .state(box.getState())
                .build();
    }

}
