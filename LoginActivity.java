package com.example.iteminventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;
    private NestedScrollView scrollView;
    private TextInputLayout inputLayoutUser;
    private TextInputLayout inputLayoutPassword;
    private TextInputEditText editTextUser;
    private TextInputEditText editTextPassword;
    private AppCompatButton loginButton;
    private AppCompatTextView registerLink;
    private Validation validateInput;
    private UserDatabase databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initializeViews();
        initializeListeners();
        initializeObjects();
    }

    private void initializeViews() {
        scrollView = findViewById(R.id.loginPageView);
        inputLayoutUser = findViewById(R.id.usernameTextInputLayout);
        inputLayoutPassword = findViewById(R.id.passwordTextInputLayout);
        editTextUser = findViewById(R.id.usernameTextInput);
        editTextPassword = findViewById(R.id.passwordTextInput);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registrationLink);
    }

    private void initializeListeners() {
        loginButton.setOnClickListener(this);
        registerLink.setOnClickListener(this);
    }

    private void initializeObjects() {
        databaseUsers = new UserDatabase(activity);
        validateInput = new Validation(activity);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.loginButton) {
            verifyCredentials();
        } else if (id == R.id.registrationLink) {
            Intent intentRegister = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intentRegister);
        }
    }

    private void verifyCredentials() {
        if (!validateInput.isInputTextFilled(editTextUser, inputLayoutUser, "Enter Valid Username")) {
            return;
        }
        if (!validateInput.isInputTextNull(editTextUser, inputLayoutUser, "Enter Valid Username")) {
            return;
        }
        if (!validateInput.isInputTextFilled(editTextPassword, inputLayoutPassword, "Enter Valid Username")) {
            return;
        }
        if (databaseUsers.checkUserCredentials(Objects.requireNonNull(editTextUser.getText()).toString().trim()
            , Objects.requireNonNull(editTextPassword.getText()).toString().trim())) {
            Intent accountsIntent = new Intent(activity, ItemActivity.class);
            accountsIntent.putExtra("USER", editTextUser.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);
        }
        else
        {
            Snackbar.make(scrollView, "Wrong Username or Password", Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        editTextUser.setText(null);
        editTextPassword.setText(null);
    }
}
