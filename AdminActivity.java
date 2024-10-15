package com.example.firebasetask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private TextView adminTitleTextView; // TextView for the title
    private Button addVehicleButton;       // Button to add a vehicle
    private Button removeVehicleButton;    // Button to remove a vehicle
    private Button viewFleetButton;        // Button to view the fleet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Initialize UI components
        adminTitleTextView = findViewById(R.id.admin_title_text);
        addVehicleButton = findViewById(R.id.add_vehicle_button);
        removeVehicleButton = findViewById(R.id.remove_vehicle_button);
        viewFleetButton = findViewById(R.id.view_fleet_button);

        // Set up button click listeners
        addVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddVehicleActivity
                startActivity(new Intent(AdminActivity.this, AddVehicleActivity.class));
            }
        });

        removeVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start RemoveVehicleActivity
                startActivity(new Intent(AdminActivity.this, RemoveVehicleActivity.class));
            }
        });

        viewFleetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ViewFleetActivity
                startActivity(new Intent(AdminActivity.this, ViewFleetActivity.class));
            }
        });
    }
}


