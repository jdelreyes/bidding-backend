package ca.jdelreyes.biddingbackend.service.item;

import ca.jdelreyes.biddingbackend.dto.item.CreateItemRequest;
import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.dto.item.UpdateItemRequest;

import java.util.List;

public interface ItemService {
    List<ItemResponse> getItems();

    ItemResponse getItem(Integer id);

    ItemResponse createItem(CreateItemRequest createItemRequest);

    ItemResponse updateItem(Integer id, UpdateItemRequest updateItemRequest);

    void deleteItem(Integer id);
}
