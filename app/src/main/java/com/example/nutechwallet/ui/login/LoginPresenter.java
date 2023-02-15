package com.example.nutechwallet.ui.login;

import com.example.nutechwallet.core.repo.Repo;
import com.example.nutechwallet.model.login.LoginBody;
import com.example.nutechwallet.model.login.LoginResponse;
import com.example.nutechwallet.ui.login.contract.LoginPresenterContract;
import com.example.nutechwallet.ui.login.contract.LoginViewContract;
import com.example.nutechwallet.util.mvp.PostCallback;

import java.io.IOException;

public class LoginPresenter implements LoginPresenterContract {
    private final Repo repo;
    private final LoginViewContract viewContract;

    public LoginPresenter(Repo repo, LoginViewContract loginViewContract){
        this.repo = repo;
        this.viewContract = loginViewContract;
        viewContract.setPresenter(this);
    }
    @Override
    public void doLogin(LoginBody body) {
        viewContract.showContentLoading();
        repo.doLogin(body, new PostCallback() {
            @Override
            public void onEntityPosted(Object ob) {
                viewContract.doRegisterSuccessfully((LoginResponse) ob);
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
