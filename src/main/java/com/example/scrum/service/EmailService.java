package com.example.scrum.service;

import com.example.scrum.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailSender mailSender;
    private final UserService userService;

    @Async
    public void sendEmail(String recipientAddress, String subject, String message){

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);

    }

    public void sendActivationLink(User user, String appUrl, String token){

        String recipientAddress = user.getEmail();
        String subject = "Potwierdzenie rejestracji";
        String confirmationUrl = appUrl + "/registrationConfirm?token=" + token;
        String message = "Dziękujemy za rejestrację w aplikacji wspomagającej działanie według metodyki SCRUM!\n"
                + "W celu aktywacji konta kliknij w link: http://localhost:8080" + confirmationUrl +
                "\n Powyższy link będzie ważny tylko przez nastepne 24h.";


        Thread thread = new Thread(() -> sendEmail(recipientAddress, subject, message));
        thread.start();
    }

    public void sendLinkToChangePassword(User user, String appUrl, String token){

        String recipientAddress = user.getEmail();
        String subject = "Zmiana hasła";
        String confirmationUrl = appUrl + "/password/changePassword?token=" + token;
        String message = "W celu zmiany hasła kliknij w link: http://localhost:8080" + confirmationUrl +
                "\n Powyższy link będzie ważny tylko przez nastepne 24h.";


        Thread thread = new Thread(() -> sendEmail(recipientAddress, subject, message));
        thread.start();
    }

}
