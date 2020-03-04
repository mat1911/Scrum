package com.example.scrum.service;

import com.example.scrum.dto.ProjectDto;
import com.example.scrum.entity.Project;
import com.example.scrum.entity.User;
import com.example.scrum.entity.UserProject;
import com.example.scrum.entity.UserProjectId;
import com.example.scrum.mappers.ProjectMapper;
import com.example.scrum.repository.ProjectRepository;
import com.example.scrum.repository.UserProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final UserProjectRepository userProjectRepository;
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectMapper projectMapper;


    public List<ProjectDto> getAllUserProjects(Long userId){

        return userProjectRepository
                .findAllByUserId(userId)
                .stream()
                .map(UserProject::getProject)
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    public Project addNewProject(Long userId, ProjectDto projectDto){

        Project project = projectMapper.toEntity(projectDto);
        User user = userService.findById(userId);


        System.out.println(project.getName());
        System.out.println(project.getDescription());

        project = projectRepository.save(project);
        assignProjectToUser(user, project);

        return project;
    }

    private UserProject assignProjectToUser(User user, Project project){

        UserProjectId userProjectId = new UserProjectId(user.getId(), project.getId());

        UserProject userProject = UserProject.builder()
                .id(userProjectId)
                .user(user)
                .project(project)
                .userCapacity(0)
                .build();

        return userProjectRepository.save(userProject);
    }

}
