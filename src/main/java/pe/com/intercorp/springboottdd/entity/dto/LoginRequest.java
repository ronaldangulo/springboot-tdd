package pe.com.intercorp.springboottdd.entity.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private long id;
    private String username;
    private  String password;
}
