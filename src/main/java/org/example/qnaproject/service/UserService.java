package org.example.qnaproject.service;

import lombok.RequiredArgsConstructor;
import org.example.qnaproject.domain.User;
import org.example.qnaproject.dto.LoginRequest;
import org.example.qnaproject.dto.SignupRequest;
import org.example.qnaproject.dto.UserInfo;
import org.example.qnaproject.exception.AlreadyExistsEmailException;
import org.example.qnaproject.exception.AlreadyExistsUsernameException;
import org.example.qnaproject.exception.PasswordNotMatchException;
import org.example.qnaproject.exception.UserNotFound;
import org.example.qnaproject.repository.UserRepository;
import org.example.qnaproject.util.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        Optional<User> userOptional = userRepository.findByEmail(signupRequest.getEmail());

        // 중복 회원 체크
        if (userOptional.isPresent()) {
            if (userOptional.get().getUsername().equals(signupRequest.getUsername())) {
                throw new AlreadyExistsUsernameException();
            }
            throw new AlreadyExistsEmailException();
        }

        // 이메일 일치 확인
        if (!Objects.equals(signupRequest.getPassword1(), signupRequest.getPassword2())) {
            throw new PasswordNotMatchException();
        }

        String encryptedPassword = getEncryptedPassword(signupRequest.getEmail(),signupRequest.getPassword1());

        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(encryptedPassword)
                .username(signupRequest.getUsername())
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserInfo login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(UserNotFound::new);

        // 비밀번호 확인 절차
        String encryptedPassword = getEncryptedPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (!Objects.equals(user.getPassword(), encryptedPassword)) {
            throw new PasswordNotMatchException();
        }

        return UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    private String getEncryptedPassword(String email, String password) {
        return passwordEncoder.encode(email, password);
    }
}