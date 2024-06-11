package your.package;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RestTemplate oauth2RestTemplate;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests -> authorizeRequests
                .antMatchers("/", "/index.html", "/login**", "/error**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login -> oauth2Login
                .loginPage("/index.html")
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
