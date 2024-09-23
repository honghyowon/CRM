package com.study.crmProject.common.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable) // http 기본 인증 비활성화
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
                .sessionManagement(sessionManagement // JWT토큰을 사용하기 때문에 Session 비활성화
                        -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request // 권한 관리
                        .requestMatchers("/api/user/signup","/api/user/login").anonymous() // 인증되지 않은 사용자 허용
                        .requestMatchers("/api/user/**").hasRole("USER") // "USER" 권한을 가진 사용자 허용
//                        .requestMatchers("/home/**").authenticated()
                        .anyRequest().permitAll() // 그밖의 요청 허용
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    // 암호화를 위한 passwordEncoder 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}