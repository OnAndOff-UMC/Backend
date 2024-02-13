package com.onnoff.onnoff.domain.user.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.auth.service.AppleLoginService;
import com.onnoff.onnoff.auth.service.KakaoLoginService;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.dto.UserRequestDTO;
import com.onnoff.onnoff.domain.user.enums.SocialType;
import com.onnoff.onnoff.domain.user.enums.Status;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final AppleLoginService appleLoginService;
    private final KakaoLoginService kakaoLoginService;


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
        inactiveUsers.forEach(this::disconnectApp);
    }
    @Transactional
    public void deleteInactiveUsersTest() {
        LocalDateTime now = LocalDateTime.now();
        List<User> inactiveUsers = userRepository.findByStatusAndInactiveDateBefore(Status.INACTIVE, now);
        log.info("inactiveUsers = {}", inactiveUsers);
        inactiveUsers.forEach(userRepository::delete);
        inactiveUsers.forEach(this::disconnectApp);

    }
    // 유저 소셜계정 앱 연동 해지
    private void disconnectApp(User user){
        SocialType socialType = user.getSocialType();
        if(SocialType.isApple(socialType)) {
            String appleRefreshToken = user.getAppleRefreshToken();
            appleLoginService.revokeTokens(appleRefreshToken);
        }
        else {
            String oauthId = user.getOauthId();
            kakaoLoginService.revokeTokens(oauthId);
        }
    }

}
