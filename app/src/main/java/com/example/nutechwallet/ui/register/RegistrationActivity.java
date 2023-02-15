package com.example.nutechwallet.ui.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nutechwallet.R;
import com.example.nutechwallet.databinding.ActivityRegistrationBinding;
import com.example.nutechwallet.databinding.IncludeInputPasswordRegisterBinding;
import com.example.nutechwallet.databinding.IncludeInputProfileRegisterBinding;
import com.example.nutechwallet.model.BaseResponse;
import com.example.nutechwallet.model.register.UserBody;
import com.example.nutechwallet.model.register.UserResponse;
import com.example.nutechwallet.ui.login.LoginActivity;
import com.example.nutechwallet.ui.register.contract.RegistrationPresenterContract;
import com.example.nutechwallet.ui.register.contract.RegistrationViewContract;
import com.example.nutechwallet.util.Injection;

public class RegistrationActivity extends AppCompatActivity implements RegistrationViewContract {
    private ActivityRegistrationBinding binding;
    private IncludeInputPasswordRegisterBinding passwordBinding;
    private IncludeInputProfileRegisterBinding profileBinding;
    private boolean valid;
    private RegistrationPresenterContract mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new RegistrationPresenter(
                Injection.provideRepository(getApplicationContext()),
                RegistrationActivity.this);


        profileBinding = binding.includeInputProfile;
        passwordBinding = binding.includeInputPassword;

        checkValidation();
        includeInputProfile();
        includeInputPassword();
        includeButtonRegister();
    }

    private void includeInputProfile() {
        profileBinding.etFirtsName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    profileBinding.warningFirstNameTv.setVisibility(View.GONE);
                } else {
                    profileBinding.warningFirstNameTv.setVisibility(View.VISIBLE);
                }
            }
        });

        profileBinding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    profileBinding.warningEmailTV.setVisibility(View.GONE);
                } else {
                    profileBinding.warningEmailTV.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void includeInputPassword() {
        passwordBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    if (!passwordBinding.etConvirmPassword.getText().toString().isEmpty()) {
                        if (editable.toString().equals(passwordBinding.etConvirmPassword.getText().toString())) {
                            passwordBinding.warningConfirmPasswordTV.setText(getText(R.string.passwords_dont_match));
                            passwordBinding.warningConfirmPasswordTV.setVisibility(View.VISIBLE);
                        }
                    }
                    passwordBinding.warningPasswordTV.setVisibility(View.GONE);
                } else {
                    passwordBinding.warningPasswordTV.setVisibility(View.VISIBLE);
                }
            }
        });

        passwordBinding.etConvirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()
                        && editable.toString().equals(passwordBinding.etPassword.getText().toString())) {
                    passwordBinding.warningConfirmPasswordTV.setVisibility(View.GONE);
                } else if (editable.toString().isEmpty()) {
                    passwordBinding.warningConfirmPasswordTV.setVisibility(View.GONE);
                } else if (!editable.toString().equals(passwordBinding.etPassword.getText().toString())) {
                    passwordBinding.warningConfirmPasswordTV.setText(getText(R.string.passwords_dont_match));
                    passwordBinding.warningConfirmPasswordTV.setVisibility(View.VISIBLE);
                } else {
                    passwordBinding.warningConfirmPasswordTV.setText(getText(R.string.password_not_allowed));
                    passwordBinding.warningConfirmPasswordTV.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void includeButtonRegister() {
        binding.includeButtonRegister.checkBox.setOnClickListener(view -> {
            if (((CompoundButton) view).isChecked()) {
                System.out.println("Checked");

            } else {
                System.out.println("Un-Checked");
            }
        });

        binding.includeButtonRegister.btnRegister.setOnClickListener(v -> {
            checkValidation();
            if (valid) {
                Log.i("Action", "doLogin");
                UserBody userBody = new UserBody();
                userBody.setFirstName(profileBinding.etFirtsName.getText().toString());
                if (profileBinding.etLastName.getText().toString().isEmpty()) {
                    userBody.setLastName("");
                } else {
                    userBody.setLastName(profileBinding.etLastName.getText().toString());
                }
                userBody.setEmail(profileBinding.etEmail.getText().toString());
                userBody.setPassword(passwordBinding.etPassword.getText().toString());
                mPresenter.doRegister(userBody);
            } else {
                checkError();
            }

        });
    }

    private void checkError() {
        if (profileBinding.etFirtsName.getText().toString().isEmpty()) {
            profileBinding.warningFirstNameTv.setVisibility(View.VISIBLE);
        }
        if (profileBinding.etEmail.getText().toString().isEmpty()) {
            profileBinding.warningEmailTV.setVisibility(View.VISIBLE);
        }
        if (passwordBinding.etPassword.getText().toString().isEmpty()) {
            passwordBinding.warningPasswordTV.setVisibility(View.VISIBLE);
        }
        if (passwordBinding.etConvirmPassword.getText().toString().isEmpty()) {
            passwordBinding.warningConfirmPasswordTV.setVisibility(View.VISIBLE);
        }
    }

    private void checkValidation() {
        valid = !profileBinding.etFirtsName.getText().toString().isEmpty()
                && !profileBinding.etEmail.getText().toString().isEmpty()
                && passwordBinding.warningPasswordTV.getVisibility() == View.GONE
                && passwordBinding.warningConfirmPasswordTV.getVisibility() == View.GONE;
    }

    @Override
    public void setPresenter(RegistrationPresenterContract mPresenter) {
        this.mPresenter = mPresenter;
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
    public void doRegisterSuccessfully(BaseResponse<UserResponse> userRegistered) {
        Toast.makeText(this, userRegistered.getMessage(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void doRegisterFailed(String toString) {
        Toast.makeText(this, toString, Toast.LENGTH_LONG).show();
    }
}