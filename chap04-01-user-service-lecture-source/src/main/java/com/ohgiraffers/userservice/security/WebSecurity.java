package com.ohgiraffers.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    // 자동으로 로그인페이지로 redirect 하지 말고 우리 방식해도 하게 좀 허락해줘!

    /* 설명. 인가(Authorization)용 메소드 (어디까지 허용할 것인가? 인가가 안돼있으면 403 에러) */
    @Bean       // bean 으로 하는 방식이 요즘 방식. extends 쓰면 옛날 방식
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        /* 설명. JWT 로그인 처리를 할 것이므로 CSRF 는 신경 쓸 필요가 없다. */
        http.csrf((csrf) -> csrf.disable());

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers(new AntPathRequestMatcher("/users/**"))
                .permitAll());

        return http.build();
    }

    /* 설명. 인증(Authentication)용 메소드 (인증이 안돼있으면 401 에러) */
}
