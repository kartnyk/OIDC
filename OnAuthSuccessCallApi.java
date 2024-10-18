import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OidcService {

    private final RestTemplate restTemplate;

    public OidcService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void saveOidcDetails(OidcDetailsRequest oidcDetailsRequest) {
        String url = "http://localhost:8081/api/oidc/save"; // API endpoint for saving details

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<OidcDetailsRequest> requestEntity = new HttpEntity<>(oidcDetailsRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("OIDC Details saved successfully");
        } else {
            System.out.println("Failed to save OIDC details");
        }
    }
}
