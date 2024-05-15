package ca.jdelreyes.biddingbackend.repository;

import ca.jdelreyes.biddingbackend.exception.ItemNotFoundException;
import ca.jdelreyes.biddingbackend.model.Category;
import ca.jdelreyes.biddingbackend.model.Item;
import ca.jdelreyes.biddingbackend.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles({"repository-test"})
public class ItemRepositoryTests {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createItemShouldReturnItem() throws ItemNotFoundException {
        Item item = createItem();
        itemRepository.save(item);

        Item savedItem = itemRepository.findItemById(1).orElseThrow(ItemNotFoundException::new);

        Assertions.assertThat(savedItem.getName()).isEqualTo(item.getName());
    }



    private User createUser() {
        return userRepository.save(User.builder().email("johndoe@email.com").build());
    }

    private Item createItem() {
        return Item.builder()
                .name("name")
                .category(Category.builder().name("Electronics").build())
                .seller(createUser())
                .build();
    }
}
