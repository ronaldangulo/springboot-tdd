package pe.com.intercorp.springboottdd.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginException extends RuntimeException {

    private Integer codigo;

}
