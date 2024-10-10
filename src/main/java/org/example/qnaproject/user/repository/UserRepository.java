package org.example.qnaproject.user.repository;

import org.example.qnaproject.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // TODO : 페이징 및 검색 기능 추가
}
