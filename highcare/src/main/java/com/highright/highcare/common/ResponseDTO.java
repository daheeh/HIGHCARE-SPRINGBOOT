package com.highright.highcare.common;

import com.highright.highcare.pm.dto.PmEmployeeDTO;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private int status;          // 상태코드값
    private String message;     // 응답 매세지
    private Object data;        // 응답 데이터

    public ResponseDTO(HttpStatus httpStatus, String 조회_성공, List<PmEmployeeDTO> data) {
    }
}
