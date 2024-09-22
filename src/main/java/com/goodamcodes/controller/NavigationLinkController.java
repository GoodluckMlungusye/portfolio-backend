package com.goodamcodes.controller;

import com.goodamcodes.model.Constant;
import com.goodamcodes.model.NavigationLink;
import com.goodamcodes.service.NavigationLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constant.BASE_URL + "/link")
public class NavigationLinkController {

    @Autowired
    private NavigationLinkService navigationLinkService;

    @GetMapping("/all")
    public ResponseEntity<?> getNavigationLinks(){
        List<NavigationLink> links =  navigationLinkService.getNavigationLinks();
        return ResponseEntity.status(HttpStatus.OK).body(links);
    }

    @PostMapping("/new")
    public ResponseEntity<String> addNavigationLink(@RequestBody NavigationLink navigationLink){
        navigationLinkService.addNavigationLink(navigationLink);
        return ResponseEntity.status(HttpStatus.CREATED).body("Navigation link added successfully");
    }

    @DeleteMapping(path = "/delete/{navigationLinkId}")
    public ResponseEntity<String> deleteNavigationLink(@PathVariable("navigationLinkId") Long navigationLinkId){
        navigationLinkService.deleteNavigationLink(navigationLinkId);
        return ResponseEntity.status(HttpStatus.OK).body("Navigation link deleted successfully");
    }

    @PutMapping(path = "/update/{navigationLinkId}")
    public ResponseEntity<String> updateNavigationLink(
            @PathVariable("navigationLinkId") Long navigationLinkId,
            @RequestBody NavigationLink navigationLink
            )
    {
        navigationLinkService.updateNavigationLink(navigationLinkId,navigationLink);
        return ResponseEntity.status(HttpStatus.CREATED).body("Navigation link updated successfully");
    }
}