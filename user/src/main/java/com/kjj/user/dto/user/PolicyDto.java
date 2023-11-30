package com.kjj.user.dto.user;

import com.kjj.user.entity.UserPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PolicyDto {
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;

    private Long defaultMenu;

    public static PolicyDto from(UserPolicy policy) {
        return new PolicyDto(
                policy.isMonday(),
                policy.isTuesday(),
                policy.isWednesday(),
                policy.isThursday(),
                policy.isFriday(),
                policy.getDefaultMenu()
        );
    }
}
