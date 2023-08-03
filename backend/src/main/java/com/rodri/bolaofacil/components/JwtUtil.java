package com.rodri.bolaofacil.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rodri.bolaofacil.dto.UserInsertDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil 
{
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

    public String generateToken(UserInsertDTO dto) 
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

    public Claims getClaims(String token) 
    {
        Claims claims = Jwts.parser()
				            .setSigningKey(clientSecret)
				            .parseClaimsJws(token)
				            .getBody();

        return claims;
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
