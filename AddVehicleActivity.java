package com.example.firebasetask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AddVehicleActivity extends AppCompatActivity {
    private EditText vehicleNameInput, vehicleTypeInput;
    private Button addVehicleButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle); // Ensure this matches your XML layout file name

        db = FirebaseFirestore.getInstance();

        vehicleNameInput = findViewById(R.id.vehicle_name_input);
        vehicleTypeInput = findViewById(R.id.vehicle_type_input);
        addVehicleButton = findViewById(R.id.add_vehicle_button);

        addVehicleButton.setOnClickListener(v -> addVehicle());
    }

    private void addVehicle() {
        String name = vehicleNameInput.getText().toString().trim();
        String type = vehicleTypeInput.getText().toString().trim();

        if (name.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> vehicle = new HashMap<>();
        vehicle.put("name", name);
        vehicle.put("type", type);

        db.collection("vehicles").add(vehicle)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddVehicleActivity.this, "Vehicle Added Successfully!", Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddVehicleActivity.this, "Failed to add vehicle: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void clearFields() {
        vehicleNameInput.setText("");
        vehicleTypeInput.setText("");
    }
}
