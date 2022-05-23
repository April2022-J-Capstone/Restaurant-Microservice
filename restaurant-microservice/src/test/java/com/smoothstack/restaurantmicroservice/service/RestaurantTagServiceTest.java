package com.smoothstack.restaurantmicroservice.service;

import com.smoothstack.common.models.*;
import com.smoothstack.common.repositories.*;
import com.smoothstack.common.services.CommonLibraryTestingService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RestaurantTagServiceTest {

    @Autowired
    RestaurantTagService restaurantTagService;
    @Autowired
    RestaurantTagRepository restaurantTagRepository;
    @Autowired
    CommonLibraryTestingService testingService;


    @BeforeEach
    public void setUpTestEnvironment(){
        testingService.createTestData();
    }

    @Test
    public void returnsAllRestaurantTags(){
        List<RestaurantTag> dbRestaurantTags = restaurantTagRepository.findAll();
        List<String> dbRestaurantTagNames = dbRestaurantTags
         .stream()
                .map( tag -> tag.getName())
                .collect(Collectors.toList());
        List<RestaurantTag> serviceRestaurantTags  = restaurantTagService.getAllRestaurantTags();
        List<String> serviceRestaurantTagsNames = serviceRestaurantTags
                .stream()
                .map( tag -> tag.getName())
                .collect(Collectors.toList());
        assertEquals(dbRestaurantTagNames, serviceRestaurantTagsNames);
    }

    @Test
    public void returnsSavedRestaurantTag() {
        RestaurantTag newRestaurantTag = new RestaurantTag();
        newRestaurantTag.setName("testTag");
        RestaurantTag savedRestaurantTag = restaurantTagService.createNewRestaurantTag(newRestaurantTag);
        assertEquals(newRestaurantTag, savedRestaurantTag);
    }

    @Test
    public void returnsUpdatedRestaurantTag(){
        Optional<RestaurantTag> dbRestaurantTag = restaurantTagRepository.findById(1);
        RestaurantTag testRestaurantTag = dbRestaurantTag.get();
        testRestaurantTag.setName("updatedTag");
        RestaurantTag returnedRestaurantTag = restaurantTagService.updateGivenRestaurantTag(testRestaurantTag, 1);
        assertEquals("updatedTag", returnedRestaurantTag.getName());
    }

    @Test
    public void confirmsDeletedRestaurantTag(){
        RestaurantTag newRestaurantTag = new RestaurantTag();
        newRestaurantTag.setName("toDelete");
        RestaurantTag savedRestaurantTag = restaurantTagRepository.saveAndFlush(newRestaurantTag);
        String deleteMessage = restaurantTagService.deleteGivenRestaurantTag(savedRestaurantTag.getId());
        assertEquals("Restaurant Tag has been deleted successfully", deleteMessage );
    }

    @AfterEach
    @Disabled
    void tearDown() {
    }
    
}
