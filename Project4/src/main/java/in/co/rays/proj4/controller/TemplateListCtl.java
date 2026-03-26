package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RecipeBean;
import in.co.rays.proj4.bean.TemplateBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RecipeModel;
import in.co.rays.proj4.model.TemplateModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "TemplateListCtl", urlPatterns = { "/TemplateListCtl" })
public class TemplateListCtl extends BaseCtl {


	@Override
	protected void preload(HttpServletRequest request) {
		TemplateModel Model = new TemplateModel();
		try {
			List<TemplateBean> list = Model.list();
			request.setAttribute("nameList", list);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TemplateBean bean = new TemplateBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setTemplateName(DataUtility.getString(request.getParameter("name")));
		bean.setFormat(DataUtility.getString(request.getParameter("format")));
		bean.setCreatedDate(DataUtility.getDate(request.getParameter("createdDate")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		TemplateBean bean = (TemplateBean) populateBean(request);
		TemplateModel model = new TemplateModel();

		try {

			List<TemplateBean> list = model.search(bean, pageNo, pageSize);
			List<TemplateBean> next = model.search(bean, pageNo + 1, pageSize);

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

		TemplateBean bean = (TemplateBean) populateBean(request);
		TemplateModel model = new TemplateModel();

		String op = request.getParameter("operation");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;

			} else if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;

			} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
				pageNo--;

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TEMPLATE_CTL, request, response);
				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TEMPLATE_LIST_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				String[] ids = request.getParameterValues("ids");
				TemplateBean deletebean = new TemplateBean();

				if (ids != null) {
					for (String id : ids) {
						model.delete(deletebean);
					}
					ServletUtility.setSuccessMessage("Template deleted successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}

			List<TemplateBean> list = model.search(bean, pageNo, pageSize);
			List<TemplateBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found ", request);
			}

			request.setAttribute("nextListSize", next.size());
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
	}

	@Override
	protected String getView() {
		return ORSView.TEMPLATE_LIST_VIEW;
	}
}