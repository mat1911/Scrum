package com.example.scrum.controllers;

import com.example.scrum.dto.PasswordDto;
import com.example.scrum.entity.User;
import com.example.scrum.entity.VerificationToken;
import com.example.scrum.service.UserService;
import com.example.scrum.validators.NewPasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UpdatePasswordController {

    private final NewPasswordValidator passwordValidator;
    private final UserService userService;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(passwordValidator);
    }

    @GetMapping("/password/changePassword")
    public String changePassword(Model model, @RequestParam("token") String token) {

        VerificationToken verificationToken = userService.getVerificationToken(token);
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if ((verificationToken.getExpireDate().getTime() - calendar.getTime().getTime()) <= 0) {
            model.addAttribute("messageHeader", "LINK WYGASŁ!");
            model.addAttribute("message", "Nie możesz zmienić hasła, ponieważ link wygasł!");
            return "infoPages/informationPage";
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);

        model.addAttribute("userId", user.getId());
        model.addAttribute("errors", new HashMap<>());
        model.addAttribute("passwordDto", new PasswordDto());

        return "password/updatePassword";
    }

    @PostMapping("/password/changePasswordForm/{id}")
    public String getChangePasswordForm(
            @Valid @ModelAttribute PasswordDto passwordDto,
            BindingResult bindingResult, Model model, @PathVariable Long id) {

        if (bindingResult.hasErrors()) {

            var errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getCode,
                            (message1, message2) -> String.join("/n", message1, message2)
                    ));

            model.addAttribute("passwordDto", passwordDto);
            model.addAttribute("errors", errors);

            return "password/updatePassword";
        }

        userService.changeUserPassword(id, passwordDto.getPassword());


        model.addAttribute("messageHeader", "HASŁO ZOSTAŁO ZMIENIONE");
        model.addAttribute("message", "Twoje hasło zostało zmienione. Możesz zalogować się na swoje konto.");
        return "infoPages/informationPage";
    }
}
