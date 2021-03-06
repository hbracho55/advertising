package com.sm360.advertising.vehicle;

import com.sm360.advertising.listing.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v FROM Vehicle v WHERE v.carDealer.id = ?1 AND v.type = ?2")
    List<Vehicle> findByCarDealerType(Long carDealerId, Vehicle.CarType type);

}
