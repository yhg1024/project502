package org.choongang.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CommonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        checkDevice(request);

        // login 페이지가 아니면 세션제거
        clearLoginData(request);

        return true;
    }

    /**
     * PC, 모바일 수동 변경 처리
     * // device - PC : PC 뷰, Mobile : Mobile 뷰
     * @param request
     */
    private void checkDevice(HttpServletRequest request) {
        String device = request.getParameter("device");
        if (!StringUtils.hasText(device)) { // device라는 값이 없을때는 return해서 끝낸다
            return;
        }

        device = device.toUpperCase().equals("MOBILE") ? "MOBILE" : "PC";

        HttpSession session = request.getSession();
        session.setAttribute("device", device); // 쿼리스트링에 이런값이 있다면 세션값에서 체크
    }

    private void clearLoginData(HttpServletRequest request) { // request인 이유 : 주소가 뭔지 알아야해서, 세션을 지우기위해 세션 객체 사용
        String URL = request.getRequestURI();
        if (URL.indexOf("/member/login") == -1) {
            HttpSession session = request.getSession();
            MemberUtil.clearLoginData(session);
        }
    }

}
