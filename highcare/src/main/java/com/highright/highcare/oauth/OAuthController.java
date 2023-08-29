package com.highright.highcare.oauth;

import com.highright.highcare.auth.service.AuthService;
import com.highright.highcare.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final AuthService authService;

    // 소셜로그인 연동 신청
    @PostMapping("/api/oauth/regist")
    public ResponseEntity<ResponseDTO> insertOauthRegist(@RequestBody Map<String, Object> data, HttpServletResponse response){

        log.info("[OAuthController] insertOauthRegist data====  {} ", data);


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "소셜로그인 연동 확인", authService.insertOauthRegist(data, response)));
    }

    // 소셜로그인 요청
//    @PostMapping("/api/oauth/jwt/google")
//    public ResponseEntity<ResponseDTO> insertOauthJwt(@RequestBody Map<String, Object> data, HttpServletResponse response){
//
//        log.info("[OAuthController] insertOauthJwt data ====  {} ", data);
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
//                "OAuth jwt 성공", authService.insertOauthJwt(data, response)));
//    }

}
