package com.sm360.advertising.vehicle;

import com.sm360.advertising.logger.LoggerApp;
import com.sm360.advertising.response.RestResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "api/vehicle")
public class VehicleController {

    static Logger LOG = LoggerApp.getLogApp("com.sm360.advertising","/log/log.txt", Level.WARNING);

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public RestResponse getVehiclesCarDealerType(
            @RequestParam Long carDealerId,
            @RequestParam Vehicle.CarType type){

        RestResponse response = new RestResponse();
        try{
            response.setMessage("Vehicles and Car Dealer list.");
            response.setData(vehicleService.getVehiclesCarDealerType(carDealerId, type));
        }catch(IllegalStateException e){
            response.setMessage(e.getMessage());
            response.setStatus(RestResponse.RestStatus.Failed);
            LOG.warning(e.getMessage());
        }catch(Exception e){
            response.setMessage(e.getMessage());
            response.setStatus(RestResponse.RestStatus.Failed);
            LOG.warning(e.getMessage());
        }

        return response;
    }

    @PostMapping(path = "/create")
    public RestResponse registerVehicle(
            @RequestBody Vehicle vehicle,
            @RequestParam Long carDealerId){

        RestResponse response = new RestResponse();
        try{
            response.setMessage("Vehicle created.");
            response.setData(vehicleService.addVehicle(vehicle, carDealerId));
        }catch(IllegalStateException e){
            response.setMessage(e.getMessage());
            response.setStatus(RestResponse.RestStatus.Failed);
            LOG.warning(e.getMessage());
        }catch(Exception e){
            response.setMessage(e.getMessage());
            response.setStatus(RestResponse.RestStatus.Failed);
            LOG.warning(e.getMessage());
        }

        return response;
    }

    @PutMapping(path = "/update/{vehicleId}")
    public RestResponse updateVehicle(
            @PathVariable("vehicleId") Long vehicleId,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Vehicle.CarType type){

        RestResponse response = new RestResponse();
        try{
            response.setMessage("Vehicle updated.");
            response.setData(vehicleService.updateVehicle(
                    vehicleId,
                    model,
                    color,
                    type));
        }catch(IllegalStateException e){
            response.setMessage(e.getMessage());
            response.setStatus(RestResponse.RestStatus.Failed);
            LOG.warning(e.getMessage());
        }catch(Exception e){
            response.setMessage(e.getMessage());
            response.setStatus(RestResponse.RestStatus.Failed);
            LOG.warning(e.getMessage());
        }

        return response;
    }

}
