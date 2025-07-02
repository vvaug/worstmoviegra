package com.outsera.worstmoviegra.interfaces.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AwardIntervalResponse {

    private List<AwardInterval> min;
    private List<AwardInterval> max;

}
