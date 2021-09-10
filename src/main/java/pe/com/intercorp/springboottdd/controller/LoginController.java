package pe.com.intercorp.springboottdd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pe.com.intercorp.springboottdd.entity.dto.LoginRequest;
import pe.com.intercorp.springboottdd.entity.dto.LoginResponse;
import pe.com.intercorp.springboottdd.service.LoginService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(HttpServletRequest request, @RequestBody LoginRequest loginRequest) throws Exception{

        try {
            LoginResponse loginResponse = loginService.authenticate(loginRequest);
            return ResponseEntity.status(HttpStatus.valueOf(loginResponse.getCodigo())).body(loginResponse);
        }catch(Exception e) {
            log.error("Error de autenticacion: {}", e.getMessage());
            log.error("El error es", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
