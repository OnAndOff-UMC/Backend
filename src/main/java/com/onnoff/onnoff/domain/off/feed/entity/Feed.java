package com.onnoff.onnoff.domain.off.feed.entity;

import com.onnoff.onnoff.domain.common.BaseEntity;
import com.onnoff.onnoff.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Feed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(nullable = false, length = 30)
    private String content;

    @Column(columnDefinition = "boolean default false")
    private Boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }
}
