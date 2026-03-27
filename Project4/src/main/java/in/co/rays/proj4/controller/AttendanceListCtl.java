package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.AttendanceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.AttendanceModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "AttendanceListCtl", urlPatterns = { "/AttendanceListCtl" })
public class AttendanceListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		AttendanceModel Model = new AttendanceModel();
		try {
			List<AttendanceBean> codeList = Model.list();
			request.setAttribute("codeList", codeList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		AttendanceBean bean = new AttendanceBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setAttendanceCode(DataUtility.getString(request.getParameter("attendanceCode")));
		bean.setEmployeeName(DataUtility.getString(request.getParameter("name")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		AttendanceBean bean = (AttendanceBean) populateBean(request);
		AttendanceModel model = new AttendanceModel();

		try {

			List<AttendanceBean> list = model.search(bean, pageNo, pageSize);
			List<AttendanceBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		AttendanceBean bean = (AttendanceBean) populateBean(request);
		AttendanceModel model = new AttendanceModel();

		String op = request.getParameter("operation");
		
		System.out.println(op);

		try {

			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;

			} else if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;

			} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
				pageNo--;

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ATTENDANCE_CTL, request, response);
				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ATTENDANCE_LIST_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				String[] ids = request.getParameterValues("ids");
				AttendanceBean deletebean = new AttendanceBean();

				if (ids != null) {
					for (String id : ids) {
						model.delete(deletebean);
					}
					ServletUtility.setSuccessMessage("Attendance deleted successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			System.out.println(bean.getAttendanceCode());

			List<AttendanceBean> list = model.search(bean, pageNo, pageSize);
			List<AttendanceBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found ", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
	}

	@Override
	protected String getView() {
		return ORSView.ATTENDANCE_LIST_VIEW;
	}
}