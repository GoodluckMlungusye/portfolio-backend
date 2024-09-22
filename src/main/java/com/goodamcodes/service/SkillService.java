package com.goodamcodes.service;

import com.goodamcodes.model.Skill;
import com.goodamcodes.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> getSkills(){
        return skillRepository.findAll();
    }

    public void addSkill(Skill skill){
        Optional<Skill> existingSkill = skillRepository.findByName(skill.getName());
        if(existingSkill.isPresent()){
            throw new IllegalStateException("Skill exists");
        }
        skillRepository.save(skill);
    }

    public void deleteSkill(Long skillId){
        boolean isExisting = skillRepository.existsById(skillId);
        if(!isExisting){
            throw new IllegalStateException("Skill does not exist");
        }
        skillRepository.deleteById(skillId);
    }

    public void updateSkill(Long skillId, Skill skill){
        Skill existingSkill = skillRepository.findById(skillId).orElseThrow(
                () -> new IllegalStateException("Skill does not exist")
        );
        Skill skillWithSameName = skillRepository.findByName(skill.getName()).orElse(null);
        if(skillWithSameName != null && !skillWithSameName.getId().equals(skillId)){
            throw new IllegalStateException("Skill exists");
        }
        existingSkill.setName(skill.getName());
        existingSkill.setSubSkillList(skill.getSubSkillList());
        skillRepository.save(existingSkill);
    }
}
