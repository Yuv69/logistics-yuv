package com.example.firebasetask;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TrackingActivity extends AppCompatActivity {
    private TextView trackingInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking); // Ensure this matches your layout file

        trackingInfo = findViewById(R.id.tracking_info); // Replace with your actual TextView ID

        // Retrieve the booking ID from the intent
        String bookingId = getIntent().getStringExtra("bookingId");
        trackingInfo.setText("Tracking Booking ID: " + bookingId); // Display it or use it as needed
    }
}
