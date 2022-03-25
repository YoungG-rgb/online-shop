package com.amr.project.model.dto;

import lombok.*;

import java.util.Calendar;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserUpdateDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String phone;
    private String firstName;
    private String lastName;
    private AddressDto address;
    private String gender;
    private Calendar birthday;
}
