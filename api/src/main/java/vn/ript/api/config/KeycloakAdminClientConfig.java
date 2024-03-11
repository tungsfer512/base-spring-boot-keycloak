package vn.ript.api.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vn.ript.api.utils.Env;

@Configuration
public class KeycloakAdminClientConfig {

    private static String KEYCLOAK_SERVER_URL = Env.KEYCLOAK_SERVER_URL;
    private static String KEYCLOAK_REALM = Env.KEYCLOAK_REALM;
    private static String KEYCLOAK_CLIENT_ID = Env.KEYCLOAK_CLIENT_ID;
    private static String KEYCLOAK_CLIENT_SECRET = Env.KEYCLOAK_CLIENT_SECRET;

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
