package org.dci.walletapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelpAndSupportAdapter extends RecyclerView.Adapter<HelpAndSupportViewHolder> {

    List<Question> questions;

    public HelpAndSupportAdapter(List<Question> questions) {
        this.questions = questions;
        Log.d("test", questions.get(0).getQuestion());


    }

    @NonNull
    @Override
    public HelpAndSupportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_help_and_support_item_view, parent, false);

        return new HelpAndSupportViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull HelpAndSupportViewHolder holder, int position) {

        Question question = questions.get(position);
        holder.getQuestionTextView().setText(question.getQuestion());
        holder.getAnswerTextView().setText(question.getAnswer());

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
