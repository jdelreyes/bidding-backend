package ca.jdelreyes.biddingbackend.repository;

import ca.jdelreyes.biddingbackend.model.Item;
import ca.jdelreyes.biddingbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findItemById(Integer id);

    Optional<Item> findItemByName(String name);

    Optional<Item> findItemByIdAndSeller(Integer id, User seller);

    void deleteById(@NonNull Integer id);
}
