package pe.com.intercorp.springboottdd.entity;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

@Getter
public enum LoginEnum {

    OK(HttpStatus.OK.value(), "Login exitoso"),
    INCORRECT_CREDENTIALS(HttpStatus.BAD_REQUEST.value(), "Credenciales incorrectas"),
    USER_NOT_EXIST(HttpStatus.UNAUTHORIZED.value(), "Usuario no existe"),
    ERROR_INTERNAL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error genérico de aplicación");


    private Integer codigo;

    private String mensaje;



    private LoginEnum(Integer codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public static LoginEnum findByCode(Integer codigo) {
        return Stream.of(LoginEnum.values())
                .filter(login -> codigo.equals(login.getCodigo()))
                .map(login -> login != null ? login : null)
                .findFirst()
                .orElse(null);
    }
}
