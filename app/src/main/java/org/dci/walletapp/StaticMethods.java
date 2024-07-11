
package org.dci.walletapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class StaticMethods {

    //To ensure the system bar icons are black when system uses the (deactivated) dark mode
    @SuppressLint("ObsoleteSdkInt")
    public static void setSystemBarAppearance(AppCompatActivity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = window.getInsetsController();
            if (insetsController != null) {
                insetsController.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                );
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    // Method to load profile from JSON file
    public static Profile loadUsernameFromJSON(Context context) {
        Profile profile = null;
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File directory = contextWrapper.getDir(context.getFilesDir().getName(), Context.MODE_PRIVATE);
        File file = new File(directory, "profile.json");
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode jsonNode = mapper.readTree(file);
            profile = mapper.treeToValue(jsonNode, Profile.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return profile;
    }
}