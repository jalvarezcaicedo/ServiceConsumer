package com.example.capgemini.mybankapp.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capgemini.mybankapp.R;
import com.example.capgemini.mybankapp.data.model.login.Login;
import com.example.capgemini.mybankapp.ui.base.BaseFragment;
import com.example.capgemini.mybankapp.ui.menu.MenuFragment;
import com.example.capgemini.mybankapp.ui.register.RegisterFragment;
import com.example.capgemini.mybankapp.util.Constants;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class LoginFragment extends BaseFragment implements LoginView, View.OnClickListener {

    public static final String FRAGMENT_TAG = "FRAGMENT_LOGIN";
    private LoginPresenter loginPresenter;
    private EditText customerId;
    private EditText password;
    private ConstraintLayout containerLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initUI(view);
        loginPresenter = new LoginPresenter(getActivity());
        loginPresenter.attachView(LoginFragment.this);

        return view;
    }

    private void initUI(View view) {
        clearUserSharedPreference();
        containerLogin = view.findViewById(R.id.container_login);
        customerId = view.findViewById(R.id.field_customer_id);
        password = view.findViewById(R.id.field_password);
        TextView linkSignUp = view.findViewById(R.id.link_sign_up);
        SpannableString content = new SpannableString(getString(R.string.link_sign_up));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        linkSignUp.setText(content);
        linkSignUp.setOnClickListener(this);

        //TODO remove dummy data on finish
        setDummyData();

        Button btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);
    }

    private void setDummyData() {
        customerId.setText("1234574");
        password.setText("1234574");
    }

    private void clearUserSharedPreference() {
        SharedPreferences.Editor sharedPreferencesEditor = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();

        sharedPreferencesEditor.putString(Constants.AUTHORIZATION_HEADER, "");
        sharedPreferencesEditor.putString(Constants.CUSTOMER_ID, "");
        sharedPreferencesEditor.apply();
    }

    @Override
    public void sendToMenu() {
        Fragment fragment = new MenuFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, MenuFragment.FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(MenuFragment.FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void showSnackBar(String msg) {
        Snackbar.make(containerLogin, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void getCustomerInfo() {
        try {
            loginPresenter.callCustomerInfo();
        } catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private void sendToRegister() {
        Fragment fragment = new RegisterFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, RegisterFragment.FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(RegisterFragment.FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Login login = new Login();
                login.setCustomerId(customerId.getText().toString());
                login.setPassword(password.getText().toString());
                try {
                    loginPresenter.callServiceLogin(login);
                } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | KeyManagementException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.link_sign_up:
                sendToRegister();
                break;
        }
    }

}
