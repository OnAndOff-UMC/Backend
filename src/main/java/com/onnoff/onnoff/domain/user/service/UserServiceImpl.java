package com.onnoff.onnoff.domain.user.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Transactional
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow( () ->
                new GeneralException(ErrorStatus.USER_NOT_FOUND)
        );
        return user;
    }
    @Transactional(readOnly = true)
    @Override
    public boolean isExistByOauthId(String oauthId) {
        return userRepository.findByOauthId(oauthId).isPresent();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByOauthId(String oauthId) {
        User user = userRepository.findByOauthId(oauthId).orElseThrow( () ->
                new GeneralException(ErrorStatus.USER_NOT_FOUND)
        );
        return user;
    }

    @Transactional
    @Override
    public User withdrawUser(){
        User user = UserContext.getUser();
        user.setUserStatusInactive();
        userRepository.save(user);
        return user;
    }
}
