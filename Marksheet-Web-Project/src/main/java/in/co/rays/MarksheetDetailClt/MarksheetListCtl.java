package in.co.rays.MarksheetDetailClt;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.MarksheetModel.MarksheetModel;
import in.co.rays.bean.MarksheetBean;

@WebServlet("/MarksheetListCtl")
public class MarksheetListCtl extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MarksheetBean bean = new MarksheetBean();
		MarksheetModel model = new MarksheetModel();

		try {
			List list = model.search(bean);
			request.setAttribute("list", list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("MarksheetListView.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MarksheetBean bean = new MarksheetBean();
		MarksheetModel model = new MarksheetModel();

		String op = request.getParameter("operation");
		String[] ids = request.getParameterValues("ids");
		if (op.equals("Delete")) {
			if (ids != null && ids.length > 0) {
				for (String id : ids) {
					bean.setId(Integer.parseInt(id));
					try {
						model.delete(bean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		try {

			List list = model.search(bean);
			request.setAttribute("list", list);

		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("MarksheetListView.jsp");
		rd.forward(request, response);
	}
}
