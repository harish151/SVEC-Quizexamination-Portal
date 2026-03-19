package com.exam.model;

import java.util.ArrayList;

public class ExamAnswers {
    private ArrayList<String> originalans = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();

    public void setOriginalans(ArrayList<String> originalans){
            this.originalans = originalans;
    }
    public ArrayList<String> getOriginalans(){
        return originalans;
    }
    public void setAnswers(ArrayList<String> answers){
            this.answers = answers;
    }
    public ArrayList<String> getAnswers(){
        return answers;
    }
}
