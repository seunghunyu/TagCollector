package com.tag.collector.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
          http.authorizeHttpRequests()
                  .antMatchers("/","/login","/register","/logout","/css/**","/js/**","/images/**","/fonts/**","/scss/**","/h2-console/**").permitAll()   //특정 URL 경로 패턴과 일치하는 요청에 대한 접근 권한을 설정   Pattern에 적힌 경로는 인증 필요없다는 의미
                  .anyRequest().authenticated()                                                      //위의 패턴을 제외한 모든 경로에 대한 인증을 요구
                  .and()
              .formLogin()    //기본 로그인 폼을 사용하도록 설정
                  .loginPage("/login") //로그인 폼 경로 ( 없으면 기본 로그인 폼 제공됨)
                  .permitAll() //인증없이 로그인 폼 경로 접근 가능
                  .and()
              .logout() // 로그아웃 기능 허용 -> 인증 없이 로그아웃 URL 접근하여 로그아웃 할 수 있음
                  .permitAll()
                  .and()
                  .csrf().disable() // H2 콘솔을 사용하려면 CSRF 비활성화
                  .headers().frameOptions().sameOrigin(); // H2 콘솔을 사용하기 위한 설정

          //Force HTTPS
          http.requiresChannel()      //특정 채널(예: HTTPS)을 요구하는 설정입니다.
                  .anyRequest()
                  .requiresSecure();  //모든 요청에 대해 HTTPS를 요구합니다. 모든 HTTP 요청은 HTTPS로 리디렉션

          //Enable HSTS
          http.headers()
                  .httpStrictTransportSecurity()      //HSTS는 웹 사이트가 항상 HTTPS를 사용하도록 브라우저에 지시하는 보안 정책
                  .includeSubDomains(true)            //HSTS 정책을 하위 도메인에도 적용, 도메인뿐만 아니라 모든 하위 도메인도 HTTPS를 사용
                  .maxAgeInSeconds(3600);            //HSTS 정책의 최대 지속 기간을 설정 -> 31536000 권장

          return http.build();
      }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests((authorizeRequests)->
//                    authorizeRequests.anyRequest().permitAll() // 로그인 페이지 나오지 않도록 모든 요청 URL 허용 속성
//        );
//        return http.build();
//    }

//    @ Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests((authorizeRequests)->
//                //authorizeRequests.anyRequest().authenticated()
//                authorizeRequests
////                        .requestMatchers("/api1").hasRole("user")
////                        .requestMatchers("/api2").hasRole("admin")
//                        .anyRequest().authenticated()
//
//
//        ).formLogin((formLogin) ->
//                formLogin
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/", true)
//
//        );
//        return http.build();
//    }

//    @ Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        //manager.createUser(User.withUsername("user1").password("1234").build());
//        manager.createUser(User.withUsername("user1").password("1234").roles("user").build());
//        return manager;
//    }
//
//    @ Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }


}
