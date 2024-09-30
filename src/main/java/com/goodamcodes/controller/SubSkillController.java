package com.goodamcodes.controller;

import com.goodamcodes.configuration.Constant;
import com.goodamcodes.model.SubSkill;
import com.goodamcodes.service.SubSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constant.BASE_URL + "/subSkill")
public class SubSkillController {
    @Autowired
    private SubSkillService subSkillService;

    @GetMapping("/all")
    public ResponseEntity<?> getSubSkills(){
        List<SubSkill> subSkills =  subSkillService.getSubSkills();
        return ResponseEntity.status(HttpStatus.OK).body(subSkills);
    }

    @PostMapping("/new")
    public ResponseEntity<String> addSubSkill(@RequestBody SubSkill subSkill){
        subSkillService.addSubSkill(subSkill);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sub-skill added successfully");
    }

    @DeleteMapping(path = "/delete/{subSkillId}")
    public ResponseEntity<String> deleteSubSkill(@PathVariable("subSkillId") Long subSkillId){
        subSkillService.deleteSubSkill(subSkillId);
        return ResponseEntity.status(HttpStatus.OK).body("Sub-skill deleted successfully");
    }

    @PutMapping(path = "/update/{subSkillId}")
    public ResponseEntity<String> updateSubSkill(
            @PathVariable("subSkillId") Long subSkillId,
            @RequestBody SubSkill subSkill
    )
    {
        subSkillService.updateSubSkill(subSkillId,subSkill);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sub-skill updated successfully");
    }
}
