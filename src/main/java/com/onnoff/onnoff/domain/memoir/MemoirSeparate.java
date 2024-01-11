package com.onnoff.onnoff.domain.memoir;

import com.onnoff.onnoff.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemoirSeparate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoir_id")
    private Memoir memoir;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoir_question_id")
    private MemoirQuestion memoirQuestion;

}
