package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class HackingSpringBootApplication {

    public static void main(String[] args) {
//        BlockHound.install();  // 스프링 부트 애플리케이션 시작할 때 블록하운드가 바이트코드를 조작할 수 있음
        BlockHound.builder()
                // 허용 리스트 추가
                .allowBlockingCallsInside(
                        TemplateEngine.class.getCanonicalName(), "process")
                // 커스텀 설정이 적용된 블록하운드가 애플리케이션에 심어짐
                .install();

        SpringApplication.run(HackingSpringBootApplication.class, args);
    }

}
