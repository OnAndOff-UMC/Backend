package com.onnoff.onnoff.domain.user.service;

import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public Long create(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow( () ->
                new IllegalArgumentException("유저가 존재하지 않습니다.")
        );
        return user;
    }
    @Override
    public boolean isExistByOauthId(Long oauthId) {
        return userRepository.findById(oauthId).isPresent();
    }
}
