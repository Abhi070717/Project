package in.co.rays.MarksheetDetailClt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.MarksheetModel.MarksheetModel;
import in.co.rays.bean.MarksheetBean;

@WebServlet("/MarksheetDetailCtl") // mapping
public class MarksheetDetailCtl extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("MarksheetDetailsView.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MarksheetBean bean = new MarksheetBean();
		MarksheetModel model = new MarksheetModel();

		String Name = request.getParameter("Name");
		String RollNo = request.getParameter("RollNo");
		String Physics = request.getParameter("Physics");
		String Chemistry = request.getParameter("Chemistry");
		String Maths = request.getParameter("Maths");
		System.out.println(bean.getName());
		System.out.println(bean.getRollNo());
		System.out.println(bean);
		try {
			bean.setName(Name);
			bean.setRollNo(Integer.parseInt(RollNo));
			bean.setPhysics(Integer.parseInt(Physics));
			bean.setChemistry(Integer.parseInt(Chemistry));
			bean.setMaths(Integer.parseInt(Maths));

			model.add(bean);
			request.setAttribute("msg", "Student Added Successful");
			response.sendRedirect("MarksheetDetailsView.jsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
