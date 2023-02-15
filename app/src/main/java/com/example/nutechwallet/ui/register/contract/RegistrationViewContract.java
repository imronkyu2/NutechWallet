package com.example.nutechwallet.ui.register.contract;

import com.example.nutechwallet.model.BaseResponse;
import com.example.nutechwallet.model.register.UserResponse;
import com.example.nutechwallet.util.mvp.BaseView;

public interface RegistrationViewContract extends BaseView<RegistrationPresenterContract> {
    void doRegisterSuccessfully(BaseResponse<UserResponse> userRegistered);

    void doRegisterFailed(String toString);
}
