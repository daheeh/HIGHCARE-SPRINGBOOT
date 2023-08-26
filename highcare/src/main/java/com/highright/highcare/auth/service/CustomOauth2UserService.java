package com.highright.highcare.auth.service;

import com.highright.highcare.auth.dto.OauthAttributes;
import com.highright.highcare.auth.entity.OauthUser;
import com.highright.highcare.auth.repository.OauthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OauthUserRepository oauthUserRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId
        // 현재 로그인 진행 중인 서비스 구분코드. 이후에 여러가지 추가할 때 네이버인지 구글인지 구분
        String registratinId = userRequest.getClientRegistration().getRegistrationId();

        // oauth2 로그인 진행시 키가 되는 필드값. 구글기본코드(sub), 네이버, 카카오는 기본 지원 안됨
        // 이후 네이버, 구글 로그인 동시 지원시 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // oauth2UserService를 통해 가져온 Oauth2User의 Attribute (네이버 등 다른 소셜로그인에도 이 클래스 사용)
        OauthAttributes attributes = OauthAttributes.of(registratinId, userNameAttributeName, oAuth2User.getAttributes());

        OauthUser oauthUser = saveOrUpdate(attributes);

        return null;
    }

    private OauthUser saveOrUpdate(OauthAttributes attributes) {
        return null;
    }
}
