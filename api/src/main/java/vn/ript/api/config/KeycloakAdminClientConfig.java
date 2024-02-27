package vn.ript.api.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminClientConfig {

    @Value("${ript.keycloak.server-url}")
    private static String KEYCLOAK_SERVER_URL;
    @Value("${ript.keycloak.realm}")
    private static String KEYCLOAK_REALM;
    @Value("${ript.keycloak.client-id}")
    private static String KEYCLOAK_CLIENT_ID;
    @Value("${ript.keycloak.client-secret}")
    private static String KEYCLOAK_CLIENT_SECRET;

    @Bean
    Keycloak keycloak() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(KEYCLOAK_SERVER_URL)
                .realm(KEYCLOAK_REALM)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(KEYCLOAK_CLIENT_ID)
                .clientSecret(KEYCLOAK_CLIENT_SECRET)
                .build();
        return keycloak;
    }
}
