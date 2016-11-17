package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.DatabaseMetaData;

import model.Answer;
import model.Question;

public class DataBase {
	
	private Connection con;
	private Statement stmt;
	
	private String url = "jbdc:mysql://kc-sec-appdb01.kc.umkc.edu/wspn8c?allowMultiQuires=true";
	private String userID = "wspn8c";
	private String password = "QbZhr7gEjG";
	
	public DataBase(){
		
		try{
			Class.forName("com.mysql.jdbc.driver");
		}
		catch(java.lang.ClassNotFoundException e){
			System.out.println(e);
			System.exit(0);
		}
		startConnect();
		try{
			java.sql.DatabaseMetaData dbm = con.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "Questions", null);
			if (!tables.next()){
				createTables();
			}
		}
			catch (SQLException e){
				System.out.println(e);
			}
		endConnect();
		}

	
	private void startConnect() {
		
		try{
			con = DriverManager.getConnection(url, userID, password);
			stmt = con.createStatement();
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}
	
	private void endConnect() {
		
		try{
			stmt.close();
			con.close();
		}
		catch (SQLException e){
			System.out.println("end connection failed");
		}
	}

	private void createTables() {
		
		String makeQString = "create table Questions" + "(uestion_id INT NOT NULL AUTO_INCREMENT,"
		+ "question VARCHAR(264), PRIMARY KEY (question_id))";
		
		String makeAString = "create table Answers (answer_id INT NOT NULL AUTO_INCREMENT," 
		+ "answer VARCHAR(264), PRIMARY KEY (answer_id))";
		
		startConnect();
		
		try{
			stmt.executeUpdate(makeQString);
			stmt.executeUpdate(makeAString);
		}
		catch (SQLException e){
			System.out.println("table creation failed");
		}
		endConnect();
	}

	public void addAnswer(String answer, int questionID) {
	
		startConnect();
		try{
			stmt.executeUpdate("INSERT INTO Answers ('question_id', 'answer') VALUES('"
					+ questionID + "', '" + answer + "')");
		}
		catch (SQLException e){
			System.out.println("failed to add answer");
		}
		endConnect();
	}

	public void addQuestion(String question){
		
		startConnect();
		try{
			stmt.executeUpdate("Insert INTO questions ('question') VALUES ('" + question + "')");
		}
		catch(SQLException e){
			System.out.println("failed to add question");
		}
		endConnect();
	}
	
	public String getQuestion(int questionID){
		String question;
		startConnect();
		try{
			ResultSet rs = stmt.executeQuery("SELECT question FROM questions WHERE question_id = "
					+ questionID);
			rs.next();
			question = rs.getString("question");
			endConnect();
			return question;
		}
		catch (SQLException e){
			System.out.println("failed to get question");
		}
		endConnect();
		return "failed access";
	}
	
	public ArrayList<Answer> getAnswers (int questionID){
		ArrayList<Answer> answers = new ArrayList<Answer>();
		startConnect();
		try{
			ResultSet rs = stmt.executeQuery("SELECT * FROM Answers WHERE question_id = " + questionID);
			while (rs.next()){
				int answer_id = rs.getInt("answer_id");
				int question_id = rs.getInt("question_id");
				String answer = rs.getString("answer");
				answers.add(new Answer(answer_id, question_id, answer));
			}
		}
		catch (SQLException e){
				System.out.println("failed to get answer from list");
			}
		endConnect();
		return answers;
		}
	
	public ArrayList<Question> getQuestions(){
		ArrayList<Question> questions = new ArrayList<Question>();
		startConnect();
		try{
			ResultSet rs = stmt.executeQuery("SELECT * FROM Questions");
			while(rs.next()){
				int question_id = rs.getInt("question_id");
				String question = rs.getString("questioin");
				questions.add(new Question(question_id, question));
				}
			}
		
			catch(SQLException e){
				System.out.println("failed to get question");
			}
			endConnect();
			return questions;
		}
	}

