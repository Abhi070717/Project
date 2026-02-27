package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.DepartmentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.DepartmentModel;

public class TestDepartmentModel {

	public static void main(String[] args) {

//		testNextPk();
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByCode();
		testSearch();
	}

	public static void testNextPk() {

		DepartmentModel model = new DepartmentModel();

		try {
			int i = model.nextPk();
			System.out.println("Next Pk : " + i);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	public static void testAdd() {

		DepartmentBean bean = new DepartmentBean();

		try {
			bean.setCode("DA102");
			bean.setName("Data analytics");
			bean.setHead("Vishvas Pawar");
			bean.setLocation("Indore");
			bean.setCreatedBy("root@gmail.com");
			bean.setModifiedBy("root@gmail.com");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			DepartmentModel model = new DepartmentModel();

			 long pk = model.add(bean);
			System.out.println("Department Added Successfully, PK = " + pk);
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		DepartmentBean bean = new DepartmentBean();

		try {
			bean.setId(1);
			bean.setCode("DS101");
			bean.setName("Data Science");
			bean.setHead("Vishvas Pawar");
			bean.setLocation("Indore");
			bean.setCreatedBy("root@gmail.com");
			bean.setModifiedBy("root@gmail.com");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			DepartmentModel model = new DepartmentModel();

			try {
				model.update(bean);
				System.out.println("Department Updated Successfully");
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		DepartmentBean bean = new DepartmentBean();

		try {
			bean.setId(3);

			DepartmentModel model = new DepartmentModel();

			model.delete(bean);

			System.out.println("Department Deleted Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		DepartmentModel model = new DepartmentModel();

		try {
			DepartmentBean bean = model.findByPk(2L);

			System.out.println("ID : " + bean.getId());
			System.out.println("Code : " + bean.getCode());
			System.out.println("Name : " + bean.getName());
			System.out.println("Head : " + bean.getHead());
			System.out.println("Location : " + bean.getLocation());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByCode() {

		DepartmentModel model = new DepartmentModel();

		try {
			DepartmentBean bean = model.findByCode("DS101");

			System.out.println("ID : " + bean.getId());
			System.out.println("Code : " + bean.getCode());
			System.out.println("Name : " + bean.getName());
			System.out.println("Head : " + bean.getHead());
			System.out.println("Location : " + bean.getLocation());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() {

		DepartmentBean bean = new DepartmentBean();

		bean.setName("Data Science");

		DepartmentModel model = new DepartmentModel();

		List list = new ArrayList();

		try {
			list = model.search(bean, 0, 0);

			Iterator it = list.iterator();

			while (it.hasNext()) {
				bean = (DepartmentBean) it.next();

				System.out.println("ID : " + bean.getId());
				System.out.println("Code : " + bean.getCode());
				System.out.println("Name : " + bean.getName());
				System.out.println("Head : " + bean.getHead());
				System.out.println("Location : " + bean.getLocation());
				System.out.println("CreatedBy : " + bean.getCreatedBy());
				System.out.println("ModifiedBy : " + bean.getModifiedBy());
				System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
				System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}