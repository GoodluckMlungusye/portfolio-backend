package com.goodamcodes.controller;

import com.goodamcodes.configuration.Constant;
import com.goodamcodes.model.Skill;
import com.goodamcodes.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constant.BASE_URL + "/skill")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @GetMapping("/all")
    public ResponseEntity<?> getSkills(){
        List<Skill> skills =  skillService.getSkills();
        return ResponseEntity.status(HttpStatus.OK).body(skills);
    }

    @PostMapping("/new")
    public ResponseEntity<String> addSkill(@RequestBody Skill skill){
        skillService.addSkill(skill);
        return ResponseEntity.status(HttpStatus.CREATED).body("Skill added successfully");
    }

    @DeleteMapping(path = "/delete/{skillId}")
    public ResponseEntity<String> deleteSkill(@PathVariable("skillId") Long skillId){
        skillService.deleteSkill(skillId);
        return ResponseEntity.status(HttpStatus.OK).body("Skill deleted successfully");
    }

    @PutMapping(path = "/update/{skillId}")
    public ResponseEntity<String> updateSkill(
            @PathVariable("skillId") Long skillId,
            @RequestBody Skill skill
    )
    {
        skillService.updateSkill(skillId,skill);
        return ResponseEntity.status(HttpStatus.CREATED).body("Skill updated successfully");
    }
}
