package ca.jdelreyes.biddingbackend.service.impl;

import ca.jdelreyes.biddingbackend.dto.item.CreateItemRequest;
import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.dto.item.UpdateItemRequest;
import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import ca.jdelreyes.biddingbackend.exception.CategoryNotFoundException;
import ca.jdelreyes.biddingbackend.exception.ItemNotFoundException;
import ca.jdelreyes.biddingbackend.exception.UserNotFoundException;
import ca.jdelreyes.biddingbackend.model.Category;
import ca.jdelreyes.biddingbackend.model.Item;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.repository.CategoryRepository;
import ca.jdelreyes.biddingbackend.repository.ItemRepository;
import ca.jdelreyes.biddingbackend.repository.UserRepository;
import ca.jdelreyes.biddingbackend.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<ItemResponse> getItems() {
        return itemRepository.findAll().stream().map(this::mapItemToItemResponse).toList();
    }

    @Override
    public ItemResponse getItem(Integer id) throws ItemNotFoundException {
        return this.mapItemToItemResponse(itemRepository.findItemById(id)
                .orElseThrow(ItemNotFoundException::new));
    }

    @Override
    public ItemResponse createItem(Integer userId, CreateItemRequest createItemRequest) throws UserNotFoundException, CategoryNotFoundException {
        User user = userRepository.findUserById(userId).orElseThrow(UserNotFoundException::new);
        Category category = categoryRepository.findCategoryById(createItemRequest.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Item item = Item.builder()
                .name(createItemRequest.getName())
                .description(createItemRequest.getDescription())
                .startBidAmount(createItemRequest.getStartBidAmount())
                .currentBidAmount(createItemRequest.getStartBidAmount())
                .finalBidAmount(createItemRequest.getFinalBidAmount())
                .bidIncrement(createItemRequest.getBidIncrement())
                .seller(user)
                .category(category)
                .build();

        itemRepository.save(item);

        return this.mapItemToItemResponse(item);
    }

    @Override
    public ItemResponse updateItem(Integer id, UpdateItemRequest updateItemRequest) throws ItemNotFoundException {
        Item item = itemRepository.findItemById(id).orElseThrow(ItemNotFoundException::new);

        item.setName(updateItemRequest.getName());
        item.setDescription(updateItemRequest.getDescription());

        itemRepository.save(item);

        return mapItemToItemResponse(item);
    }

    @Override
    public ItemResponse updateOwnItem(Integer userId, Integer id, UpdateItemRequest updateItemRequest) throws Exception {
        Item item = itemRepository.findItemById(id).orElseThrow(ItemNotFoundException::new);
        User user = userRepository.findUserById(userId).orElseThrow(UserNotFoundException::new);
        Category category = categoryRepository.findCategoryById(updateItemRequest.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        if (!Objects.equals(user.getId(), item.getSeller().getId()))
            throw new Exception("user does not own the item");

        item.setName(updateItemRequest.getName());
        item.setDescription(updateItemRequest.getDescription());
        item.setStartBidAmount(updateItemRequest.getStartBidAmount());
        item.setFinalBidAmount(updateItemRequest.getFinalBidAmount());
        item.setBidIncrement(updateItemRequest.getBidIncrement());
        item.setCategory(category);

        itemRepository.save(item);

        return mapItemToItemResponse(item);
    }

    @Transactional
    @Override
    public ItemResponse deleteItem(Integer id) throws Exception {
        Item item = itemRepository.findItemById(id).orElseThrow(ItemNotFoundException::new);

        itemRepository.deleteById(id);

        if (itemRepository.existsById(id))
            throw new Exception("Item delete failed");

        return mapItemToItemResponse(item);
    }

    @Override
    public ItemResponse deleteOwnItem(Integer userId, Integer id) throws Exception {
        Item item = itemRepository.findItemById(id).orElseThrow(ItemNotFoundException::new);
        User user = userRepository.findUserById(userId).orElseThrow(UserNotFoundException::new);

        if (!Objects.equals(user.getId(), item.getSeller().getId()))
            throw new Exception("Item is not owned by current user");

        itemRepository.deleteById(id);

        if (itemRepository.existsById(id))
            throw new Exception("Item delete failed");

        return mapItemToItemResponse(item);
    }

    public ItemResponse mapItemToItemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .createdAt(item.getCreatedAt())
                .startBidAmount(item.getStartBidAmount())
                .finalBidAmount(item.getFinalBidAmount())
                .currentBidAmount(item.getCurrentBidAmount())
                .bidIncrement(item.getBidIncrement())
                .seller(mapUserToUserResponse(item.getSeller()))
                .category(item.getCategory())
                .build();
    }

    private UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .dateTimeCreated(user.getDateTimeCreated())
                .role(user.getRole())
                .build();
    }
}
