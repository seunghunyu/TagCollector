package com.tag.collector.util;

import com.tag.collector.data.AuthTokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.Cookie;
@Slf4j
public class Utility {
    @Autowired
    WebClient webClient;

    //받아온 JWT 토큰정보를 가지고 Secure Cookie 생성
    public Cookie createSecureCookie(String value){
        //클라이언트로 보낼 Cookie 생성
        Cookie cookie = new Cookie("JWT",value);

        cookie.setHttpOnly(true); // JavaScript에서 접근 불가
        cookie.setSecure(true); // HTTPS에서만 전송
        cookie.setPath("/"); // 모든 경로에서 접근 가능
        cookie.setMaxAge(3600); // 1시간 (초 단위)
        return cookie;
    }

    //토큰요청
    public String requestTokenInfo(AuthTokenInfo tokenInfo){

        webClient = WebClient.builder().build();

        String url = "https://192.168.24.136/api/v1/regToken";
//        String response = webClient.get()
//                .uri(url)
//                .header("x-test","header")
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
        String response = webClient.post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(tokenInfo)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        //Blocking 방식 처리 시 .block() , Non-Blocking 방식으로 처리 시 .subscribe()

        log.info(response);

        return "";
    }
}
