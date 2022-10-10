package com.project_4.cookpad_api.entity.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    @NotEmpty(message = "username missing")
    private String username;
    @NotEmpty(message = "password missing")
    @Min(value = 8, message = "password not strong enough")
    private String password;
    @NotEmpty(message = "password repeat missing")
    private String rePass;
    @NotEmpty(message = "full name missing")
    private String fullName;
}
