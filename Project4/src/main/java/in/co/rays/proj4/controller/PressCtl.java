package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PressBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.PressModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "PressCtl", urlPatterns = { "/PressCtl" })
public class PressCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "Title"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("title"))) {
			request.setAttribute("title", "Invalid Title");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("releaseDate"))) {
			request.setAttribute("releaseDate", PropertyReader.getValue("error.require", "Release Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("releaseDate"))) {
			request.setAttribute("releaseDate", PropertyReader.getValue("error.date", "Release Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("author"))) {
			request.setAttribute("author", PropertyReader.getValue("error.require", "Author"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("author"))) {
			request.setAttribute("author", "Invalid Author");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		PressBean bean = new PressBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setTitle(DataUtility.getString(request.getParameter("title")));
		bean.setReleaseDate(DataUtility.getDate(request.getParameter("releaseDate")));
		bean.setAuthor(DataUtility.getString(request.getParameter("author")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		PressModel model = new PressModel();

		if (id > 0) {
			try {
				PressBean bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		PressModel model = new PressModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			PressBean bean = (PressBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is Successfully Saved", request);

			}catch (DuplicateRecordException e) {
			    ServletUtility.setBean(bean, request);
			    ServletUtility.setErrorMessage("Press Release already exists", request);
			   
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			PressBean bean = (PressBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully updated", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Press Release already exists", request);
				

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PRESS_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PRESS_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.PRESS_VIEW;
	}
}