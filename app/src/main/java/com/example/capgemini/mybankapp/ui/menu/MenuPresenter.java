package com.example.capgemini.mybankapp.ui.menu;

import android.content.Context;

import com.example.capgemini.mybankapp.ui.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;

public class MenuPresenter extends BasePresenter<MenuView> {

    private CompositeDisposable disposables;
    private Context context;

    MenuPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(MenuView mvpView) {
        super.attachView(mvpView);
        if (disposables == null)
            disposables = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        super.detachView();
        if (disposables != null)
            disposables.clear();
    }
}
