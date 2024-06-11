package your.package;

import org.springframework.security.oauth2.client.endpoint.DefaultOAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.web.client.RestTemplate;

public class CustomOAuth2AccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private final RestTemplate restTemplate;

    public CustomOAuth2AccessTokenResponseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        DefaultOAuth2AccessTokenResponseClient client = new DefaultOAuth2AccessTokenResponseClient();
        client.setRestOperations(restTemplate);
        return client.getTokenResponse(authorizationGrantRequest);
    }
}
