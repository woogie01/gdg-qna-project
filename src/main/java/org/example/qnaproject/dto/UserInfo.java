package org.example.qnaproject.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfo {

    private Long id;

    private String email;

    private String username;
}