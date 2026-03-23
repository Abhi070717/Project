package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ParkingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ParkingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ParkingCtl", urlPatterns = { "/ParkingCtl" })
public class ParkingCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("vehicle"))) {
			request.setAttribute("vehicle", PropertyReader.getValue("error.require", "Vehicle Number"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("slot"))) {
			request.setAttribute("slot", PropertyReader.getValue("error.require", "Slot Number"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("entry"))) {
			request.setAttribute("entry", PropertyReader.getValue("error.require", "Entry Time"));
			pass = false;
		} else if (DataValidator.isDate(request.getParameter("entry"))) {
			request.setAttribute("entry", "Invalid Time Format");
		}
		if (DataValidator.isNull(request.getParameter("exit"))) {
			request.setAttribute("exit", PropertyReader.getValue("error.require", "Exit Time"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected void preload(HttpServletRequest request) {

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("1st slot", "1st slot");
		map.put("2st slot", "2nd slot");
		map.put("3st slot", "3rd slot");
		map.put("4st slot", "4th slot");
		map.put("5st slot", "5th slot");
		map.put("6st slot", "6th slot");
		map.put("7st slot", "7th slot");
		map.put("8st slot", "8th slot");
		map.put("9st slot", "9th slot");
		map.put("10st slot", "10th slot");

		request.setAttribute("map", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		ParkingBean bean = new ParkingBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setVehicleNumber(DataUtility.getString(request.getParameter("vehicle")));
		bean.setSlotNumber(DataUtility.getString(request.getParameter("slot")));
		bean.setEntryTime(DataUtility.getDate(request.getParameter("entry")));
		bean.setExitTime(DataUtility.getDate(request.getParameter("exit")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		ParkingModel model = new ParkingModel();

		if (id > 0) {
			try {
				ParkingBean bean = model.findByPK(id);
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

		ParkingModel model = new ParkingModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			ParkingBean bean = (ParkingBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
					ServletUtility.setSuccessMessage("Parking updated successfully", request);
				} else {
					model.add(bean);
					ServletUtility.setSuccessMessage("Parking added successfully", request);
				}

				ServletUtility.setBean(bean, request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Parking Name already exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			ParkingBean bean = (ParkingBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Vehicle Number Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PARKING_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PARKING_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.PARKING_VIEW;
	}
}