package com.onnoff.onnoff.domain.user.service;

import com.onnoff.onnoff.domain.user.User;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
}