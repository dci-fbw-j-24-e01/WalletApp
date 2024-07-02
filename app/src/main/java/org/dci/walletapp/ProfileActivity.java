package org.dci.walletapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText emailEditText;

    ImageView profileImageView;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;
    private static final int PERMISSION_REQUEST_CODE = 3;
    Profile profile;

    Bitmap imageBitmap;


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
            showImageSourceDialog();


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
                profile = new Profile(nameEditText.getText().toString(), emailEditText.getText().toString());

                if(imageBitmap != null){
                    profile.setProfileImage(bitmapToBase64(imageBitmap));
                }else{
                    profile.setProfileImage(String.valueOf(R.drawable.default_profile));
                }


                nameEditText.setText(profile.getName());
                emailEditText.setText(profile.getEmail());

                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("name", profile.getName());
                editor.putString("email", profile.getEmail());
                editor.apply();

                storeProfile(this, profile);
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
        } else if (name.length() < 3) {
            nameEditText.setError("Name length should have at least 2 characters");
            isValid = false;
        } else if (!name.matches("^[a-zA-Z-.' ]+$")) {
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
    private void showImageSourceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");
        String[] options = {"Camera", "Gallery"};

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    checkPermissionAndOpenCamera();
                } else {
                    openGallery();
                }
            }
        });

        builder.show();
    }
    private void checkPermissionAndOpenCamera() {
        Log.d("SK","checkPermissionAndOpenCamera method");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        } else {
            Log.d("SK","openCamera call");
            openCamera();
        }
    }


    private void openCamera() {
        Log.d("SK","openCamera method");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Log.d("SK","openCamera method startActivityForResult");
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        Log.d("SK","openGallery method");
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("SK","onActivityResult method");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                 imageBitmap = (Bitmap) extras.get("data");
                profileImageView.setImageBitmap(imageBitmap);


                saveImageToInternalStorage(imageBitmap);
            } else if (requestCode == REQUEST_PICK_IMAGE && data != null) {
                Uri selectedImage = data.getData();
                try {
                     imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    profileImageView.setImageBitmap(imageBitmap);

//                    profile.setProfileImage(bitmapToBase64(bitmap));
                    saveImageToInternalStorage(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveImageToInternalStorage(Bitmap bitmap) {
        try {
            String filename = "image_" + System.currentTimeMillis() + ".jpg";
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public void storeProfile(Context context, Profile profile) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ContextWrapper contextWrapper = new ContextWrapper(context);
        File directory = contextWrapper.getDir(context.getFilesDir().getName(), Context.MODE_PRIVATE);
        File file =  new File(directory, "profile.json");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(mapper.writeValueAsBytes(profile));
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

