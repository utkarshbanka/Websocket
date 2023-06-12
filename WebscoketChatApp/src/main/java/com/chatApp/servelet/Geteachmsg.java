package com.chatApp.servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.chatApp.Dao.UserDaoImpl;

/**
 * Servlet implementation class Geteachmsg
 */
@WebServlet("/Geteachmsg")
public class Geteachmsg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Geteachmsg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
		String from = request.getParameter("from");
		String to = request.getParameter("to");

		
		if(from!=null && to !=null)
		{
		     HashMap<List<String>, List<String>> mess =  new UserDaoImpl().getMessages(from, to);
			if(mess !=null)
			{
				ObjectMapper objectMapper = new ObjectMapper();
		        String json = objectMapper.writeValueAsString(mess);

		        // Set the content type to JSON
		        response.setContentType("application/json");
		        response.getWriter().write(json);
			}
			else
			{
				response.setStatus(HttpServletResponse.SC_BAD_GATEWAY); // set the response status code
		        response.setContentType("text/plain"); // set the content type
		        PrintWriter out = response.getWriter();
			}
			
		}
		else
		{
		
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
