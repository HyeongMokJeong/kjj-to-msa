package com.kjj.user.service.manager;

import com.kjj.user.dto.info.ManagerInfoDto;
import com.kjj.user.entity.User;
import com.kjj.user.exception.CantFindByUsernameException;
import com.kjj.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ManagerInfoDto getInfo(String username) throws CantFindByUsernameException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CantFindByUsernameException("""
                username을 가진 유저 데이터가 없습니다.
                username : """ + username));
        return ManagerInfoDto.from(user);
    }
}
