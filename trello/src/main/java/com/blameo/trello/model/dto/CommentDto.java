package com.blameo.trello.model.dto;

import com.blameo.trello.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto extends Comment {
    String fullName;
    Boolean edit = false;
}
