package com.example.firebasetask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class RemoveVehicleActivity extends AppCompatActivity {
    private EditText vehicleIdInput;
    private Button removeVehicleButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_vehicle); // Ensure this matches your XML layout file name

        db = FirebaseFirestore.getInstance();

        vehicleIdInput = findViewById(R.id.vehicle_id_input);
        removeVehicleButton = findViewById(R.id.remove_vehicle_button);

        removeVehicleButton.setOnClickListener(v -> removeVehicle());
    }

    private void removeVehicle() {
        String vehicleId = vehicleIdInput.getText().toString().trim();

        if (vehicleId.isEmpty()) {
            Toast.makeText(this, "Please enter a vehicle ID", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("vehicles").document(vehicleId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(RemoveVehicleActivity.this, "Vehicle Removed Successfully!", Toast.LENGTH_SHORT).show();
                    vehicleIdInput.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RemoveVehicleActivity.this, "Failed to remove vehicle: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
