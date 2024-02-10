package com.onnoff.onnoff.domain.user.service;

import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.dto.UserRequestDTO;

import java.util.List;

public interface UserService {
    public User create(User user);

    public List<User> getUserList();

    public User getUser(Long id);

    public boolean isExistByOauthId(String oauthId);

    public User getUserByOauthId(String oauthId);

    public User withdrawUser();

    public void deleteInactiveUsers();
    public User modifyUser(UserRequestDTO.ModifyUserDTO modifyUserDTO);
}
