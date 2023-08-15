package com.ghkwhd.shop.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "";
        if (exception instanceof BadCredentialsException) {
            errorMessage = "비밀번호가 일치하지 않습니다";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "등록되지 않은 사용자입니다";
        }
        setDefaultFailureUrl("/loginHome?exception=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8));
        super.onAuthenticationFailure(request, response, exception);
    }
}
