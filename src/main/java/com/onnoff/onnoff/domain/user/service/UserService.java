package com.onnoff.onnoff.domain.user.service;

import com.onnoff.onnoff.domain.user.User;

import java.util.List;

public interface UserService {
    public Long create(User user);

    public List<User> getUserList();

    public User getUser(Long id);

    public boolean isExistByOauthId(Long oauthId);

    public User getUserByOauthId(Long oauthId);
}
