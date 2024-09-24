import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.*;
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

                // Log the token to make sure it's the correct one
                System.out.println("Attempting to revoke token: " + accessToken);

                // Send the revocation request to Google
                URL url = new URL("https://oauth2.googleapis.com/revoke");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                // Write the token to the request body
                String urlParameters = "token=" + accessToken;
                try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                    out.writeBytes(urlParameters);
                    out.flush();
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Token revoked successfully.");
                } else {
                    System.out.println("Failed to revoke token. Response code: " + responseCode);
                    
                    // Log the error response
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                        String inputLine;
                        StringBuilder responseText = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            responseText.append(inputLine);
                        }
                        System.out.println("Error response from Google: " + responseText.toString());
                    }
                }
            }
        }

        // Perform default logout actions
        response.sendRedirect("/login/oauth2");  // Redirect to login page after logout
    }
}
