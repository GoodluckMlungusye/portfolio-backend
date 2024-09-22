package com.goodamcodes.service;
import com.goodamcodes.model.SubSkill;
import com.goodamcodes.repository.SubSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubSkillService {

    @Autowired
    private SubSkillRepository subSkillRepository;

    public List<SubSkill> getSubSkills(){
        return subSkillRepository.findAll();
    }

    public void addSubSkill(SubSkill subSkill){
        Optional<SubSkill> existingSubSkill = subSkillRepository.findByName(subSkill.getName());
        if(existingSubSkill.isPresent()){
            throw new IllegalStateException("SubSkill exists");
        }
        subSkillRepository.save(subSkill);
    }

    public void deleteSubSkill(Long subSkillId){
        boolean isExisting = subSkillRepository.existsById(subSkillId);
        if(!isExisting){
            throw new IllegalStateException("SubSkill does not exist");
        }
        subSkillRepository.deleteById(subSkillId);
    }

    public void updateSubSkill(Long subSkillId, SubSkill subSkill){
        SubSkill existingSubSkill = subSkillRepository.findById(subSkillId).orElseThrow(
                () -> new IllegalStateException("SubSkill does not exist")
        );
        SubSkill subSkillWithSameName = subSkillRepository.findByName(subSkill.getName()).orElse(null);
        if(subSkillWithSameName != null && !subSkillWithSameName.getId().equals(subSkillId)){
            throw new IllegalStateException("SubSkill exists");
        }
        existingSubSkill.setName(subSkill.getName());
        existingSubSkill.setSkill(subSkill.getSkill());
        existingSubSkill.setPercentageLevel(subSkill.getPercentageLevel());
        existingSubSkill.setSkillClass(subSkill.getSkillClass());
        subSkillRepository.save(existingSubSkill);
    }
}
