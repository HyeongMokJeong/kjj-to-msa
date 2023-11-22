package com.kjj.api.service.leftover;

import com.kjj.api.dto.leftover.LeftoverDto;

import java.util.List;

public interface LeftoverService {
    List<LeftoverDto> getLastWeeksLeftovers(int type);
}