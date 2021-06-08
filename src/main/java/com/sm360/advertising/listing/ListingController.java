package com.sm360.advertising.listing;

import com.sm360.advertising.logger.LoggerApp;
import com.sm360.advertising.response.RestResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "api/listing")
public class ListingController {

    static Logger LOG = LoggerApp.getLogApp("com.sm360.advertising","/log/log.txt", Level.WARNING);

    private final ListingService listingService;

    @Autowired
    public ListingController(ListingService listingService) {
        this.listingService = listingService;

    }

    @GetMapping
    public RestResponse getListingsDealerState(
            @RequestParam Long carDealerId,
            @RequestParam Listing.State state){

        RestResponse response = new RestResponse();
        try{
            response.setMessage("Car Dealer listings with state.");
            response.setData(listingService.getListingsDealerState(carDealerId, state));
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
    public RestResponse registerListing(
            @RequestBody Listing listing,
            @RequestParam Long vehicleId,
            @RequestParam Long carDealerId,
            @RequestParam Listing.ActionLimit actionLimit){

        RestResponse response = new RestResponse();

        try {
            response.setMessage("Listing created.");
            response.setData(listingService.addListing(listing, vehicleId, carDealerId, actionLimit));
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

    @PutMapping(path = "/update/{listingId}")
    public RestResponse updateListing(
            @PathVariable("listingId") Long listingId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String seller,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Listing.State state,
            @RequestParam Listing.ActionLimit actionLimit){

        RestResponse response = new RestResponse();
        try{
            response.setMessage("Listing updated");
            response.setData(listingService.updateListing(
                listingId,
                title,
                seller,
                price,
                description,
                state,
                actionLimit));
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

    @PutMapping(path = "/publish/{listingId}")
    public RestResponse publishListing(
            @PathVariable("listingId") Long listingId,
            @RequestParam Listing.ActionLimit actionLimit){

        RestResponse response = new RestResponse();
        try{
            response.setMessage("Listing published.");
            response.setData(listingService.publishListing(listingId, actionLimit));
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

    @PutMapping(path = "/unpublish/{listingId}")
    public RestResponse unPublishListing(
            @PathVariable("listingId") Long listingId){

        RestResponse response = new RestResponse();
        try{
            response.setMessage("Listing unpublished.");
            response.setData(listingService.unPublishListing(listingId));
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
