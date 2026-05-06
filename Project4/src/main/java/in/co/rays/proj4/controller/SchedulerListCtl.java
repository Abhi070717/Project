package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SchedulerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.SchedulerModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "SchedulerListCtl", urlPatterns = { "/SchedulerListCtl" })
public class SchedulerListCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {
        SchedulerModel model = new SchedulerModel();
        try {
            List namelist = model.list();
            request.setAttribute("nameList", namelist);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        SchedulerBean bean = new SchedulerBean();

        bean.setJobName(DataUtility.getString(request.getParameter("name")));
        bean.setJobCode(DataUtility.getString(request.getParameter("code")));

        return bean;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        SchedulerBean bean = (SchedulerBean) populateBean(request);
        SchedulerModel model = new SchedulerModel();

        try {

            List<SchedulerBean> list = model.search(bean, pageNo, pageSize);
            List<SchedulerBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
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

        SchedulerBean bean = (SchedulerBean) populateBean(request);
        SchedulerModel model = new SchedulerModel();

        String op = request.getParameter("operation");
        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op)) {
                pageNo = 1;

            } else if (OP_NEXT.equalsIgnoreCase(op)) {
                pageNo++;

            } else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
                pageNo--;

            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SCHEDULER_CTL, request, response);
                return;

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.SCHEDULER_LIST_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                if (ids != null && ids.length > 0) {

                    SchedulerBean deleteBean = new SchedulerBean();

                    for (String id : ids) {
                        deleteBean.setId(Integer.parseInt(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage("Data deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }

            List<SchedulerBean> list = model.search(bean, pageNo, pageSize);
            List<SchedulerBean> next = model.search(bean, pageNo + 1, pageSize);

            request.setAttribute("nextListSize", next.size());
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setBean(bean, request);
            ServletUtility.setPageSize(pageSize, request);

            ServletUtility.forward(getView(), request, response);

        } catch (Exception e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
        }
    }

    @Override
    protected String getView() {
        return ORSView.SCHEDULER_LIST_VIEW;
    }
}