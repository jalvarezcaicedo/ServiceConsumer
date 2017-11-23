package com.example.jalvarez.serviceconsumer.ui.consumer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jalvarez.serviceconsumer.R;
import com.example.jalvarez.serviceconsumer.data.model.DataResponse;
import com.example.jalvarez.serviceconsumer.data.model.Login;
import com.example.jalvarez.serviceconsumer.ui.base.BaseFragment;
import com.example.jalvarez.serviceconsumer.util.Util;

import java.util.List;

public class ConsumerFragment extends BaseFragment implements ConsumerView {

    public static final String FRAGMENT_TAG = "FRAGMENT_CONSUMER";
    ConsumerPresenter consumerPresenter;
    private TextView message;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer, container, false);
        setHasOptionsMenu(true);
        initUI(view);
        consumerPresenter = new ConsumerPresenter();
        consumerPresenter.attachView(ConsumerFragment.this);

        return view;
    }

    private void initUI(View view) {
        message = view.findViewById(R.id.message);
    }

    @Override
    public void showData(List<DataResponse> dataResponse) {
        Log.i(getClass().getSimpleName(), "Size" + dataResponse.size());
        message.setText(dataResponse.get(0).toString());
    }

    @Override
    public void showDataLogin(Login login) {
        Log.i(getClass().getSimpleName(), "Login -> " + login.toString());
        message.setText(login.toString());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_consumer, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_get:
                consumerPresenter.callServiceData();
                break;
            case R.id.menu_post:
                Login login = new Login();
                login.setUsuario(Long.parseLong("10295721"));
                login.setCelular(Long.parseLong("3167405486"));
                login.setFecha(Util.getDate(Util.NDATEFORMAT));
                consumerPresenter.callServiceDataPost(login);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
