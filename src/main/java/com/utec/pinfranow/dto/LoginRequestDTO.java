package com.utec.pinfranow.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {

        private String username; // lo usás como email
        private String password;
    }


