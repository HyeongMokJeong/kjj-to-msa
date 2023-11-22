package com.kjj.api.service.leftover;

import com.kjj.api.dto.leftover.LeftoverDto;
import com.kjj.api.entity.leftover.Leftover;
import com.kjj.api.entity.leftover.LeftoverPre;
import com.kjj.api.repository.leftover.LeftoverPreRepository;
import com.kjj.api.repository.leftover.LeftoverRepository;
import com.kjj.api.service.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeftoverServiceImplV1 implements LeftoverService{

    private final LeftoverRepository leftoverRepository;
    private final LeftoverPreRepository leftoverPreRepository;
    private final DateUtil dateUtil;

    private static final int DATA_SIZE = 5;

    @Override
    @Transactional(readOnly = true)
    public List<LeftoverDto> getLastWeeksLeftovers(int type) {
        List<LeftoverDto> result = new ArrayList<>();
        LocalDate date = dateUtil.getLastWeeksMonday(type);

        for (int i = 0; i < DATA_SIZE; i ++) {
            LocalDate targetDate = date.plusDays(i);

            LeftoverPre target = leftoverPreRepository.findByDate(targetDate).orElse(null);
            if (target == null) {
                result.add(LeftoverDto.of(targetDate, 0.0));
                continue;
            }

            Leftover leftover = leftoverRepository.findByLeftoverPreId(target.getId()).orElse(null);
            if (leftover == null) result.add(LeftoverDto.of(targetDate, 0.0));
            else result.add(LeftoverDto.from(leftover));
        }
        return result;
    }
}
