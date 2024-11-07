package org.example.qnaproject.global;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseEntity {

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @PrePersist
    public void onPrePersist() {
        this.createDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }
}
