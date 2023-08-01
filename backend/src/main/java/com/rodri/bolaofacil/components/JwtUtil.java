package com.rodri.bolaofacil.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil 
{
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

    public String generateToken(Long userId) 
    {
        return Jwts.builder()
                   .setSubject(userId.toString())
                   .signWith(SignatureAlgorithm.HS512, clientSecret)
                   .compact();
    }

    public String getUserIdFromToken(String token) 
    {
        Claims claims = Jwts.parser()
				            .setSigningKey(clientSecret)
				            .parseClaimsJws(token)
				            .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) 
    {
        try 
        {
            Jwts.parser().setSigningKey(clientSecret).parseClaimsJws(token);
            return true;
        } 
        catch (Exception ex) { return false; }
    }
}
