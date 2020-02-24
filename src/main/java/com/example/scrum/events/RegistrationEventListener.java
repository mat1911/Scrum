package com.example.scrum.events;

import com.example.scrum.entity.User;
import com.example.scrum.service.EmailServiceImpl;
import com.example.scrum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationEventListener implements ApplicationListener<OnRegistrationSuccessEvent> {

    private final UserService userService;
    private final EmailServiceImpl emailService;


    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Potwierdzenie rejestracji";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = "Dziękujemy za rejestrację w aplikacji wspomagającej działanie według metodyki SCRUM!\n"
                + "W celu aktywacji konta kliknij w link: http://localhost:8080" + confirmationUrl +
                "\n Powyższy link będzie ważny tylko przez nastepne 24h.";

        emailService.sendEmail(recipientAddress, subject, message);
    }
}
