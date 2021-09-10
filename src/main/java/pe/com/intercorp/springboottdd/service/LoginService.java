package pe.com.intercorp.springboottdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.com.intercorp.springboottdd.entity.User;
import pe.com.intercorp.springboottdd.entity.dto.LoginRequest;
import pe.com.intercorp.springboottdd.entity.dto.LoginResponse;
import pe.com.intercorp.springboottdd.repository.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;


    public LoginResponse authenticate(LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername());
        LoginResponse loginResponse;
        switch (user != null ? 1: 0){
            case 1:
                if (loginRequest.getPassword().equals(user.getPassword())){
                    loginResponse = LoginResponse.builder().codigo(HttpStatus.OK.value()).build();
                }else{
                    loginResponse = LoginResponse.builder().codigo(HttpStatus.BAD_REQUEST.value()).build();
                }

                break;
            default:
                loginResponse = LoginResponse.builder().codigo(HttpStatus.UNAUTHORIZED.value()).build();
                break;

        }
        return loginResponse;

    }
}
