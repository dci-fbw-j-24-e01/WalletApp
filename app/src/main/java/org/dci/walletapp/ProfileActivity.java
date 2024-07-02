package org.dci.walletapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText emailEditText;

    ImageView profileImageView;

    static final int REQUEST_IMAGE_CAPTURE = 1;


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

        Button editImageButton = findViewById(R.id.editImageButton);

        editImageButton.setOnClickListener(view -> {
            Log.d("SK", "Edit");

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                Log.d("SK", "Camera");
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }


        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // The image is captured; you can access it from the `data` intent
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Now you can save this `imageBitmap` to internal storage
            saveImageToInternalStorage(imageBitmap);
        }
    }

    private void saveImageToInternalStorage(Bitmap imageBitmap) {
        try {
            File dir = getDir("images", Context.MODE_PRIVATE); // Create a directory
            File file = new File(dir, "profile_image.jpg"); // Specify the filename

            FileOutputStream fos = new FileOutputStream(file);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            // Now the image is saved in internal storage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}