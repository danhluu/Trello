package com.blameo.trello.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeadlineDto {
    private Long deadlineId;
    private Long taskId;
    private String startDate;
    private String endDate;
    private Boolean complete;
}
