package com.example.scrum.controllers;


import com.example.scrum.dto.UserRegisterDto;
import com.example.scrum.entity.User;
import com.example.scrum.entity.VerificationToken;
import com.example.scrum.service.EmailService;
import com.example.scrum.service.UserService;
import com.example.scrum.validators.UserValidator;
import lombok.RequiredArgsConstructor;
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

    private final UserService userService;
    private final UserValidator userValidator;
    private final EmailService emailService;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(userValidator);
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {

        model.addAttribute("user", new UserRegisterDto());
        model.addAttribute("errors", new HashMap<>());

        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(
            @Valid @ModelAttribute UserRegisterDto userRegisterDto,
            BindingResult bindingResult, Model model, WebRequest request
    ) {

        if (bindingResult.hasErrors()) {

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
        String appUrl = request.getContextPath();
        String token = userService.createVerificationToken(addedUser);
        emailService.sendActivationLink(addedUser, appUrl, token);

        model.addAttribute("messageHeader", "REGISTRATION DONE");
        model.addAttribute("message", "In order to activate your account, go to the e-mail " +
                "given in the registration process and use the link provided in the e-mail from us! " +
                "If you did not receive the message, try registering again later.");

        return "infoPages/informationPage";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

        VerificationToken verificationToken = userService.getVerificationToken(token);
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if ((verificationToken.getExpireDate().getTime() - calendar.getTime().getTime()) <= 0) {
            model.addAttribute("messageHeader", "ACCOUNT IS NOT ACTIVATED!");
            model.addAttribute("message", "Your account has not been activated. This may be due to the expiry of the activation link.");
            return "infoPages/informationPage";
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);

        return "redirect:/loginSuccess";
    }

}
