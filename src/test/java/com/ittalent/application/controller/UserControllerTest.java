package com.ittalent.application.controller;

import com.ittalent.application.model.Phone;
import com.ittalent.application.model.User;
import com.ittalent.application.repository.UserRepository;
import com.ittalent.application.response.UserRegistrationResponse;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    private static final String DEFAULT_NAME = "Username Lastname";
    private static final String DEFAULT_EMAIL = "usernamer@contact.com";
    private static final String DEFAULT_PASSWORD = "Pass01";
    private static final String DEFAULT_NUMBER = "1234567";
    private static final String DEFAULT_CITYCODE = "9";
    private static final String DEFAULT_COUNTRYCODE = "56";

    private User userDummy;
    private Phone phoneDummy;

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    UserController userController;

    @BeforeEach
    public void init() {
        phoneDummy = new Phone();
        phoneDummy.setNumber(DEFAULT_NUMBER);
        phoneDummy.setCitycode(DEFAULT_CITYCODE);
        phoneDummy.setCountrycode(DEFAULT_COUNTRYCODE);

        List<Phone> phones = new ArrayList<>();
        phones.add(phoneDummy);

        userDummy = new User();
        userDummy.setName(DEFAULT_NAME);
        userDummy.setEmail(DEFAULT_EMAIL);
        userDummy.setPassword(DEFAULT_PASSWORD);
        userDummy.setPhones(phones);
    }

    /*
     *   Field Name is Empty
     */
    @Test
    public void testRegisterUser01() {
        userDummy.setName("");

        ResponseEntity<UserRegistrationResponse> result = userController.registerUser(userDummy);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(result.getBody().getErrors().get(0).getMensaje(), "Los campos Nombre, Email o Password no pueden ser nulos.");

    }

    /*
     *   Field Email is Empty
     */
    @Test
    public void testRegisterUser02() {
        userDummy.setEmail("");

        ResponseEntity<UserRegistrationResponse> result = userController.registerUser(userDummy);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(result.getBody().getErrors().get(0).getMensaje(), "Los campos Nombre, Email o Password no pueden ser nulos.");

    }

    /*
     *   Field Password is Empty
     */
    @Test
    public void testRegisterUser03() {
        userDummy.setPassword("");

        ResponseEntity<UserRegistrationResponse> result = userController.registerUser(userDummy);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(result.getBody().getErrors().get(0).getMensaje(), "Los campos Nombre, Email o Password no pueden ser nulos.");

    }

    /*
     *   Email already Exists
     */
    @Test
    public void testRegisterUser04() {
        when(userRepositoryMock.checkForExistingEmail(anyString())).thenReturn(true);

        ResponseEntity<UserRegistrationResponse> result = userController.registerUser(userDummy);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.CONFLICT);
        Assert.assertEquals(result.getBody().getErrors().get(0).getMensaje(), "El correo ya existe.");

    }

    /*
     *   Invalid Name pattern
     */
    @Test
    public void testRegisterUser05() {
        userDummy.setName("J?a/( S*al*a");
        when(userRepositoryMock.checkForExistingEmail(anyString())).thenReturn(false);

        ResponseEntity<UserRegistrationResponse> result = userController.registerUser(userDummy);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(result.getBody().getErrors().get(0).getMensaje(), "El nombre del usuario no puede contener caracteres como '?, *, (, )' o numeros.");

    }

    /*
     *   Invalid Email pattern
     */
    @Test
    public void testRegisterUser06() {
        userDummy.setEmail("aaaa@a");
        when(userRepositoryMock.checkForExistingEmail(anyString())).thenReturn(false);

        ResponseEntity<UserRegistrationResponse> result = userController.registerUser(userDummy);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(result.getBody().getErrors().get(0).getMensaje(), "El correo no es correcto. Debe contener tener el formato 'aaaaaaa@dominio.cl'.");

    }

    /*
     *   Invalid Password pattern
     */
    @Test
    public void testRegisterUser07() {
        userDummy.setPassword("1Q2Qaaa");
        when(userRepositoryMock.checkForExistingEmail(anyString())).thenReturn(false);

        ResponseEntity<UserRegistrationResponse> result = userController.registerUser(userDummy);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(result.getBody().getErrors().get(0).getMensaje(), "El formato del password no es correcto. Debe contener Una (1) Mayuscula, lestras minusculas y Dos (2) numeros.");

    }

    /*
     *   Correct Request without any Exception
     */
    @Test
    public void testRegisterUser08() {
        when(userRepositoryMock.checkForExistingEmail(anyString())).thenReturn(false);
        when(userRepositoryMock.save(any(User.class))).thenReturn(userDummy);

        ResponseEntity<UserRegistrationResponse> result = userController.registerUser(userDummy);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        Assert.assertNotNull(result.getBody().getUserData());

    }

}
