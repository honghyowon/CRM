package com.study.crmProject.user.service;

import com.study.crmProject.common.authority.JwtTokenProvider;
import com.study.crmProject.common.authority.TokenInfo;
import com.study.crmProject.common.exception.InvalidInputException;
import com.study.crmProject.common.status.Role;
import com.study.crmProject.user.contriller.UserInfoResponse;
import com.study.crmProject.user.contriller.request.LoginRequest;
import com.study.crmProject.user.contriller.request.UserRegisterRequest;
import com.study.crmProject.user.entity.User;
import com.study.crmProject.user.entity.UserRole;
import com.study.crmProject.user.repository.UserRepository;
import com.study.crmProject.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     */
    public String signUp(UserRegisterRequest request) {
        if (userRepository.findByLoginId(request.getLoginId()).isPresent()) {
            throw new InvalidInputException("loginId", "중복된 ID 입니다.");
        }

        User user = request.toEntity(passwordEncoder.encode(request.getPassword()));
        System.out.println(user);

        userRepository.save(user);
        UserRole userRole = UserRole.builder()
                .role(Role.USER)
                .user(user)
                .build();
        userRoleRepository.save(userRole);

        return "가입되었습니다.";
    }

    /**
     * 로그인
     */
    public TokenInfo login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authentication);
    }

    /**
     * 정보조회
     */
    public UserInfoResponse userInfo(Long userId) {
        User findMember = userRepository.findById(userId).orElseThrow(() ->
                new InvalidInputException("token", "회원 정보가 존재하지 않습니다."));
        return new UserInfoResponse(findMember);
    }
}