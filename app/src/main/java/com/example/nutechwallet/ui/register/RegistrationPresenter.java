package com.example.nutechwallet.ui.register;

import com.example.nutechwallet.core.repo.Repo;
import com.example.nutechwallet.model.NutechBaseResponse;
import com.example.nutechwallet.model.register.UserBody;
import com.example.nutechwallet.model.register.UserResponse;
import com.example.nutechwallet.ui.register.contract.RegistrationPresenterContract;
import com.example.nutechwallet.ui.register.contract.RegistrationViewContract;
import com.example.nutechwallet.util.mvp.PostCallback;

import java.io.IOException;

public class RegistrationPresenter implements RegistrationPresenterContract {
    private final Repo repo;
    private final RegistrationViewContract viewContract;

    public RegistrationPresenter(Repo repo, RegistrationViewContract viewContract) {
        this.repo = repo;
        this.viewContract = viewContract;
        viewContract.setPresenter(this);
    }

    @Override
    public void doRegister(UserBody userBody) {
        viewContract.showContentLoading();
        repo.doRegistration(userBody, new PostCallback() {
            @Override
            public void onEntityPosted(Object object) {
                viewContract.doRegisterSuccessfully((NutechBaseResponse<UserResponse>) object);
                viewContract.hideContentLoading();
            }

            @Override
            public void onErrorRequest(Throwable throwable) {
                String string = throwable.toString();
                if(throwable instanceof IOException) {
                    viewContract.showConnectionFailed();
                }else {
                    if (string.equals("500")){
                        viewContract.showConnectionError();
                    }else {
                        viewContract.doRegisterFailed(throwable.toString());
                    }
                }
                viewContract.hideContentLoading();
            }
        });

    }

}
