package com.onnoff.onnoff.domain.user;

import com.onnoff.onnoff.domain.common.BaseEntity;
import com.onnoff.onnoff.domain.user.enums.SocialType;
import com.onnoff.onnoff.domain.user.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.ArrayList;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicInsert //Default 값 DB에 제대로 적용 시키기 위해서
@Table(name = "users") // h2 DB에서 user가 예약어라 테이블명 살짝 변경
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String nickname;

    private String email;

    private String field_of_work;

    // private String profile_image;

    private String job;

    private String experience_year;

    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime inactive_date;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean receive_push_notification;

    private String fcm_token;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Memoir> memoirList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Feed> feedList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<FeedImage> feedImageList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // OFF 관련 엔티티는 클래스명 확정 나면 수정해야 할 수도
//    private List<Resolution> ResolutionList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Worklog> WorklogList = new ArrayList<>();
}
