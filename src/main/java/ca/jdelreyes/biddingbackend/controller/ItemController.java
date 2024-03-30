package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.item.CreateItemRequest;
import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.dto.item.UpdateItemRequest;
import ca.jdelreyes.biddingbackend.service.item.ItemServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@EnableMethodSecurity
public class ItemController {
    private final ItemServiceImpl itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getItems() {
        return ResponseEntity.ok(itemService.getItems());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@Valid @RequestBody CreateItemRequest createItemRequest) {
        ItemResponse itemResponse = itemService.createItem(createItemRequest);

        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @PutMapping("{itemId}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable("itemId") Integer id, @RequestBody UpdateItemRequest updateItemRequest) {
        ItemResponse itemResponse = itemService.updateItem(id, updateItemRequest);

        return ResponseEntity.ok(itemResponse);
    }

    @DeleteMapping("{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable("itemId") Integer id) {
        itemService.deleteItem(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
