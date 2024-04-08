package ca.jdelreyes.biddingbackend.service;

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

    ItemResponse createItem(Integer userId, CreateItemRequest createItemRequest) throws UserNotFoundException, CategoryNotFoundException;

    ItemResponse updateItem(Integer id, UpdateItemRequest updateItemRequest) throws ItemNotFoundException;

    ItemResponse updateOwnItem(Integer userId, Integer id, UpdateItemRequest updateItemRequest) throws Exception;

    ItemResponse deleteItem(Integer id) throws Exception;

    ItemResponse deleteOwnItem(Integer userId, Integer id) throws Exception;
}
