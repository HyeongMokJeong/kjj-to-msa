package com.kjj.user.service.sso;

import com.kjj.user.dto.user.JoinDto;
import com.kjj.user.dto.user.UserDto;
import com.kjj.user.entity.User;
import com.kjj.user.entity.UserMyPage;
import com.kjj.user.entity.UserPolicy;
import com.kjj.user.repository.UserMyPageRepository;
import com.kjj.user.repository.UserPolicyRepository;
import com.kjj.user.repository.UserRepository;
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
        UserMyPage userMyPage = userMyPageRepository.save(UserMyPage.createNewUserMyPage());
        return User.of(dto, userPolicy, userMyPage);
    }

    private User createNewUserWithDefaultPolicyAndMyPage(UserDto dto) {
        UserPolicy userPolicy = userPolicyRepository.save(UserPolicy.createNewUserPolicy());
        UserMyPage userMyPage = userMyPageRepository.save(UserMyPage.createNewUserMyPage());
        return User.of(dto, userPolicy, userMyPage);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return null;
        return UserDto.from(user);
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

    @Transactional
    public Boolean withdrawKeycloak(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return false;
        userRepository.delete(user);
        return true;
    }
}
