package com.highright.highcare.oauth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KaKaoResponse {

    private String code;
    private String state="";
}
