package org.inventory.magedsystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JWTUtils {

    private static final  long Expiration_Time_In_MilSeconds=100L*60L*60L*24L*30L*6L;//expires in 6 Months
    private SecretKey key;

    @Value("${secretejwtString}")
    private String secretejwtString;

    @PostConstruct
    private void init(){
        byte[]bytekey=secretejwtString.getBytes(StandardCharsets.UTF_8);
        this.key=new SecretKeySpec(bytekey,"HmacSHA256");
    }

    public String GenerateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+Expiration_Time_In_MilSeconds))
                .signWith(key)
                .compact();
    }
    public String getusernameFromToken(String token){
        return exterectClaims(token, Claims::getSubject);


    }
    private <T> T exterectClaims(String token, Function<Claims,T> claimsTFunction){

       return claimsTFunction.apply(Jwts.parserBuilder().
                 setSigningKey(key).build().parseClaimsJws(token).getBody());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username=getusernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return exterectClaims(token, Claims::getExpiration).before(new Date());
    }


 }
