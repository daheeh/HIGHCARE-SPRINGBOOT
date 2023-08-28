package com.highright.highcare.auth.service;

import com.highright.highcare.auth.dto.LoginMemberDTO;
import com.highright.highcare.auth.dto.TokenDTO;
import com.highright.highcare.auth.entity.ADMAccount;
import com.highright.highcare.auth.entity.ADMRefreshToken;
import com.highright.highcare.auth.repository.AccountRepository;
import com.highright.highcare.auth.repository.RefreshTokenRepository;
import com.highright.highcare.exception.LoginFailedException;
import com.highright.highcare.exception.TokenException;
import com.highright.highcare.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;



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
    public Object selectLogin(LoginMemberDTO loginInfo, HttpServletResponse response) {
        log.info("[AuthServiceImpl] login : loginInfo ====== {} ", loginInfo);

        // 멤버 조회
        ADMAccount member = memberFindById(loginInfo.getId());
        // 패스워드 검증
        // 비번 조회

//        if(!passwordEncoder.matches(member.getPassword(), loginInfo.getPassword())){
//            // 비번 실패 예외
//            throw new LoginFailedException("PASSWORD incorrect : 잘못된 비밀번호입니다.");
//        }

        // 성공시 token 발급

        // 멤버 dto에 다시 여러 정보 담아서 전달하기
        LoginMemberDTO loginMemberDTO = modelMapper.map(member, LoginMemberDTO.class);
        log.info("[AuthServiceImpl] login : loginMemberDTO======{}", loginMemberDTO);
        loginMemberDTO.setEmpNo(member.getEmployee().getEmpNo());
        loginMemberDTO.setName(member.getEmployee().getName());
        loginMemberDTO.setDeptName(member.getEmployee().getDeptCode().getDeptName());
        loginMemberDTO.setJobName(member.getEmployee().getJobCode().getJobName());

        // access 토큰 + refresh 토큰 받아오기
        TokenDTO token = tokenProvider.generateTokenDTO(loginMemberDTO);
        Cookie cookie = tokenProvider.generateRefreshTokenInCookie(loginMemberDTO);



        log.info("[AuthServiceImpl] login : cookie======{}", cookie);
        log.info("[AuthServiceImpl] login : token======{}", token);

        // 헤더에 쿠키 저장
        response.addCookie(cookie);
        try {
            // 리프레시토큰 레디스에 id와 저장
            refreshTokenSave(ADMRefreshToken.builder().id(loginMemberDTO.getId())
                                                    .refreshToken(cookie.getValue().split("=")[0])
                                                    .build());
            return token;
        } catch (Exception e){
            throw new TokenException("토큰 발급 실패, 다시 시도해주세요.");
        }
    }


    // 멤버조회
    private ADMAccount memberFindById(String id){
        ADMAccount member = accountRepository.findByMemberId(id);
        if (member == null) {
            throw new LoginFailedException("ID not found : " + id + "를 찾을 수 없습니다");
        }
        return member;
    }

    // 리프레쉬토큰을 통한 엑세스토큰 재발행
    @Override
    public Object reIssueToken(HttpServletRequest request) {

        // 리프레시토큰 쿠키 resolve
        ADMRefreshToken refreshToken = tokenProvider.resolveCookie(request);
        log.info("[AuthServiceImpl] reIssueToken : refreshToken======{}", refreshToken);

        // 리프레시토큰 db와 일치하는지 검증 - true면 access토큰 발급
        if(findRefreshToken(refreshToken)){

            return tokenProvider.generateTokenDTO(modelMapper
                    .map(memberFindById(refreshToken.getId()), LoginMemberDTO.class));
        }

        throw new TokenException("액세트 토큰 재발급 실패");
    }


    @Transactional
    public void refreshTokenSave(ADMRefreshToken refreshToken) {
        log.info("[AuthServiceImpl] refreshTokenSave : refreshToken ==== {}", refreshToken);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public boolean findRefreshToken(ADMRefreshToken refreshToken) {
        log.info("[AuthServiceImpl] findRefreshToken : refreshToken ==== {}", refreshToken);

            Optional<ADMRefreshToken> findRefreshToken = (refreshTokenRepository.findById(refreshToken.getId()));
        if (findRefreshToken.isPresent() && findRefreshToken.get().getRefreshToken().equals(refreshToken.getRefreshToken())) {
                log.info("[AuthServiceImpl] refreshTokenSave : findRefreshToken ==== {}", findRefreshToken);

                return true;
            }

            throw new TokenException("리프레시토큰 검증 실패하였습니다. 재로그인 해주세요.");

    }
}
