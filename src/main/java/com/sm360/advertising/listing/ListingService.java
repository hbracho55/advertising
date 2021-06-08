package com.sm360.advertising.listing;

import com.sm360.advertising.cardealer.CarDealer;
import com.sm360.advertising.cardealer.CarDealerRepository;
import com.sm360.advertising.vehicle.Vehicle;
import com.sm360.advertising.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final VehicleRepository vehicleRepository;
    private final CarDealerRepository carDealerRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository,
                          VehicleRepository vehicleRepository,
                          CarDealerRepository carDealerRepository) {
        this.listingRepository = listingRepository;
        this.vehicleRepository = vehicleRepository;
        this.carDealerRepository = carDealerRepository;
    }

    public List<Listing> getListings() {
        return listingRepository.findAll();
    }

    public List<Listing> getListingsDealerState(Long carDealerId, Listing.State state) {

        List<Listing> listings = listingRepository.findByCarDealerAndState(carDealerId, state);

        return listings;
    }

    public Listing addListing(
            Listing listing,
            Long vehicleId,
            Long carDealerId,
            Listing.ActionLimit actionLimit) {

        if (listing.getTitle() == null
                || listing.getTitle().length() == 0){
            throw new IllegalStateException("Title empty or invalid");
        }

        if (listing.getSeller() == null
                || listing.getSeller().length() == 0){
            throw new IllegalStateException("Seller value empty or invalid");
        }

        if (listing.getPrice() == null
                || listing.getPrice() == 0){
            throw new IllegalStateException("Price value empty or invalid");
        }

        if (vehicleId == null || vehicleId == 0 ) {
            throw new IllegalStateException("VehicleId invalid");
        }

        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);

        if (!vehicleOptional.isPresent()){
            throw new IllegalStateException(
                    "Vehicle with id "+ vehicleId + " does not exists");
        }

        Optional<CarDealer> carDealerOptional = carDealerRepository.findById(carDealerId);

        if (!carDealerOptional.isPresent()){
            throw new IllegalStateException(
                    "Car Dealer with id "+ carDealerId + " does not exists");
        }

        checkPublishedListingAvailability(carDealerOptional.get(), listing.getState(), actionLimit);

        Optional<Listing> listingVehicleOptional = listingRepository.findByVehicle(vehicleId);
        if (listingVehicleOptional.isPresent()){
            throw  new IllegalStateException(
                    "Vehicle with id "+ vehicleId + " already has a listing"
            );
        }

        listing.setVehicle(vehicleOptional.get());
        listing.setCarDealer(carDealerOptional.get());
        listing.setListingAt(LocalDate.now());
        listingRepository.save(listing);

        return listing;
    }

    @Transactional
    public Listing updateListing(
            Long listingId,
            String title,
            String seller,
            Double price,
            String description,
            Listing.State state,
            Listing.ActionLimit actionLimit) {

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalStateException(
                        "Listing with id " + listingId + " does not exists"));

        if (title != null
                && title.length() >0
                && !Objects.equals(listing.getTitle(),title)){

            listing.setTitle(title);
        }

        if (seller != null
                && seller.length() >0
                && !Objects.equals(listing.getSeller(),seller)){

            listing.setSeller(seller);
        }

        if (price != null
                && price > 0
                && !Objects.equals(listing.getPrice(),price)){

            listing.setPrice(price);
        }

        if (description != null
                && description.length() >0
                && !Objects.equals(listing.getDescription(),description)){

            listing.setDescription(description);
        }

        if (state != null
                && !Objects.equals(listing.getState(),state)){

            checkPublishedListingAvailability(listing.getCarDealer(), state, actionLimit);

            listing.setState(state);
        }
        return listing;
    }

    @Transactional
    public Listing publishListing(Long listingId, Listing.ActionLimit actionLimit) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalStateException(
                        "Listing with id " + listingId + " does not exists"));

        checkPublishedListingAvailability(listing.getCarDealer(), Listing.State.Published, actionLimit);

        listing.setState(Listing.State.Published);

        return listing;
    }

    @Transactional
    public Listing unPublishListing(Long listingId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalStateException(
                        "Listing with id " + listingId + " does not exists"));

        listing.setState(Listing.State.Draft);

        return listing;
    }

    private void checkPublishedListingAvailability(
            CarDealer carDealer, Listing.State state, Listing.ActionLimit actionLimit) {

        if (Objects.equals(state, Listing.State.Published)) {

            List<Listing> dealerPublishedListing = listingRepository.findByCarDealerPublishedListing(
                    carDealer.getId(), Listing.State.Published);

            if (dealerPublishedListing.stream().count() >=
                    carDealer.getListingLimit()) {

                if (Objects.equals(actionLimit, Listing.ActionLimit.ShowError)) {
                    throw new IllegalStateException(
                            "Published listing limit has been reached for Car Dealer with id: "
                                    + carDealer.getId()
                    );
                }else{
                    dealerPublishedListing.stream()
                            .min(Comparator.comparing(Listing::getId))
                            .ifPresent(listingMin -> {
                                listingMin.setState(Listing.State.Draft);
                            });
                }
            }
        }
    }
}
