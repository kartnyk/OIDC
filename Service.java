import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OidcDetailsService {

    @Autowired
    private OidcDetailsRepository oidcDetailsRepository;

    public void saveOidcDetails(String authId, String appCode, String emailId, String provider,
                                String providerId, String accessToken, String refreshToken,
                                Timestamp tokenExpiry, String idToken, String scope, String enabledFlg) {
        OidcDetails oidcDetails = new OidcDetails();
        oidcDetails.setAuthId(authId);
        oidcDetails.setAppCode(appCode);
        oidcDetails.setEmailId(emailId);
        oidcDetails.setProvider(provider);
        oidcDetails.setProviderId(providerId);
        oidcDetails.setAccessToken(accessToken);
        oidcDetails.setRefreshToken(refreshToken);
        oidcDetails.setTokenExpiry(tokenExpiry);
        oidcDetails.setIdToken(idToken);
        oidcDetails.setScope(scope);
        oidcDetails.setEnabledFlg(enabledFlg);
        oidcDetails.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        oidcDetails.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        oidcDetailsRepository.save(oidcDetails);
    }
}
