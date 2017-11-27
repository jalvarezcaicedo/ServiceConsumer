package com.example.capgemini.mybankapp.ui.login;

import android.test.AndroidTestCase;

import com.example.capgemini.mybankapp.data.model.login.Login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest extends AndroidTestCase {

    @Mock
    private LoginView loginView;
    private LoginPresenter loginPresenter;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        loginPresenter = new LoginPresenter(getContext());
    }

    @Test
    public void callServiceLogin() throws Exception {
        Login login = new Login();
        login.setCustomerId("1");
        loginPresenter.callServiceLogin(login);
        verify(loginView).sendToMenu();
    }

    /*@Test
    public void callCustomerInfo() throws Exception {
    }

    @Test
    public void callCustomerProducts() throws Exception {
    }*/


}