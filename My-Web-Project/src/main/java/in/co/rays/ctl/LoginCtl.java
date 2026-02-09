package in.co.rays.ctl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.bean.UserBean;
import in.co.rays.model.UserModel;

@WebServlet("/LoginCtl")
public class LoginCtl extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String op = request.getParameter("operation");

		if (op != null) {
			HttpSession session = request.getSession();
			session.invalidate();
			
		}
		response.sendRedirect("LoginView.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserBean bean = new UserBean();
		UserModel model = new UserModel();

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();

		try {
			bean = model.authenticate(login, password);
			if (bean != null) {
				session.setAttribute("user", bean);
				response.sendRedirect("WelcomeCtl");
			} else {
				request.setAttribute("errormsg", "Invalid login or password!");
				RequestDispatcher rd = request.getRequestDispatcher("LoginView.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
