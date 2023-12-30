package com.kjj.user.service.sso;

import com.kjj.user.dto.user.JoinDto;
import com.kjj.user.dto.user.UserDto;
import com.kjj.user.entity.User;
import com.kjj.user.entity.UserMypage;
import com.kjj.user.entity.UserPolicy;
import com.kjj.user.exception.CantFindByIdException;
import com.kjj.user.repository.user.UserMyPageRepository;
import com.kjj.user.repository.user.UserPolicyRepository;
import com.kjj.user.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserKeycloakService {
    private final UserRepository userRepository;
    private final UserPolicyRepository userPolicyRepository;
    private final UserMyPageRepository userMyPageRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private User createNewUserWithDefaultPolicyAndMyPage(JoinDto dto) {
        UserPolicy userPolicy = userPolicyRepository.save(UserPolicy.createNewUserPolicy());
        UserMypage userMyPage = userMyPageRepository.save(UserMypage.createNewUserMyPage());
        return User.of(dto, userPolicy, userMyPage);
    }

    private User createNewUserWithDefaultPolicyAndMyPage(UserDto dto) {
        UserPolicy userPolicy = userPolicyRepository.save(UserPolicy.createNewUserPolicy());
        UserMypage userMyPage = userMyPageRepository.save(UserMypage.createNewUserMyPage());
        return User.of(dto, userPolicy, userMyPage);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CantFindByIdException("""
                해당 username을 가진 유저를 찾을 수 없습니다.
                username = """ + username));
        UserDto result = UserDto.from(user);
        user.updateLoginDate();
        return result;
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public Boolean join(JoinDto dto) {
        dto.setEncodePassword(bCryptPasswordEncoder);
        try {
            userRepository.save(createNewUserWithDefaultPolicyAndMyPage(dto));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional
    public UserDto joinKeycloak(UserDto dto) {
        try {
            userRepository.save(createNewUserWithDefaultPolicyAndMyPage(dto));
        } catch (Exception e) {
            return null;
        }
        return dto;
    }
}
