package ca.jdelreyes.biddingbackend.service.item;

import ca.jdelreyes.biddingbackend.dto.item.CreateItemRequest;
import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.dto.item.UpdateItemRequest;
import ca.jdelreyes.biddingbackend.model.Item;
import ca.jdelreyes.biddingbackend.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<ItemResponse> getItems() {
        return itemRepository.findAll().stream().map(this::mapItemToItemResponse).toList();
    }

    @Override
    public ItemResponse getItem(Integer id) {
        return this.mapItemToItemResponse(itemRepository.findItemById(id).orElseThrow());
    }

    @Override
    public ItemResponse createItem(CreateItemRequest createItemRequest) {
        Item item = Item.builder()
                .name(createItemRequest.getName())
                .description(createItemRequest.getDescription())
                .startBidAmount(createItemRequest.getStartBidAmount())
                .bidIncrement(createItemRequest.getBidIncrement())
                .build();

        itemRepository.save(item);

        return this.mapItemToItemResponse(item);
    }

    @Override
    public ItemResponse updateItem(Integer id, UpdateItemRequest updateItemRequest) {
        return null;
    }

    @Override
    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }

    public ItemResponse mapItemToItemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .startBidAmount(item.getStartBidAmount())
                .bidIncrement(item.getBidIncrement())
                .build();
    }
}
