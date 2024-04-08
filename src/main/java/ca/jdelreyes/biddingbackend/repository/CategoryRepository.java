package ca.jdelreyes.biddingbackend.repository;

import ca.jdelreyes.biddingbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(@NonNull String name);

    Optional<Category> findCategoryById(Integer id);
}
