package com.example.nutechwallet.ui.login.contract;

import com.example.nutechwallet.model.login.LoginResponse;
import com.example.nutechwallet.util.mvp.BaseView;

public interface LoginViewContract extends BaseView<LoginPresenterContract> {
    void doRegisterSuccessfully(LoginResponse ob);

    void doRegisterFailed(String toString);
}
