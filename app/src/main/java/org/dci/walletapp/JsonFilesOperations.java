package org.dci.walletapp;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFilesOperations {


    public void write(Context context, Transaction transaction) {
        String userString = convertTransaction(transaction).toString();

        File file = new File(context.getFilesDir(), "transactions.json");

        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(userString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Log.d("tetjson", file.getAbsolutePath());
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