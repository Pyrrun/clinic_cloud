package com.website.demo.API.login;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class LoginControllerUnitTest {


    @Test
    void login() {
        LoginService loginService = Mockito.mock(LoginService.class);
        LoginController loginController = new LoginController(loginService);
    }
}