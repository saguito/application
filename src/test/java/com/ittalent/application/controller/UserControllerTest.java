package com.ittalent.application.controller;

import com.ittalent.application.model.User;
import com.ittalent.application.repository.UserRepository;
import com.ittalent.application.response.UserRegistrationResponse;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    private static final String DEFAULT_NAME = "Username Lastname";
    private static final String DEFAULT_EMAIL = "usernamer@contact.com";
    private static final String DEFAULT_PASSWORD = "Pass01";
    private static User userDummy;

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    UserController userController;

    @BeforeAll
    public static void setup() {
        userDummy = new User();
        userDummy.setName(DEFAULT_NAME);
        userDummy.setEmail(DEFAULT_EMAIL);
        userDummy.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    public void testRegisterUser01() throws Exception {
        when(userRepositoryMock.checkForExistingEmail(anyString())).thenReturn(true);

        ResponseEntity<UserRegistrationResponse> result = userController.registerUser(userDummy);

        Assert.assertNull(result.getBody().getUserData());

    }

    @Test
    public void testRegisterUser02() throws Exception {
        when(userRepositoryMock.checkForExistingEmail(anyString())).thenReturn(false);

        ResponseEntity<UserRegistrationResponse> result = userController.registerUser(userDummy);

        Assert.assertNotNull(result.getBody().getUserData());

    }

}
