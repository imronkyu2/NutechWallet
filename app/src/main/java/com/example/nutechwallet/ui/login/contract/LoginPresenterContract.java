package com.example.nutechwallet.ui.login.contract;

import com.example.nutechwallet.model.login.LoginBody;
import com.example.nutechwallet.util.mvp.BasePresenter;

public interface LoginPresenterContract extends BasePresenter {
    void doLogin(LoginBody body);
}
