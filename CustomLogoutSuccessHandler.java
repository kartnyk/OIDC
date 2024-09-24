import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public CustomLogoutSuccessHandler(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            OAuth2AuthorizedClient authorizedClient = authorizedClientService
                .loadAuthorizedClient("google", oidcUser.getName());

            if (authorizedClient != null) {
                String accessToken = authorizedClient.getAccessToken().getTokenValue();

                // Send the revocation request to Google
                URL url = new URL("https://oauth2.googleapis.com/revoke?token=" + accessToken);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Token revoked successfully.");
                } else {
                    System.out.println("Failed to revoke token.");
                }
            }
        }

        // Perform default logout actions
        response.sendRedirect("/login/oauth2");  // Redirect to login page after logout
    }
}
