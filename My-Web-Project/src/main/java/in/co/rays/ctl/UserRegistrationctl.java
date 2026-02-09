package in.co.rays.ctl;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.UserBean;
import in.co.rays.model.UserModel;

@WebServlet("/UserRegistrationCtl") // wild Card mapping of webservlet	
public class UserRegistrationCtl extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("UserRegistrationView.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		UserBean bean = new UserBean();
		UserModel model = new UserModel();

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String DOB = request.getParameter("DOB");
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		try {
			bean.setFirstName(firstName);
			bean.setLastName(lastName);
			bean.setDob(sdf.parse(DOB));
			bean.setLogin(login);
			bean.setPassword(password);

			model.add(bean);
			request.setAttribute("msg", "User Registration Successful");

		} catch (Exception e) {
		}
		RequestDispatcher rd = request.getRequestDispatcher("UserRegistrationView.jsp");
		rd.forward(request, response);
	}

}
