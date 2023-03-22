package miu.edu.service.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import miu.edu.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final Long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000L; //24hours
    @Value("${user.jwt.secret}")
    private String jwtSecretToken;

    private static Set<String> getRolesFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        if (authorities != null) {
            return authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
        }
        return null;

    }

    private static Claims createClaims(Set<String> roles) {
        Claims claims = Jwts.claims();
        claims.put("roles", roles);
        return claims;
    }

    public String generateAccessToken(Member user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
                .setIssuer("CodeJava")
                .claim("roles", user.getRoleTypes().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, jwtSecretToken)
                .compact();
//        return Jwts.builder()
//                .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
//                .setIssuer("CodeJava")
//                .claim("roles", user.getRoleTypes().toString())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
//                .signWith(SignatureAlgorithm.HS512, jwtSecretToken)
//                .compact();
//        return Jwts
//                .builder()
//                .setSubject(String.format("%s,%s", userDetails.getId(), userDetails.getEmail()))
//
//               .claim("roles", userDetails.getRoleTypes().toString())
//               //   .claim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                //.setClaims(createClaims(getRolesFromAuthorities(userDetails.getAuthorities()).stream().collect(Collectors.toSet())))
//              //  .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
//                //.signWith(SignatureAlgorithm.HS256, jwtSecretToken)
//                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
//                .compact();
//        return Jwts.builder()
//                .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
//                .setIssuer("CodeJava")
//                .claim("roles", user.getRoleTypes().toString())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
//                .signWith(SignatureAlgorithm.HS512, jwtSecretToken)
//                .compact();
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractUserEmail(token);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretToken);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecretToken).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }

        return false;
    }

    //subject of the token
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretToken)
                .parseClaimsJws(token)
                .getBody();
    }
}
