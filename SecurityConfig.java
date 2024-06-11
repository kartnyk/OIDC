package your.package;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig {

    private final RestTemplate oauth2RestTemplate;

    public SecurityConfig(RestTemplate oauth2RestTemplate) {
        this.oauth2RestTemplate = oauth2RestTemplate;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/", "/index.html").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login -> oauth2Login
                .tokenEndpoint(tokenEndpoint ->
                    tokenEndpoint.accessTokenResponseClient(new CustomOAuth2AccessTokenResponseClient(oauth2RestTemplate))
                )
                .userInfoEndpoint(userInfoEndpoint -> 
                    userInfoEndpoint.oidcUserService(oidcUserService())
                )
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/index.html")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }
}
