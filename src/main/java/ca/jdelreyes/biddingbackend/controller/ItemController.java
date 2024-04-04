package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.item.CreateItemRequest;
import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.dto.item.UpdateItemRequest;
import ca.jdelreyes.biddingbackend.exception.CategoryNotFoundException;
import ca.jdelreyes.biddingbackend.exception.ItemNotFoundException;
import ca.jdelreyes.biddingbackend.exception.UserNotFoundException;
import ca.jdelreyes.biddingbackend.service.item.ItemServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<?> createItem(@AuthenticationPrincipal UserDetails userDetails,
                                        @Valid @RequestBody CreateItemRequest createItemRequest)
            throws UserNotFoundException, CategoryNotFoundException {
        return new ResponseEntity<>(itemService.createItem(userDetails.getUsername(), createItemRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/{itemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable("itemId") Integer id, @RequestBody UpdateItemRequest updateItemRequest) throws ItemNotFoundException {
        return ResponseEntity.ok(itemService.updateItem(id, updateItemRequest));
    }

    @PutMapping("/update-item/{itemId}")
    public ResponseEntity<ItemResponse> updateOwnItem(@AuthenticationPrincipal UserDetails userDetails,
                                                      @PathVariable("itemId") Integer id,
                                                      UpdateItemRequest updateItemRequest) throws Exception {

        return ResponseEntity.ok(itemService.updateOwnItem(userDetails.getUsername(), id, updateItemRequest));
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable("itemId") Integer id) {
        itemService.deleteItem(id);
    }
}
