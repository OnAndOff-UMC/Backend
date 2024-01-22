package com.onnoff.onnoff.domain.user;

import com.onnoff.onnoff.domain.common.BaseEntity;
import com.onnoff.onnoff.domain.off.feed.entity.Feed;
import com.onnoff.onnoff.domain.off.feedImage.entity.FeedImage;
import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;
import com.onnoff.onnoff.domain.on.worklog.entity.Worklog;
import com.onnoff.onnoff.domain.user.enums.SocialType;
import com.onnoff.onnoff.domain.user.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@DynamicInsert //Default 값 DB에 제대로 적용 시키기 위해서
@Table(name = "users") // h2 DB에서 user가 예약어라 테이블명 살짝 변경
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long oauthId;  //토큰으로 얻어온 정보로 유저를 조회할 때 사용

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean infoSet; //추가 정보 기입 여부

    @Column(nullable = false)
    private String name;

    @Column(length = 10)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(length = 30)
    private String fieldOfWork;

    // private String profile_image;
    @Column(length = 30)
    private String job;

    @Column(length = 30)
    private String experienceYear;

    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime inactiveDate;

    //@Column(columnDefinition = "BOOLEAN DEFAULT false") //h2 테스트 전용
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean receivePushNotification;

    private String fcmToken;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Memoir> memoirList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Feed> feedList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FeedImage> feedImageList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Resolution> resolutionList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Worklog> worklogList = new ArrayList<>();
}
