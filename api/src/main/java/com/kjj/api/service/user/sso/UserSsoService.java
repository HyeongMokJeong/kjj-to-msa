package com.kjj.api.service.user.sso;

import com.kjj.api.dto.user.info.UserInfoDto;
import com.kjj.api.dto.user.user.UserJoinDto;
import com.kjj.api.entity.user.User;
import com.kjj.api.exception.exceptions.CantFindByIdException;
import com.kjj.api.exception.exceptions.CantFindByUsernameException;
import org.springframework.transaction.annotation.Transactional;

public interface UserSsoService {
    @Transactional
    User join(User user);

    boolean existsByUsername(String userName);

    @Transactional
    void joinSso(UserJoinDto dto);

    @Transactional
    UserInfoDto login(Long id) throws CantFindByIdException;

    void withdrawSso(String username) throws CantFindByUsernameException;
}
