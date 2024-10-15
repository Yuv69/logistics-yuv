package com.example.firebasetask;

import android.content.Intent; // Import Intent class
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Ensure this matches your XML file name

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize EditTexts and Button
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerBtn);

        // Set up the button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input text from EditTexts
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate email and password
                if (isValidEmail(email) && isValidPassword(password)) {
                    // Proceed with user registration
                    registerUser(email, password);
                } else {
                    // Show error message
                    Toast.makeText(RegisterActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(RegisterActivity.this, "Registration Successful.", Toast.LENGTH_SHORT).show();

                // Start UserDashboardActivity after successful registration
                Intent intent = new Intent(RegisterActivity.this, UserDashboardActivity.class);
                startActivity(intent);
                finish(); // Optionally finish the RegisterActivity
            } else {
                Toast.makeText(RegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Simple email validation logic
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Add password validation logic here (length, special characters, etc.)
        return password.length() >= 6; // Example: minimum length
    }
}

