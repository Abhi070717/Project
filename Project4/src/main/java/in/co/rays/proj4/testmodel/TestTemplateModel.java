package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.TemplateBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TemplateModel;

public class TestTemplateModel {

	public static void main(String[] args) throws ParseException {

//		testNextPk();
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByName();
		testSearch();
	}

	public static void testNextPk() {

		TemplateModel model = new TemplateModel();

		try {
			long pk = model.nextPk();
			System.out.println("Next PK : " + pk);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public static void testAdd() throws ParseException {

		TemplateBean bean = new TemplateBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			bean.setTemplateName("Invoice Template");
			bean.setFormat("PDF");
			bean.setCreatedDate(sdf.parse("2026-03-25"));
			bean.setCreatedBy("admin@gmail.com");
			bean.setModifiedBy("admin@gmail.com");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			TemplateModel model = new TemplateModel();

			long pk;
			try {
				pk = model.add(bean);
				System.out.println("Template Added Successfully, PK = " + pk);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws ParseException {

		TemplateBean bean = new TemplateBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bean.setId(2);
			bean.setTemplateName("Updated Template");
			bean.setFormat("DOC");
			bean.setCreatedDate(sdf.parse("2026-03-25"));
			bean.setCreatedBy("admin@gmail.com");
			bean.setModifiedBy("admin@gmail.com");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			TemplateModel model = new TemplateModel();

			try {
				model.update(bean);
				System.out.println("Template Updated Successfully");
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		TemplateBean bean = new TemplateBean();

		try {
			bean.setId(3);

			TemplateModel model = new TemplateModel();

			model.delete(bean);
			System.out.println("Template Deleted Successfully");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		TemplateModel model = new TemplateModel();

		try {
			TemplateBean bean = model.findByPK(1);

			System.out.println("ID : " + bean.getId());
			System.out.println("Name : " + bean.getTemplateName());
			System.out.println("Format : " + bean.getFormat());
			System.out.println("CreatedDate : " + bean.getCreatedDate());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByName() {

		TemplateModel model = new TemplateModel();

		try {
			TemplateBean bean = model.findByName("Invoice Template");

			System.out.println("ID : " + bean.getId());
			System.out.println("Name : " + bean.getTemplateName());
			System.out.println("Format : " + bean.getFormat());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() throws ParseException {

		TemplateBean bean = new TemplateBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		bean.setTemplateName("Invoice");
//		bean.setFormat("pdf");
//		bean.setCreatedDate(sdf.parse("2026-03-25"));

		TemplateModel model = new TemplateModel();

		List<TemplateBean> list = new ArrayList<TemplateBean>();

		try {
			list = model.search(bean, 0, 0);

			Iterator<TemplateBean> it = list.iterator();

			while (it.hasNext()) {
				bean = it.next();

				System.out.println("ID : " + bean.getId());
				System.out.println("Name : " + bean.getTemplateName());
				System.out.println("Format : " + bean.getFormat());
				System.out.println("CreatedDate : " + bean.getCreatedDate());
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