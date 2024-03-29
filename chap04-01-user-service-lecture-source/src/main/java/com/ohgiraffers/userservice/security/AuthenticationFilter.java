package com.ohgiraffers.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.userservice.service.UserService;
import com.ohgiraffers.userservice.vo.RequestLogin;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment environment;

    // AuthenticationManager 는 UsernamePasswordAuthenticationFilter 에서 받아온거
    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment environment) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }

    /* 설명. 로그인 시도 시 동작하는 기능 (POST 방식의 /login 요청) -> Request Body 에 담겨 온다. */
    /* 설명. service 계층 손보러 가자 (우리의 Service 클래스를 UserDetailsService 로 만들기 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            RequestLogin requestLogin =
                    new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestLogin.getEmail(), requestLogin.getPassword(), new ArrayList<>()
                    )
            );  // email 이 id 개념이고, password 가 password 개념이라고 토큰 재정의
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /* 설명. 로그인 성공 시 JWT 토큰 생성하는 기능 */

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {

//        System.out.println("authResult = " + authResult);
        String userName = ((User) authResult.getPrincipal()).getUsername();

        System.out.println("시크릿 키: " + environment.getProperty("token.secret"));
        System.out.println("userName = " + userName);

    }
}
