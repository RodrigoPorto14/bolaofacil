package com.rodri.bolaofacil.components;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rodri.bolaofacil.dto.UserInsertDTO;
import com.rodri.bolaofacil.services.exceptions.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil 
{
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

    public String generateRegisterToken(UserInsertDTO dto) 
    {
    	Claims claims = Jwts.claims().setSubject(dto.getId().toString());
        claims.put("email", dto.getEmail());
        claims.put("nickname", dto.getNickname());
        claims.put("password", dto.getPassword());
    	
        return Jwts.builder()
                   .setClaims(claims)
                   .signWith(SignatureAlgorithm.HS512, clientSecret)
                   .compact();
    }
    
    public String generatePasswordRecoveryToken(Long id, String email, int expirationMinutes) 
    {
    	Date expirationDate = new Date(System.currentTimeMillis() + expirationMinutes * 60 * 1000);
    	Claims claims = Jwts.claims().setSubject(id.toString());
    	claims.put("email", email);

        return Jwts.builder()
                   .setClaims(claims)
                   .setExpiration(expirationDate)
                   .signWith(SignatureAlgorithm.HS512, clientSecret)
                   .compact();
    } 

    public Claims getClaims(String token) 
    {
        Claims claims = Jwts.parser()
				            .setSigningKey(clientSecret)
				            .parseClaimsJws(token)
				            .getBody();

        return claims;
    }

    public void validateToken(String token) 
    {
        try 
        {
            Jwts.parser().setSigningKey(clientSecret).parseClaimsJws(token);
        } 
        catch (Exception ex) { throw new InvalidTokenException(); }
    }
}
