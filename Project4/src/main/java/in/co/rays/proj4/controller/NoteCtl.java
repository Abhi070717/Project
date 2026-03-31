package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.NoteBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.NoteModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "NoteCtl", urlPatterns = { "/NoteCtl" })
public class NoteCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("notePayableId"))) {
			request.setAttribute("notePayableId", PropertyReader.getValue("error.require", "Note Payable ID"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("notePayableId"))) {
			request.setAttribute("notePayableId", "Invalid Note Payable ID");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("totalPrincipal"))) {
			request.setAttribute("totalPrincipal", PropertyReader.getValue("error.require", "Total Principal"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("totalInterestPaid"))) {
			request.setAttribute("totalInterestPaid", PropertyReader.getValue("error.require", "Total Interest Paid"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("totalOutstanding"))) {
			request.setAttribute("totalOutstanding", PropertyReader.getValue("error.require", "Total Outstanding"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		NoteBean bean = new NoteBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setNotePayableId(DataUtility.getLong(request.getParameter("notePayableId")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));
		bean.setTotalPrincipal(DataUtility.getDouble(request.getParameter("totalPrincipal")));
		bean.setTotalInterestPaid(DataUtility.getDouble(request.getParameter("totalInterestPaid")));
		bean.setTotalOutstanding(DataUtility.getDouble(request.getParameter("totalOutstanding")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		NoteModel model = new NoteModel();

		if (id > 0) {
			try {
				NoteBean bean = model.findByPk(id);
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

		NoteModel model = new NoteModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			NoteBean bean = (NoteBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setSuccessMessage("Data is Successfully Updated", request);
				} else {
					long pk = model.add(bean);
					ServletUtility.setSuccessMessage("Data is Successfully Saved", request);
				}

				ServletUtility.setBean(bean, request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Note already exists", request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.NOTE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.NOTE_VIEW;
	}
}