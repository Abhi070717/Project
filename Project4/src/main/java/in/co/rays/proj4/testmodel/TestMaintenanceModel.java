package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.FeedbackBean;
import in.co.rays.proj4.bean.MaintenanceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.FeedbackModel;
import in.co.rays.proj4.model.MaintenanceModel;

public class TestMaintenanceModel {

	public static void main(String[] args) throws Exception {

//		testAdd();
//		testUpdate();
		testDelete();
//		testFindByPK();
//		testFindByName();
//		testSearch();

	}

	public static void testAdd() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		MaintenanceModel model = new MaintenanceModel();
		MaintenanceBean bean = new MaintenanceBean();

		bean.setRequestName("Water Leakage");
		bean.setIssueType("Plumbing");
		bean.setLocation("Block A");
		bean.setRequestDate(sdf.parse("2012-03-16"));
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		long pk = model.add(bean);

		System.out.println("Record Added ID = " + pk);
	}

	public static void testUpdate() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		MaintenanceModel model = new MaintenanceModel();
		MaintenanceBean bean = new MaintenanceBean();

		bean.setId(1);
		bean.setRequestName("Electric Issue");
		bean.setIssueType("Electrical");
		bean.setLocation("Block B");
		bean.setRequestDate(sdf.parse("2013-03-16"));
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		model.update(bean);

		System.out.println("Record Updated");
	}

	public static void testDelete() throws Exception {

		MaintenanceBean bean = new MaintenanceBean();
		bean.setId(3);

		MaintenanceModel model = new MaintenanceModel();
		try {
			model.delete(bean);
			System.out.println("Record Deleted");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() throws Exception {

		MaintenanceModel model = new MaintenanceModel();

		MaintenanceBean bean = model.findByPk(1);

		if (bean != null) {

			System.out.println("Id : " + bean.getId());
			System.out.println("Request Name : " + bean.getRequestName());
			System.out.println("Issue Type : " + bean.getIssueType());
			System.out.println("Location : " + bean.getLocation());
			System.out.println("Request Date : " + bean.getRequestDate());
			System.out.println("Created By : " + bean.getCreatedBy());
			System.out.println("Modified By : " + bean.getModifiedBy());
			System.out.println("Created Datetime : " + bean.getCreatedDatetime());
			System.out.println("Modified Datetime : " + bean.getModifiedDatetime());

		} else {

			System.out.println("Record Not Found");

		}
	}

	public static void testFindByName() throws Exception {

		MaintenanceModel model = new MaintenanceModel();

		MaintenanceBean bean = model.findByMaintenanceName("Water Leakage");

		if (bean != null) {

			System.out.println("Id : " + bean.getId());
			System.out.println("Request Name : " + bean.getRequestName());
			System.out.println("Issue Type : " + bean.getIssueType());
			System.out.println("Location : " + bean.getLocation());
			System.out.println("Request Date : " + bean.getRequestDate());
			System.out.println("Created By : " + bean.getCreatedBy());
			System.out.println("Modified By : " + bean.getModifiedBy());
			System.out.println("Created Datetime : " + bean.getCreatedDatetime());
			System.out.println("Modified Datetime : " + bean.getModifiedDatetime());

		} else {

			System.out.println("Record Not Found");

		}
	}

	public static void testSearch() throws Exception {

		MaintenanceModel model = new MaintenanceModel();
		MaintenanceBean bean = new MaintenanceBean();

		bean.setRequestName("W");

		List list = model.search(bean, 0, 0);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			MaintenanceBean b = (MaintenanceBean) it.next();

			System.out.println("Id : " + b.getId());
			System.out.println("Request Name : " + b.getRequestName());
			System.out.println("Issue Type : " + b.getIssueType());
			System.out.println("Location : " + b.getLocation());
			System.out.println("Request Date : " + b.getRequestDate());
			System.out.println("Created By : " + b.getCreatedBy());
			System.out.println("Modified By : " + b.getModifiedBy());
			System.out.println("Created Datetime : " + b.getCreatedDatetime());
			System.out.println("Modified Datetime : " + b.getModifiedDatetime());
		}
	}
}
