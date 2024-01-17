package com.onnoff.onnoff.domain.off.memoir.entity;

import com.onnoff.onnoff.domain.common.BaseEntity;
import com.onnoff.onnoff.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
public class Memoir extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String icon;

    @Column(columnDefinition = "boolean default false")
    private Boolean bookmarked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "memoir", cascade = CascadeType.ALL)
    private List<MemoirAnswer> memoirAnswerList = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void setMemoirAnswerList(List<MemoirAnswer> memoirAnswerList) {
        this.memoirAnswerList = memoirAnswerList;
    }
}
