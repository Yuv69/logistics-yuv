package com.example.firebasetask;

import android.Manifest;
import android.content.Intent; // Import Intent class
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DriverActivity extends AppCompatActivity {
    private static final int LOCATION_REQUEST_CODE = 1000; // Request code for location permissions

    // Firebase Realtime Database
    private DatabaseReference databaseReference;

    // Firebase Firestore
    private FirebaseFirestore db;

    // Location services
    private FusedLocationProviderClient fusedLocationClient;

    // UI components
    private TextView jobStatusTextView;
    private TextView currentLocationText;
    private Button updateButton;
    private Button updateLocationBtn;
    private Button acceptButton;
    private Button rejectButton;
    private Button goToAdminButton; // Declare the new button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver); // Ensure this matches your XML file name

        // Initialize Firebase instances
        databaseReference = FirebaseDatabase.getInstance().getReference("driver_status");
        db = FirebaseFirestore.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Find views by ID
        jobStatusTextView = findViewById(R.id.jobStatusTextView);
        currentLocationText = findViewById(R.id.current_location_text);
        updateButton = findViewById(R.id.updateButton);
        updateLocationBtn = findViewById(R.id.update_location_btn);
        acceptButton = findViewById(R.id.accept_button);
        rejectButton = findViewById(R.id.reject_button);
        goToAdminButton = findViewById(R.id.goToAdminButton); // Initialize the new button

        // Check location permissions
        checkLocationPermissions();

        // Set button click listener for updating job status
        updateButton.setOnClickListener(v -> updateJobStatus("Job Completed")); // Example status update

        // Set button click listener for updating location
        updateLocationBtn.setOnClickListener(view -> startLocationTracking());

        // Set button click listeners for accepting/rejecting jobs
        acceptButton.setOnClickListener(v -> acceptJob());
        rejectButton.setOnClickListener(v -> rejectJob());

        // Set button click listener for navigating to AdminActivity
        goToAdminButton.setOnClickListener(v -> {
            Intent intent = new Intent(DriverActivity.this, AdminActivity.class); // Ensure AdminActivity is created
            startActivity(intent);
        });
    }

    private void checkLocationPermissions() {
        // Check if location permissions are granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions if not granted
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            // Permissions are already granted, start location tracking
            startLocationTracking();
        }
    }

    private void startLocationTracking() {
        // Check if location permissions are granted again before accessing location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    // Update driver's location in Firestore
                    updateDriverLocation(location);
                    // Update the TextView with the current location
                    currentLocationText.setText("Current Location: " + location.getLatitude() + ", " + location.getLongitude());
                } else {
                    Toast.makeText(DriverActivity.this, "Location not found. Try again.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(DriverActivity.this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Location permission is required to access your current location.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDriverLocation(Location location) {
        Map<String, Object> driverLocation = new HashMap<>();
        driverLocation.put("latitude", location.getLatitude());
        driverLocation.put("longitude", location.getLongitude());

        db.collection("drivers").document("driverId") // Replace "driverId" with the actual driver's ID
                .update(driverLocation)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(DriverActivity.this, "Location updated.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DriverActivity.this, "Failed to update location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void updateJobStatus(String status) {
        databaseReference.setValue(status)
                .addOnSuccessListener(aVoid -> jobStatusTextView.setText("Status: " + status))
                .addOnFailureListener(e -> jobStatusTextView.setText("Failed to update status."));
    }

    private void acceptJob() {
        // Code to handle job acceptance
        Toast.makeText(DriverActivity.this, "Job Accepted", Toast.LENGTH_SHORT).show();
        // You can add code here to update Firebase or perform other actions
    }

    private void rejectJob() {
        // Code to handle job rejection
        Toast.makeText(DriverActivity.this, "Job Rejected", Toast.LENGTH_SHORT).show();
        // You can add code here to update Firebase or perform other actions
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start location tracking
                startLocationTracking();
            } else {
                Toast.makeText(this, "Location permission denied. Cannot access current location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



