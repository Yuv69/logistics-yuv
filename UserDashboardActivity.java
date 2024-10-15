package com.example.firebasetask;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class UserDashboardActivity extends AppCompatActivity {

    private Button bookVehicleButton;
    private Button viewBookingsButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        // Initialize buttons
        bookVehicleButton = findViewById(R.id.book_vehicle_button);
        viewBookingsButton = findViewById(R.id.view_bookings_button);
        logoutButton = findViewById(R.id.logout_button);

        // Intent for booking a vehicle
        bookVehicleButton.setOnClickListener(v -> {
            // Start BookingActivity
            startActivity(new Intent(UserDashboardActivity.this, BookingActivity.class));
        });

        // Intent for viewing bookings
        viewBookingsButton.setOnClickListener(v -> {
            // Start ViewBookingsActivity (You need to create this activity)
            startActivity(new Intent(UserDashboardActivity.this, ViewBookingsActivity.class));
        });

        // Intent for logout
        logoutButton.setOnClickListener(v -> {
            // Perform logout action (e.g., FirebaseAuth sign out) and go back to login screen
            // Example for Firebase Authentication:
            // FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(UserDashboardActivity.this, LoginActivity.class));
            finish(); // Close UserDashboardActivity so the user can't go back to it after logging out
        });
    }
}


