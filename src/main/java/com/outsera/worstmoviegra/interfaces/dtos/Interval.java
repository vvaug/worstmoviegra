package com.outsera.worstmoviegra.interfaces.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Interval {
    private String producer;
    private int interval;
    private int previous;
    private int next;
}
