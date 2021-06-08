package com.sm360.advertising.listing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Query("SELECT l FROM Listing l WHERE l.vehicle.id = ?1")
    Optional<Listing> findByVehicle(Long carDealerId);

    @Query("SELECT l FROM Listing l " +
            "WHERE l.carDealer.id = ?1 AND l.state = ?2")
    List<Listing> findByCarDealerAndState(Long carDealerId, Listing.State state);

    @Query("SELECT l FROM Listing l " +
            "WHERE l.carDealer.id = ?1 AND l.state = ?2")
    List<Listing> findByCarDealerPublishedListing(Long carDealerId, Listing.State state);

}