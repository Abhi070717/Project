package in.co.rays.MarksheetDetailClt;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import in.co.rays.MarksheetModel.MarksheetModel;
import in.co.rays.bean.MarksheetBean;

@WebServlet("/MarksheetCtl")
public class MarksheetCtl extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		bean.setName(Name);
		bean.setRollNo(Integer.parseInt(RollNo));
		bean.setPhysics(Integer.parseInt(Physics));
		bean.setChemistry(Integer.parseInt(Chemistry));
		bean.setMaths(Integer.parseInt(Maths));

		try {
			model.add(bean);
			request.setAttribute("msg", "Student Added Successfully");
		} catch (Exception e) {
		}
		RequestDispatcher rd = request.getRequestDispatcher("MarksheetView.jsp");
		rd.forward(request, response);
	}
}
