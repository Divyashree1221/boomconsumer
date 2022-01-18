package com.boommotors.btp.security;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author rjanumpally
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    
    @Value("${app.jwtTempExpirationInMs}")
    private int jwtTempExpirationInMs;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    private static final String AUTHORITIES_KEY = "scopes";

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public String generateTempToken(Authentication authentication, String mobileNo) {

        UserPrincipal userPrincipal = null;
        if (authentication != null) {
            userPrincipal = (UserPrincipal) authentication.getPrincipal();
        } else {
            userPrincipal = (UserPrincipal) customUserDetailsService.loadUserByUsername(mobileNo);
        }
        UserDetails userDetails = customUserDetailsService.loadUserById(userPrincipal.getId());
        userPrincipal = (UserPrincipal) userDetails;
        /*
        final String authorities = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        System.out.println("generateTempToken::authorities::" + authorities);
         */
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtTempExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .claim(AUTHORITIES_KEY, "ROLE_TEMP")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Integer getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        //System.out.println("getUserIdFromJWT::"+claims.getSubject());
        return Integer.parseInt(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
