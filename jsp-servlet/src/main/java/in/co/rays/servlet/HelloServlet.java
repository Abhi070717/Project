package in.co.rays.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HelloServlet")						//Wild Card Mapping
public class HelloServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Now in doGet Method...!");
		response.sendRedirect("UserForm.jsp");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Now in doPost Method...!");
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String DOB = request.getParameter("DOB");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		System.out.println(firstName + "\n" + lastName + "\n" + DOB + "\n" + login + "\n" + password);

	}
	

}
