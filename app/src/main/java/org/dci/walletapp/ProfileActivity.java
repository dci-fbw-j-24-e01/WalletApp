package org.dci.walletapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;
    private static final int PERMISSION_REQUEST_CODE = 3;

    private EditText nameEditText;
    private EditText emailEditText;

    private ImageView profileImageView;

    private Profile profile;

    private Bitmap imageBitmap;

    private Button saveButton;
    private Button cancelButton;
    private Button goBackButton;
    private Button editImageButton;


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
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        goBackButton = findViewById(R.id.backButton);
        editImageButton = findViewById(R.id.editImageButton);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nameEditText.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_YES);
            emailEditText.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_YES);
        }

        editTextChangedListener(nameEditText, saveButton, cancelButton, goBackButton, editImageButton);
        editTextChangedListener(emailEditText, saveButton, cancelButton, goBackButton, editImageButton);

        profile = readProfileFromJSON(this);

        if(profile != null) {

            Bitmap decodedByte = base64StringToBitmap(profile.getProfileImage());

            if (decodedByte != null) {
                profileImageView.setImageBitmap(decodedByte);
            } else {
                profileImageView.setImageResource(R.drawable.default_profile);
            }

            nameEditText.setText(profile.getName());
            emailEditText.setText(profile.getEmail());
        }

        editImageButton.setOnClickListener(view -> showImageSourceDialog());

        goBackButton.setOnClickListener(view -> {
            updateButtonVisibility(true);
            finish();
        });

        cancelButton.setOnClickListener(view -> finish());

        saveButton.setOnClickListener(view -> {
            boolean isValid = validateProfileForm();

            if (isValid) {
                Toast.makeText(this, "Profile submitted successfully", Toast.LENGTH_SHORT).show();
                profile = new Profile(nameEditText.getText().toString(), emailEditText.getText().toString());

                if (imageBitmap != null) {
                    profile.setProfileImage(bitmapToBase64(imageBitmap));
                }

                nameEditText.setText(profile.getName());
                emailEditText.setText(profile.getEmail());

                storeProfileAsSharedPreferences();
                storeProfileAsJSON(this, profile);

                updateButtonVisibility(false);
            }
        });
    }

    private void updateButtonVisibility(boolean isProfileEditable) {
        int goBackVisibility = isProfileEditable ? View.INVISIBLE : View.VISIBLE;
        int editModeVisibility = isProfileEditable ? View.VISIBLE : View.INVISIBLE;

        goBackButton.setVisibility(goBackVisibility);
        saveButton.setVisibility(editModeVisibility);
        cancelButton.setVisibility(editModeVisibility);
        editImageButton.setVisibility(editModeVisibility);
    }

    public static Bitmap base64StringToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void storeProfileAsSharedPreferences() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", profile.getName());
        editor.putString("email", profile.getEmail());
        editor.apply();
    }

    private void setEditTextInputError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
    }

    private void editTextChangedListener(EditText editText, Button saveButton, Button cancelButton, Button backButton, Button editImageButton) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called to notify you that somewhere within s, the text is about to be replaced with new text
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called to notify you that somewhere within s, the text has been replaced with new text
                // You can use s.toString() to get the current text
                updateButtonVisibility(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This method is called to notify you that somewhere within s, the text has been changed
            }
        });
    }

    private boolean validateProfileForm() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        return validateName(name) && validateEmail(email);
    }

    private boolean validateName(String name) {
        if (name.isEmpty()) {
            setEditTextInputError(nameEditText, "Name is required");
            return false;
        }
        if (name.length() < 3) {
            setEditTextInputError(nameEditText, "Name length should have at least 2 characters");
            return false;
        }
        if (!name.matches("^[a-zA-Z-.' ]+$")) {
            setEditTextInputError(nameEditText, "Name can only contain letters, dash, dot, single quote or space");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            setEditTextInputError(emailEditText, "Email is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setEditTextInputError(emailEditText, "Enter a valid email address");
            return false;
        }
        return true;
    }

    private void showImageSourceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Image Source");
        String[] options = {"Camera", "Gallery"};

        builder.setItems(options, (dialog, selected) -> {
            if (selected == 0) {
                checkPermissionAndOpenCamera();
            } else {
                openGallery();
            }
        });

        builder.show();
    }

    private void checkPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        } else {
            openCamera();
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

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    imageBitmap = (Bitmap) extras.get("data");
                }
                profileImageView.setImageBitmap(imageBitmap);
                saveImageToInternalStorage(imageBitmap);
            } else if (requestCode == REQUEST_PICK_IMAGE && data != null) {
                Uri selectedImage = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    profileImageView.setImageBitmap(imageBitmap);
                    saveImageToInternalStorage(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveImageToInternalStorage(Bitmap bitmap) {
        try {
            String filename = "profile_image_" + System.currentTimeMillis() + ".jpg";
            FileOutputStream fileOutputStream = openFileOutput(filename, MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
        }
    }

    public void storeProfileAsJSON(Context context, Profile profile) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ContextWrapper contextWrapper = new ContextWrapper(context);
        File directory = contextWrapper.getDir(context.getFilesDir().getName(), Context.MODE_PRIVATE);
        File file = new File(directory, "profile.json");

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(mapper.writeValueAsBytes(profile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Profile readProfileFromJSON(Context context) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File directory = contextWrapper.getDir(context.getFilesDir().getName(), Context.MODE_PRIVATE);

        if (directory != null) {
            File file =  new File(directory, "profile.json");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                try (InputStream stream = Files.newInputStream(file.toPath())) {
                    JsonNode rootNode = new ObjectMapper().readTree(stream);

                    profile = new Profile(rootNode.get("name").asText(), rootNode.get("email").asText());
                    profile.setProfileImage(rootNode.get("profileImage").asText());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            profile = new Profile();
        }

        return profile;
    }
}

