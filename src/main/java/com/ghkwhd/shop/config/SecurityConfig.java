package com.ghkwhd.shop.config;

import com.ghkwhd.shop.service.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Spring Security 에서 사용자의 정보를 담는 인터페이스 : UserDetails
     * UserDetailsService 내의 loadUserByUsername 은 유저의 정보를 불러와 UserDetails 로 변환한다
     */

    private final UserDetailsService userDetailsService;    // spring security 에서 제공

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .formLogin()
                .loginPage("/login")    // 인증되지 않은 사용자가 접근했을 때 이동시킬 url
                .loginProcessingUrl("/login")   // POST 방식으로 login url 이 호출되면 실행된다
                .usernameParameter("id")    // 사용자를 찾기 위해 사용할 매개변수
                .passwordParameter("password")
                .defaultSuccessUrl("/userHome")
                .failureUrl("/login");


        http.authorizeRequests()
                .antMatchers("/", "/signUp", "/login").permitAll()     // 설정된 url 의 접근을 인증없이 허용한다
                .antMatchers("/adminHome").access("hasRole('ROLE_ADMIN')")  // adminPage 의 요청은 ADMIN 만 가능
                .anyRequest().authenticated();      // 모든 리소스가 인증을 해야만 접근이 허용된다

        http.exceptionHandling().accessDeniedPage("/accessDenied");     // 권한이 없는 페이지 접속하면 해당 url 로 요청

    }

}
