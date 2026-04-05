package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import in.co.rays.proj4.bean.EmployeeBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.model.EmployeeModel;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.util.*;

@WebServlet(name = "EmployeeListCtl", urlPatterns = { "/EmployeeListCtl" })
public class EmployeeListCtl extends BaseCtl {

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        EmployeeBean bean = new EmployeeBean();

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDepartment(DataUtility.getString(request.getParameter("department")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));

        return bean;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        EmployeeBean bean = (EmployeeBean) populateBean(request);
        EmployeeModel model = new EmployeeModel();

        try {
            List list = model.search(bean, pageNo, pageSize);
            List next = model.search(bean, pageNo + 1, pageSize);

            ServletUtility.setList(list, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            ServletUtility.handleException(e, request, response);
        }
    }

    @Override
    protected String getView() {
        return ORSView.EMPLOYEE_LIST_VIEW;
    }
}