package com.goodamcodes.service;

import com.goodamcodes.model.Explore;
import com.goodamcodes.repository.ExploreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
@Service
public class ExploreService {

    @Autowired
    private ExploreRepository exploreRepository;

    @Autowired
    private FileUploadService fileUploadService;

    public List<Explore> getExploreList(){
        return exploreRepository.findAll();
    }

    public void addExplore(String description, int counts, MultipartFile file)
            throws IOException, GeneralSecurityException {
                    File convertedFile = fileUploadService.convertMultipartFileToFile(file);
                    String fileUrl = fileUploadService.uploadFileToDrive(convertedFile);

                    Explore explore = new Explore();
                    explore.setDescription(description);
                    explore.setCounts(counts);
                    explore.setImage(fileUrl);
                    exploreRepository.save(explore);
    }

    public void deleteExplore(Long exploreId){
        boolean isExisting = exploreRepository.existsById(exploreId);
        if(!isExisting){
            throw new IllegalStateException("Explore Object does not exist");
        }
        exploreRepository.deleteById(exploreId);
    }

    public void updateExplore(Long exploreId, String description, int counts, MultipartFile file)
            throws IOException, GeneralSecurityException {
                    Explore existingExplore = exploreRepository.findById(exploreId).orElseThrow(
                            () -> new IllegalStateException("Explore Item does not exist")
                    );

                    File convertedFile = fileUploadService.convertMultipartFileToFile(file);
                    String fileUrl = fileUploadService.uploadFileToDrive(convertedFile);

                    existingExplore.setDescription(description);
                    existingExplore.setCounts(counts);
                    existingExplore.setImage(fileUrl);
                    exploreRepository.save(existingExplore);
    }
}
