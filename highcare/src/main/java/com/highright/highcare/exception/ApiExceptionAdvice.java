package com.highright.highcare.exception;


import com.highright.highcare.exception.dto.ApiExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 예외 처리용 어드바이스
@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(LoginFailedException e){

        // 로그인 시도 횟수 증가, 이력 남김 - 테이블 save 날리기
        // ID / PASSWORD 사유


        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
    }


    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ApiExceptionDTO> exceptionHandler(TokenException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiExceptionDTO(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 유효성 검사에 실패한 경우 예외 처리
        StringBuilder errorMessage = new StringBuilder("유효성 검사 오류: ");
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorMessage.append(fieldError.getField()).append(" ").append(error.getDefaultMessage()).append("; ");
            } else {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            }
        }
        return ResponseEntity.badRequest().body(new ApiExceptionDTO(HttpStatus.BAD_REQUEST, errorMessage.toString()));
    }



}
