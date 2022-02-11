package dartsgame.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    String publicKey = "-----BEGIN PUBLIC KEY-----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQ+7yKlJGuvYtf1soMsJjkQJGAXe90QAxqppycf+3JT5ehnvvWtwS8ef+UsqrNa5Rc9tyyHjP7ZXRN145SlRTZzc0d03Ez10OfAEVdhGACgRxS5s+GZVtdJuVcje3Luq3VIvZ8mV/P4eRcV3yVKDwQEenMuL6Mh6JLH48KxgbNRQIDAQAB-----END PUBLIC KEY-----";
    private TokenStore tokenStore;

    @Bean
    public TokenStore tokenStore() {
        if (tokenStore == null) {
            tokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        }
        return tokenStore;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setVerifierKey(publicKey);
        return jwtAccessTokenConverter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("api");
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/history/**")
                .access("(#oauth2.hasScope('read'))")
                .antMatchers(HttpMethod.GET, "/**")
                .access("(#oauth2.hasScope('read') and hasRole('ROLE_GAMER'))")
                .antMatchers(HttpMethod.POST, "/**")
                .access("(#oauth2.hasScope('write') and hasRole('ROLE_GAMER'))")
                .antMatchers(HttpMethod.PUT, "/**")
                .access("(#oauth2.hasScope('update') and hasRole('ROLE_REFEREE'))")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }
}
