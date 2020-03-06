package com.example.scrum.controllers;

import com.example.scrum.dto.StoriesDtoList;
import com.example.scrum.dto.StoryDto;
import com.example.scrum.service.SprintService;
import com.example.scrum.service.StoryService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.Semaphore;

@Controller
@RequiredArgsConstructor
public class KanbanController {

    private final StoryService storyService;
    private final SprintService sprintService;
    private Semaphore synchKanbanMethods = new Semaphore(1);

    @GetMapping("/kanbanBoard")
    @Synchronized
    public String kanban(Model model, HttpServletRequest request){

        Long projectId = (Long) request.getSession().getAttribute("projectId");
        List<StoryDto> stories = storyService.getStoriesFromCurrentSprint(projectId);
        model.addAttribute("stories", stories);

        return "kanbanBoard";
    }

    @PostMapping("/kanbanBoard/{id}")
    @Synchronized
    public String getKanbanBoard(Model model, HttpServletRequest request, @PathVariable Long id){

        request.getSession().setAttribute("projectId", id);
        request.getSession().setAttribute("sprintId", sprintService.getCurrentSprint(id).getId());

        List<StoryDto> stories = storyService.getStoriesFromCurrentSprint(id);
        model.addAttribute("stories", stories);

        return "kanbanBoard";
    }

    @PostMapping("/updateStories")
    @ResponseBody
    @Synchronized
    public void updateStories(@RequestBody StoriesDtoList stories, HttpServletRequest request){

        Long projectId = (Long) request.getSession().getAttribute("projectId");
        Long sprintId = (Long) request.getSession().getAttribute("sprintId");

        stories.getStories().forEach(x -> System.out.println(x.getTitle() + " " + x.getStatus().getName()));

        storyService.updateStories(stories, projectId, sprintId);
    }

}
