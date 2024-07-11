package org.dci.walletapp;

import static org.dci.walletapp.StaticMethods.setSystemBarAppearance;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelpAndSupportActivity extends AppCompatActivity {


TextView titleTextView;
TextView contactDetailsTextView;

RecyclerView listOfQuestions;

ContactDetails contactDetails = new ContactDetails("Dummy Phone Number", "Dummy Email", "Dummy Fax Number");

List<Question> questions = new QuestionsContainer().getQuestionsList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_help_and_support);
        setSystemBarAppearance(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleTextView = findViewById(R.id.titleTextView);
        listOfQuestions = findViewById(R.id.listOfQuestions);
        contactDetailsTextView = findViewById(R.id.contactDetailsTextView);

listOfQuestions.setLayoutManager(new LinearLayoutManager(this));

HelpAndSupportAdapter helpAndSupportAdapter = new HelpAndSupportAdapter(questions);
listOfQuestions.setAdapter(helpAndSupportAdapter);







titleTextView.setText("Help and support");
contactDetailsTextView.setText("Contact Information: \n" + "\nPhone Number: " + contactDetails.getCompanyPhoneNumber() +
        "\nEmail: " + contactDetails.getCompanyEmail() +
        "\nFax Number: " + contactDetails.getCompanyFaxNumbers());

        setSeparatorLine();

    }

    private void setSeparatorLine() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listOfQuestions.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.custom_divider);
        if (dividerDrawable != null) {
            dividerItemDecoration.setDrawable(dividerDrawable);
        }
        listOfQuestions.addItemDecoration(dividerItemDecoration);
    }
}
