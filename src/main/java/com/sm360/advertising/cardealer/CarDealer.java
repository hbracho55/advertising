package com.sm360.advertising.cardealer;

import com.sm360.advertising.listing.Listing;
import com.sm360.advertising.vehicle.Vehicle;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity(name = "CarDealer")
@Table(name = "cardealer")
public class CarDealer {

    @Id
    @SequenceGenerator(
            name = "cardealer_sequence",
            sequenceName = "cardealer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cardealer_sequence"
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "listingLimit",
            nullable = false
    )
    private Integer listingLimit;

    @OneToMany(
            mappedBy = "carDealer",
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER
    )
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(
            mappedBy = "carDealer",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY
    )
    private List<Listing> listings = new ArrayList<>();

    public CarDealer(String name, Integer listingLimit) {
        this.name = name;
        this.listingLimit = listingLimit;
    }

    public void addVehicle(Vehicle vehicle) {
        if (!this.vehicles.contains(vehicle)) {
            this.vehicles.add(vehicle);
            vehicle.setCarDealer(this);
        }
    }

    public void removeVehicle(Vehicle vehicle) {
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
            vehicle.setCarDealer(null);
        }
    }

    public void addListing(Listing listing) {
        if (!this.listings.contains(listing)) {
            this.listings.add(listing);
            listing.setCarDealer(this);
        }
    }

    public void removeListing(Listing listing) {
        if (this.listings.contains(listing)) {
            this.listings.remove(listing);
            listing.setCarDealer(null);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getListingLimit() {
        return listingLimit;
    }

    public void setListingLimit(Integer listingLimit) {
        this.listingLimit = listingLimit;
    }

    @Override
    public String toString() {
        return "CarDealer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", listingLimit=" + listingLimit +
                '}';
    }
}
