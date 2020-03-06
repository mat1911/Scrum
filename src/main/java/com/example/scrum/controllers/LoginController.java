package com.example.scrum.controllers;

import com.example.scrum.dto.UserPasswordRecoverDto;
import com.example.scrum.entity.User;
import com.example.scrum.exceptions.ProjectNotSelectedException;
import com.example.scrum.service.EmailService;
import com.example.scrum.service.UserService;
import com.example.scrum.validators.PasswordRecoveryFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final PasswordRecoveryFormValidator passwordRecoveryFormValidator;
    private final UserService userService;
    private final EmailService emailService;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(passwordRecoveryFormValidator);
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/password/remindPassword")
    public String getRemindPasswordPage(Model model) {

        model.addAttribute("user", new UserPasswordRecoverDto());
        model.addAttribute("errors", new HashMap<>());

        return "password/remindPassword";
    }

    @PostMapping("/password/remindPassword")
    public String remindPassword(
            @Valid @ModelAttribute UserPasswordRecoverDto userPasswordRecover,
            BindingResult bindingResult, Model model, WebRequest request){

        if(bindingResult.hasErrors()){
            var errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getCode,
                            (message1, message2) -> String.join("/n", message1, message2)
                    ));

            model.addAttribute("user", userPasswordRecover);
            model.addAttribute("errors", errors);

            return "password/remindPassword";
        }

        if(userService.existsByEmail(userPasswordRecover.getEmail())){
            User user = userService.findByEmail(userPasswordRecover.getEmail());
            String token = userService.createVerificationToken(user);
            String appUrl = request.getContextPath();

            emailService.sendLinkToChangePassword(user, appUrl, token);
        }

        model.addAttribute("messageHeader", "LINK DO ZMIANY HASŁA WYSŁANY!");
        model.addAttribute("message", "Jeśli podany przez Ciebie email znajduje się w bazie danych" +
                "wkrótce otrzymasz link.\n W przypadku nie otrzymania linku spróbuj ponownie później.");


        return "infoPages/informationPage";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess() {


        return "redirect:/";
    }

}
