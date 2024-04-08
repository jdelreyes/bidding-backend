package ca.jdelreyes.biddingbackend.repository;

import ca.jdelreyes.biddingbackend.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
}
