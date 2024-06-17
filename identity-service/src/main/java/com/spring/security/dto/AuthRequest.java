package com.spring.security.dto;

import lombok.*;

@Data
//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String username;
    private String password;

}
