package main.java.com.ledgermesh.authservice.security;

import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(UserDetails userDetails)
    {
        return Jwts.builder()
                   .subject(userDetails.getUsername())
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                   .signWith(getSignInKey())
                   .compact();
    }
    
    public String extractUsername(String token) 
    {
        return extractClaim(token , Claims::getSubject);
    }
    
    public boolean isTokenValid(String token , UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    
    private boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token , Function <Claims,T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parser()
                   .verifyWith(getSignInToken())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    private SecretKey getSignInKey()
    {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
