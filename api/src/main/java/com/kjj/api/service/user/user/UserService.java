package com.kjj.api.service.user.user;

import com.kjj.api.auth.login.userDetails.UserDetailsInterface;
import com.kjj.api.dto.user.auth.UserJoinDto;
import com.kjj.api.dto.user.auth.WithdrawDto;
import com.kjj.api.dto.user.info.UserInfoDto;
import com.kjj.api.dto.user.user.*;
import com.kjj.api.entity.user.User;
import com.kjj.api.exception.exceptions.CantFindByIdException;
import com.kjj.api.exception.exceptions.CantFindByUsernameException;
import com.kjj.api.exception.exceptions.WrongParameter;
import com.kjj.api.exception.exceptions.WrongRequestDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    void join(UserJoinDto dto);

    void withdraw(String username, WithdrawDto dto) throws WrongRequestDetails, CantFindByIdException, CantFindByUsernameException;

    boolean check(String username);

    UserInfoDto getInfo(Long id) throws CantFindByIdException;

    UserMypageDto getMyPage(Long id) throws CantFindByIdException;

    Integer usePoint(Long id, UsePointDto dto) throws CantFindByIdException, WrongRequestDetails;

    UserPolicyDto setUserDatePolicy(UserDatePolicyDto dto, Long id) throws CantFindByIdException;

    UserPolicyDto setUserMenuPolicy(Long id, Long menuId) throws CantFindByIdException, WrongParameter;

    UserPolicyDto getUserPolicy(Long id) throws CantFindByIdException;

    UserInfoDto getInfoForUsername(String username) throws CantFindByUsernameException;

    User findByUsername(String username) throws CantFindByUsernameException;

    @Override
    UserDetailsInterface loadUserByUsername(String username) throws UsernameNotFoundException;
}
