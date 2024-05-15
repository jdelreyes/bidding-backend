package ca.jdelreyes.biddingbackend.service;

import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.repository.ItemRepository;
import ca.jdelreyes.biddingbackend.service.impl.ItemServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTests {
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    public void getProductsShouldReturnProductResponseList() {
        List<ItemResponse> itemResponseList = itemService.getItems();

        Assertions.assertThat(itemResponseList.size()).isEqualTo(0);
    }
}
