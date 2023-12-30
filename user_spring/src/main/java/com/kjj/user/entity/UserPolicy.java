package com.kjj.user.entity;

import com.kjj.user.dto.user.DatePolicyDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;

    private Long defaultMenu;

    public static UserPolicy createNewUserPolicy() {
        return new UserPolicy(
                null,
                false,
                false,
                false,
                false,
                false,
                null
        );
    }

    public void setPolicy(DatePolicyDto dto) {
        monday = dto.isMonday();
        tuesday = dto.isTuesday();
        wednesday = dto.isWednesday();
        thursday = dto.isThursday();
        friday = dto.isFriday();
    }

    public void setDefaultMenu(Long id) {
        defaultMenu = id;
    }
    public void clearDefaultMenu() {
        defaultMenu = null;
    }
}
