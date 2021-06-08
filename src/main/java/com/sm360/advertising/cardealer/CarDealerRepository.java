package com.sm360.advertising.cardealer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDealerRepository extends JpaRepository<CarDealer, Long> {
}
