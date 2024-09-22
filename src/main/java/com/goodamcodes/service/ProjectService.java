package com.goodamcodes.service;

import com.goodamcodes.model.Project;
import com.goodamcodes.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    FileUploadService fileUploadService;

    public List<Project> getProjects(){
        return projectRepository.findAll();
    }

    public void addProject(
            String name,
            String technology,
            int rate,
            String projectLink,
            String colorCode,
            Boolean isHosted,
            MultipartFile file)
        throws IOException, GeneralSecurityException {
            Optional<Project> existingProject = projectRepository.findByName(name);
            if(existingProject.isPresent()){
                throw new IllegalStateException("Project name exists");
            }

            File convertedFile = fileUploadService.convertMultipartFileToFile(file);
            String fileUrl = fileUploadService.uploadFileToDrive(convertedFile);

            Project project = new Project();
            project.setName(name);
            project.setTechnology(technology);
            project.setRate(rate);
            project.setProjectLink(projectLink);
            project.setColorCode(colorCode);
            project.setIsHosted(isHosted);
            project.setImage(fileUrl);
            projectRepository.save(project);
    }

    public void deleteProject(Long projectId){
        boolean isExisting = projectRepository.existsById(projectId);
        if(!isExisting){
            throw new IllegalStateException("Project does not exist");
        }
        projectRepository.deleteById(projectId);

    }

    public void updateProject(
            Long projectId,
            String name,
            String technology,
            int rate,
            String projectLink,
            String colorCode,
            Boolean isHosted,
            MultipartFile file)
        throws IOException, GeneralSecurityException {
            Project existingProject = projectRepository.findById(projectId).orElseThrow(
                    () -> new IllegalStateException("Project does not exist")
            );

            File convertedFile = fileUploadService.convertMultipartFileToFile(file);
            String fileUrl = fileUploadService.uploadFileToDrive(convertedFile);

            existingProject.setName(name);
            existingProject.setTechnology(technology);
            existingProject.setRate(rate);
            existingProject.setProjectLink(projectLink);
            existingProject.setColorCode(colorCode);
            existingProject.setIsHosted(isHosted);
            existingProject.setImage(fileUrl);
            projectRepository.save(existingProject);
    }

}
