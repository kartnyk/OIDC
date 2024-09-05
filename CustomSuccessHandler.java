package com.example.oidc;  // Ensure this matches your package structure

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = Logger.getLogger(CustomAuthenticationSuccessHandler.class.getName());

    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public CustomAuthenticationSuccessHandler(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

            // Retrieve the authorized client
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                clientRegistrationId, oauthToken.getName()
            );

            if (authorizedClient != null) {
                // Get access token from authorized client
                OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

                // Log token values
                logger.info("Access Token Value: " + (accessToken != null ? accessToken.getTokenValue() : "No access token"));

                // Check if the principal is an OIDC user and get the ID token if applicable
                if (oauthToken.getPrincipal() instanceof OidcUser) {
                    OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();
                    OidcIdToken idToken = oidcUser.getIdToken();
                    logger.info("ID Token Value: " + (idToken != null ? idToken.getTokenValue() : "No ID token"));
                } else {
                    logger.info("User is authenticated as an OAuth2User.");
                }
            } else {
                logger.warning("No authorized client found for user.");
            }
        } else {
            logger.info("Authentication object is not an instance of OAuth2AuthenticationToken.");
        }

        // Redirect to default URL after successful authentication
        response.sendRedirect("/dashboard");  // Or your preferred URL
    }
}
