package com.example.scrum.controllers;


import com.example.scrum.dto.UserRegisterDto;
import com.example.scrum.entity.User;
import com.example.scrum.entity.VerificationToken;
import com.example.scrum.events.OnRegistrationSuccessEvent;
import com.example.scrum.service.UserService;
import com.example.scrum.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;
    private final UserValidator userValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(userValidator);
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){

        model.addAttribute("user", new UserRegisterDto());
        model.addAttribute("errors", new HashMap<>());

        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(
            @Valid @ModelAttribute UserRegisterDto userRegisterDto,
            BindingResult bindingResult, Model model, WebRequest request
    ){

        if(bindingResult.hasErrors()){

            var errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getCode,
                            (message1, message2) -> String.join("/n", message1, message2)
                    ));

            model.addAttribute("user", userRegisterDto);
            model.addAttribute("errors", errors);

            return "register";
        }

        User addedUser = userService.addNewUser(userRegisterDto);

        try{
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationSuccessEvent(appUrl, addedUser));
        }catch (Exception e){
            System.out.println(e.getMessage());
            model.addAttribute("messageHeader", "WYSTĄPIŁ PROBLEM!");
            model.addAttribute("message", "Email z linkiem do aktywacji Twojego konta nie został wysłany. Spróbuj ponownie później.");
            return "infoPages/informationPage";
        }

        model.addAttribute("messageHeader", "REJESTRACJA UDANA");
        model.addAttribute("message", "W celu aktywacji swojego konta udaj się na podanego w procesie rejestracji maila i wykorzystaj zamieszczony w mailu od nas link!");
        return "infoPages/informationPage";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token){

        VerificationToken verificationToken = userService.getVerificationToken(token);
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if((verificationToken.getExpireDate().getTime() - calendar.getTime().getTime()) <= 0){
            model.addAttribute("messageHeader", "KONTO NIE ZOSTAŁO AKTYWOWANE!");
            model.addAttribute("message", "Twoje konto nie zostało aktywowane. Może być to spowodowane wygaśnięciem ważności linku aktywacyjnego.");
            return "infoPages/informationPage";
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);

        return "redirect:/loginSuccess";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(){
        return "redirect:/";
    }

}
