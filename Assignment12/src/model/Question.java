package model;

public class Question {
	public int question_id;
	public String question;

	public Question(int qNumber, String questionText) {
		this.question_id = qNumber;
		this.question = questionText;
	}
}
