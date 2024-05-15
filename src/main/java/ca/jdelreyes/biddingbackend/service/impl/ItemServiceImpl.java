package ca.jdelreyes.biddingbackend.service.impl;

import ca.jdelreyes.biddingbackend.dto.item.CreateItemRequest;
import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.dto.item.UpdateItemRequest;
import ca.jdelreyes.biddingbackend.exception.CategoryNotFoundException;
import ca.jdelreyes.biddingbackend.exception.ItemNotFoundException;
import ca.jdelreyes.biddingbackend.exception.UserNotFoundException;
import ca.jdelreyes.biddingbackend.mapper.Mapper;
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

import static ca.jdelreyes.biddingbackend.mapper.Mapper.mapItemToItemResponse;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<ItemResponse> getItems() {
        return itemRepository.findAll().stream().map(Mapper::mapItemToItemResponse).toList();
    }

    @Override
    public ItemResponse getItem(Integer id) throws ItemNotFoundException {
        return mapItemToItemResponse(itemRepository.findItemById(id)
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

        return mapItemToItemResponse(item);
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
        User user = userRepository.findUserById(userId).orElseThrow(UserNotFoundException::new);
        Item item = itemRepository.findItemByIdAndSeller(id, user).orElseThrow(ItemNotFoundException::new);
        Category category = categoryRepository.findCategoryById(updateItemRequest.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

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

    @Transactional
    @Override
    public ItemResponse deleteOwnItem(Integer userId, Integer id) throws Exception {
        User user = userRepository.findUserById(userId).orElseThrow(UserNotFoundException::new);
        Item item = itemRepository.findItemByIdAndSeller(id, user).orElseThrow(ItemNotFoundException::new);

        itemRepository.deleteById(id);

        if (itemRepository.existsById(id))
            throw new Exception("Item delete failed");

        return mapItemToItemResponse(item);
    }

}
