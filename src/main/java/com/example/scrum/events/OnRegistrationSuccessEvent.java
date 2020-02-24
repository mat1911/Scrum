package com.example.scrum.events;

import com.example.scrum.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnRegistrationSuccessEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private String appUrl;
    private User user;

    public OnRegistrationSuccessEvent(String appUrl, User user) {
        super(user);
        this.appUrl = appUrl;
        this.user = user;
    }
}
