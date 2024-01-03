package org.choongang.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.choongang.member.service.LoginFailureHandler;
import org.choongang.member.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 인증 설정 Start - 로그인, 로그아웃
        http.formLogin(f -> {
           f.loginPage("/member/login")
                   .usernameParameter("username")
                   .passwordParameter("password")
                   //defaultSuccessUrl("/") // 로그인 이후 이동 페이지
                   .successHandler(new LoginSuccessHandler())
                   .failureHandler(new LoginFailureHandler());
                   /*.failureUrl("/member/login?error=true");*/
        });

        http.logout(c -> {
           c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                   .logoutSuccessUrl("/member/login"); // 로그아웃 이후에 로그인 페이지로 이동
        });
        // 인증 설정 End - 로그인, 로그아웃

        /*인가 설정 S - 접근 통제*/
        // hasAuthority(..) hasAnyAuthority(...), hasRole, hasAnyRole
        // ROLE_롤명칭
        // hasAuthority('ADMIN)
        // ROLE_ADMIN -> hasAuthority('ROLE_ADMIN')
        // hasRole('ADMIN')
        http.authorizeHttpRequests(c -> {
            // authorizeHttpRequests : 인가설정, 이 주소로 접근할때는 회원 로그인을 해야 접근 가능
            c.requestMatchers("/mypage/**").authenticated() // 회원전용
                    //.requestMatchers("/admin/**").hasAnyAuthority("ADMIN", "MANAGER") // 여러개 권한
                    .anyRequest().permitAll(); // 그외 모든 페이지는 모두 접근 가능
            // requestMatchers("/mypage/**") : /mypage/ 시작하는 URL에 대한 보안 설정
        });

        /*http.exceptionHandling(c -> {
            c.authenticationEntryPoint(new AuthenticationEntryPoint() {
                @Override
                public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                }
            });
        });*/

        http.exceptionHandling(c -> { // exceptionHandling : 예외처리
                    c.authenticationEntryPoint((req, res, e) -> {
                        // AuthenticationEntryPoint : 기본 오류 페이지가 아닌 커스텀 오류 페이지를 보여준다거나, 특정 로직을 수행 또는 JSON 데이터 등으로 응답해야 하는 경우
                        String URL = req.getRequestURI();
                        if (URL.indexOf("/admin") != -1) { // 관리자 페이지
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 401
                        } else { // 회원 전용 페이지
                            res.sendRedirect(req.getContextPath() + "/member/login");
                        }

                    });
                });



        /*인가 설정 E - 접근 통제*/

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
