package com.sm360.advertising.vehicle;

import com.sm360.advertising.cardealer.CarDealer;
import com.sm360.advertising.cardealer.CarDealerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CarDealerRepository carDealerRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository,
                          CarDealerRepository carDealerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.carDealerRepository = carDealerRepository;
    }

    public List<Vehicle> getVehiclesCarDealerType(Long carDealerId, Vehicle.CarType type) {

        List<Vehicle> vehicles = vehicleRepository.findByCarDealerType(carDealerId, type);

        return vehicles;
    }


    public Vehicle addVehicle(Vehicle vehicle, Long carDealerId) {

        if (vehicle.getModel() == null
                || vehicle.getModel().length() == 0){
            throw new IllegalStateException("Model must be indicated");
        }

        if (vehicle.getColor() == null
                || vehicle.getColor().length() == 0){
            throw new IllegalStateException("Color must be indicated");
        }

        Optional<CarDealer> carDealerOptional = carDealerRepository.findById(carDealerId);

        if (!carDealerOptional.isPresent()){
            throw new IllegalStateException(
                    "Car Dealer with id "+ carDealerId + " does not exists");
        }
        vehicle.setCarDealer(carDealerOptional.get());
        vehicleRepository.save(vehicle);

        return vehicle;
    }

    @Transactional
    public Vehicle updateVehicle(
            Long vehicleId,
            String model,
            String color,
            Vehicle.CarType type) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalStateException(
                        "Vehicle with id " + vehicleId + " does not exists"));

        if (model != null
                && model.length() >0
                && !Objects.equals(vehicle.getModel(),model)){

            vehicle.setModel(model);
        }

        if (color != null
                && color.length() >0
                && !Objects.equals(vehicle.getColor(),color)){

            vehicle.setColor(color);
        }

        if (type != null
                && !Objects.equals(vehicle.getType(),type)){

            vehicle.setType(type);
        }

        return vehicle;
    }

}
