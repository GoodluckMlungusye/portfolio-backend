package com.goodamcodes.service;

import com.goodamcodes.model.Education;
import com.goodamcodes.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EducationService {

    @Autowired
    private EducationRepository educationRepository;

    public List<Education> getEducationList(){
        return educationRepository.findAll();
    }

    public void addEducation(Education education){
        Optional<Education> existingEducation = educationRepository.findByProgramme(education.getProgramme());
        if(existingEducation.isPresent()){
            throw new IllegalStateException("Programme exists");
        }
        educationRepository.save(education);
    }

    public void deleteEducation(Long educationId){
        boolean isExisting = educationRepository.existsById(educationId);
        if(!isExisting){
            throw new IllegalStateException("Education does not exist");
        }
        educationRepository.deleteById(educationId);
    }

    public void updateEducation(Long educationId, Education education){
        Education existingEducation = educationRepository.findById(educationId).orElseThrow(
            () -> new IllegalStateException("Education does not exist")
        );
        existingEducation.setDescription(education.getDescription());
        existingEducation.setDuration(education.getDuration());
        existingEducation.setProgramme(education.getProgramme());
        existingEducation.setInstitute(education.getInstitute());
        educationRepository.save(existingEducation);
    }
}
