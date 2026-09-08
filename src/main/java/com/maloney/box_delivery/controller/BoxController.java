package com.maloney.box_delivery.controller;

import com.maloney.box_delivery.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.maloney.box_delivery.service.BoxService;

import java.util.List;

@RestController
@RequestMapping("/api/boxes")
@RequiredArgsConstructor
public class BoxController {

    private final BoxService boxService;

    @PostMapping
    public ResponseEntity<BoxResponse> createBox(
            @Valid @RequestBody CreateBoxRequest request) {

        BoxResponse response = boxService.createBox(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/{boxId}/items")
    public ResponseEntity<List<ItemResponse>> loadItems(
            @PathVariable Long boxId,
            @Valid
            @NotEmpty(message = "At least one item is required")
            @RequestBody List<@Valid AddItemRequest> requests) {

        List<ItemResponse> response =
                boxService.loadItems(boxId, requests);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{boxId}/items")
    public ResponseEntity<List<ItemResponse>> getBoxItems(
            @PathVariable Long boxId) {

        return ResponseEntity.ok(
                boxService.getBoxItems(boxId)
        );
    }

    @GetMapping("/available")
    public ResponseEntity<List<BoxResponse>> getAvailableBoxes() {

        return ResponseEntity.ok(
                boxService.getAvailableBoxes()
        );
    }

    @GetMapping("/{boxId}/battery")
    public ResponseEntity<BatteryResponse> getBoxBattery(
            @PathVariable Long boxId) {

        return ResponseEntity.ok(
                boxService.getBoxBattery(boxId)
        );
    }

    @GetMapping
    public ResponseEntity<List<BoxResponse>> getAllBoxes() {

        return ResponseEntity.ok(
                boxService.getAllBoxes()
        );
    }

    @GetMapping("/{boxId}")
    public ResponseEntity<BoxResponse> getBoxById(
            @PathVariable Long boxId) {

        return ResponseEntity.ok(
                boxService.getBoxById(boxId)
        );
    }
}