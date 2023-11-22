package com.kjj.auth.service;

import com.kjj.auth.security.userDetails.UserDetailsInterface;
import com.kjj.auth.security.userDetails.UserDetailsInterfaceImpl;
import com.kjj.auth.entity.User;
import com.kjj.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetailsInterface loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("""
                해당 username을 가진 유저를 찾을 수 없습니다.
                username : """ + username);
        return new UserDetailsInterfaceImpl(user);
    }
}