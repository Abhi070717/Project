package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.BroadcastBean;
import in.co.rays.proj4.bean.BroadcastBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.BroadcastModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/BroadcastCtl")
public class BroadcastCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("messageTitle"))) {
			request.setAttribute("messageTitle", PropertyReader.getValue("error.require", "Message Title"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("messageTitle"))) {
			request.setAttribute("messageTitle", "Invalid Message Title");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("broadcastCode"))) {
			request.setAttribute("broadcastCode", PropertyReader.getValue("error.require", "Broadcast Code"));
			pass = false;
		} 

		if (DataValidator.isNull(request.getParameter("messageContent"))) {
			request.setAttribute("messageContent", PropertyReader.getValue("error.require", "Message Content"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("broadcastTime"))) {
			request.setAttribute("broadcastTime", PropertyReader.getValue("error.require", "Broadcast Time"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("broadcastTime"))) {
			request.setAttribute("broadcastTime", "Time should be in HH:mm:ss Format");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("broadcastStatus"))) {
			request.setAttribute("broadcastStatus", PropertyReader.getValue("error.require", "Broadcast Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		BroadcastBean bean = new BroadcastBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setMessageTitle(DataUtility.getString(request.getParameter("messageTitle")));
		bean.setBroadcastCode(DataUtility.getString(request.getParameter("broadcastCode")));
		bean.setMessageContent(DataUtility.getString(request.getParameter("messageContent")));
		bean.setBroadcastTime(DataUtility.getDate(request.getParameter("broadcastTime")));
		bean.setBroadcastStatus(DataUtility.getString(request.getParameter("broadcastStatus")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		BroadcastModel model = new BroadcastModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			BroadcastBean bean = (BroadcastBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Broadcast added successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Broadcast already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.BROADCAST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.BROADCAST_VIEW;
	}

}
