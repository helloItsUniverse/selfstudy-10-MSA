package com.ohgiraffers.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

/* 설명. Gateway 의 Routes 경로 추가 후 작성했던 부분 */
//@RequestMapping("first-service")

/* 설명. Gateway 에서 RewritePath 필터 추가 후 뒤에 segment 만 넘어오게 한 이후 */
@RequestMapping("/")
public class FirstServiceController {

    /* 설명. @Value 와 같이 application.yml 또는 환경설정 값 불러오기 위해 추가(생성자 주입) */
    private Environment env;

    @Autowired
    public FirstServiceController(Environment env) {
        this.env = env;
    }

    @GetMapping("health_check")
    public String healthCheck() {

        /* 설명. first-service 어플리케이션 여러개 실행 시 (스케일 아웃) 실행되는 어플리케이션 확인(포트 번호로) */
        /* 설명. Gateway 의 lb(load balancing)를 통해 RoundRobin 방식으로 스위칭됨을 확인 */
//        log.info("요청 경로 포트1: {}", env.getProperty("local.server.port"));
        return "I'm OK port at" + env.getProperty("local.server.port");
    }

    /* 설명. Gateway 를 거치고 넘어오는 요청 시 RequestHeader 에 값이 추가됨(Gateway 의 필터에 의해) */
    @GetMapping("message")
    public String message(@RequestHeader("first-request") String header) {
        // @RequestHeader: 쿠키가 넘어올 수 있는 공간
        log.info("넘어온 헤더 값: {}", header);
        return "First Service Message";
    }
}
