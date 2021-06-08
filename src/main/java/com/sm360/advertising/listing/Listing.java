package com.sm360.advertising.listing;

import com.sm360.advertising.cardealer.CarDealer;
import com.sm360.advertising.vehicle.Vehicle;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@ToString
@Entity(name = "Listing")
@Table(name = "listing")
public class Listing {

    @Id
    @SequenceGenerator(
            name = "listing_sequence",
            sequenceName = "listing_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "listing_sequence"
    )
    private Long id;

    @Column(
            name = "title",
            nullable = false
    )
    private String title;

    @Column(
            name = "seller",
            nullable = false
    )
    private String seller;

    @Column(
            name = "listingat",
            nullable = false
    )
    private LocalDate listingAt;

    @Column(
            name = "price",
            nullable = false
    )
    private Double price;

    @Column(
            name = "descripcion"
    )
    private String description;

    @Column(
            name = "state",
            nullable = false
    )
    private State state = State.Draft;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "vehicle_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "vehicle_id_fk"
            )
    )
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(
            name = "cardealer_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "cardealer_listing_fk"
            )
    )
    private CarDealer carDealer;

    public Listing(String title,
                   String seller,
                   LocalDate listingAt,
                   Double price,
                   String description,
                   State state) {

        this.title = title;
        this.seller = seller;
        this.listingAt = listingAt;
        this.price = price;
        this.description = description;
        this.state = state;
    }

    enum State {
        Draft, Published
    }

    enum ActionLimit {
        ShowError, RemoveLast
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public LocalDate getListingAt() {
        return listingAt;
    }

    public void setListingAt(LocalDate listingAt) {
        this.listingAt = listingAt;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public CarDealer getCarDealer() {
        return carDealer;
    }

    public void setCarDealer(CarDealer carDealer) {
        this.carDealer = carDealer;
    }
}
