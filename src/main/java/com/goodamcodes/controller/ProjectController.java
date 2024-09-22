package com.goodamcodes.controller;

import com.goodamcodes.model.Constant;
import com.goodamcodes.model.Project;
import com.goodamcodes.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping(path = Constant.BASE_URL + "/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/all")
    public ResponseEntity<?> getProjects(){
        List<Project> projects =  projectService.getProjects();
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<String> addProject(@RequestParam("name") String name,
                                             @RequestParam("technology") String technology,
                                             @RequestParam("projectLink") String projectLink,
                                             @RequestParam("rate") int rate,
                                             @RequestParam("colorCode") String colorCode,
                                             @RequestParam("isHosted") Boolean isHosted,
                                             @RequestParam("file") MultipartFile file){
        try {
                projectService.addProject(
                        name,
                        technology,
                        rate,
                        projectLink,
                        colorCode,
                        isHosted,
                        file
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Project added successfully");
        }
        catch (IOException | GeneralSecurityException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file and add project: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long projectId){
        projectService.deleteProject(projectId);
        return ResponseEntity.status(HttpStatus.OK).body("Project deleted successfully");
    }

    @PutMapping(path = "/update/{projectId}")
    public ResponseEntity<String> updateProject( @PathVariable("projectId") Long projectId,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("technology") String technology,
                                                 @RequestParam("projectLink") String projectLink,
                                                 @RequestParam("rate") int rate,
                                                 @RequestParam("colorCode") String colorCode,
                                                 @RequestParam("isHosted") Boolean isHosted,
                                                 @RequestParam("file") MultipartFile file){
        try {
            projectService.updateProject(
                    projectId,
                    name,
                    technology,
                    rate,
                    projectLink,
                    colorCode,
                    isHosted,
                    file
            );
            return ResponseEntity.ok("Project updated successfully");
        }
        catch (IOException | GeneralSecurityException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file and update project: " + e.getMessage());
        }
    }
}
