package com.chatApp.servelet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chatApp.Dao.UserDaoImpl;

/**
 * Servlet implementation class SendMessages
 */
@WebServlet("/SendMessages")
public class SendMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMessages() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String fromt = request.getParameter("fromt");
		String too = request.getParameter("too");
		String message = request.getParameter("messages");

		if (fromt != null && too != null && message != null) {

			String messag = new UserDaoImpl().savemessage(fromt, too, message);

			if (messag != null) {

				response.setStatus(HttpServletResponse.SC_OK); // set the response status code
				response.setContentType("text/plain"); // set the content type
				PrintWriter out = response.getWriter();
				out.println(messag);

			}
			else
			{
				response.setStatus(HttpServletResponse.SC_BAD_GATEWAY); // set the response status code
				response.setContentType("text/plain"); // set the content type
				
			}

		} else {

			response.setStatus(HttpServletResponse.SC_BAD_GATEWAY); // set the response status code
			response.setContentType("text/plain"); // set the content type
			
		}
	}

}
