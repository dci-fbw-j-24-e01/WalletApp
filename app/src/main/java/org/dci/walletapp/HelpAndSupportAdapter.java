package org.dci.walletapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelpAndSupportAdapter extends RecyclerView.Adapter<HelpAndSupportViewHolder> {

    private List<Question> questions;

    public HelpAndSupportAdapter() {
        this.questions = new QuestionsContainer().getQuestionsList();

    }

    @NonNull
    @Override
    public HelpAndSupportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

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
        return 0;
    }
}
