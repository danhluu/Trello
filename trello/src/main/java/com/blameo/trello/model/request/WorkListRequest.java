package com.blameo.trello.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkListRequest {
    private Long boardId;
    private String title;
    private Long workListId;
    private Long addedIndex;
    private Long removeIndex;
}
