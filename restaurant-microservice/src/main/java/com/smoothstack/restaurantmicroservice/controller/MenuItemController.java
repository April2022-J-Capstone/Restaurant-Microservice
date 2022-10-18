package com.smoothstack.restaurantmicroservice.controller;

import java.util.List;

import com.smoothstack.common.exceptions.*;
import com.smoothstack.common.models.MenuItem;
import com.smoothstack.restaurantmicroservice.data.MenuItemInformation;
import com.smoothstack.restaurantmicroservice.data.MenuItemParams;
import com.smoothstack.restaurantmicroservice.service.MenuItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MenuItemController {

    @Autowired
    MenuItemService menuItemService;


    @GetMapping(value = "/restaurants/menuItems")
    public ResponseEntity<List<MenuItemInformation>> getAllMenuItems() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(menuItemService.getAllMenuItems());
        } catch(Exception exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/restaurant/{restaurantId}/menuItems")
    public ResponseEntity<List<MenuItemInformation>> getRestaurantMenu(@PathVariable Integer restaurantId) throws RestaurantNotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(menuItemService.getRestaurantMenu(restaurantId));
        } catch(RestaurantNotFoundException restaurantNotFoundException){
            return new ResponseEntity(restaurantNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/restaurant/{restaurantId}/menuItem/{menuItemName}")
    public ResponseEntity getMenuItem(@PathVariable Integer restaurantId, @PathVariable String menuItemName) {
        try {
            return ResponseEntity.ok().body(menuItemService.getMenuItemByRestaurantAndName(restaurantId, menuItemName));
        } catch(MenuItemNotFoundException menuItemNotFoundException) {
            return ResponseEntity.badRequest().body(menuItemNotFoundException.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/restaurant/menuItem")
    public ResponseEntity<String> createMenuItem(@RequestBody MenuItem menuItem) throws RestaurantNotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(menuItemService.createNewMenuItem(menuItem));
        } catch(RestaurantNotFoundException restaurantNotFoundException){
            return new ResponseEntity(restaurantNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "restaurant/menuItem/{menuItemId}")
    public ResponseEntity<String>updateMenuItem(@RequestBody MenuItem menuItem, @PathVariable Integer menuItemId) throws MenuItemNotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(menuItemService.updateGivenMenuItem(menuItem, menuItemId));
        } catch ( MenuItemNotFoundException menuItemNotFoundException){
            return new ResponseEntity(menuItemNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "restaurant/menuItem/{menuItemId}/enable")
    public ResponseEntity<String>enableMenuItem(@PathVariable Integer menuItemId) throws MenuItemNotFoundException{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(menuItemService.enableGivenMenuItem(menuItemId));
        } catch ( MenuItemNotFoundException menuItemNotFoundException){
            return new ResponseEntity(menuItemNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "restaurant/menuItem/{menuItemId}/disable")
    public ResponseEntity<String>disableMenuItem(@PathVariable Integer menuItemId) throws MenuItemNotFoundException{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(menuItemService.disableGivenMenuItem(menuItemId));
        } catch ( MenuItemNotFoundException menuItemNotFoundException){
            return new ResponseEntity(menuItemNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "restaurant/menuItem/{menuItemId}")
    public ResponseEntity<String>deleteMenuItem(@PathVariable Integer menuItemId) throws MenuItemNotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(menuItemService.deleteGivenMenuItem(menuItemId));
        } catch ( MenuItemNotFoundException menuItemNotFoundException){
            return new ResponseEntity(menuItemNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/restaurant/{restaurantId}/menuItems/search")
    public ResponseEntity<List<MenuItemInformation>> searchRestaurantsMenuItems(@PathVariable Integer restaurantId, @RequestBody MenuItemParams menuItemsSearch) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(menuItemService.findMenuItems(restaurantId, menuItemsSearch));
        } catch (InvalidSearchException invalidSearchException) {
            return new ResponseEntity(invalidSearchException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
