package view;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Answer;
import control.DataBase;
/**
 * Servlet implementation class AnsView
 */
@WebServlet("/AnsView")
public class AnsView extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ArrayList<Answer> answers = new ArrayList<Answer>();
    private String question;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer questionID = Integer.parseInt(request.getParameter("id"));
		DataBase data = new DataBase();
		answers = data.getAnswers(questionID);
		question = data.getQuestion(questionID);
		
		request.setAttribute("answers", answers);
		request.setAttribute("question", question);
		
		RequestDispatcher rd = request.getRequestDispatcher("/Views/AnswersView.jsp");
		rd.forward(request,  response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String newAnswer= request.getParameter("newAnswer");
		Integer questionID = Integer.parseInt(request.getParameter("questionID"));
		DataBase data = new DataBase();
		data.addAnswer(newAnswer, questionID);
		
		response.sendRedirect("/answer?id=" + questionID);
		
	}

}
