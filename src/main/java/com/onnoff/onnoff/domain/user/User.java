package com.onnoff.onnoff.domain.user;

import com.onnoff.onnoff.domain.common.BaseEntity;
import com.onnoff.onnoff.domain.user.enums.SocialType;
import com.onnoff.onnoff.domain.user.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "users") // h2 DB에서 user가 예약어라 테이블명 살짝 변경
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String field_of_work;

    // private String profile_image;

    private String job;

    private String experience_year;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime inactive_date;

    private Boolean receive_push_notification;

    private String fcm_token;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;
}
