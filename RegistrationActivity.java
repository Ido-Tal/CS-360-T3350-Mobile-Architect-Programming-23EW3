package com.example.iteminventoryapp;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = RegistrationActivity.this;
    private NestedScrollView scrollView;
    private TextInputLayout inputLayoutUser;
    private TextInputLayout inputLayoutPassword;
    private TextInputLayout inputLayoutRetypePassword;
    private TextInputEditText editTextUser;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextRetypePassword;
    private AppCompatButton registerButton;
    private AppCompatTextView loginLink;
    private Validation validateInput;
    private UserDatabase databaseUsers;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        scrollView = (NestedScrollView) findViewById(R.id.loginPageView);
        inputLayoutUser = (TextInputLayout) findViewById(R.id.usernameTextInputLayout);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.passwordTextInputLayout);
        inputLayoutRetypePassword = (TextInputLayout) findViewById(R.id.retypePasswordLayout);
        editTextUser = (TextInputEditText) findViewById(R.id.usernameTextInput);
        editTextPassword = (TextInputEditText) findViewById(R.id.passwordTextInput);
        editTextRetypePassword = (TextInputEditText) findViewById(R.id.retypePasswordInputText);
        registerButton = (AppCompatButton) findViewById(R.id.registrationButton);
        loginLink = (AppCompatTextView) findViewById(R.id.loginLink);
    }

    private void initListeners() {
        registerButton.setOnClickListener(this);
        loginLink.setOnClickListener(this);
    }

    private void initObjects() {
        validateInput = new Validation(activity);
        databaseUsers = new UserDatabase(activity);
        user = new User();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.registrationButton) {
            postDataToSQLite();
        } else if (id == R.id.loginLink) {
            finish();
        }
    }

    private void postDataToSQLite() {
        if (!validateInput.isInputTextFilled(editTextUser, inputLayoutUser, "Enter Valid Username")) {
            return;
        }
        if (!validateInput.isInputTextNull(editTextUser, inputLayoutUser, "Enter Valid Username")) {
            return;
        }
        if (!validateInput.isInputTextFilled(editTextPassword, inputLayoutPassword, "Enter Password")) {
            return;
        }
        if (!validateInput.isInputEditTextMatches(editTextPassword, editTextRetypePassword,
                inputLayoutRetypePassword, "Passwords Do Not Match")) {
            return;
        }
        if (!databaseUsers.checkUserCredentials(Objects.requireNonNull(editTextUser.getText()).toString().trim())) {
            user.setUser(editTextUser.getText().toString().trim());
            user.setPassword(Objects.requireNonNull(editTextPassword.getText()).toString().trim());
            databaseUsers.addUser(user);
            Snackbar.make(scrollView, "Registration Successful", Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        } else {
            Snackbar.make(scrollView, "Username Already Used", Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        editTextUser.setText(null);
        editTextPassword.setText(null);
        editTextRetypePassword.setText(null);
    }
}