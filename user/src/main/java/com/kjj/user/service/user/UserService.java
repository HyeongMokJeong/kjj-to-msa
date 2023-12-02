package com.kjj.user.service.user;

import com.kjj.user.dto.user.*;
import com.kjj.user.entity.User;
import com.kjj.user.entity.UserMyPage;
import com.kjj.user.entity.UserPolicy;
import com.kjj.user.exception.CantFindByIdException;
import com.kjj.user.exception.CantFindByUsernameException;
import com.kjj.user.exception.WrongRequestBodyException;
import com.kjj.user.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Transactional(readOnly = true)
    public UserInfoDto getInfo(Long id) throws CantFindByIdException {
        User user = userRepository.findById(id).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 유저를 찾을 수 없습니다.
                id :""" + id));
        return UserInfoDto.from(user);
    }

    
    @Transactional(readOnly = true)
    public MyPageDto getMyPage(Long id) throws CantFindByIdException {
        UserMyPage mypage= userRepository.findByIdWithFetchMyPage(id).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 유저 데이터를 찾을 수 없습니다.
                id : """ + id)).getUserMypage();
        return MyPageDto.from(mypage);
    }

    
    @Transactional
    public Integer usePoint(Long id, UsePointDto dto) throws WrongRequestBodyException, CantFindByIdException {
        UserMyPage mypage= userRepository.findByIdWithFetchMyPage(id).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 유저 데이터를 찾을 수 없습니다.
                id : """ + id)).getUserMypage();
        int data = -1 * dto.getValue();
        if (mypage.getPoint() + data < 0) throw new WrongRequestBodyException("""
            포인트가 부족합니다.
            포인트 사용 시 잔여 포인트가 음수가 됩니다.
            point : """ + mypage.getPoint());
        mypage.updatePoint(data);

        return mypage.getPoint();
    }
    
    @Transactional
    public PolicyDto setUserDatePolicy(DatePolicyDto dto, Long id) throws CantFindByIdException {
        User user = userRepository.findByIdWithFetchPolicy(id).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 유저 데이터를 찾을 수 없습니다.
                id : """ + id));
        UserPolicy userPolicy = user.getUserPolicy();
        userPolicy.setPolicy(dto);

        return PolicyDto.from(userPolicy);
    }


    @Transactional
    public PolicyDto setUserMenuPolicy(Long id, Long menuId) throws CantFindByIdException {
//        if (!menuClient.existsById(menuId)) throw new WrongRequestBodyException("""
//                menuId를 가진 메뉴 데이터가 존재하지 않습니다.
//                menuId : """ + menuId);
//
//        UserPolicy policy = userRepository.findByIdWithFetchPolicy(id).orElseThrow(() -> new CantFindByIdException("""
//                해당 id를 가진 유저 데이터를 찾을 수 없습니다.
//                id : """ + id)).getUserPolicy();
//        policy.setDefaultMenu(menuId);
//
//        return PolicyDto.from(policy);
        return null;
    }

    
    @Transactional(readOnly = true)
    public PolicyDto getUserPolicy(Long id) throws CantFindByIdException {
        UserPolicy policy = userRepository.findByIdWithFetchPolicy(id).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 유저 데이터를 찾을 수 없습니다.
                id : """ + id)).getUserPolicy();
        return PolicyDto.from(policy);
    }

    @Transactional
    public Boolean withdraw(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return false;
        userRepository.delete(user);
        return true;
    }

    public Boolean checkPassword(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CantFindByUsernameException("""
                해당 username을 가진 유저 데이터를 찾을 수 없습니다.
                username = """ + username));
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }
}