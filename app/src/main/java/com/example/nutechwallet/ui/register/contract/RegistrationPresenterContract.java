package com.example.nutechwallet.ui.register.contract;

import com.example.nutechwallet.model.register.UserBody;
import com.example.nutechwallet.util.mvp.BasePresenter;

public interface RegistrationPresenterContract extends BasePresenter {
    void doRegister(UserBody userBody);
}
