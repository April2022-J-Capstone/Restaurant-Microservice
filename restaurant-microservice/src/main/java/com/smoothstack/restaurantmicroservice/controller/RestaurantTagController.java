package com.smoothstack.restaurantmicroservice.controller;

import com.smoothstack.common.models.RestaurantTag;
import com.smoothstack.restaurantmicroservice.exception.*;
import com.smoothstack.restaurantmicroservice.service.RestaurantTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestaurantTagController {

    @Autowired
    RestaurantTagService restaurantTagService;

    @GetMapping(value = "/restaurants/restaurantTags")
    public ResponseEntity<List<RestaurantTag>> getRestaurantTags() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(restaurantTagService.getAllRestaurantTags());
        } catch(Exception exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/restaurant/restaurantTag")
    public ResponseEntity<RestaurantTag> createRestaurantTag(@RequestBody RestaurantTag restaurantTag) throws RestaurantTagAlreadyExistsException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurantTagService.createNewRestaurantTag(restaurantTag));
        } catch(RestaurantTagAlreadyExistsException restaurantTagAlreadyExistsException){
            return new ResponseEntity(restaurantTagAlreadyExistsException.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @PutMapping(value = "restaurant/restaurantTag/{restaurantTagId}")
    public ResponseEntity<String>updateRestaurantTag(@RequestBody RestaurantTag restaurantTag, @PathVariable Integer restaurantTagId) throws RestaurantTagNotFoundException, RestaurantTagAlreadyExistsException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(restaurantTagService.updateGivenRestaurantTag(restaurantTag, restaurantTagId));
        }  catch(RestaurantTagNotFoundException restaurantTagNotFoundException){
            return new ResponseEntity(restaurantTagNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(RestaurantTagAlreadyExistsException restaurantTagAlreadyExistsException){
            return new ResponseEntity(restaurantTagAlreadyExistsException.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @PutMapping(value = "restaurant/restaurantTag/{restaurantTagId}/enable")
    public ResponseEntity<String>enableRestaurantTag(@PathVariable Integer restaurantTagId) throws RestaurantNotFoundException, RestaurantTagNotFoundException, RestaurantTagAlreadyExistsException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(restaurantTagService.enableGivenRestaurantTag(restaurantTagId));
        } catch(RestaurantNotFoundException restaurantNotFoundException){
            return new ResponseEntity(restaurantNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RestaurantTagAlreadyEnabledException restaurantTagAlreadyEnabledException) {
            return new ResponseEntity(restaurantTagAlreadyEnabledException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "restaurant/disable/restaurantTag/{restaurantTagId}")
    public ResponseEntity<String>disableRestaurantTag(@PathVariable Integer restaurantTagId) throws RestaurantNotFoundException, RestaurantTagNotFoundException, RestaurantTagAlreadyExistsException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(restaurantTagService.disableGivenRestaurantTag(restaurantTagId));
        } catch(RestaurantTagNotFoundException restaurantTagNotFoundException){
            return new ResponseEntity(restaurantTagNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RestaurantTagAlreadyDisabledException restaurantTagAlreadyDisabledException) {
            return new ResponseEntity(restaurantTagAlreadyDisabledException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping(value = "restaurant/restaurantTag/{restaurantTagId}")
    public ResponseEntity<String>deleteRestaurant(@PathVariable Integer restaurantTagId) throws RestaurantTagNotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(restaurantTagService.deleteGivenRestaurantTag(restaurantTagId));
        } catch(RestaurantTagNotFoundException restaurantTagNotFoundException){
            return new ResponseEntity(restaurantTagNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
