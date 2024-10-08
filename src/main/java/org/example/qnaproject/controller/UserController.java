package org.example.qnaproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.qnaproject.dto.LoginRequest;
import org.example.qnaproject.dto.SignupRequest;
import org.example.qnaproject.dto.UserInfo;
import org.example.qnaproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Validated SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return new ResponseEntity<>("가입 완료", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated LoginRequest loginRequest, HttpServletRequest request) {
        UserInfo loginUser = userService.login(loginRequest);
        return new ResponseEntity<>("로그인 완료", HttpStatus.OK);
    }
}