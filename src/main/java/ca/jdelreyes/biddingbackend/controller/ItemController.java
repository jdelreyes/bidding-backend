package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.item.CreateItemRequest;
import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.dto.item.UpdateItemRequest;
import ca.jdelreyes.biddingbackend.exception.CategoryNotFoundException;
import ca.jdelreyes.biddingbackend.exception.ItemNotFoundException;
import ca.jdelreyes.biddingbackend.exception.UserNotFoundException;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.service.impl.ItemServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
@EnableMethodSecurity
public class ItemController {
    private final ItemServiceImpl itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getItems() {
        return ResponseEntity.ok(itemService.getItems());
    }

    @GetMapping("{itemId}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable("itemId") Integer id) throws ItemNotFoundException {
        return ResponseEntity.ok(itemService.getItem(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createItem(@AuthenticationPrincipal User user,
                                        @Valid @RequestBody CreateItemRequest createItemRequest)
            throws UserNotFoundException, CategoryNotFoundException {
        return new ResponseEntity<>(itemService.createItem(user.getId(), createItemRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/{itemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable("itemId") Integer id, @RequestBody UpdateItemRequest updateItemRequest) throws ItemNotFoundException {
        return ResponseEntity.ok(itemService.updateItem(id, updateItemRequest));
    }

    @PutMapping("/update-item/{itemId}")
    public ResponseEntity<ItemResponse> updateOwnItem(@AuthenticationPrincipal User user,
                                                      @PathVariable("itemId") Integer id,
                                                      UpdateItemRequest updateItemRequest) throws Exception {

        return ResponseEntity.ok(itemService.updateOwnItem(user.getId(), id, updateItemRequest));
    }

    @DeleteMapping("/{itemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteItem(@PathVariable("itemId") Integer id) throws Exception {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete-item/{itemId}")
    public ResponseEntity<?> deleteOwnItem(@AuthenticationPrincipal User user,
                                           @PathVariable("itemId") Integer id) throws Exception {
        itemService.deleteOwnItem(user.getId(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
