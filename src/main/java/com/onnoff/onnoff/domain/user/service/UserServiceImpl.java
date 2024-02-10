package com.onnoff.onnoff.domain.user.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.dto.UserRequestDTO;
import com.onnoff.onnoff.domain.user.enums.Status;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Override
    public String isExistByNickname(UserRequestDTO.getNicknameDTO nicknameDTO) {
        if(userRepository.findByNickname(nicknameDTO.getNickname()).isPresent()){
            throw new GeneralException(ErrorStatus.NICKNAME_EXIST);
        }
        return "사용 가능한 닉네임입니다.";
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

    @Transactional
    @Override
    public User modifyUser(UserRequestDTO.ModifyUserDTO modifyUserDTO){
        User user = UserContext.getUser();
        user.updateUser(modifyUserDTO);
        userRepository.save(user);
        return user;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 탈퇴 후 1달 지난 유저 디비 삭제
    public void deleteInactiveUsers() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<User> inactiveUsers = userRepository.findByStatusAndInactiveDateBefore(Status.INACTIVE, oneMonthAgo);
        inactiveUsers.forEach(userRepository::delete);
    }
}
