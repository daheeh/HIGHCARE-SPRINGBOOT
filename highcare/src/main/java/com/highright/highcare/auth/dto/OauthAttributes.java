package com.highright.highcare.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OauthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OauthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    // of()
    // Oauth2User에서 반환하는 사용자 정보는 map이기 때문에 값 하나하나 변환해줌
    public static OauthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes){
        return OauthAttributes.builder()
                .name((String)attributes.get("name"))
//                .email(String)attributes.get("email"))
//                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }
}
