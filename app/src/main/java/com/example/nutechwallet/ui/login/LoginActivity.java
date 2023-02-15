package com.example.nutechwallet.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nutechwallet.R;
import com.example.nutechwallet.core.singleton.SingletonLikeApp;
import com.example.nutechwallet.databinding.ActivityLoginBinding;
import com.example.nutechwallet.model.login.LoginBody;
import com.example.nutechwallet.model.login.LoginResponse;
import com.example.nutechwallet.ui.home.HomeActivity;
import com.example.nutechwallet.ui.login.contract.LoginPresenterContract;
import com.example.nutechwallet.ui.login.contract.LoginViewContract;
import com.example.nutechwallet.ui.register.RegistrationActivity;
import com.example.nutechwallet.ui.register.RegistrationPresenter;
import com.example.nutechwallet.util.Injection;

public class LoginActivity extends AppCompatActivity implements LoginViewContract {
    private ActivityLoginBinding binding;
    private LoginPresenterContract presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new LoginPresenter(
                Injection.provideRepository(getApplicationContext()),
                LoginActivity.this);
        ActionButton();
    }

    private void ActionButton() {
        binding.registerTV.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        binding.btnLogin.setOnClickListener(v->{
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(LoginActivity.this, "Pastikan anda memasukan Email dan Katasandi", Toast.LENGTH_LONG).show();
            }else {
                LoginBody body = new LoginBody();
                body.setEmail(email);
                body.setPassword(password);
                presenter.doLogin(body);
            }
        });


    }

    @Override
    public void setPresenter(LoginPresenterContract mPresenter) {
        this.presenter = mPresenter;
    }

    @Override
    public void showConnectionError() {
        Toast.makeText(this, "Server mengalami gangguan", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showContentLoading() {
        binding.animateView.playAnimation();
        binding.animateView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContentLoading() {
        binding.animateView.setVisibility(View.GONE);
        binding.animateView.pauseAnimation();
    }

    @Override
    public void showConnectionFailed() {
        Toast.makeText(this, "Terjadi kesalahan koneksi", Toast.LENGTH_LONG).show();
    }

    @Override
    public void doRegisterSuccessfully(LoginResponse ob) {
        SingletonLikeApp.getInstance().getSharedPreferences(getApplicationContext()).setUser(ob);
        SingletonLikeApp.getInstance().getSharedPreferences(getApplicationContext()).setToken(ob.getToken());
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void doRegisterFailed(String toString) {
        Toast.makeText(this, toString, Toast.LENGTH_LONG).show();
    }
}