package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CertificateBean;
import in.co.rays.proj4.bean.DepartmentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.model.CertificateModel;
import in.co.rays.proj4.model.DepartmentModel;

public class TestCertificateModel {

	public static void main(String[] args) throws ParseException {

//		testNextPk();
//		testAdd();
//		testUpdate();
//		testDelete();
		testFindByPk();
		testFindByCode();

	}

	public static void testNextPk() {

		CertificateModel model = new CertificateModel();

		try {
			long pk = model.nextPk();
			System.out.println("Next Pk : " + pk);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	public static void testAdd() throws ParseException {

		CertificateBean bean = new CertificateBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setCertificateCode("12122026");
		bean.setStudentName("Yuvraj Jain");
		bean.setCourseName("B.Tech");
		bean.setIssueDate(sdf.parse("2025-08-19"));
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		CertificateModel model = new CertificateModel();

		try {
			long pk = model.add(bean);
			System.out.println("Certificate Added Successfully, Pk = " + pk);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws ParseException {

		CertificateBean bean = new CertificateBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setId(2);
		bean.setCertificateCode("12122026");
		bean.setStudentName("Yuvraj");
		bean.setCourseName("B.Tech");
		bean.setIssueDate(sdf.parse("2025-08-19"));
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		CertificateModel model = new CertificateModel();

		try {
			model.update(bean);
			System.out.println("Certificate Updated Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws ParseException {

		CertificateBean bean = new CertificateBean();

		bean.setId(3);

		CertificateModel model = new CertificateModel();

		try {
			model.delete(bean);
			System.out.println("Certificate Delete Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		CertificateModel model = new CertificateModel();

		try {
			CertificateBean bean = model.findByPK(1L);
			System.out.println("ID : " + bean.getId());
			System.out.println("Certificate_Code : " + bean.getCertificateCode());
			System.out.println("Student_Name" + bean.getStudentName());
			System.out.println("Course_Name" + bean.getCourseName());
			System.out.println("Issue_Date" + bean.getIssueDate());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByCode() {

		CertificateModel model = new CertificateModel();

		try {
			CertificateBean bean = model.findByCode("12122026");
			System.out.println("ID : " + bean.getId());
			System.out.println("Certificate_Code : " + bean.getCertificateCode());
			System.out.println("Student_Name" + bean.getStudentName());
			System.out.println("Course_Name" + bean.getCourseName());
			System.out.println("Issue_Date" + bean.getIssueDate());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindBysearch() {

		CertificateBean bean = new CertificateBean();
		bean.setStudentName("ss");

		CertificateModel model = new CertificateModel();

		List list = new ArrayList();

		try {
			list = model.Search(bean, 0, 0);

			Iterator it = list.iterator();

			while (it.hasNext()) {
				bean = (CertificateBean) it.next();
				System.out.println("ID : " + bean.getId());
				System.out.println("Certificate_Code : " + bean.getCertificateCode());
				System.out.println("Student_Name" + bean.getStudentName());
				System.out.println("Course_Name" + bean.getCourseName());
				System.out.println("Issue_Date" + bean.getIssueDate());
				System.out.println("CreatedBy : " + bean.getCreatedBy());
				System.out.println("ModifiedBy : " + bean.getModifiedBy());
				System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
				System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
