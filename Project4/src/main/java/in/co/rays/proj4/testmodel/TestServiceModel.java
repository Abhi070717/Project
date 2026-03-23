package in.co.rays.proj4.testmodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.ServiceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ServiceModel;

public class TestServiceModel {

	public static void main(String[] args) throws ParseException {

//		testNextPk();
//		testAdd();
//		testUpdate();
//		testdelete();
//		testFindByPk();
//		testFindByServiceCode();
		testSearch();
	}

	public static void testNextPk() {

		ServiceModel model = new ServiceModel();

		try {
			long pk = model.nextPk();
			System.out.println("Next Pk : " + pk);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	public static void testAdd() throws ParseException {

		ServiceBean bean = new ServiceBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setServiceCode("vege1212");
		bean.setCarId(2311);
		bean.setServiceDate(sdf.parse("2026-02-18"));
		bean.setServiceStatus("Pending State");

		ServiceModel model = new ServiceModel();

		try {
			long pk;
			try {
				pk = model.add(bean);
				System.out.println("Service Added Successfully, Pk = " + pk);
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws ParseException {

		ServiceBean bean = new ServiceBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setId(2);
		bean.setServiceCode("vege1213");
		bean.setCarId(2312);
		bean.setServiceDate(sdf.parse("2026-02-19"));
		bean.setServiceStatus("Pending State");

		ServiceModel model = new ServiceModel();

		try {
			try {
				model.update(bean);
				System.out.println("Service Updated Successfully");
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testdelete() throws ParseException {

		ServiceBean bean = new ServiceBean();

		bean.setId(3);

		ServiceModel model = new ServiceModel();

		try {
			model.delete(bean);
			System.out.println("Service Delete Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		ServiceModel model = new ServiceModel();

		try {
			ServiceBean bean = model.findByPk(2L);

			System.out.println("ID : " + bean.getId());
			System.out.println("Service Code : " + bean.getServiceCode());
			System.out.println("Car Id : " + bean.getCarId());
			System.out.println("Service Date : " + bean.getServiceDate());
			System.out.println("Service Status : " + bean.getServiceStatus());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByServiceCode() {

		ServiceModel model = new ServiceModel();

		try {
			ServiceBean bean = model.findByServiceCode("vege1212");

			System.out.println("ID : " + bean.getId());
			System.out.println("Service Code : " + bean.getServiceCode());
			System.out.println("Car Id : " + bean.getCarId());
			System.out.println("Service Date : " + bean.getServiceDate());
			System.out.println("Service Status : " + bean.getServiceStatus());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() throws ParseException {

		ServiceBean bean = new ServiceBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//		bean.setServiceCode("vege1212");
		bean.setServiceDate(sdf.parse("2026-02-18"));

		ServiceModel model = new ServiceModel();

		List<ServiceBean> list = new ArrayList<ServiceBean>();

		try {
			list = model.Search(bean, 0, 0);

			Iterator<ServiceBean> it = list.iterator();

			while (it.hasNext()) {
				bean = (ServiceBean) it.next();

				System.out.println("ID : " + bean.getId());
				System.out.println("Service Code : " + bean.getServiceCode());
				System.out.println("Car Id : " + bean.getCarId());
				System.out.println("Service Date : " + bean.getServiceDate());
				System.out.println("Service Status : " + bean.getServiceStatus());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
