package com.sparta.mytodoapp.service;

import com.sparta.mytodoapp.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StatusCheck {

    //기능이 성공적으로 수행되었을 때 성공 메시지,상태코드,성공한 데이터를 출력합니다.
    public static ResponseEntity<CommonResponse<?>> success(String msg, Object object) {
        return ResponseEntity.ok(CommonResponse.builder()
            .statusCode(HttpStatus.OK.value())
            .msg(msg)
            .data(object)
            .build());
    }

    //요청이 잘못되었을 때 잘못된 이유에 관한 메시지와 상태 코드를 보냅니다.
    public static ResponseEntity<CommonResponse<?>> badRequest(String msg) {
        return ResponseEntity.badRequest().body(CommonResponse.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .msg(msg)
            .data(null)
            .build());
    }

    //요청에 필요한 권한이 없을 때 필요한 권한에 대한 메시지와 함께 상태 코드를 보냅니다.
    static ResponseEntity<CommonResponse<?>> forBidden(String msg) {
        return ResponseEntity.status(403).body(CommonResponse.builder()
            .statusCode(HttpStatus.FORBIDDEN.value())
            .msg(msg)
            .data(null)
            .build());
    }


}
