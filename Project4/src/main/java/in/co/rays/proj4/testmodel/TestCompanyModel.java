package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.CompanyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.model.CompanyModel;

public class TestCompanyModel {

	public static void main(String[] args) {

//		testNextPK();
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPK();
//		testFindByCode();
//		testSearch();
	}

	private static void testNextPK() {

		CompanyModel model = new CompanyModel();

		try {

			long pk = model.nextPK();
			System.out.println("Next PK is : " + pk);

		} catch (Exception e) {

			System.out.println("Error in getting Next PK : " + e.getMessage());
		}
	}

	private static void testAdd() {

		CompanyModel model = new CompanyModel();
		CompanyBean bean = new CompanyBean();

		bean.setCode("TCS001");
		bean.setName("Tata Consultancy Services");
		bean.setHead("Ratan Tata");
		bean.setLocation("Mumbai");
		bean.setCreatedBy("root@gmail.com");
		bean.setModifiedBy("root@gmail.com");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		try {
			long pk = model.add(bean);
			System.out.println("Company Added Successfully with PK = " + pk);

		} catch (DuplicateRecordException e) {
			System.out.println("Duplicate Record: " + e.getMessage());

		} catch (ApplicationException e) {
			System.out.println("Application Error: " + e.getMessage());
		}
	}

	private static void testUpdate() {

		CompanyModel model = new CompanyModel();

		try {
			CompanyBean bean = model.findByPK(1);

			if (bean != null) {

				bean.setName("TCS Updated");
				bean.setHead("New CEO");
				bean.setCreatedBy("root@gmail.com");
				bean.setModifiedBy("root@gmail.com");
				bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
				bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

				model.update(bean);
				System.out.println("Company Updated Successfully");

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testDelete() {

		CompanyModel model = new CompanyModel();

		try {
			model.delete(2);
			System.out.println("Company Deleted Successfully");

		} catch (RecordNotFoundException e) {
			System.out.println("Record Not Found: " + e.getMessage());

		} catch (ApplicationException e) {
			System.out.println("Application Error: " + e.getMessage());
		}
	}

	private static void testFindByPK() {

		CompanyModel model = new CompanyModel();

		try {
			CompanyBean bean = model.findByPK(1);

			if (bean != null) {
				System.out.println("ID: " + bean.getId());
				System.out.println("Code: " + bean.getCode());
				System.out.println("Name: " + bean.getName());
				System.out.println("Head: " + bean.getHead());
				System.out.println("Location: " + bean.getLocation());
				System.out.println("CreatedBy : " + bean.getCreatedBy());
				System.out.println("ModifiedBy : " + bean.getModifiedBy());
				System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
				System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testFindByCode() {

		CompanyModel model = new CompanyModel();

		try {
			CompanyBean bean = model.findByCode("TCS001");

			if (bean != null) {
				System.out.println("ID: " + bean.getId());
				System.out.println("Code: " + bean.getCode());
				System.out.println("Name: " + bean.getName());
				System.out.println("Head: " + bean.getHead());
				System.out.println("Location: " + bean.getLocation());
				System.out.println("CreatedBy : " + bean.getCreatedBy());
				System.out.println("ModifiedBy : " + bean.getModifiedBy());
				System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
				System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
			} else {
				System.out.println("Record not found");
			}

		} catch (ApplicationException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testSearch() {

		CompanyModel model = new CompanyModel();
		CompanyBean bean = new CompanyBean();

		bean.setName("TCS");

		try {
			List<CompanyBean> list = model.search(bean);

			for (CompanyBean cb : list) {

				System.out.println("ID: " + cb.getId());
				System.out.println("Code: " + cb.getCode());
				System.out.println("Name: " + cb.getName());
				System.out.println("Head: " + cb.getHead());
				System.out.println("Location: " + cb.getLocation());
				System.out.println("CreatedBy : " + bean.getCreatedBy());
				System.out.println("ModifiedBy : " + bean.getModifiedBy());
				System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
				System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			System.out.println(e.getMessage());
		}
	}
}
