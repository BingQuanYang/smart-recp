package com.smart.recp.common.core.util;

import cn.hutool.core.util.IdUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

public class JwtUtils {
    /**
     * token 过期时间, 单位: 秒
     */
    private static final long TOKEN_EXPIRED_TIME = 60 * 60;

    public static final String jwtId = "tokenId";

    /**
     * jwt 加密解密密钥(可自行填写)
     */
    private static final String JWT_SECRET = "1234567890";

    /**
     * 创建JWT
     */
    public static String createJWT(Map<String, Object> claims, Long time) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        Date now = new Date(System.currentTimeMillis());

        String secretKey = generalKey();
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        UUID uuid = UUID.randomUUID();
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
//                .setId(jwtId)
                .setId(IdUtil.randomUUID())
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, secretKey);
        if (time >= 0) {
            long expMillis = nowMillis + time;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    /**
     * 验证jwt
     */
    public static Claims verifyJwt(String token) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String secretKey = generalKey();
        Claims claims;
        try {
            //得到DefaultJwtParser
            claims = Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }//设置需要解析的jwt
        return claims;

    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static String generalKey() {
        String key = JWT_SECRET;
        String secretKey = Base64.getEncoder().encodeToString(key.getBytes());
        return secretKey;
    }

    /**
     * 根据userId和角色id列表生成token
     */
    public static String createJWT(Long userId, String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("username", username);
        return createJWT(map, TOKEN_EXPIRED_TIME * 1000);
    }

    /**
     * 根据key-value生成JWT
     *
     * @param key
     * @param jsonString
     * @return
     */
    public static String createJWT(String key, String jsonString) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, jsonString);
        return createJWT(map, TOKEN_EXPIRED_TIME * 1000);
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        String s = createJWT(1L, "admin");
        System.out.println(s);
        Claims claims = verifyJwt(s);
        System.out.println(claims.getExpiration().getTime());
    }
}
