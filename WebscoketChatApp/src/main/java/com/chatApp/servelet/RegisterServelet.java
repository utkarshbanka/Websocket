package com.chatApp.servelet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.chatApp.Dao.UserDaoImpl;
import com.chatApp.Model.Register;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class RegisterServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServelet() {
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

		 String email = request.getParameter("email");
	        String password = request.getParameter("password");

//		if (email != null && password != null) {
//			Register register = new Register(email, password);
//
//			String message = new UserDaoImpl().Registeruser(register);
//			response.setContentType("text/plain");
//			PrintWriter out = response.getWriter();
//			out.print(message);
//			out.close();
//		} else {
//			response.sendError(500, "Email or Password is not null");
//		}
		if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // set the response status code
            response.setContentType("text/plain"); // set the content type
            PrintWriter out = response.getWriter();
            out.println("Email or Password is empty"); // return the error message
            return;
        }
		else
		{
			Register register = new Register(email, password);
			String message = new UserDaoImpl().Registeruser(register);
			
			response.setStatus(HttpServletResponse.SC_OK); // set the response status code
	        response.setContentType("text/plain"); // set the content type
	        PrintWriter out = response.getWriter();
	        out.println(message); // return the success message
		}
		
		
		
		

	}

}
