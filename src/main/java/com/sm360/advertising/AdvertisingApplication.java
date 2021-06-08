package com.sm360.advertising;

import com.sm360.advertising.cardealer.CarDealer;
import com.sm360.advertising.cardealer.CarDealerRepository;
import com.sm360.advertising.vehicle.Vehicle;
import com.sm360.advertising.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AdvertisingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvertisingApplication.class, args);
	}

}
