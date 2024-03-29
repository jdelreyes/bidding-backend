package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.service.item.ItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class ItemController {
    private final ItemServiceImpl itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getItems() {
        return ResponseEntity.ok(itemService.getItems());
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem() {
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ItemResponse> updateItem() {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteItem() {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
