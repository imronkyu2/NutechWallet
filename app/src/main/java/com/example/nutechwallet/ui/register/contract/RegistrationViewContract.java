package com.example.nutechwallet.ui.register.contract;

import com.example.nutechwallet.model.NutechResponse;
import com.example.nutechwallet.model.register.UserRegistered;
import com.example.nutechwallet.util.mvp.BaseView;

public interface RegistrationViewContract extends BaseView<RegistrationPresenterContract> {
    void doRegisterSuccessfully(NutechResponse<UserRegistered> userRegistered);

    void doRegisterFailed(String toString);
}
