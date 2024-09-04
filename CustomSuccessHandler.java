import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = Logger.getLogger(CustomAuthenticationSuccessHandler.class.getName());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2LoginAuthenticationToken) {
            OAuth2LoginAuthenticationToken oauthToken = (OAuth2LoginAuthenticationToken) authentication;

            // Log the entire authentication object
            logger.info("Authentication Object: " + oauthToken.toString());

            // Extract and log token values
            OAuth2AccessToken accessToken = oauthToken.getAccessToken();
            OAuth2RefreshToken refreshToken = oauthToken.getRefreshToken();
            OidcIdToken idToken = oauthToken.getIdToken();

            logger.info("Access Token Value: " + (accessToken != null ? accessToken.getTokenValue() : "No access token"));
            logger.info("Refresh Token Value: " + (refreshToken != null ? refreshToken.getTokenValue() : "No refresh token"));
            logger.info("ID Token Value: " + (idToken != null ? idToken.getTokenValue() : "No ID token"));
        }

        // Redirect to default URL after successful authentication
        response.sendRedirect("/dashboard");  // Or your preferred URL
    }
}
