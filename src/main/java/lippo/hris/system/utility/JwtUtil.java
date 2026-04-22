package lippo.hris.system.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username, List<String> roles) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        long now = System.currentTimeMillis();
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", roles)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + expiration))
                .sign(algorithm);
    }

    public String getUsername(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return jwt.getSubject();
    }

    public List<String> getRoles(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return jwt.getClaim("roles").asList(String.class);
    }

    public boolean validateToken(String token) {
        try {
            getUsername(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
