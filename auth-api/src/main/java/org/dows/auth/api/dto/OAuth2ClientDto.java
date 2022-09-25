package org.dows.auth.api.dto;

import lombok.Data;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * The type Oauth2 client dto.
 */
@Data
public class OAuth2ClientDto implements Serializable{
    private String id;
    private String clientId;
    private String clientSecret;
    private String clientName;
    private Set<String> clientAuthenticationMethods;
    private Set<String> authorizationGrantTypes;
    private Set<String> scopes;
    private Set<String> redirectUris;
    private OAuth2ClientSettings clientSettings;
    private OAuth2TokenSettings tokenSettings;


    @Data
    static class OAuth2ClientSettings implements Serializable {
        private static final long serialVersionUID = -7956711700342643896L;
        private String clientId;
        private boolean requireProofKey;
        private boolean requireAuthorizationConsent;
        private String jwkSetUrl;
        private String signingAlgorithm;


        /**
         * To client settings client settings.
         *
         * @return the client settings
         */
        public ClientSettings toClientSettings() {
            ClientSettings.Builder builder = ClientSettings.builder()
                    .requireProofKey(this.requireProofKey)
                    .requireAuthorizationConsent(this.requireAuthorizationConsent);
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.from(this.signingAlgorithm);
            JwsAlgorithm jwsAlgorithm = signatureAlgorithm == null ? MacAlgorithm.from(this.signingAlgorithm) : signatureAlgorithm;
            if (jwsAlgorithm != null) {
                builder.tokenEndpointAuthenticationSigningAlgorithm(jwsAlgorithm);
            }
            if (StringUtils.hasText(this.jwkSetUrl)) {
                builder.jwkSetUrl(jwkSetUrl);
            }
            return builder.build();
        }

        /**
         * From clientSettings to oauth2ClientSettings.
         *
         * @param clientSettings the clientSettings
         * @return the oauth2ClientSettings
         */
        public static OAuth2ClientSettings fromClientSettings(ClientSettings clientSettings) {
            OAuth2ClientSettings oAuth2ClientSettings = new OAuth2ClientSettings();
            oAuth2ClientSettings.setRequireProofKey(clientSettings.isRequireProofKey());
            oAuth2ClientSettings.setRequireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent());
            oAuth2ClientSettings.setJwkSetUrl(clientSettings.getJwkSetUrl());
            JwsAlgorithm algorithm = clientSettings.getTokenEndpointAuthenticationSigningAlgorithm();
            if (algorithm != null) {
                oAuth2ClientSettings.setSigningAlgorithm(algorithm.getName());
            }
            return oAuth2ClientSettings;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || this.getClass() != o.getClass()) return false;
            OAuth2ClientSettings that = (OAuth2ClientSettings) o;
            return clientId != null && Objects.equals(clientId, that.clientId);
        }

        @Override
        public int hashCode() {
            return getClass().hashCode();
        }
    }


    @Data
    public static class OAuth2TokenSettings implements Serializable {
        private static final long serialVersionUID = -7077164876986169673L;
        private String clientId;
        private Duration accessTokenTimeToLive;
        private String tokenFormat;
        private boolean reuseRefreshTokens = true;
        private Duration refreshTokenTimeToLive;
        private String idTokenSignatureAlgorithm;


        /**
         * To token settings token settings.
         *
         * @return the token settings
         */
        public TokenSettings toTokenSettings() {

            return TokenSettings.builder()
                    .accessTokenTimeToLive(Optional.ofNullable(this.accessTokenTimeToLive)
                            .orElse(Duration.ofMinutes(5)))
                    .accessTokenFormat(Optional.ofNullable(tokenFormat)
                            .map(OAuth2TokenFormat::new)
                            .orElse(OAuth2TokenFormat.SELF_CONTAINED))
                    .reuseRefreshTokens(this.reuseRefreshTokens)
                    .refreshTokenTimeToLive(Optional.ofNullable(this.refreshTokenTimeToLive)
                            .orElse(Duration.ofMinutes(60)))
                    .idTokenSignatureAlgorithm(Optional.ofNullable(idTokenSignatureAlgorithm)
                            .map(SignatureAlgorithm::from)
                            .orElse(SignatureAlgorithm.RS256))
                    .build();

        }

        /**
         * From tokenSettings to oauth2TokenSettings.
         *
         * @param tokenSettings the tokenSettings
         * @return the oauth2TokenSettings
         */
        public static OAuth2TokenSettings fromTokenSettings(TokenSettings tokenSettings) {
            OAuth2TokenSettings oAuth2TokenSettings = new OAuth2TokenSettings();
            oAuth2TokenSettings.setAccessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive());
            oAuth2TokenSettings.setTokenFormat(tokenSettings.getAccessTokenFormat().getValue());
            oAuth2TokenSettings.setReuseRefreshTokens(tokenSettings.isReuseRefreshTokens());
            oAuth2TokenSettings.setRefreshTokenTimeToLive(tokenSettings.getRefreshTokenTimeToLive());
            oAuth2TokenSettings.setIdTokenSignatureAlgorithm(tokenSettings.getIdTokenSignatureAlgorithm().getName());
            return oAuth2TokenSettings;
        }


    }


}
