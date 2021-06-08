package com.sm360.advertising.cardealer;

import com.sm360.advertising.logger.LoggerApp;
import com.sm360.advertising.response.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "api/dealer")
public class CarDealerController {

    static Logger LOG = LoggerApp.getLogApp("com.sm360.advertising","/log/log.txt", Level.WARNING);

    private final CarDealerService carDealerService;

    @Autowired
    public CarDealerController(CarDealerService carDealerService) {
        this.carDealerService = carDealerService;
    }

    @GetMapping
    public RestResponse getCarDealers(){

        RestResponse response = new RestResponse();
        try{
            response.setMessage("Car Dealers list");
            response.setData(carDealerService.getCarDealers());
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
    public RestResponse registerCarDealer(
            @RequestBody CarDealer carDealer){

        RestResponse response = new RestResponse();
        try{
            response.setMessage("Car Dealer created.");
            response.setData(carDealerService.addCarDealer(carDealer));
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

    @PutMapping(path = "/update/{carDealerId}")
    public RestResponse updateCarDealer(
            @PathVariable("carDealerId") Long carDealerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer listingLimit){

        RestResponse response = new RestResponse();
        try{
            response.setMessage("Car Dealer updated.");
            response.setData(carDealerService.updateCarDealer(
                    carDealerId,
                    name,
                    listingLimit));
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
