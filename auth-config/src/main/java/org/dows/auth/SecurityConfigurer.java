package org.dows.auth;

import lombok.extern.slf4j.Slf4j;
import org.dows.auth.filter.AuthenticationTokenFilter;
import org.dows.auth.filter.ValidateCodeFilter;
import org.dows.auth.handler.DowsAuthenticationFailHandler;
import org.dows.auth.handler.DowsAuthenticationSuccessHandler;
import org.dows.auth.handler.DowsLogoutSuccessHandler;
import org.dows.auth.handler.TokenAuthenticationFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Slf4j
//@EnableWebSecurity
public class SecurityConfigurer {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthIgnoreConfig authIgnoreConfig;

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> permitAll = authIgnoreConfig.getIgnoreUrls();
        permitAll.add("/error");
        permitAll.add("/v3/**");
        // 暂时先放开所有请求
        permitAll.add("/**");
        permitAll.add("/swagger-ui/**");
        permitAll.add("/swagger-resources/**");
        permitAll.add(Constant.TOKEN_ENTRY_POINT_URL);
        permitAll.add(Constant.TOKEN_LOGOUT_URL);
        String[] urls = permitAll.stream().distinct().toArray(String[]::new);

        // 基于 token，不需要 csrf
        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        // 跨域配置
        http.cors().configurationSource(corsConfigurationSource());

        // 基于 token，不需要 session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 权限
        http.authorizeRequests(authorize ->
                // 开放权限
                authorize.antMatchers(urls).permitAll()
                        // 其他地址的访问均需验证权限
                        .anyRequest().authenticated());
        // 设置登录URL
        http.formLogin()
                .loginProcessingUrl(Constant.TOKEN_ENTRY_POINT_URL)
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler());
        // 设置退出URL
        http.logout()
                .logoutUrl(Constant.TOKEN_LOGOUT_URL)
                .logoutSuccessUrl("/sys/logout")
                .addLogoutHandler(logoutHandler());
        // 如果不用验证码，注释这个过滤器即可
        http.addFilterBefore(new ValidateCodeFilter(redisTemplate, authenticationFailureHandler()), UsernamePasswordAuthenticationFilter.class);
        // token 验证过滤器
        http.addFilterBefore(new AuthenticationTokenFilter(authenticationManager(), authIgnoreConfig, redisTemplate), UsernamePasswordAuthenticationFilter.class);
        // 认证异常处理
        http.exceptionHandling().authenticationEntryPoint(new TokenAuthenticationFailHandler());
        // 用户管理service
        http.userDetailsService(userDetailsService());
        return http.build();
    }*/

    // 解决跨域
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 开放静态资源权限
        return web -> web.ignoring().antMatchers("/actuator/**", "/css/**", "/error");
    }

    @Bean
    public DowsDaoAuthenticationProvider authenticationProvider() {
        DowsDaoAuthenticationProvider authenticationProvider = new DowsDaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
//        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

   /* @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(authenticationProvider()));
    }*/

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new DowsAuthenticationFailHandler();
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new DowsLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new DowsAuthenticationSuccessHandler();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new DowsUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}