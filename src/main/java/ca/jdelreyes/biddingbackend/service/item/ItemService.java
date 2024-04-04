package ca.jdelreyes.biddingbackend.service.item;

import ca.jdelreyes.biddingbackend.dto.item.CreateItemRequest;
import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.dto.item.UpdateItemRequest;
import ca.jdelreyes.biddingbackend.exception.CategoryNotFoundException;
import ca.jdelreyes.biddingbackend.exception.ItemNotFoundException;
import ca.jdelreyes.biddingbackend.exception.UserNotFoundException;

import java.util.List;

public interface ItemService {
    List<ItemResponse> getItems();

    ItemResponse getItem(Integer id) throws ItemNotFoundException;

    ItemResponse createItem(String userName, CreateItemRequest createItemRequest) throws UserNotFoundException, CategoryNotFoundException;

    ItemResponse updateItem(Integer id, UpdateItemRequest updateItemRequest) throws ItemNotFoundException;

    ItemResponse updateOwnItem(String userName, Integer id, UpdateItemRequest updateItemRequest) throws Exception;

    void deleteItem(Integer id);
}
