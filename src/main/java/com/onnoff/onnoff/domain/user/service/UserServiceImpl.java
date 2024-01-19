package com.onnoff.onnoff.domain.user.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
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
                new GeneralException(ErrorStatus.USER_NOT_FOUND)
        );
        return user;
    }
    @Override
    public boolean isExistByOauthId(Long oauthId) {
        return userRepository.findById(oauthId).isPresent();
    }

    @Override
    public User getUserByOauthId(Long oauthId) {
        User user = userRepository.findByOauthId(oauthId).orElseThrow( () ->
                new GeneralException(ErrorStatus.USER_NOT_FOUND)
        );
        return user;
    }
}
