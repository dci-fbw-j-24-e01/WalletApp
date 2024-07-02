package org.dci.walletapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;

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



            boolean isValid = validateForm();

            if (isValid) {

                Toast.makeText(this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
                Profile profile = new Profile(nameEditText.getText().toString(), emailEditText.getText().toString());
                profile.setProfileImage(String.valueOf(R.drawable.default_profile));
                nameEditText.setText(profile.getName());
                emailEditText.setText(profile.getEmail());

                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("name", profile.getName());
                editor.putString("email", profile.getEmail());
                editor.apply();

            }




        });
    }

    private boolean validateForm() {

        boolean isValid = true;

        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError("Name is required");
            isValid = false;
        }else if (name.length() < 3){
            nameEditText.setError("Name length should have at least 2 characters");
            isValid = false;
        }
        else if (!name.matches("^[a-zA-Z-.' ]+$")) {
            nameEditText.setError("Name can only contain letters, dash, dot, single quote or space");
            isValid = false;
        }

        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            isValid = false;
        }

        return isValid;


    }
}