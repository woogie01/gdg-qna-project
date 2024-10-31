package org.example.qnaproject.user;

import org.example.qnaproject.user.domain.User;
import org.example.qnaproject.user.dto.SignupRequest;
import org.example.qnaproject.user.exception.AlreadyExistsEmailException;
import org.example.qnaproject.user.repository.UserRepository;
import org.example.qnaproject.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        SignupRequest signup = SignupRequest.builder()
                .email("nmkk1234@naver.com")
                .password1("1234")
                .password2("1234")
                .username("kim")
                .build();

        // when
        userService.signup(signup);

        // then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("nmkk1234@naver.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertNotEquals("1234", user.getPassword());
        assertEquals("kim", user.getUsername());
    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2() {

        // given
        User user = User.builder()
                .email("nmkk1234@naver.com")
                .password("12345")
                .username("kim")
                .build();

        userRepository.save(user);

        // when
        SignupRequest signup = SignupRequest.builder()
                .email("nmkk1234@naver.com")
                .password1("1234")
                .password1("1234")
                .username("kim2")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class, () -> userService.signup(signup));
    }
}
