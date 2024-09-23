package com.study.crmProject.user.contriller;

import com.study.crmProject.common.authority.CustomUser;
import com.study.crmProject.common.response.BaseResponse;
import com.study.crmProject.user.contriller.request.LoginRequest;
import com.study.crmProject.user.contriller.request.UserRegisterRequest;
import com.study.crmProject.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public BaseResponse<?> signup(@RequestBody @Valid UserRegisterRequest request) {
        return BaseResponse.ok(userService.signUp(request));
    }

    @PostMapping("/login")
    public BaseResponse<?> login(@RequestBody @Valid LoginRequest request) {
        return BaseResponse.ok(userService.login(request));
    }

    @GetMapping
    public BaseResponse<?> myInfo() {
        Long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return BaseResponse.ok(userService.userInfo(userId));
    }
}
