package com.chatApp.servelet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chatApp.Dao.UserDaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class GetchatDetails
 */
@WebServlet("/GetchatDetails")
public class GetchatDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetchatDetails() {
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
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		String username = request.getParameter("username");

		if (username != null) {

			List<String> emlist = new UserDaoImpl().getuserdata(username);

			if (!emlist.isEmpty()) {
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(emlist);

				System.out.println(json);

				// Set the content type to JSON
				response.setContentType("application/json");
				response.getWriter().write(json);
			} else {

			}

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
