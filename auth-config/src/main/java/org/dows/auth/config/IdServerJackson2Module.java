package org.dows.auth.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;

public class IdServerJackson2Module extends SimpleModule {

    public IdServerJackson2Module() {
        super(IdServerJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        //context.setMixInAnnotations(CaptchaAuthenticationToken.class, CaptchaAuthenticationTokenMixin.class);
        //context.setMixInAnnotations(MiniAppAuthenticationToken.class, MiniAppAuthenticationTokenMixin.class);
    }
}
