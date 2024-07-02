package org.dci.walletapp;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public void writeTransactions(Context context, List<Transaction> transactionsList) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ContextWrapper contextWrapper = new ContextWrapper(context);
        File directory = contextWrapper.getDir(context.getFilesDir().getName(), Context.MODE_PRIVATE);
        File file =  new File(directory, "transaction.json");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(mapper.writeValueAsBytes(transactionsList));
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> readTransactions(Context context) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File directory = contextWrapper.getDir(context.getFilesDir().getName(), Context.MODE_PRIVATE);
        File file =  new File(directory, "transaction.json");
        List<Transaction> transactionsList = new ArrayList<>();
        try (InputStream stream = Files.newInputStream(file.toPath())) {
            JsonNode rootNode = new ObjectMapper().readTree(stream);

            for (JsonNode transaction : rootNode) {
                Log.d("Testnode", transaction.toString());
                transactionsList.add(new Transaction(
                        transaction.get("amount").asDouble(),
                        LocalDateTime.parse(transaction.get("dateTime").asText()),
                        transaction.get("description").asText(),
                        transaction.get("income").asBoolean(),
                        transaction.get("source").asText()
                ));
            }
        } catch (IOException e) {
            return new ArrayList<Transaction>();
        }
        return transactionsList;
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