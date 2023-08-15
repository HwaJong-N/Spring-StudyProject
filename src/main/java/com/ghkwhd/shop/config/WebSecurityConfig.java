package com.ghkwhd.shop.config;

import com.ghkwhd.shop.filter.CustomAuthenticationFilter;
import com.ghkwhd.shop.handler.CustomAccessDeniedHandler;
import com.ghkwhd.shop.handler.CustomLoginFailureHandler;
import com.ghkwhd.shop.handler.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpSession;


@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 로그인 성공 시 동작할 핸들러 등록
    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }


    // 접근 권한 오류 처리 핸들러 등록
    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    // 로그인 실패 처리 핸들러  
    @Bean
    public CustomLoginFailureHandler customLoginFailureHandler() {
        return new CustomLoginFailureHandler();
    }


    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder());
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        //  로그인 요청 url 을 정의, Controller 따로 생성할 필요 없음, loginProcessingUrl 과 동일
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        // 실패 핸들러 등록, configure() 에 failureHandler 에 적으면 핸들러가 동작 안함
        customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());    
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 회원가입
        http.authorizeRequests()
                .antMatchers("/", "/signUp", "/js/**", "/loginHome").permitAll()    // js 실행을 위해 필요
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")  // admin 으로 시작하는 요청은 모두 ADMIN 권한 필요
                .anyRequest().authenticated();   // 그 외 모든 요청은 인증이 필요

        // 로그인
        http.csrf().disable() // post 방식으로 값을 전송할 때 token을 사용해야하는 보안 설정을 해제
                .formLogin()
                .loginPage("/loginHome")    // 로그인 페이지 url ( 요청 url )
                .usernameParameter("id")    // 이메일이 아닌 id 로 로그인을 할 것이기 때문에 설정
                .permitAll()
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler());  // 접근 권한 오류 처리 핸들러

        // 로그아웃
        http.logout()
                .logoutUrl("/logout")   // 로그인과 마찬가지로 POST 요청이 와야함
                .addLogoutHandler(((request, response, authentication) -> {
                    HttpSession session = request.getSession();
                    if (session != null) {
                        session.invalidate();   // 세션 삭제
                    }
                }))
                .logoutSuccessHandler(((request, response, authentication) -> {
                    response.sendRedirect("/");
                }));
    }
    
}
