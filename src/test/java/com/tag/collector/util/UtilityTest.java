package com.tag.collector.util;

import com.tag.collector.data.AuthTokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UtilityTest {

    @Autowired
    WebClient webClient;

    @Test
    void requestTokenInfo(){
        //토큰요청

        webClient = WebClient.builder().build();

        String url = "https://192.168.24.136/api/regToken";

        String response = webClient.post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        //Blocking 방식 처리 시 .block() , Non-Blocking 방식으로 처리 시 .subscribe()

        log.info(response);

    }
}