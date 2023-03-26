package org.dows.auth.security.annotation;

import org.dows.auth.security.config.ResourceServerAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;

/**
 * 资源服务注解
 */
@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ ResourceServerAutoConfiguration.class })
public @interface EnableResourceServer {

}
