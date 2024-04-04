package ca.jdelreyes.biddingbackend.repository;

import ca.jdelreyes.biddingbackend.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    boolean existsById(@NonNull Integer id);

    Optional<Auction> findAuctionById(Integer id);
}
