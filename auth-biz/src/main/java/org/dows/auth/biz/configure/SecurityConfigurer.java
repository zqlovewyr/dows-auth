package org.dows.auth.biz.configure;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@EnableWebSecurity
public class SecurityConfigurer {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> permitAll = new ArrayList<>();
        permitAll.add("/error");
        permitAll.add("/v3/**");
        // 暂时先放开所有请求
        permitAll.add("/**");
        permitAll.add("/swagger-ui/**");
        permitAll.add("/swagger-resources/**");
        // 基于 token，不需要 csrf
        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        return http.build();
    }
}