package org.dci.walletapp;
import android.content.Context;
import android.content.ContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JsonFilesOperations {
    private static JsonFilesOperations instance;

    private JsonFilesOperations() {

    }

    public static JsonFilesOperations getInstance() {
        if (instance == null) {
            instance = new JsonFilesOperations();
        }
        return instance;
    }

    public void writeTransaction(Context context, Transaction transaction) {
        String data = convertTransaction(transaction).toString();

        ContextWrapper contextWrapper = new ContextWrapper(context);
        File directory = contextWrapper.getDir(context.getFilesDir().getName(), Context.MODE_PRIVATE);
        File file =  new File(directory, "transaction.json");

        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write(data.getBytes());
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JSONObject convertTransaction(Transaction transaction) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("isIncome", String.valueOf(transaction.isIncome()));
            jsonObject.put("amount", transaction.getAmount());
            jsonObject.put("dateTime", transaction.getDateTime());
            jsonObject.put("incomeSource", transaction.getSource());
            jsonObject.put("description", transaction.getDescription());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }
}