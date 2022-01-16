package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.posts.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
// 스프링 시큐리티 설정들을 활성화 시켜 줍니다.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable() //h2-console 화면을 사용하기 위해 해당 옵션들을 disable 합니다.
                .and().authorizeRequests() //URL별 권한 관리를 설정하는 옵션의 시작점 입니다. authorizeRequests가 선언되야만 andMatchers 옵션 사용 가능
                //권한 관리 대상을 지정하는 옵션
                //URL, HTTP 메소드별로 관리가 가능함
                // "/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었습니다.
                // "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 했습니다.
                .antMatchers("/","/css/**", "/images/**", "/js/**", "/h2-console/**","/profile").permitAll().antMatchers("/api/vi/**").hasRole(Role.USER.name())
                .anyRequest().authenticated() //나머지 url은 모두 인증된 사용자 들이게만 허용함(로그인한 사용자만 가능)
                .and().logout().logoutSuccessUrl("/") //로그아웃 성공시 / 주소로 이동합니다.
                .and().oauth2Login() //oauth2 로그인 기능에 대한 여러 설정의 진입점 입니다.
                .userInfoEndpoint() //oauth2 로그인 후 사용자 정보를 가져올 떄 설정들을 담당합니다.
                .userService(customOAuth2UserService); //소셜로그인 성공 시 후속 조치를 진행할 UserSerice 인터페이스의 구현체를 등록 함
    }
}
