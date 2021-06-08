package com.sm360.advertising.cardealer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class CarDealerService {

    private final CarDealerRepository carDealerRepository;

    @Autowired
    public CarDealerService(CarDealerRepository carDealerRepository) {

        this.carDealerRepository = carDealerRepository;
    }

    public List<CarDealer> getCarDealers() {

        List<CarDealer> dealers = carDealerRepository.findAll();

        return dealers;
    }


    public CarDealer addCarDealer(CarDealer carDealer) {

        if (carDealer.getName() == null
                || carDealer.getName().length() == 0){
            throw new IllegalStateException("Name must be indicated");
        }

        if (carDealer.getListingLimit() == null
                || carDealer.getListingLimit() == 0){
            throw new IllegalStateException("Listing limit invalid");
        }

        carDealerRepository.save(carDealer);

        return carDealer;
    }

    @Transactional
    public CarDealer updateCarDealer(
            Long carDealerId,
            String name,
            Integer listingLimit) {

        CarDealer carDealer = carDealerRepository.findById(carDealerId)
                .orElseThrow(() -> new IllegalStateException(
                        "Car Dealer with id " + carDealerId + " does not exists"));

        if (name != null
                && name.length() >0
                && !Objects.equals(carDealer.getName(),name)){

            carDealer.setName(name);
        }

        if (listingLimit != null
                && listingLimit > 0
                && !Objects.equals(carDealer.getListingLimit(),listingLimit)){

            carDealer.setListingLimit(listingLimit);
        }

        return carDealer;
    }

}
