package com.tag.collector.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tag.collector.data.AuthTokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
@Slf4j
@Controller
public class MainController {

    @RequestMapping("/home")
    public String home(){
        System.out.println("home");
        log.info("::::::::::::::::::::::START HOME:::::::::::::::::::::::::");
        return "index";
    }


    @RequestMapping("/template")
    public String template(HttpServletResponse response){
        Cookie cookie = new Cookie("UID","yush");
        cookie.setMaxAge(60*60);
        response.addCookie(cookie);
        System.out.println("template0.5");
        return "index";
    }

    @PostMapping("/getTokenInfo")
    public ResponseEntity<String> getTokenInfo(HttpServletResponse response){
        String urlInfo = "http://192.168.24.136:8888/api/authenticate";

        //AuthTokenInfo authTokenInfo = new AuthTokenInfo();

        URL url = null;
        HttpURLConnection con = null;

        JSONParser parser = new JSONParser();
        JSONObject message = new JSONObject();

        OutputStreamWriter wr = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        try {

            url = new URL(urlInfo);
            con = (HttpURLConnection) url.openConnection();

            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("POST");
            //conn.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString(BasicToken.getBytes()));
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-type", "application/json; charset=utf-8");
            con.setDoInput(true);
            con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            String securityUser = "ecube";
            String securityPassword = "obzcom1!";
            String BasicToken = securityUser + ":" + securityPassword;

            System.out.println(Base64.getEncoder().encodeToString(BasicToken.getBytes()));


            // JSON 객체 생성
            AuthTokenInfo authTokenInfo = new AuthTokenInfo();
            authTokenInfo.setUserId("yush");
            authTokenInfo.setUserName("yu seung hun");
            authTokenInfo.setPassword("obzcom");

            System.out.println(authTokenInfo.toString());

            // Jackson ObjectMapper를 사용하여 객체를 JSON으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(authTokenInfo);

            // 요청 본문에 JSON 데이터 작성
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            // 응답 읽기
            if (responseCode == HttpURLConnection.HTTP_OK) { // 성공적인 응답 코드
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String inputLine;
                StringBuilder responseBuilder = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                in.close();

                // 응답 출력
                System.out.println("Response :: " + responseBuilder.toString());

                String cookieValue = con.getHeaderField("Set-Cookie");
                System.out.println("Cookie Value::::::"+cookieValue);

                String jwtToken = "";
                String cookieAttribute[] = cookieValue.split(";");

                for(String attr : cookieAttribute){
                    String val = attr.split("=")[0];
                    if(val.equals("JWT")){
                        jwtToken = attr.split("=")[1];
                        break;
                    }
                }

                System.out.println("jwt Token Value : " + jwtToken);

                Cookie cookie = createSecureCookie(jwtToken);

                response.addCookie(cookie);

            } else {
                System.out.println("POST request not worked");
            }

        } catch (Exception e){
            System.err.println(e.toString());
        }


        return ResponseEntity.ok("Authentication successful");
    }

    @PostMapping("/validate")
    //public ResponseEntity<String> validateToken( @RequestBody AuthTokenInfo authTokenInfo,
    public ResponseEntity<String> validateToken(@RequestBody AuthTokenInfo authTokenInfo, HttpServletRequest request){
        System.out.println("validate AuthTokenInfo : " + authTokenInfo.toString());

        Cookie[] cookies = request.getCookies();

        System.out.println("cookies length : " + Integer.toString(cookies.length));

        String jwtToken = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.toString());
                if ("JWT".equals(cookie.getName())) {
                    System.out.println("CookieName : " + cookie.getName() + "/ CookieValue : " + cookie.getValue());
                    jwtToken = cookie.getValue();
                }
            }
        }

        if (jwtToken == null || jwtToken.equals("")) {
            return ResponseEntity.ok("jwtToken is empty.");
        }

        //System.out.println("validateToken / Authorization : " + authorizationHeader.replace("Bearer",""));

        String urlInfo = "http://192.168.24.136:8888/api/validate";

        //AuthTokenInfo authTokenInfo = new AuthTokenInfo();

        URL url = null;
        HttpURLConnection con = null;

        JSONParser parser = new JSONParser();
        JSONObject message = new JSONObject();

        OutputStreamWriter wr = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        try {

            url = new URL(urlInfo);
            con = (HttpURLConnection) url.openConnection();

            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer "+jwtToken);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-type", "application/json; charset=utf-8");
            con.setDoInput(true);
            con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

           // authTokenInfo.setUserName("yush222");

            // Jackson ObjectMapper를 사용하여 객체를 JSON으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(authTokenInfo);

            System.out.println("Request Body : " + jsonInputString);

            // 요청 본문에 JSON 데이터 작성
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            // 응답 읽기
            if (responseCode == HttpURLConnection.HTTP_OK) { // 성공적인 응답 코드
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String inputLine;
                StringBuilder responseBuilder = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                in.close();

                // 응답 출력
                System.out.println("Response :: " + responseBuilder.toString());

                return ResponseEntity.ok("Authentication successful");
            } else {
                System.out.println("POST request not worked");
            }

        } catch (Exception e){
            System.err.println(e.toString());
        }
        return ResponseEntity.ok("POST request not worked");
    }


    private Cookie createSecureCookie(String value){
        //클라이언트로 보낼 Cookie 생성
        Cookie cookie = new Cookie("JWT",value);

        cookie.setHttpOnly(true); // JavaScript에서 접근 불가
        cookie.setSecure(true); // HTTPS에서만 전송
        cookie.setPath("/"); // 모든 경로에서 접근 가능
        cookie.setMaxAge(3600); // 1시간 (초 단위)
        return cookie;
    }

}
