package com.goodamcodes.controller;

import com.goodamcodes.configuration.Constant;
import com.goodamcodes.model.Explore;
import com.goodamcodes.service.ExploreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping(path = Constant.BASE_URL + "/explore")
public class ExploreController {

    @Autowired
    private ExploreService exploreService;

    @GetMapping("/all")
    public ResponseEntity<?> getExploreList(){
        List<Explore> exploreList =  exploreService.getExploreList();
        return ResponseEntity.status(HttpStatus.OK).body(exploreList);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<String> addExplore(@RequestParam("description") String description,
                                             @RequestParam("counts") int counts,
                                             @RequestParam("file") MultipartFile file){
        try {
            exploreService.addExplore(description, counts, file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Explore Object added successfully");
        }
        catch (IOException | GeneralSecurityException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file and add explore object: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete/{exploreId}")
    public ResponseEntity<String> deleteExplore(@PathVariable("exploreId") Long exploreId){
        exploreService.deleteExplore(exploreId);
        return ResponseEntity.status(HttpStatus.OK).body("Explore Object deleted successfully");
    }

    @PutMapping(path = "/update/{exploreId}")
    public ResponseEntity<String> updateExplore(@PathVariable("exploreId") Long exploreId,
                                                @RequestParam("description") String description,
                                                @RequestParam("counts") int counts,
                                                @RequestParam("file") MultipartFile file){
        try {
            exploreService.updateExplore(exploreId, description, counts, file);
            return ResponseEntity.ok("Explore Object updated successfully");
        }
        catch (IOException | GeneralSecurityException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file and update explore object: " + e.getMessage());
        }
    }
}
