package com.example.firebasetask;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ViewBookingsActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView bookingsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI component
        bookingsTextView = findViewById(R.id.bookings_text_view);

        // Load and display bookings from Firestore
        loadBookings();
    }

    private void loadBookings() {
        db.collection("bookings").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> bookingsList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String bookingInfo = "Pickup: " + document.getString("pickupLocation") +
                            "\nDropoff: " + document.getString("dropOffLocation") +
                            "\nVehicle: " + document.getString("vehicleType") +
                            "\nStatus: " + document.getString("status") + "\n";
                    bookingsList.add(bookingInfo);
                }
                // Display all bookings
                bookingsTextView.setText(String.join("\n\n", bookingsList));
            } else {
                bookingsTextView.setText("Failed to load bookings.");
            }
        });
    }
}

