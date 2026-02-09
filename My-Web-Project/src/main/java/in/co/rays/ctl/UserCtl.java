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

@WebServlet("/UserCtl.do")
public class UserCtl extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserModel model = new UserModel();
		UserBean bean = new UserBean();

		String id = request.getParameter("id");

		if (id != null) {
			try {
				bean = model.findByPk(Integer.parseInt(id));
				request.setAttribute("bean", bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		RequestDispatcher rd = request.getRequestDispatcher("UserView.jsp");
		rd.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = request.getParameter("operation");

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

			if (op.equals("Update")) {
				bean.setId(Integer.parseInt(request.getParameter("id")));
				model.update(bean);
				request.setAttribute("msg", "User updated successful");
//				request.setAttribute("bean", bean);
			} else {
				model.add(bean);
				request.setAttribute("msg", "User Added Successful");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("UserView.jsp");
		rd.forward(request, response);
	}
}
