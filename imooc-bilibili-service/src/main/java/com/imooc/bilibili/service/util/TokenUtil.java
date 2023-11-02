package com.imooc.bilibili.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.imooc.bilibili.domain.exception.ConditionException;

import java.util.Calendar;
import java.util.Date;

public class TokenUtil {

    private static final String ISSUER = "gg";
    public static String generateToken(Long userId) throws Exception{

        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        // 生成 jwt过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 30);
        return JWT.create().withKeyId(String.valueOf(userId)).withIssuer(ISSUER).withExpiresAt(calendar.getTime()).sign(algorithm);
    }

    public static Long verifyToken(String token) {
        // 这里之所以不能直接抛出异常是因为验证token 有很多错误
        // 1.验证成功通过 2.token 过期（不能直接抛出异常 => 看前端是手动刷新token还是让用户退出）
        try {

            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String userId = jwt.getKeyId();
            return Long.valueOf(userId);
        } catch (TokenExpiredException e) {
            // token 过期异常
            throw new ConditionException("555", "token 过期！");
        }catch(Exception e) {
            throw new ConditionException("非法用户token！");

        }

    }
}
