package pe.com.intercorp.springboottdd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pe.com.intercorp.springboottdd.entity.dto.LoginRequest;
import pe.com.intercorp.springboottdd.entity.dto.LoginResponse;
import pe.com.intercorp.springboottdd.service.LoginService;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LoginService loginService;


    @Test
    public void debeRetornarOKSiLasCredencialesSonCorrectas() throws Exception {

        //******************************************
        //Arrange
        //******************************************
        //input
        LoginRequest request = LoginRequest.builder().username("miusername").password("mypassword").build();
        //output
        LoginResponse loginResponse = LoginResponse.builder().codigo(HttpStatus.OK.value()).mensaje("OK").build();


        //******************************************
        //act
        //******************************************
        given(loginService.authenticate(request)).willReturn(loginResponse);
        //when(loginService.authenticate(request)).thenReturn(loginResponse);

        //******************************************
        //assert
        //******************************************
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("OK")));

    }


    @Test
    public void debeRetornar400SiLasCredencialesSonIncorrectas() throws Exception {

        //******************************************
        //Arrange
        //******************************************
        //input
        LoginRequest request = LoginRequest.builder().username("badusername").password("").build();
        //output
        LoginResponse loginResponse = LoginResponse.builder().codigo(HttpStatus.BAD_REQUEST.value()).mensaje("Credenciales incorrectas").build();


        //******************************************
        //act
        //******************************************
        given(loginService.authenticate(request)).willReturn(loginResponse);
        //when(loginService.authenticate(request)).thenReturn(loginResponse);

        //******************************************
        //assert
        //******************************************
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje", is("Credenciales incorrectas")));

    }

    @Test
    public void debeRetornar401SiElUsuarioNoExiste() throws Exception {

        //******************************************
        //Arrange
        //******************************************
        //input
        LoginRequest request = LoginRequest.builder().username("noexisto").password("mypassword").build();
        //output
        LoginResponse loginResponse = LoginResponse.builder().codigo(HttpStatus.UNAUTHORIZED.value()).mensaje("Usuario no existe").build();


        //******************************************
        //act
        //******************************************
        given(loginService.authenticate(request)).willReturn(loginResponse);
        //when(loginService.authenticate(request)).thenReturn(loginResponse);


        //******************************************
        //assert
        //******************************************
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensaje", is("Usuario no existe")));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
