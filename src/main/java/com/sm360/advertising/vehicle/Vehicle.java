package com.sm360.advertising.vehicle;

import com.sm360.advertising.cardealer.CarDealer;
import com.sm360.advertising.listing.Listing;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity(name = "Vehicle")
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @SequenceGenerator(
            name = "vehicle_sequence",
            sequenceName = "vehicle_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vehicle_sequence"
    )
    private Long id;

    @Column(
            name = "model",
            nullable = false
    )
    private String model;

    @Column(
            name = "color",
            nullable = false
    )
    private String color;

    @Column(
            name = "type",
            nullable = false
    )
    private CarType type;

    @ManyToOne
    @JoinColumn(
            name = "cardealer_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "cardealer_vehicle_fk"
            )
    )
    private CarDealer carDealer;

    @OneToOne(
            mappedBy = "vehicle",
            cascade = {CascadeType.ALL}

    )
    private Listing listing;

    public Vehicle(String model, String color, CarType type) {
        this.model = model;
        this.color = color;
        this.type = type;
    }

    public enum CarType {
        New, Used
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public CarDealer getCarDealer() {
        return carDealer;
    }

    public void setCarDealer(CarDealer carDealer) {
        this.carDealer = carDealer;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
