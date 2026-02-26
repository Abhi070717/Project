package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.NotificationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.NotificationModel;

public class TestNotificationModel {

	public static void main(String[] args) throws ParseException {

//		testNextPk();
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByCode();
//		testSearch();

	}

	public static void testNextPk() {

		NotificationModel model = new NotificationModel();

		try {
			int i = model.nextPk();
			System.out.println("Next Pk : " + i);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	public static void testAdd() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		NotificationBean bean = new NotificationBean();

		try {
			bean.setCode("10121");
			bean.setMessage("Hello, How are you");
			bean.setSendto("WhatsApp");
			bean.setSenttime(sdf.parse("2026-02-26"));
			bean.setStatus("Readed");
			bean.setCreatedBy("root@gmail.com");
			bean.setModifiedBy("root@gmail.com");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			NotificationModel model = new NotificationModel();

			long pk;
			try {
				pk = model.add(bean);
				System.out.println("Notification Added Successfully, PK = " + pk);
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testUpdate() throws ParseException {

		NotificationBean bean = new NotificationBean();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bean.setId(2);
			bean.setCode("10122");
			bean.setMessage("Hii, How are you");
			bean.setSendto("WhatsApp");
			bean.setSenttime(sdf.parse("2026-02-26"));
			bean.setStatus("Send");
			bean.setCreatedBy("root@gmail.com");
			bean.setModifiedBy("root@gmail.com");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			NotificationModel model = new NotificationModel();

			try {
				model.update(bean);
				System.out.println("Notification Updated Successfully");
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		NotificationBean bean = new NotificationBean();

		try {
			bean.setId(3);

			NotificationModel model = new NotificationModel();

			model.delete(bean);

			System.out.println("Notification Deleted Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		NotificationModel model = new NotificationModel();

		try {
			NotificationBean bean = model.findByPk(2L);

			System.out.println("ID : " + bean.getId());
			System.out.println("Code : " + bean.getCode());
			System.out.println("Message : " + bean.getMessage());
			System.out.println("Send TO : " + bean.getSendto());
			System.out.println("Sent Time : " + bean.getSenttime());
			System.out.println("Status : " + bean.getStatus());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByCode() {

		NotificationModel model = new NotificationModel();

		try {
			NotificationBean bean = model.findByCode("10122");

			System.out.println("ID : " + bean.getId());
			System.out.println("Code : " + bean.getCode());
			System.out.println("Message : " + bean.getMessage());
			System.out.println("Send TO : " + bean.getSendto());
			System.out.println("Sent Time : " + bean.getSenttime());
			System.out.println("Status : " + bean.getStatus());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() {

		NotificationBean bean = new NotificationBean();

		bean.setCode("10121");

		NotificationModel model = new NotificationModel();

		List list = new ArrayList();

		try {
			list = model.search(bean, 0, 0);

			Iterator it = list.iterator();

			while (it.hasNext()) {
				bean = (NotificationBean) it.next();

				System.out.println("ID : " + bean.getId());
				System.out.println("Code : " + bean.getCode());
				System.out.println("Message : " + bean.getMessage());
				System.out.println("Send TO : " + bean.getSendto());
				System.out.println("Sent Time : " + bean.getSenttime());
				System.out.println("Status : " + bean.getStatus());
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
