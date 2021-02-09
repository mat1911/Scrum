package com.example.scrum.service;

import com.example.scrum.dto.ProjectDto;
import com.example.scrum.entity.*;
import com.example.scrum.exceptions.ObjectNotFoundException;
import com.example.scrum.mappers.ProjectMapper;
import com.example.scrum.repository.ProjectRepository;
import com.example.scrum.repository.StatusRepository;
import com.example.scrum.repository.UserProjectRepository;
import com.example.scrum.security.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final UserProjectRepository userProjectRepository;
    private final ProjectRepository projectRepository;
    private final StatusRepository statusRepository;
    private final UserService userService;
    private final ProjectMapper projectMapper;

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }

    public User findProjectOwner(Long projectId){
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ObjectNotFoundException("Project with a such id is not found!"))
                .getOwner();
    }

    public List<ProjectDto> getAllActiveUserProjects(Long userId) {

        return userProjectRepository
                .findAllByUserId(userId)
                .stream()
                .map(UserProject::getProject)
                .filter(project -> !project.getIsArchived())
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> getAllArchivedUserProjects(Long userId) {

        return userProjectRepository
                .findAllByUserId(userId)
                .stream()
                .map(UserProject::getProject)
                .filter(Project::getIsArchived)
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    public Project addNewProject(Long userId, ProjectDto projectDto) {

        Project project = projectMapper.toEntity(projectDto);
        User user = userService.findById(userId);

        project.setOwner(user);
        project.setIsArchived(false);
        project = projectRepository.save(project);
        assignProjectToUser(user, project);

        return project;
    }

    public Project updateProject(ProjectDto projectDto, Long projectId) {

        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ObjectNotFoundException("Project with a such id is not found!"));

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project = projectRepository.save(project);

        return project;
    }

    public UserProject addUserToProject(Long userId, Long projectId) {

        User invitedUser = userService.findById(userId);
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ObjectNotFoundException("Project with a such id is not found!"));


        return assignProjectToUser(invitedUser, project);
    }

    public ProjectDto getCurrentProjectAsDto(Long projectId) {

        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ObjectNotFoundException("Project with a such id is not found!"));

        return projectMapper.toDto(project);
    }

    public Project setProjectStatus(Long projectId, boolean isArchived){

        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ObjectNotFoundException("Project with a such id is not found!"));

        project.setIsArchived(isArchived);

        return project;
    }

    public Long deleteUserFromProject(Long userId, Long projectId) {

        return userProjectRepository
                .deleteByUserIdAndProjectId(userId, projectId);
    }

    private UserProject assignProjectToUser(User user, Project project) {

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
