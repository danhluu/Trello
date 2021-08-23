package com.blameo.trello.model.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private String email;
    private String password;
    private String fullName;
    private String rePassword;
}
