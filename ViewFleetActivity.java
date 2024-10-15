package com.example.firebasetask;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ViewFleetActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView fleetInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fleet);

        db = FirebaseFirestore.getInstance();
        fleetInfoTextView = findViewById(R.id.fleet_info_text_view);

        // Fetch fleet information
        fetchFleetData();
    }

    private void fetchFleetData() {
        db.collection("vehicles").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(ViewFleetActivity.this, "Error loading vehicles: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (snapshots != null) {
                    StringBuilder fleetInfo = new StringBuilder();
                    for (QueryDocumentSnapshot document : snapshots) {
                        fleetInfo.append(document.getString("vehicleName")).append("\n"); // Assuming the vehicle name field is "vehicleName"
                    }
                    fleetInfoTextView.setText(fleetInfo.toString());
                }
            }
        });
    }
}


