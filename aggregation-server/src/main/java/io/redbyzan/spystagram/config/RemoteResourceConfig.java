package io.redbyzan.spystagram.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

/**
 * Created by roger on 2016. 5. 2..
 */
@Configuration
public class RemoteResourceConfig {

    @Bean
    @Qualifier("resource1")
    public OAuth2ProtectedResourceDetails resource1 () {

        OAuth2ProtectedResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        return resourceDetails;
    }
}
