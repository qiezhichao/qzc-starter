package com.qzc.jwt;

import com.qzc.annotation.ApplicationConfigCheck;
import com.qzc.jwt.config.JwtConfig;
import com.qzc.util.BaseUuidUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Jwt相关服务
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/12 13:36
 */
@Service
@Slf4j
@ApplicationConfigCheck(configClass = JwtConfig.class, configServiceName = "Jwt服务")
public class JwtService {

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 生成jwt加密字符串
     *
     * @param subject  签发人
     * @param key      生成签名的时候使用的秘钥secret
     * @param claimMap jwt中的payload私有声明
     * @Author: qiezhichao
     * @CreateDate: 2019/5/12 13:36
     */
    public String createJwt(String subject, String key, Map<String, Object> claimMap) {

        // 使用Hs256算法
        SignatureAlgorithm sa = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date nowDate = new Date(nowMillis);

        JwtBuilder jwtBuilder
                = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claimMap)
                // 设置JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击
                .setId(BaseUuidUtil.generateUuid())
                // jwt的签发时间
                .setIssuedAt(nowDate)
                // 代表这个JWT的主体，即它的所有人
                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(sa, key);

        long ttlMillis = Long.parseLong(jwtConfig.getExpireSecond()) * 1000;
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            // 设置过期时间
            jwtBuilder.setExpiration(exp);
        }

        return jwtBuilder.compact();
    }

    /**
     * 解析jwt密文
     *
     * @param token 需要解析的jwt
     * @param key   密钥
     * @Author: qiezhichao
     * @CreateDate: 2019/5/12 13:42
     */
    public Claims parseJWT(String token, String key) {

        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(key)
                // 设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }
}
