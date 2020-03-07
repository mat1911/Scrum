package com.example.scrum.service;

import com.example.scrum.dto.UserInvitationDto;
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
        String subject = "Confirmation of registration";
        String confirmationUrl = appUrl + "/registrationConfirm?token=" + token;
        String message = "Thank you for registering in the application supporting work according to the SCRUM methodology!\n"
                + "In order to activate your account click on the link: http://localhost:8080" + confirmationUrl +
                "\n The above link will be valid only for the next 24 hours.";


        Thread thread = new Thread(() -> sendEmail(recipientAddress, subject, message));
        thread.start();
    }

    public void sendLinkToChangePassword(User user, String appUrl, String token){

        String recipientAddress = user.getEmail();
        String subject = "Change password";
        String confirmationUrl = appUrl + "/password/changePassword?token=" + token;
        String message = "To change your password click on the link: http://localhost:8080" + confirmationUrl +
                "\n The above link will only be valid for the next 24 hours.";


        Thread thread = new Thread(() -> sendEmail(recipientAddress, subject, message));
        thread.start();
    }

    public void sendInvitationToProject(UserInvitationDto userInvitationDto, User projectOwner, String appUrl, String token){

        String recipientAddress = userInvitationDto.getEmail();
        String subject = "Invitation to project";
        String confirmationUrl = appUrl + "/acceptInvitation?token=" + token + "&projectId=" + userInvitationDto.getProjectId();
        String message = projectOwner.getFirstName() + " " + projectOwner.getLastName() +
                " invited you to the project. To accept this invitation click on link: http://localhost:8080" + confirmationUrl;

        Thread thread = new Thread(() -> sendEmail(recipientAddress, subject, message));
        thread.start();
    }

}
