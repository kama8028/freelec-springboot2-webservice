package com.jojoldu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing //JPA Auditing 활성화
/**
 * @SpringBootApplication으로 인해 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정 한다.
 * @SpringBootApplication이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트의 최상단에 위치 해야만 한다.
 * 한용선 수정본 입니다. 다시 수정하였습니다.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        /**
         * SpringApplication.run으로 인해 내장 WAS(웹 어플리케이션 서버)를 실행합니다. 내장 WAS란 별도로 외부에 WAS를 두지 않고
         * 애플리케이션을 실행할 때 내부에서 WAS를 실행하는 것을 이야기 합니다. 이렇게 되면 항상 서버에 톰캣을 설치할 필요가 없게 되고
         * 스프링 부트로 만들어진 jar 파일(실행가능한 java 패키징 파일)로 실행하면 됩니다.
         */
        SpringApplication.run(Application.class, args);
    }
}
