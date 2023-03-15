package org.dows.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

@Configuration
@RequiredArgsConstructor
public class LoginLogListener {


    @Async
    @EventListener(LoginLogEvent.class)
    public void saveSysLog(LoginLogEvent event) {
    }

}