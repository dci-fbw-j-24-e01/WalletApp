package org.dci.walletapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText emailEditText;

    ImageView profileImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        profileImageView = findViewById(R.id.profileImageView);

        profileImageView.setImageResource(R.drawable.default_profile);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);


        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(view -> {
            Log.d("SK", "Cancel");
            finish();
        });


        saveButton.setOnClickListener(view -> {
            Log.d("SK", "Save");


            Profile profile = new Profile(nameEditText.getText().toString(), emailEditText.getText().toString());
            profile.setProfileImage(String.valueOf(R.drawable.default_profile));

            nameEditText.setText(profile.getName());
            emailEditText.setText(profile.getEmail());



        });
    }
}