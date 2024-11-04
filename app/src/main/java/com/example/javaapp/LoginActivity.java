package com.example.javaapp;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    // Initialize Firebase Auth
    mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
        finish();
        return;
    }

    Button btnLogin = findViewById(R.id.btLog);
        btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            authenticateUser();
        }
    });

    Button tvSwitchToRegister = findViewById(R.id. btLogreg);
        tvSwitchToRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switchToRegister();
        }
    });
}

private void authenticateUser() {
    EditText etLoginEmail = findViewById(R.id.etLogEmail);
    EditText etLoginPassword = findViewById(R.id.etLogPw);

    String email = etLoginEmail.getText().toString();
    String password = etLoginPassword.getText().toString();

    if (email.isEmpty() || password.isEmpty()) {
        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
        return;
    }

    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        showMainActivity();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
}

private void showMainActivity() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
}

private void switchToRegister() {
    Intent intent = new Intent(this, registerActivity.class);
    startActivity(intent);
    finish();
}
}