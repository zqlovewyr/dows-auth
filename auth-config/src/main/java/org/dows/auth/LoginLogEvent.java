package org.dows.auth;

import org.springframework.context.ApplicationEvent;

public class LoginLogEvent extends ApplicationEvent {

    public LoginLogEvent(Object source) {
        super(source);
    }
}