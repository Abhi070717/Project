package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.List;

import in.co.rays.proj4.bean.OtpBean;
import in.co.rays.proj4.model.OtpModel;

public class TestOtpModel {

	public static OtpModel model = new OtpModel();

	public static void main(String[] args) throws Exception {
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPK();
//		testFindByOtpCode();
		testSearch();
	}

	// ------------------- ADD -------------------
	public static void testAdd() throws Exception {

		OtpBean bean = new OtpBean();

		bean.setOtpCode("678901");
		bean.setMobileNumber("9999999999");
		bean.setExpiryTime(new java.sql.Date(System.currentTimeMillis()));
		bean.setOtpStatus("ACTIVE");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		long pk = model.add(bean);

		System.out.println("Add success, PK = " + pk);
	}

	// ------------------- UPDATE -------------------
	public static void testUpdate() throws Exception {

		OtpBean bean = new OtpBean();

		bean.setId(2); // existing ID
		bean.setOtpCode("654321");
		bean.setMobileNumber("8888888888");
		bean.setExpiryTime(new java.sql.Date(System.currentTimeMillis()));
		bean.setOtpStatus("USED");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		model.update(bean);

		System.out.println("Update success");
	}

	// ------------------- DELETE -------------------
	public static void testDelete() throws Exception {

		model.delete(3); // existing ID

		System.out.println("Delete success");
	}

	// ------------------- FIND BY PK -------------------
	public static void testFindByPK() throws Exception {

		OtpBean bean = model.findByPK(1);

		if (bean != null) {
			System.out.println("ID: " + bean.getId());
			System.out.println("OTP: " + bean.getOtpCode());
			System.out.println("Mobile: " + bean.getMobileNumber());
			System.out.println("Status: " + bean.getOtpStatus());
		} else {
			System.out.println("Record not found");
		}
	}

	// ------------------- FIND BY OTP CODE -------------------
	public static void testFindByOtpCode() throws Exception {

		OtpBean bean = model.findByOtpCode("123456");

		if (bean != null) {
			System.out.println("Found:");
			System.out.println("ID: " + bean.getId());
			System.out.println("Mobile: " + bean.getMobileNumber());
		} else {
			System.out.println("OTP not found");
		}
	}

	// ------------------- SEARCH -------------------
	public static void testSearch() throws Exception {

		OtpBean bean = new OtpBean();
		bean.setOtpStatus("ACTIVE");

		List<OtpBean> list = model.search(bean, 0, 0);

		for (OtpBean b : list) {
			System.out.println(b.getId() + " | " + b.getOtpCode() + " | " + b.getMobileNumber());
		}
	}
}
