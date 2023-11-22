package com.kjj.auth.security.userDetails;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsInterface extends UserDetails {
    String getMemberRoles();

    Long getMemberId();

}
