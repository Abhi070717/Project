package in.co.rays.proj4.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import in.co.rays.proj4.bean.ProductBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.exception.*;
import in.co.rays.proj4.model.ProductModel;
import in.co.rays.proj4.util.*;

@WebServlet("/ProductCtl")
public class ProductCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("productName"))) {
			request.setAttribute("productName", "Product Name required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("category"))) {
			request.setAttribute("category", "Category required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", "Price required");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("stock"))) {
			request.setAttribute("stock", "Stock required");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		ProductBean bean = new ProductBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setProductName(DataUtility.getString(request.getParameter("productName")));
		bean.setCategory(DataUtility.getString(request.getParameter("category")));
		bean.setPrice(Double.parseDouble(request.getParameter("price")));
		bean.setStock(DataUtility.getInt(request.getParameter("stock")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		ProductModel model = new ProductModel();

		if (id > 0) {
			try {
				ProductBean bean = model.findByPk(id);
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

		String op = request.getParameter("operation");

		ProductModel model = new ProductModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			ProductBean bean = (ProductBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Product Added Successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Product already exists", request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			ProductBean bean = (ProductBean) populateBean(request);

			try {
				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Product Updated Successfully", request);

			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PRODUCT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PRODUCT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.PRODUCT_VIEW;
	}
}