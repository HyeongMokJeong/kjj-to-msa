package com.kjj.noauth.service;

import com.kjj.noauth.dto.user.JoinDto;
import com.kjj.noauth.entity.User;
import com.kjj.noauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("""
                해당 username을 가진 유저를 찾을 수 없습니다.
                username : """ + username);
        return user;
    }

    public void join(JoinDto dto) {
        // user 생성 및 저장, id를 얻어서 user 서버에 mypage, policy 추가 요청
    }

    public User joinKeycloak(String sub, String roles) {
        User user = User.ofKeycloak(sub, roles);
        // user 저장, id를 얻어서 user 서버에 mypage, policy 추가 요청

        return user;
    }

    public boolean checkLoginId(String username) {
        return userRepository.existsByUsername(username);
    }
}
