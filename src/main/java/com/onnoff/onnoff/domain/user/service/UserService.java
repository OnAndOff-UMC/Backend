package com.onnoff.onnoff.domain.user.service;

import com.onnoff.onnoff.domain.user.User;

import java.util.List;

public interface UserService {
    public User create(User user);

    public List<User> getUserList();

    public User getUser(Long id);

    public boolean isExistByOauthId(String oauthId);

    public User getUserByOauthId(String oauthId);

    public User withdrawUser();
}
