package com.goodamcodes.service;

import com.goodamcodes.model.NavigationLink;
import com.goodamcodes.repository.NavigationLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NavigationLinkService {

    @Autowired
    private NavigationLinkRepository navigationLinkRepository;

    public List<NavigationLink> getNavigationLinks(){
        return navigationLinkRepository.findAll();
    }

    public void addNavigationLink(NavigationLink navigationLink){
        Optional<NavigationLink> existingNavigationLink = navigationLinkRepository.findByName(navigationLink.getName());
        if(existingNavigationLink.isPresent()){
            throw new IllegalStateException("Navigation Link exists");
        }
        navigationLinkRepository.save(navigationLink);
    }

    public void deleteNavigationLink(Long navigationLinkId){
        boolean isExisting = navigationLinkRepository.existsById(navigationLinkId);
        if(!isExisting){
            throw new IllegalStateException("Navigation Link does not exist");
        }
        navigationLinkRepository.deleteById(navigationLinkId);
    }

    public void updateNavigationLink(Long navigationLinkId, NavigationLink navigationLink){
        NavigationLink existingNavigationLink = navigationLinkRepository.findById(navigationLinkId).orElseThrow(
                () -> new IllegalStateException("Navigation Link does not exist")
        );
        NavigationLink navigationLinkWithSameName = navigationLinkRepository.findByName(navigationLink.getName()).orElse(null);
        if(navigationLinkWithSameName != null && !navigationLinkWithSameName.getId().equals(navigationLinkId)){
            throw new IllegalStateException("Navigation link exists");
        }
        existingNavigationLink.setName(navigationLink.getName());
        navigationLinkRepository.save(existingNavigationLink);
    }
}
