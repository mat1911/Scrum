package com.example.scrum.controllers;

import com.example.scrum.entity.Story;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class KanbanController {

    @GetMapping("/kanbanBoard")
    public String kanban(Model model, HttpServletRequest request){

        Long id = (Long) request.getSession().getAttribute("projectId");

        System.out.println(id);

        List<Story> stories = List.of(
                Story.builder()
                        .title("Historyjka")
                        .description("Opis historyjki")
                        .shortDescription("Krótki opis hiustoryjki")
                        .number(2L)
                        .storyPoints(5)
                        .acceptanceCriteria("Musi działać")
                        .build()
        );

        model.addAttribute("stories", stories);

        return "kanbanBoard";
    }

    @PostMapping("/kanbanBoard/{id}")
    public String getKanbanBoard(Model model, HttpServletRequest request, @PathVariable Long id){

        request.getSession().setAttribute("projectId", id);

        List<Story> stories = List.of(
                Story.builder()
                        .title("Historyjka")
                        .description("Opis historyjki")
                        .shortDescription("Krótki opis hiustoryjki")
                        .number(2L)
                        .storyPoints(5)
                        .acceptanceCriteria("Musi działać")
                        .build()
        );

        model.addAttribute("stories", stories);

        return "kanbanBoard";
    }

}
