package com.atmosware.library_project.core.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService
{
    private String SECRET_KEY = "C53m0R5fXD2VXQwoWbV6Rhqk44l/fY0n6rk67028lFw9ZCU9nIpc0V6N1S7hNGHSj6nNGKszIHMUCGxOwatyQXtcA+HmkiCGtO19bhhVEEhfDVxdYP/PLDTnolseuAMP9bYYmXPUXZ/79iRq90kIsM37Uiw/Q3xFFjHObjzzD78ZP8ucmaavcEBTeW9dpOieMvyS7Zdz4ut/slMtIpq1gKBKBw+r3e3sUbUGmoINGJoBsPEQflbE10J1aTRLLREq1EmUNw9VZmVOtCrVErwKawkQFVniWieqAeQze+OY1BfkjYI11lhO7Y7LyOl3Vx35EezWhrYj8ceajsnEr7CIMg==";
    private long EXPIRATION = 600000;

    // Boilerplate => Basmakalıp
    public String generateToken(Map<String,Object> claims, String userName) {
        return createToken(claims, userName);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUser(token);
        Date expirationDate = extractExpiration(token);
        return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());
    }

    private Date extractExpiration(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    public String extractUser(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
