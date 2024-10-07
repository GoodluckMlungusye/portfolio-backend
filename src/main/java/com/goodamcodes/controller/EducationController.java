package com.goodamcodes.controller;

import com.goodamcodes.configuration.Constant;
import com.goodamcodes.model.Education;
import com.goodamcodes.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Constant.BASE_URL)
public class EducationController {

    @Autowired
    private EducationService educationService;

    @GetMapping("/all/education")
    public ResponseEntity<?> getEducationList(){
        List<Education> educationList =  educationService.getEducationList();
        return ResponseEntity.status(HttpStatus.OK).body(educationList);
    }

    @PostMapping("/new/education")
    public ResponseEntity<String>  addEducation(@RequestBody Education education){
        educationService.addEducation(education);
        return ResponseEntity.status(HttpStatus.CREATED).body("Education added successfully");
    }

    @DeleteMapping(path = "/delete/education/{educationId}")
    public ResponseEntity<String> deleteEducation(@PathVariable("educationId") Long educationId){
        educationService.deleteEducation(educationId);
        return ResponseEntity.status(HttpStatus.OK).body("Education deleted successfully");
    }

    @PutMapping(path = "/update/education/{educationId}")
    public ResponseEntity<String> updateEducation(
            @PathVariable("educationId") Long educationId,
            @RequestBody Education education
    )
    {
        educationService.updateEducation(educationId,education);
        return ResponseEntity.status(HttpStatus.CREATED).body("Education updated successfully");
    }
}
