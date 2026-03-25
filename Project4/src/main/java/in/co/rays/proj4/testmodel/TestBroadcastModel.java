package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.BroadcastBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.BroadcastModel;

public class TestBroadcastModel {

	public static void main(String[] args) {

//		testNextPk();
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByCode();
//		testSearch();
	}

	public static void testNextPk() {

		BroadcastModel model = new BroadcastModel();

		try {
			long pk = model.nextPk();
			System.out.println("Next PK : " + pk);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public static void testAdd() {

		BroadcastBean bean = new BroadcastBean();

		try {
			bean.setBroadcastCode("BC101");
			bean.setMessageTitle("System Update");
			bean.setMessageContent("System will be down tonight");
			bean.setBroadcastTime(new Date());
			bean.setBroadcastStatus("Active");
			bean.setCreatedBy("admin@gmail.com");
			bean.setModifiedBy("admin@gmail.com");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			BroadcastModel model = new BroadcastModel();

			long pk = model.add(bean);
			System.out.println("Broadcast Added Successfully, PK = " + pk);

		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		BroadcastBean bean = new BroadcastBean();

		try {
			bean.setId(1);
			bean.setBroadcastCode("BC101");
			bean.setMessageTitle("Updated Title");
			bean.setMessageContent("Updated Message");
			bean.setBroadcastTime(new Date());
			bean.setBroadcastStatus("Inactive");
			bean.setCreatedBy("admin@gmail.com");
			bean.setModifiedBy("admin@gmail.com");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			BroadcastModel model = new BroadcastModel();

			model.update(bean);
			System.out.println("Broadcast Updated Successfully");

		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		BroadcastBean bean = new BroadcastBean();

		try {
			bean.setId(1);

			BroadcastModel model = new BroadcastModel();

			model.delete(bean);
			System.out.println("Broadcast Deleted Successfully");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		BroadcastModel model = new BroadcastModel();

		try {
			BroadcastBean bean = model.findByPK(1);

			System.out.println("ID : " + bean.getId());
			System.out.println("Code : " + bean.getBroadcastCode());
			System.out.println("Title : " + bean.getMessageTitle());
			System.out.println("Content : " + bean.getMessageContent());
			System.out.println("Time : " + bean.getBroadcastTime());
			System.out.println("Status : " + bean.getBroadcastStatus());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByCode() {

		BroadcastModel model = new BroadcastModel();

		try {
			BroadcastBean bean = model.findByCode("BC101");

			System.out.println("ID : " + bean.getId());
			System.out.println("Code : " + bean.getBroadcastCode());
			System.out.println("Title : " + bean.getMessageTitle());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {

		BroadcastBean bean = new BroadcastBean();

		bean.setBroadcastCode("BC");

		BroadcastModel model = new BroadcastModel();

		List<BroadcastBean> list = new ArrayList<BroadcastBean>();

		try {
			list = model.Search(bean, 0, 0);

			Iterator<BroadcastBean> it = list.iterator();

			while (it.hasNext()) {
				bean = it.next();

				System.out.println("ID : " + bean.getId());
				System.out.println("Code : " + bean.getBroadcastCode());
				System.out.println("Title : " + bean.getMessageTitle());
				System.out.println("Content : " + bean.getMessageContent());
				System.out.println("Time : " + bean.getBroadcastTime());
				System.out.println("Status : " + bean.getBroadcastStatus());
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