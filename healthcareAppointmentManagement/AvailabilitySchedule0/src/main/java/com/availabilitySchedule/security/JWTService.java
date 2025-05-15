package com.availabilitySchedule.security;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private static final String secretkey = "qwertyuioppoiuytrewqasdfghjkllkjhgfdsazxcvbnmmnbvcxz";
  // Token expiration time (e.g., 10 minutes)
	    private static final long EXPIRATION_TIME = 10 * 60 * 1000;
	 
	    // Generate JWT Token
	    

	    private static SecretKey getKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }
	    
	    public static Claims extractAllClaims(String token) {
	 
	        return Jwts.parserBuilder()
	                .setSigningKey(getKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }
	   
	    
	    public String extractRoles(String token) {
	    	return extractAllClaims(token).get("role", String.class);
	    }
	    
	 
	 
	    // Extract specific claim (e.g., username)
//	    public static String extractUsername(String token) {
////	    	System.out.println("extractAllClaims(token):"+extractAllClaims(token));
//	        return extractAllClaims(token).get("userInfo", String.class);
//	    }
	    
	    public String extractUsername(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject(); // Return username
	    }
	    
	    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
			final Claims claims = extractAllClaims(token);
			return claimResolver.apply(claims);
		}
	    
	    public String extractEmail(String token) {
			return extractClaim(token, Claims::getSubject);
		}
	 
	    // Extract expiration date
	    public static Date extractExpiration(String token) {
	        return extractAllClaims(token).getExpiration();
	    }


	    
	    public boolean validateToken(String token) {
	        try {
	            Jwts.parserBuilder()
	                .setSigningKey(getKey())
	                .build()
	                .parseClaimsJws(token);
	            return true; // Token is valid
	        } catch (ExpiredJwtException e) {
	            return false; // Token is expired
	        } catch (JwtException e) {
	            return false; // Token is invalid
	        }
	    }
}

