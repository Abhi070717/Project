package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.EmployeeBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EmployeeModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "EmployeeCtl", urlPatterns = { "/EmployeeCtl" })
public class EmployeeCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Employee Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("department"))) {
            request.setAttribute("department", PropertyReader.getValue("error.require", "Department"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("salary"))) {
            request.setAttribute("salary", PropertyReader.getValue("error.require", "Salary"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        EmployeeBean bean = new EmployeeBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDepartment(DataUtility.getString(request.getParameter("department")));
        bean.setSalary(DataUtility.getDouble(request.getParameter("salary")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));

        populateDTO(bean, request);

        return bean;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        EmployeeModel model = new EmployeeModel();

        if (id > 0) {
            try {
                EmployeeBean bean = model.findByPk(id);
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
        EmployeeModel model = new EmployeeModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            EmployeeBean bean = (EmployeeBean) populateBean(request);

            try {
                model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Employee Added Successfully", request);

           
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            EmployeeBean bean = (EmployeeBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Employee Updated Successfully", request);

            
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.EMPLOYEE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.EMPLOYEE_VIEW;
    }
}