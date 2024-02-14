package com.onnoff.onnoff.domain.off.memoir.entity;

import com.onnoff.onnoff.domain.common.BaseEntity;
import com.onnoff.onnoff.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Memoir extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emoticon_id")
    private Emoticon emoticon;

    @Column(columnDefinition = "boolean default false")
    private Boolean isBookmarked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "memoir", cascade = CascadeType.ALL)
    private List<MemoirAnswer> memoirAnswerList = new ArrayList<>();

    public void setEmoticon(Emoticon emoticon) {
        this.emoticon = emoticon;
    }

    public void setIsBookmarked(Boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }
}
