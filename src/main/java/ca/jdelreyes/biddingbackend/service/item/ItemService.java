package ca.jdelreyes.biddingbackend.service.item;

import ca.jdelreyes.biddingbackend.dto.item.CreateItemRequest;
import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.dto.item.UpdateItemRequest;
import ca.jdelreyes.biddingbackend.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<ItemResponse> getItems();
    Item getItem(Integer id);
    Item createItem(CreateItemRequest createItemRequest);
    Item updateItem(Integer id, UpdateItemRequest updateItemRequest);
    void deleteItem(Integer id);
}
