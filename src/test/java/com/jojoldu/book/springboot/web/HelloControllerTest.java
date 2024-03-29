package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킵니다.
 * 여기서는 SpringRunner라는 스프링 실행자를 사용합니다.
 * 즉, 스프링부트 테스트와 Junit 사이에 연결자 역할을 합니다.
 */
@RunWith(SpringRunner.class)
/**
 * 여러 스프링 테스트 어노테이션 중 Web에 집중할 수 있는 어노테이션 입니다.
 * 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있습니다.
 * 단, @Service, @Component, @Repository 등은 사용할 수 없습니다.
 * 여기서는 컨트롤러만 사용하기 때문에 선언합니다.
 */
@WebMvcTest(controllers = HelloController.class,
            excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
            })
public class HelloControllerTest {

    /**
     * 스프링이 관리하는 빈(Bean)을 주입 받습니다
     */
    @Autowired
    /**
     * 웹 API를 테스트할 때 사용합니다.
     * 스프링 MVC 테스트의 시작점 입니다.
     * 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있습니다.
     */
    private MockMvc mvc;

    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        /**
         * 결과를 검증한다.
         * isOK : 200, 404, 500등의 상태를 검증
         * content : 응답 본문의 내용을 검증합니다. controller에서 "hello"를 리턴하기 떄문에 이 값이 맞는지 검증 합니다.
         */
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        /**
         * param : API 테스트할 떄 사용될 요청 파라미터를 설정합니다. 단, String만 허용합니다.
         *         그래서 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경해야만 가능 합니다.
         * jsonPath : JSON 응답값을 필드별로 검증할 수 있는 메소드 입니다.
         *            $를 기준으로 필드명을 명시합니다.
         *            여기서는 name과 amount를 검증하니 $.name, $.amount를 검증합니다.
         */
        mvc.perform(
                get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount))
        );
    }
}
