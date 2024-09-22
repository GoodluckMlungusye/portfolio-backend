package com.goodamcodes.service;

import com.goodamcodes.model.ServiceOffered;
import com.goodamcodes.repository.ServiceOfferedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceOfferedService {

    @Autowired
    private ServiceOfferedRepository serviceOfferedRepository;

    @Autowired
    private FileUploadService fileUploadService;

    public List<ServiceOffered> getServiceOfferedList(){
        return serviceOfferedRepository.findAll();
    }

    public void addServiceOffered(String description, String name, MultipartFile file)
            throws IOException, GeneralSecurityException {
                Optional<ServiceOffered> existingServiceOffered = serviceOfferedRepository.findByName(name);
                if(existingServiceOffered.isPresent()){
                    throw new IllegalStateException("Service exists");
                }

                File convertedFile = fileUploadService.convertMultipartFileToFile(file);
                String fileUrl = fileUploadService.uploadFileToDrive(convertedFile);

                ServiceOffered serviceOffered = new ServiceOffered();
                serviceOffered.setDescription(description);
                serviceOffered.setName(name);
                serviceOffered.setImage(fileUrl);
                serviceOfferedRepository.save(serviceOffered);
    }

    public void deleteServiceOffered(Long serviceOfferedId){
        boolean isExisting = serviceOfferedRepository.existsById(serviceOfferedId);
        if(!isExisting){
            throw new IllegalStateException("Service does not exist");
        }
        serviceOfferedRepository.deleteById(serviceOfferedId);
    }

    public void updateServiceOffered(Long serviceOfferedId, String description, String name, MultipartFile file)
            throws IOException, GeneralSecurityException {
                ServiceOffered existingServiceOffered = serviceOfferedRepository.findById(serviceOfferedId).orElseThrow(
                        () -> new IllegalStateException("Service does not exist")
                );

                File convertedFile = fileUploadService.convertMultipartFileToFile(file);
                String fileUrl = fileUploadService.uploadFileToDrive(convertedFile);

                existingServiceOffered.setDescription(description);
                existingServiceOffered.setName(name);
                existingServiceOffered.setImage(fileUrl);
                serviceOfferedRepository.save(existingServiceOffered);
    }
}
