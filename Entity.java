import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "oidcdetails")
public class OidcDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "AUTH_ID", nullable = false)
    private String authId;

    @Column(name = "APP_CODE", nullable = false)
    private String appCode;

    @Column(name = "EMAIL_ID", nullable = false)
    private String emailId;

    @Column(name = "PROVIDER", nullable = false)
    private String provider;

    @Column(name = "PROVIDER_ID", nullable = true)
    private String providerId;

    @Column(name = "ACCESS_TOKEN", nullable = true)
    private String accessToken;

    @Column(name = "REFRESH_TOKEN", nullable = true)
    private String refreshToken;

    @Column(name = "TOKEN_EXPIRY", nullable = true)
    private Timestamp tokenExpiry;

    @Column(name = "ID_TOKEN", nullable = true)
    private String idToken;

    @Column(name = "SCOPE", nullable = true)
    private String scope;

    @Column(name = "ENABLED_FLG", nullable = true)
    private String enabledFlg;

    @Column(name = "CREATED_AT", nullable = true)
    private Timestamp createdAt;

    @Column(name = "UPDATED_AT", nullable = true)
    private Timestamp updatedAt;

    // Getters and setters
    // ...
}
