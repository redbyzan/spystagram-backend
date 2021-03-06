package io.redbyzan.spystagram.config;

import io.redbyzan.spystagram.common.Constant;
import io.redbyzan.spystagram.handler.SparklrUserApprovalHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * Created by roger on 2016. 4. 26..
 */
@Configuration
@EnableAuthorizationServer
@EnableWebSecurity
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    public static final String DUMMY_RESOURCE_ID = "dummy";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Value("${default.redirect:http://localhost:8080/tonr2/sparklr/redirect}")
    private String defaultRedirectUri;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer()).authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreFactory = new KeyStoreKeyFactory(new ClassPathResource(Constant.KEY_FILE_NAME), Constant.KEY_PASSWORD.toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreFactory.getKeyPair(Constant.KEY_FILE_ALIAS));
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //// TODO: 2016. 4. 26. inMemory -> datasource
//        clients.inMemory().withClient("redbyzan")
//                .resourceIds(DUMMY_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write")
//                .secret("secret")
//            .and()
//                .withClient("redbyzan-with-redirect")
//                .resourceIds(DUMMY_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write")
//                .secret("secret")
//                .redirectUris(defaultRedirectUri)
//            .and()
//                .withClient("my-client-with-registered-redirect")
//                .resourceIds(DUMMY_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "client_credentials")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "trust")
//                .redirectUris("http://anywhere?key=value")
//            .and()
//                .withClient("my-trusted-client")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit","client_credentials")
//                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                .scopes("read", "write", "trust")
//                .accessTokenValiditySeconds(60)
//            .and()
//                .withClient("my-trusted-client-with-secret")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit","client_credentials")
//                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                .scopes("read", "write", "trust")
//                .secret("somesecret")
//            .and()
//                .withClient("my-less-trusted-client")
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write", "trust")
//            .and()
//                .withClient("my-less-trusted-autoapprove-client")
//                .authorizedGrantTypes("implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write", "trust")
//                .autoApprove(true);

        clients.inMemory().withClient("tonr")
                .resourceIds(DUMMY_RESOURCE_ID)
                .authorizedGrantTypes("authorization_code", "implicit")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write")
                .secret("secret")
                .and()
                .withClient("tonr-with-redirect")
                .resourceIds(DUMMY_RESOURCE_ID)
                .authorizedGrantTypes("authorization_code", "implicit")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write")
                .secret("secret")
                .redirectUris(defaultRedirectUri)
                .and()
                .withClient("my-client-with-registered-redirect")
                .resourceIds(DUMMY_RESOURCE_ID)
                .authorizedGrantTypes("authorization_code", "client_credentials")
                .authorities("ROLE_CLIENT")
                .scopes("read", "trust")
                .redirectUris("http://anywhere?key=value")
                .and()
                .withClient("my-trusted-client")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                .accessTokenValiditySeconds(60)
                .and()
                .withClient("my-trusted-client-with-secret")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit","client_credentials")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                .secret("somesecret")
                .and()
                .withClient("my-less-trusted-client")
                .authorizedGrantTypes("authorization_code", "implicit")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write", "trust")
                .and()
                .withClient("my-less-trusted-autoapprove-client")
                .authorizedGrantTypes("implicit")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write", "trust")
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.realm("sparklr2/client");
    }


    protected static class Stuff {

        @Autowired
        private ClientDetailsService clientDetailsService;

        @Autowired
        private TokenStore tokenStore;

        @Bean
        public ApprovalStore approvalStore() throws Exception {

            TokenApprovalStore store = new TokenApprovalStore();
            store.setTokenStore(tokenStore);
            return store;
        }

        @Bean
        @Lazy
        @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
        public SparklrUserApprovalHandler userApprovalHander() throws Exception {

            SparklrUserApprovalHandler hander = new SparklrUserApprovalHandler();
            hander.setApprovalStore(approvalStore());
            hander.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
            hander.setClientDetailsService(clientDetailsService);
            hander.setUseApprovalStore(true);
            return hander;
        }

    }
}


