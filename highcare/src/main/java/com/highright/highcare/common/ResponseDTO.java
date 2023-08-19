package com.highright.highcare.common;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private int status;          // 상태코드값
    private String message;     // 응답 매세지
    private Object data;        // 응답 데이터

}
