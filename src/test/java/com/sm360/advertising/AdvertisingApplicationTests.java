package com.sm360.advertising;

import com.sm360.advertising.cardealer.CarDealer;
import com.sm360.advertising.cardealer.CarDealerRepository;
import com.sm360.advertising.vehicle.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootTest
class AdvertisingApplicationTests {

	@Test
	void contextLoads() {
	}

	@Bean
	CommandLineRunner commandLineRunner(CarDealerRepository carDealerRepository){
		return args -> {

			CarDealer carDealer1 = new CarDealer("Car Dealer Montreal",2);
			carDealer1.addVehicle(new Vehicle("Corolla","Red", Vehicle.CarType.New));
			carDealer1.addVehicle(new Vehicle("Civic","White", Vehicle.CarType.New));
			carDealer1.addVehicle(new Vehicle("Ranger","Blue", Vehicle.CarType.Used));
			carDealer1.addVehicle(new Vehicle("Corolla","Green", Vehicle.CarType.Used));
			carDealer1.addVehicle(new Vehicle("Hilux","Black", Vehicle.CarType.New));
			carDealer1.addVehicle(new Vehicle("Civic","Pink", Vehicle.CarType.Used));
			carDealer1.addVehicle(new Vehicle("Silverado","Black", Vehicle.CarType.Used));
			carDealer1.addVehicle(new Vehicle("FourRunner","White", Vehicle.CarType.Used));
			carDealer1.addVehicle(new Vehicle("Accent","Silver", Vehicle.CarType.New));

			CarDealer carDealer2 = new CarDealer("Car Dealer Old Montreal",3);
			carDealer2.addVehicle(new Vehicle("Explorer","Red", Vehicle.CarType.Used));
			carDealer2.addVehicle(new Vehicle("Malibu","Back", Vehicle.CarType.New));
			carDealer2.addVehicle(new Vehicle("Tax","Brown", Vehicle.CarType.Used));
			carDealer2.addVehicle(new Vehicle("Sportage","Yellow", Vehicle.CarType.New));
			carDealer2.addVehicle(new Vehicle("Neon","Red", Vehicle.CarType.New));
			carDealer2.addVehicle(new Vehicle("Optra","White", Vehicle.CarType.Used));

			carDealerRepository.saveAll(
					List.of(carDealer1, carDealer2)
			);

		};
	}
}
