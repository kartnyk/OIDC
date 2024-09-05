package com.example.oidc;  // Ensure this matches your package structure

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

            // Extract principal
            OAuth2User oauth2User = oauthToken.getPrincipal();

            if (oauth2User instanceof OidcUser) {
                OidcUser oidcUser = (OidcUser) oauth2User;
                OidcIdToken idToken = oidcUser.getIdToken();
                OAuth2AccessToken accessToken = oauthToken.getAccessToken();  // Get access token from the token itself

                // Log token values
                logger.info("Access Token Value: " + (accessToken != null ? accessToken.getTokenValue() : "No access token"));
                logger.info("ID Token Value: " + (idToken != null ? idToken.getTokenValue() : "No ID token"));
            } else {
                // If it's a plain OAuth2User, we can still log user attributes
                logger.info("User is authenticated as an OAuth2User.");
                logger.info("User Attributes: " + oauth2User.getAttributes().toString());
            }
        } else {
            logger.info("Authentication object is not an instance of OAuth2AuthenticationToken.");
        }

        // Redirect to default URL after successful authentication
        response.sendRedirect("/dashboard");  // Or your preferred URL
    }
}
