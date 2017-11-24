package com.example.jalvarez.serviceconsumer.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jalvarez.serviceconsumer.R;
import com.example.jalvarez.serviceconsumer.data.model.login.Login;
import com.example.jalvarez.serviceconsumer.ui.Register.RegisterFragment;
import com.example.jalvarez.serviceconsumer.ui.base.BaseFragment;

public class LoginFragment extends BaseFragment implements LoginView, View.OnClickListener {

    public static final String FRAGMENT_TAG = "FRAGMENT_LOGIN";
    private LoginPresenter loginPresenter;
    private EditText customerId;
    private EditText password;
    private TextView linkSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initUI(view);
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(LoginFragment.this);

        return view;
    }

    private void initUI(View view) {
        customerId = view.findViewById(R.id.field_customer_id);
        password = view.findViewById(R.id.field_password);
        linkSignUp = view.findViewById(R.id.link_sign_up);
        SpannableString content = new SpannableString(getString(R.string.link_sign_up));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        linkSignUp.setText(content);
        linkSignUp.setOnClickListener(this);

        Button btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void sendToMenu() {
        Fragment fragment = new LoginFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        fragmentManager.beginTransaction()
                //TODO: replace tag for valid menu tag
                .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                .commit();
    }

    private void sendToRegister() {
        Fragment fragment = new RegisterFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, RegisterFragment.FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Login login = new Login();
                login.setCustomerId(customerId.getText().toString());
                login.setPassword(password.getText().toString());
                loginPresenter.callServiceLogin(login);
                break;
            case R.id.link_sign_up:
                sendToRegister();
                break;
        }
    }

}
