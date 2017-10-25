package ru.masharan.model.events;

import lombok.Data;
import org.springframework.context.ApplicationEvent;
import ru.masharan.model.entity.User;

import java.util.Locale;

/**
 * This event after user was stored to bd, due to invoke email confirmation.
 * @author mash0916
 */
@Data
public class CompleteRegistrationEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final User user;


    public CompleteRegistrationEvent(User user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;

    }
}
