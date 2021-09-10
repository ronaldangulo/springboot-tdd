package pe.com.intercorp.springboottdd.service;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;


import org.springframework.http.ResponseEntity;
import pe.com.intercorp.springboottdd.entity.User;
import pe.com.intercorp.springboottdd.entity.dto.LoginRequest;
import pe.com.intercorp.springboottdd.entity.dto.LoginResponse;
import pe.com.intercorp.springboottdd.repository.UserRepository;

import static org.junit.Assert.assertEquals;
//Hamcrest
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testSePuedeAutenticarExitosamente(){
        //******************************************
        //Arrange
        //******************************************
        //input
        User user = User.builder().id(1L).username("myusername").password("mypassword").build();
        LoginRequest loginRequest = LoginRequest.builder().username("myusername").password("mypassword").build();




        //******************************************
        //act
        //******************************************
        //given(userRepository.findByUsername(loginRequest.getUsername())).willReturn(user);
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        LoginResponse loginResponse = loginService.authenticate(loginRequest);


        //******************************************
        //assert
        //******************************************

        assertThat("usuario valido", loginRequest.getUsername(), equalTo(user.getUsername()));
        assertThat("password valido", loginRequest.getPassword(), equalTo(user.getPassword()));
        assertThat("login ok", loginResponse.getCodigo(), equalTo(HttpStatus.OK.value()));

    }


    @Test
    public void testNosePuedeAutenticarUsernameNoExiste(){
        //******************************************
        //Arrange
        //******************************************
        //input
        LoginRequest loginRequest = LoginRequest.builder().username("myusername").password("mypassword").build();


        //******************************************
        //act
        //******************************************
        //given(userRepository.findByUsername(loginRequest.getUsername())).willReturn(user);
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(null);
        LoginResponse loginResponse = loginService.authenticate(loginRequest);


        //******************************************
        //assert
        //******************************************

        assertThat("usuario no existe", loginResponse.getCodigo(), equalTo(HttpStatus.UNAUTHORIZED.value()));


    }

    @Test
    public void testNosePuedeAutenticarLasCrendecialesSonIncorrectas(){
        //******************************************
        //Arrange
        //******************************************
        //input
        User user = User.builder().id(1L).username("myusername").password("mypassword").build();
        LoginRequest loginRequest = LoginRequest.builder().username("myusername").password("mypass123").build();


        //******************************************
        //act
        //******************************************
        //given(userRepository.findByUsername(loginRequest.getUsername())).willReturn(user);
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        LoginResponse loginResponse = loginService.authenticate(loginRequest);


        //******************************************
        //assert
        //******************************************

        assertThat("credenciales incorrectas", loginResponse.getCodigo(), equalTo(HttpStatus.BAD_REQUEST.value()));


    }


}
