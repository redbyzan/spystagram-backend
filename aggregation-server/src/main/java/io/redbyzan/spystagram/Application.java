package io.redbyzan.spystagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * Created by roger on 2016. 5. 2..
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableOAuth2Client
public class Application {

    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }
}
