package com.example.firebasetask;

import android.content.Intent; // Import Intent
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {
    private EditText pickupLocationEditText, dropOffLocationEditText, vehicleTypeEditText;
    private Button bookButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking); // Ensure this matches your XML layout file name

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        pickupLocationEditText = findViewById(R.id.pickup_location);
        dropOffLocationEditText = findViewById(R.id.drop_off_location);
        vehicleTypeEditText = findViewById(R.id.vehicle_type);
        bookButton = findViewById(R.id.book_button); // Make sure this ID matches your XML layout

        // Set click listener for booking button
        bookButton.setOnClickListener(v -> bookVehicle());
    }

    private void bookVehicle() {
        // Get the input values
        String pickupLocation = pickupLocationEditText.getText().toString().trim();
        String dropOffLocation = dropOffLocationEditText.getText().toString().trim();
        String vehicleType = vehicleTypeEditText.getText().toString().trim();

        // Validate inputs before booking
        if (pickupLocation.isEmpty() || dropOffLocation.isEmpty() || vehicleType.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map for the booking details
        Map<String, Object> booking = new HashMap<>();
        booking.put("pickupLocation", pickupLocation);
        booking.put("dropOffLocation", dropOffLocation);
        booking.put("vehicleType", vehicleType);
        booking.put("status", "pending");

        // Add booking details to Firestore
        db.collection("bookings").add(booking)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(BookingActivity.this, "Booking Successful!", Toast.LENGTH_SHORT).show();
                    // Navigate to TrackingActivity after successful booking
                    Intent intent = new Intent(BookingActivity.this, TrackingActivity.class);
                    // Optionally, pass the document ID or other data if needed
                    intent.putExtra("bookingId", documentReference.getId());
                    startActivity(intent);
                    // Optionally, clear the fields after successful booking
                    clearFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(BookingActivity.this, "Failed to book vehicle: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Optional method to clear input fields
    private void clearFields() {
        pickupLocationEditText.setText("");
        dropOffLocationEditText.setText("");
        vehicleTypeEditText.setText("");
    }
}



