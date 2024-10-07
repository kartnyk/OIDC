import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtDecoder {

    public void decodeToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);

        // Access claims from the token
        String subject = decodedJWT.getSubject();
        String issuer = decodedJWT.getIssuer();
        String audience = decodedJWT.getAudience().toString();
        
        System.out.println("Subject: " + subject);
        System.out.println("Issuer: " + issuer);
        System.out.println("Audience: " + audience);
    }
}
