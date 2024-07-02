package org.dci.walletapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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

        // Get the existing data
        JSONArray transactionsArray = readTransactions(context);

        // Convert new transaction to JSON and add it to the array
        JSONObject newTransaction = convertTransaction(transaction);
        transactionsArray.put(newTransaction);

        // Pretty-print the updated array and write it back to the file
        String data = null;
        try {
            data = transactionsArray.toString(4);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File directory = contextWrapper.getDir(context.getFilesDir().getName(), Context.MODE_PRIVATE);
        File file = new File(directory, "transaction.json");

        try (FileOutputStream fos = new FileOutputStream(file, false)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONArray readTransactions(Context context) {


        ContextWrapper contextWrapper = new ContextWrapper(context);
        File directory = contextWrapper.getDir(context.getFilesDir().getName(), Context.MODE_PRIVATE);
        File file = new File(directory, "transaction.json");

        StringBuilder jsonString = new StringBuilder();

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonString.append(line);
                }
            } catch (IOException e) {
                Log.e("JsonFilesOperations", "Error reading file", e);
            }

            try {
                String jsonContent = jsonString.toString().trim();
                if (jsonContent.startsWith("{")) {
                    // If the content starts with {, wrap it in an array
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(new JSONObject(jsonContent));
                    return jsonArray;
                } else {
                    return new JSONArray(jsonContent);
                }
            } catch (JSONException e) {
                Log.e("JsonFilesOperations", "Error parsing JSON", e);
            }
        }

        return new JSONArray();  // Return an empty array if the file doesn't exist or is empty
    }


    private JSONObject convertTransaction(Transaction transaction) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("isIncome", transaction.isIncome());   // Store as boolean
            jsonObject.put("amount", transaction.getAmount());
            jsonObject.put("dateTime", transaction.getDateTime().toString());
            jsonObject.put("source", transaction.getSource());
            jsonObject.put("description", transaction.getDescription());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }
}
