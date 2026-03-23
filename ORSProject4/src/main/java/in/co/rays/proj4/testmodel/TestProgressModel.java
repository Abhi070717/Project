package in.co.rays.proj4.testmodel;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.ProgressBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.ProgressModel;

public class TestProgressModel {

	public static void main(String[] args) throws Exception {

//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
		testSearch();

	}

	public static void testAdd() {

		ProgressBean bean = new ProgressBean();

		bean.setDeveloperName("Rahul Prasad");
		bean.setWork("Login Module");
		bean.setTarget("Complete CRUD");
		bean.setLastWeek("Design");
		bean.setCurrentWeek("Coding");
		bean.setToday(new Date());

		ProgressModel model = new ProgressModel();
		long pk = model.add(bean);

		System.out.println("Data Added with PK = " + pk);
	}

	public static void testUpdate() {

		ProgressBean bean = new ProgressBean();

		bean.setId(2);
		bean.setDeveloperName("Amit Jain");
		bean.setWork("Update Module");
		bean.setTarget("Fix Bugs");
		bean.setLastWeek("Testing");
		bean.setCurrentWeek("Deployment");
		bean.setToday(new Date());

		ProgressModel model = new ProgressModel();
		model.update(bean);

		System.out.println("Data Updated Successfully");
	}

	public static void testDelete() {

		ProgressModel model = new ProgressModel();
		model.delete(3);

		System.out.println("Data Deleted Successfully");
	}

	public static void testFindByPk() {

		ProgressModel model = new ProgressModel();
		ProgressBean bean = model.findbypk(2);

		if (bean != null) {
			System.out.println("ID: " + bean.getId());
			System.out.println("Developer Name: " + bean.getDeveloperName());
			System.out.println("Work: " + bean.getWork());
			System.out.println("Target: " + bean.getTarget());
			System.out.println("Last Week: " + bean.getLastWeek());
			System.out.println("Current Week: " + bean.getCurrentWeek());
			System.out.println("Today: " + bean.getToday());
		}
	}

	public static void testSearch() throws ApplicationException {

		ProgressModel model = new ProgressModel();
		ProgressBean bean = new ProgressBean();

		bean.setDeveloperName("A");

		try {

			List<ProgressBean> list = model.search(bean, 0, 0);

			Iterator<ProgressBean> it = list.iterator();

			while (it.hasNext()) {
				bean = (ProgressBean) it.next();

				System.out.println("ID : " + bean.getId());
				System.out.println("Developer Name : " + bean.getDeveloperName());
				System.out.println("Work : " + bean.getWork());
				System.out.println("Target : " + bean.getTarget());
				System.out.println("Last Week : " + bean.getLastWeek());
				System.out.println("Current Week : " + bean.getCurrentWeek());
				System.out.println("Today : " + bean.getToday());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
