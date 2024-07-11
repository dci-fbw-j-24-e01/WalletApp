package org.dci.walletapp;

import java.util.ArrayList;
import java.util.List;

public class QuestionsContainer {

    private List<Question> questionsList;

    public QuestionsContainer() {

        questionsList = new ArrayList<>();
        dummyFillList();
    }
     public void addQuestion(Question question){
        questionsList.add(question);

     }

    public List<Question> getQuestionsList() {
        return questionsList;
    }


    private void dummyFillList(){

        questionsList.add(new Question("How are you?", "Fine, hehe"));
        questionsList.add(new Question("How is the wheather", "Rainy"));
        questionsList.add(new Question("Do you like pineapples?", "eeh... no"));
        questionsList.add(new Question("Is this question useful?", "Of course :D not."));
        questionsList.add(new Question("What's your favorite color?", "Blue"));
        questionsList.add(new Question("Do you have any pets?", "Yes, a cat"));
        questionsList.add(new Question("What's your favorite movie?", "Inception Inception Inception Inception Inception Inception Inception Inception Inception Inception Inception Inception Inception Inception Inception Inception Inception Inception Inception Inception "));
        questionsList.add(new Question("Can you cook?", "Barely"));
        questionsList.add(new Question("Do you like to travel?", "Absolutely"));
        questionsList.add(new Question("What's your hobby?", "Reading"));
        questionsList.add(new Question("Do you play sports?", "Sometimes"));
        questionsList.add(new Question("What's your dream job?", "Astronaut"));
        questionsList.add(new Question("Do you enjoy music?", "Very much"));
        questionsList.add(new Question("What's your favorite book?", "A book? What's that?"));
    }
}
