package com.example.languagequiz;

public class Question {
private String question;
private String answer;
private boolean correct=false;

public Question(String question,String answer){
	this.answer=answer;
	this.question=question;
}

public boolean isCorrect() {
	return correct;
}

public void setCorrect(boolean correct) {
	this.correct = correct;
}

public String getQuestion() {
	return question;
}

public String getAnswer() {
	return answer;
}

	
	
	
	
}