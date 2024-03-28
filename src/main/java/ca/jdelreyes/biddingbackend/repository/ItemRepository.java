package ca.jdelreyes.biddingbackend.repository;

import ca.jdelreyes.biddingbackend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findItemByName(String name);
}
