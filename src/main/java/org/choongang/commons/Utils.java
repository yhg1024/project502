package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;

    public boolean isMobile() {
        // 요청 헤더 : User-Agent 에서 모바일인지 확인
        String ua = request.getHeader("User-Agent");

        // 이 패턴이 일치하면 모바일 장비
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

        boolean isMobile = ua.matches(pattern);

        return isMobile;
    }
}
