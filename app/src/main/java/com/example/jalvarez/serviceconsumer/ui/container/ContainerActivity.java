package com.example.jalvarez.serviceconsumer.ui.container;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;

import com.example.jalvarez.serviceconsumer.R;
import com.example.jalvarez.serviceconsumer.ui.base.BaseActivity;
import com.example.jalvarez.serviceconsumer.ui.consumer.ConsumerFragment;

public class ContainerActivity extends BaseActivity implements ContainerView {

    ContainerPresenter containerPresenter;
    ConstraintLayout containerActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        containerPresenter = new ContainerPresenter();
        containerPresenter.attachView(this);
        addFragment(R.id.fragment_container, new ConsumerFragment(), ConsumerFragment.FRAGMENT_TAG);
        initUI();
    }

    private void initUI() {
        containerActivity = findViewById(R.id.container_activity);
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(containerActivity, message, Snackbar.LENGTH_SHORT).show();
    }
}
