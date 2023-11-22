package com.kjj.api.service.user.manager;

import com.kjj.api.auth.login.userDetails.UserDetailsInterface;
import com.kjj.api.dto.user.info.ManagerInfoDto;
import com.kjj.api.exception.exceptions.CantFindByUsernameException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ManagerService extends UserDetailsService {
    ManagerInfoDto getInfoForUsername(String username) throws CantFindByUsernameException;

    ManagerInfoDto getInfo(String username) throws CantFindByUsernameException;

    @Override
    UserDetailsInterface loadUserByUsername(String username) throws UsernameNotFoundException;
}
