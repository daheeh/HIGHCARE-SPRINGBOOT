package com.highright.highcare.jwt;

import com.highright.highcare.auth.dto.LoginMemberDTO;
import com.highright.highcare.auth.dto.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class TokenProvider {

    private final String BEARER_TYPE = "Bearer"; // 토큰 타입
    private final String AUTHORITIES_KEY = "auth";   // 권한클레임 key값
    private final Key key;

    @Value("${jwt.expire-time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    public TokenProvider(    @Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 1. 토큰 생성
    public TokenDTO generateTokenDTO(LoginMemberDTO loginMemberDTO) {
        log.info("[TokenProvider] generateTokenDTO : loginMemberDTO === {}", loginMemberDTO);

        // 멤버 권한이름 추출
        List<String> roles = new ArrayList<>();
        loginMemberDTO.getRoleList().forEach(role -> roles.add(role.getAuthCode()));
        log.info("[TokenProvider] generateTokenDTO : roles === {}", roles);

        // 클레임 설정(회원아이디, 권한, 부서, 직급)
        Claims claims = Jwts.claims().setSubject(loginMemberDTO.getId());
        claims.put(AUTHORITIES_KEY, roles); // "auth" - "roles"
        claims.put("dept", loginMemberDTO.getDeptName());
        claims.put("job", loginMemberDTO.getJobName());

        // 만료시간 설정
        long now = System.currentTimeMillis();
        Date AccessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(AccessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        log.info("[TokenProvider] generateTokenDTO : accessToken === {}", accessToken);

        // 리프레쉬 토큰 uuid 생성
        String refreshToken = UUID.randomUUID().toString();

        log.info("[TokenProvider] generateTokenDTO : refreshToken === {}", refreshToken);

        return new TokenDTO(BEARER_TYPE, loginMemberDTO.getName(), loginMemberDTO.getId(), accessToken, refreshToken, AccessTokenExpiresIn.getTime());
    }

    public String resolveToken(HttpServletRequest request) {

        return null;
    }

    public boolean validateToken(String jwt) {

        return false;
    }

    public Authentication getAuthentication(String jwt) {

        return null;
    }

    // 토큰
}
