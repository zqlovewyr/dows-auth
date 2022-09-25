package org.dows.auth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    //ApplicationContextInitializer

    private List<String> uriAuthority = new ArrayList<>();

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
  /*      // 自动权限设置
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                expressionInterceptUrlRegistry = httpSecurity.authorizeRequests();

        uriAuthority.forEach(u->{
            expressionInterceptUrlRegistry.antMatchers("").hasAuthority("");
        });

        // 登录设置
        httpSecurity.formLogin()
//                .loginPage("login").loginProcessingUrl("login") // 自定义登录
                .defaultSuccessUrl("main.html")
                .failureUrl("/failed");*/

        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // 放行
                .antMatchers("/demo/**").hasAuthority("demo")
                .anyRequest().authenticated()
                .and()
//                .rememberMe().rememberMeParameter("isRemember").userDetailsService(accountDetailService) // 记住我并自定义参数
                .formLogin()
//                .loginPage("login").loginProcessingUrl("login") // 自定义登录
//                .defaultSuccessUrl("main.html")
//                .failureUrl("/failed")
                ;
        return httpSecurity.build();
    }

}
