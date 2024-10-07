package com.goodamcodes.controller;

import com.goodamcodes.configuration.Constant;
import com.goodamcodes.model.ServiceOffered;
import com.goodamcodes.service.ServiceOfferedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping(path = Constant.BASE_URL)
public class ServiceOfferedController {

    @Autowired
    private ServiceOfferedService serviceOfferedService;

    @GetMapping("/all/services")
    public ResponseEntity<?> getServiceOfferedList(){
        List<ServiceOffered> serviceOfferedList =  serviceOfferedService.getServiceOfferedList();
        return ResponseEntity.status(HttpStatus.OK).body(serviceOfferedList);
    }

    @PostMapping(value = "/new/services")
    public ResponseEntity<String> addServiceOffered( @RequestParam("description") String description,
                                                     @RequestParam("name") String name,
                                                     @RequestParam("file") MultipartFile file){
        try {
            serviceOfferedService.addServiceOffered(description, name, file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Service added successfully");
        }
        catch (IOException | GeneralSecurityException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file and add service: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete/services/{serviceOfferedId}")
    public ResponseEntity<String> deleteServiceOffered(@PathVariable("serviceOfferedId") Long serviceOfferedId){
        serviceOfferedService.deleteServiceOffered(serviceOfferedId);
        return ResponseEntity.status(HttpStatus.OK).body("Service deleted successfully");
    }

    @PutMapping(path = "/update/services/{serviceOfferedId}")
    public ResponseEntity<String> updateServiceOffered(  @PathVariable("serviceOfferedId") Long serviceOfferedId,
                                                         @RequestParam("description") String description,
                                                         @RequestParam("name") String name,
                                                         @RequestParam("file") MultipartFile file){
        try {
            serviceOfferedService.updateServiceOffered(serviceOfferedId, description, name, file);
            return ResponseEntity.ok("Service added successfully");
        }
        catch (IOException | GeneralSecurityException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file and update service: " + e.getMessage());
        }
    }
}
