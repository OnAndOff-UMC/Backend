package com.onnoff.onnoff.domain.user.service;

import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired UserService userService;
    @Test
    void 생성_및_조회() {
        User user = User.builder().name("우성").nickname("우스").build();

        Long userId = userService.create(user);

        User findUser = userService.getUser(userId);
        Assertions.assertThat(user.getName()).isEqualTo(findUser.getName());

    }

}