package com.xingyu.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingyu.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtils {
    private static final int expiration = 3600;
    private static final KeyPair keyPair = RsaProvider.generateKeyPair(1024);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String generateToken(User user) {
        Map<String, Object> claims = objectMapper.convertValue(user, Map.class);
        return JwtUtils.jwtCompact(keyPair.getPrivate(), claims, new Date(System.currentTimeMillis() + expiration * 1000));
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(keyPair.getPublic()).parseClaimsJws(token).getBody();
        } catch (SignatureException exception) {
            log.warn("Failed to get claims from token \"{}\"", token);
        }
        return claims;
    }

    public static User verifyUserFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        log.info("Claims got from token: {}", claims);
        claims.remove("exp");
        return objectMapper.convertValue(new HashMap<>(claims), User.class);
    }

    public static String jwtCompact(PrivateKey privateKey, Map<String, Object> claims, Date expirationDate) {
        JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.RS512, privateKey);
        if (expirationDate != null) {
            jwtBuilder.setExpiration(expirationDate);
        }
        return jwtBuilder.compact();
    }
}
