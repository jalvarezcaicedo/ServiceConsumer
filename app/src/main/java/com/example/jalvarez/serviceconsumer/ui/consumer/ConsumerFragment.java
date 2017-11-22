package com.example.jalvarez.serviceconsumer.ui.consumer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jalvarez.serviceconsumer.R;
import com.example.jalvarez.serviceconsumer.data.model.DataResponse;
import com.example.jalvarez.serviceconsumer.ui.base.BaseFragment;

import java.util.List;

public class ConsumerFragment extends BaseFragment implements ConsumerView {

    public static final String FRAGMENT_TAG = "FRAGMENT_CONSUMER";
    ConsumerPresenter consumerPresenter;
    private TextView message;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer, container, false);
        initUI(view);
        consumerPresenter = new ConsumerPresenter();
        consumerPresenter.attachView(ConsumerFragment.this);
        consumerPresenter.callServiceData();
        return view;
    }

    private void initUI(View view) {
        message = view.findViewById(R.id.message);
    }

    @Override
    public void showData(List<DataResponse> dataResponse) {
        Log.i(getClass().getSimpleName(), dataResponse.get(0).toString());
        message.setText(dataResponse.get(0).toString());
    }
}
