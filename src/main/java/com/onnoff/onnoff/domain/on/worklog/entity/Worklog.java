package com.onnoff.onnoff.domain.on.worklog.entity;

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
public class Worklog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(length = 30)
    private String content;

    private Boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user){
        if(this.user != null){
            user.getWorklogList().remove(this);
        }
        this.user = user;
        user.getWorklogList().add(this);
    }

    public void setIsChecked(){
        this.isChecked= this.isChecked.equals(false);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }
}