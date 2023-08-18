package com.highright.highcare.auth.service;

import com.highright.highcare.auth.dto.AuthAccountDTO;
import com.highright.highcare.auth.dto.LoginMemberDTO;
import com.highright.highcare.auth.dto.TokenDTO;
import com.highright.highcare.auth.entity.Account;
import com.highright.highcare.auth.entity.RefreshToken;
import com.highright.highcare.auth.repository.AccountRepository;
import com.highright.highcare.auth.repository.RefreshTokenRepository;
import com.highright.highcare.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.ReactiveRedisCallback;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Object login(LoginMemberDTO loginInfo) {
        log.info("[AuthServiceImpl] login : loginInfo ====== {} ", loginInfo);

        Account member = accountRepository.findByMemberId(loginInfo.getId());
        log.info("[AuthServiceImpl] login : member======{}", member);

        // 멤버 조회 실패 예외

        // 비번 조회
        if(!passwordEncoder.matches(member.getPassword(), loginInfo.getPassword())){
            // 비번 실패 예외
        }

        // 성공시 token 발급
        LoginMemberDTO loginMemberDTO = modelMapper.map(member, LoginMemberDTO.class);
        TokenDTO token = tokenProvider.generateTokenDTO(loginMemberDTO);

        log.info("[AuthServiceImpl] login : token======{}", token);

        // 리프레시토큰 레디스에 id와 저장
        refreshTokenSave(new RefreshToken(token.getId(), token.getRefreshToken()));

        // 토큰 발급 실패 처리

        return token;
    }

    @Transactional
    public void refreshTokenSave(RefreshToken refreshToken) {

        refreshTokenRepository.save(refreshToken);
    }
}
