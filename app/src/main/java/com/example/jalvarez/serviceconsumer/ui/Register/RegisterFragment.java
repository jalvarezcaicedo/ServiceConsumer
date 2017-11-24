package com.example.jalvarez.serviceconsumer.ui.Register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jalvarez.serviceconsumer.R;
import com.example.jalvarez.serviceconsumer.data.model.signup.SignUpRequest;
import com.example.jalvarez.serviceconsumer.ui.base.BaseFragment;
import com.example.jalvarez.serviceconsumer.ui.login.LoginFragment;

public class RegisterFragment extends BaseFragment implements RegisterView, View.OnClickListener {

    public static final String FRAGMENT_TAG = "FRAGMENT_REGISTER";
    private RegisterPresenter registerPresenter;
    private EditText name;
    private EditText surname;
    private EditText customerId;
    private EditText email;
    private EditText mobile;
    private EditText phone;
    private EditText password;
    private Button btnSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initUI(view);
        registerPresenter = new RegisterPresenter();
        registerPresenter.attachView(RegisterFragment.this);

        return view;
    }

    private void initUI(View view) {
        name = view.findViewById(R.id.field_name);
        surname = view.findViewById(R.id.field_surname);
        customerId = view.findViewById(R.id.field_customer_id);
        email = view.findViewById(R.id.field_email);
        mobile = view.findViewById(R.id.field_mobile);
        phone = view.findViewById(R.id.field_phone);
        password = view.findViewById(R.id.field_password);
        btnSignUp = view.findViewById(R.id.btn_sign_up_now);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up_now:
                SignUpRequest signUpRequest = new SignUpRequest();
                signUpRequest.setName(name.getText().toString());
                signUpRequest.setSurname(surname.getText().toString());
                signUpRequest.setCustomerId(customerId.getText().toString());
                signUpRequest.setEmail(email.getText().toString());
                signUpRequest.setMobile(mobile.getText().toString());
                signUpRequest.setPhone(phone.getText().toString());
                signUpRequest.setPassword(password.getText().toString());

                registerPresenter.callServiceSignUp(signUpRequest);
                break;
        }
    }

    @Override
    public void sendToLogin() {
        Fragment fragment = new RegisterFragment();

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, LoginFragment.FRAGMENT_TAG)
                .commit();
    }
}
