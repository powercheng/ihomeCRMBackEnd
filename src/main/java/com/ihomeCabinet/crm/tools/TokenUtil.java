package com.ihomeCabinet.crm.tools;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.Claims;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {

    private static final long EXPIRE_TIME = 1000*60*60*24*7;
    private static final String TOKEN_SECRET = "ihome2022";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(TOKEN_SECRET);

    public String generateToken(JwtSubject jwtSubject) {
        return JWT.create()
                .withClaim("user", BeanUtil.beanToMap(jwtSubject))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .sign(ALGORITHM);
    }

    public JwtSubject getUserFromToken(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Claim claim = jwt.getClaim("user");
        JwtSubject jwtSubject = claim.as(JwtSubject.class);
        return jwtSubject;
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

}
