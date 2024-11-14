package org.example.qnaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class QnaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(QnaProjectApplication.class, args);
    }

}
