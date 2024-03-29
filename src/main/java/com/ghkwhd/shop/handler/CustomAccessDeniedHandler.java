package com.ghkwhd.shop.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String redirectUrl = "/accessDenied";
        // 쿼리 파라미터로 에러 메세지를 넘긴다
        String queryParameter = "?exception=" + accessDeniedException.getMessage() + "&status=" + HttpStatus.FORBIDDEN.value();
        response.sendRedirect(redirectUrl + queryParameter);
    }
}
