package com.onnoff.onnoff.domain.user.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.user.User;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class UserServiceImplTest {

    private final UserService userService;

    public UserServiceImplTest(@Autowired UserService userService) {
        this.userService = userService;
    }
    @Test
    void 생성_및_조회() {
        User user = User.builder().name("우성").nickname("우스").build();

        User createdUser = userService.create(user);

        User findUser = userService.getUser(createdUser.getId());
        Assertions.assertThat(user.getName()).isEqualTo(findUser.getName());

    }
    @Test
    void 회원탈퇴() {
        // 유저가 회원가입을 했고 id가 6인 상황

        //유저 id 6인 사용자가 회원탈퇴 버튼 누름
        User user = userService.getUser(6l);
        user.setUserStatusInactive();
        //한달 후에 hard delete
        userService.deleteInactiveUsers();
        // 삭제 됐는지 확인
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            userService.getUser(6l);
        });
        Assertions.assertThat(ErrorStatus.USER_NOT_FOUND).isEqualTo(exception.getCode());
        // 카카오톡 들어가서 연결된 앱에서 해지 됐는지도 확인
    }

}