package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PressBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.PressModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "PressListCtl", urlPatterns = { "/PressListCtl" })
public class PressListCtl extends BaseCtl {

    @Override
    protected void preload(HttpServletRequest request) {

        PressModel model = new PressModel();
        try {
            List list = model.list();
            request.setAttribute("titleList", list);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
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

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        PressBean bean = (PressBean) populateBean(request);
        PressModel model = new PressModel();

        try {

            List<PressBean> list = model.search(bean, pageNo, pageSize);
            List<PressBean> next = model.search(bean, pageNo + 1, pageSize);

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

        PressBean bean = (PressBean) populateBean(request);
        PressModel model = new PressModel();

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
                ServletUtility.redirect(ORSView.PRESS_CTL, request, response);
                return;

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.PRESS_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.PRESS_LIST_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;
                PressBean deleteBean = new PressBean();

                if (ids != null) {
                    for (String id : ids) {
                        deleteBean.setId(Long.parseLong(id));
                        model.delete(deleteBean);
                    }
                    ServletUtility.setSuccessMessage("Data deleted successfully", request);
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }

            List<PressBean> list = model.search(bean, pageNo, pageSize);
            List<PressBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No Record Found", request);
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
        return ORSView.PRESS_LIST_VIEW;
    }
}