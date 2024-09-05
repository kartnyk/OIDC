package com.example.oidc;  // Ensure this matches your package structure

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUser;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {  // Correct type check
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

            // Log the entire authentication object
            logger.info("Authentication Object: " + oauthToken.toString());

            // Extract token values if they exist
            if (oauthToken.getPrincipal() instanceof OidcUser) {
                OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();
                OAuth2AccessToken accessToken = oauthToken.getAccessToken();  // Get access token
                OidcIdToken idToken = oidcUser.getIdToken();

                logger.info("Access Token Value: " + (accessToken != null ? accessToken.getTokenValue() : "No access token"));
                logger.info("ID Token Value: " + (idToken != null ? idToken.getTokenValue() : "No ID token"));
                // No refresh token in OIDC context usually
            } else {
                logger.info("User is not authenticated with an OpenID Connect provider.");
            }
        } else {
            logger.info("Authentication object is not an instance of OAuth2AuthenticationToken.");
        }

        // Redirect to default URL after successful authentication
        response.sendRedirect("/dashboard");  // Or your preferred URL
    }
}
