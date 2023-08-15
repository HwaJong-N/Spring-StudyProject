package com.ghkwhd.shop.handler;

import com.ghkwhd.shop.domain.user.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /**
         *  기존에는 로그인 성공 시 url 을 WebSecurityConfig 에서 지정
         *  성공 handler 를 만들었으니 이곳에서 리다이렉트 설정
         */
        response.sendRedirect("/userHome");
    }
}
