package com.onnoff.onnoff.domain.on.resolution.entity;

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
public class Resolution extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(length = 30)
    private String content;

    @Column(name = "order_num")
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user){
        if(this.user != null){
            user.getResolutionList().remove(this);
        }
        this.user = user;
        user.getResolutionList().add(this);
    }

    public void setOrder(Integer order){
        this.order = order;
    }

    public void modifyResolution(Integer order, String content){
        this.order = order;
        this.content = content;
    }
}
