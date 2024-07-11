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
        questionsList.add(new Question("What's your dream job?", "Astronaut"));
        questionsList.add(new Question("Do you enjoy music?", "Very much"));
        questionsList.add(new Question("What's your favorite book?", "A book? What's that?"));
        questionsList.add(new Question("What's your favorite hobby?", "Photography"));
        questionsList.add(new Question("Do you like traveling?", "Absolutely"));
        questionsList.add(new Question("What's your favorite food?", "Pizza"));
        questionsList.add(new Question("Do you have any pets?", "Yes, a dog"));
        questionsList.add(new Question("What's your favorite movie?", "Inception"));
        questionsList.add(new Question("Do you play any sports?", "Yes, soccer"));
        questionsList.add(new Question("What's your favorite season?", "Spring"));
        questionsList.add(new Question("Do you like reading?", "Sometimes"));
        questionsList.add(new Question("What's your favorite color?", "Blue"));
        questionsList.add(new Question("Do you prefer tea or coffee?", "Coffee"));
        questionsList.add(new Question("What's your favorite holiday destination?", "Hawaii"));
        questionsList.add(new Question("Do you like cooking?", "Yes, very much"));
        questionsList.add(new Question("What's your favorite animal?", "Dolphin"));
        questionsList.add(new Question("Do you play any musical instruments?", "Yes, guitar"));
        questionsList.add(new Question("What's your favorite TV show?", "Breaking Bad"));
        questionsList.add(new Question("Do you prefer the beach or the mountains?", "Beach"));
        questionsList.add(new Question("What's your favorite ice cream flavor?", "Chocolate"));




    }
}
