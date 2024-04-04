package ca.jdelreyes.biddingbackend.bootstrap;

import ca.jdelreyes.biddingbackend.model.Category;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.model.enums.Role;
import ca.jdelreyes.biddingbackend.repository.CategoryRepository;
import ca.jdelreyes.biddingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        generateInitialUserData();
        generateInitialCategoryData();
    }

    private void generateInitialUserData() {
        if (!userRepository.existsById(1))
            userRepository.save(User.builder()
                    .email("admin@domain.ca")
                    .firstName("firstName")
                    .lastName("lastName")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.ADMIN)
                    .build());
    }

    private void generateInitialCategoryData() {
        if (!categoryRepository.existsByName("Electronics"))
            categoryRepository.save(Category.builder().name("Electronics").build());

        if (!categoryRepository.existsByName("Clothing and Apparel"))
            categoryRepository.save(Category.builder().name("Clothing and Apparel").build());

        if (!categoryRepository.existsByName("Home and Kitchen"))
            categoryRepository.save(Category.builder().name("Home and Kitchen").build());

        if (!categoryRepository.existsByName("Health and Personal Care"))
            categoryRepository.save(Category.builder().name("Health and Personal Care").build());

        if (!categoryRepository.existsByName("Sports and Outdoors"))
            categoryRepository.save(Category.builder().name("Sports and Outdoors").build());

        if (!categoryRepository.existsByName("Automotive and Tools"))
            categoryRepository.save(Category.builder().name("Automotive and Tools").build());

        if (!categoryRepository.existsByName("Toys and Games"))
            categoryRepository.save(Category.builder().name("Toys and Games").build());

        if (!categoryRepository.existsByName("Books and Stationery"))
            categoryRepository.save(Category.builder().name("Books and Stationery").build());

        if (!categoryRepository.existsByName("Jewelry and Accessories"))
            categoryRepository.save(Category.builder().name("Jewelry and Accessories").build());

        if (!categoryRepository.existsByName("Pet Supplies"))
            categoryRepository.save(Category.builder().name("Pet Supplies").build());

        if (!categoryRepository.existsByName("Others"))
            categoryRepository.save(Category.builder().name("Others").build());
    }
}
