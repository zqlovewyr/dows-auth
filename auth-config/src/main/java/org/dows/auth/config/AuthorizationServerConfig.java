//package org.dows.auth.config;
//
//import com.fasterxml.jackson.databind.Module;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nimbusds.jose.jwk.JWKSet;
//import com.nimbusds.jose.jwk.RSAKey;
//import com.nimbusds.jose.jwk.source.JWKSource;
//import com.nimbusds.jose.proc.SecurityContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.jackson2.SecurityJackson2Modules;
//import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
//import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
//import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
//import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
//
//import java.util.List;
//
//@Configuration(proxyBeanMethods = false)
//public class AuthorizationServerConfig {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Bean
//    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
//        return new JdbcRegisteredClientRepository(jdbcTemplate);
//    }
//
//    @Bean
//    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
//        ClassLoader classLoader = AuthorizationServerConfig.class.getClassLoader();
//        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
//        this.objectMapper.registerModules(securityModules);
//        this.objectMapper.registerModules(new OAuth2AuthorizationServerJackson2Module(),new IdServerJackson2Module());
//
//        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
//    }
//
//    @Bean
//    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
//        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
//    }
//
//    @Bean
//    public JWKSource<SecurityContext> jwkSource() {
//        RSAKey rsaKey = Jwks.generateRsa();
//        JWKSet jwkSet = new JWKSet(rsaKey);
//        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
//    }
//
//    @Bean
//    public ProviderSettings providerSettings() {
//        return ProviderSettings.builder().issuer("http://auth-server:9000").build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//}
