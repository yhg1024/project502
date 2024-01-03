package org.choongang.configs;

import org.choongang.member.service.MemberInfo;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

// 로그인 사용자가 자동 DB 추가
@Component // 스프링 관리 객체
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        String userId = null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        /**
         * getPrincipal()
         * 로그인 상태 : UserDetails 구현 객체 : MemberInfo
         * 미로그인 상태 : String(문자열) / anonymousUser
         */
        if ( auth != null && auth.getPrincipal() instanceof MemberInfo) {
            MemberInfo memberInfo = (MemberInfo) auth.getPrincipal();
            userId = memberInfo.getUserId();
        }

        return Optional.ofNullable(userId);
    }
}
