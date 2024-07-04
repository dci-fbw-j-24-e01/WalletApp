package org.dci.walletapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HelpAndSupportViewHolder extends RecyclerView.ViewHolder {


    private TextView questionTextView;
    private TextView answerTextView;

    public HelpAndSupportViewHolder(@NonNull View itemView) {
        super(itemView);


        questionTextView = itemView.findViewById(R.id.question);
        answerTextView = itemView.findViewById(R.id.answer);
    }

    public TextView getQuestionTextView() {
        return questionTextView;
    }

    public TextView getAnswerTextView() {
        return answerTextView;
    }
}
