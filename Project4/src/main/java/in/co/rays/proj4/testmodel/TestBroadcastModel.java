package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.BroadcastBean;
import in.co.rays.proj4.model.BroadcastModel;

public class TestBroadcastModel {
	public static void main(String[] args) throws Exception {
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPK();
//		testFindByCode();
//		testSearch();
	}

	public static void testAdd() throws Exception {

		BroadcastBean bean = new BroadcastBean();

		bean.setBroadcastCode("BC101");
		bean.setMessageTitle("Important Notice");
		bean.setMessageContent("System maintenance tonight");
		bean.setBroadcastTime(new java.util.Date());
		bean.setBroadcastStatus("Active");
		bean.setCreatedBy("root@gmail.com");
		bean.setModifiedBy("root@gmail.com");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		BroadcastModel model = new BroadcastModel();
		long pk = model.add(bean);

		System.out.println("Data Added Successfully ID = " + pk);
	}

	public static void testUpdate() throws Exception {

		BroadcastBean bean = new BroadcastBean();

		bean.setId(2);
		bean.setBroadcastCode("BC102");
		bean.setMessageTitle("Updated Notice");
		bean.setMessageContent("Maintenance updated");
		bean.setBroadcastTime(new java.util.Date());
		bean.setBroadcastStatus("Inactive");
		bean.setCreatedBy("root@gmail.com");
		bean.setModifiedBy("root@gmail.com");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		BroadcastModel model = new BroadcastModel();
		model.update(bean);

		System.out.println("Data Updated Successfully");
	}

	public static void testDelete() throws Exception {

		BroadcastBean bean = new BroadcastBean();
		bean.setId(3);

		BroadcastModel model = new BroadcastModel();
		model.delete(bean);

		System.out.println("Data Deleted Successfully");
	}

	public static void testFindByPK() throws Exception {

		BroadcastModel model = new BroadcastModel();
		BroadcastBean bean = model.findByPK(1);

		if (bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getBroadcastCode());
			System.out.println(bean.getMessageTitle());
			System.out.println(bean.getMessageContent());
			System.out.println(bean.getBroadcastTime());
			System.out.println(bean.getBroadcastStatus());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
		}
	}

	public static void testFindByCode() throws Exception {

		BroadcastModel model = new BroadcastModel();
		BroadcastBean bean = model.findByCode("BC101");

		if (bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getBroadcastCode());
			System.out.println(bean.getMessageTitle());
			System.out.println(bean.getMessageContent());
			System.out.println(bean.getBroadcastTime());
			System.out.println(bean.getBroadcastStatus());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
		}
	}

	public static void testSearch() throws Exception {

		BroadcastModel model = new BroadcastModel();
		BroadcastBean bean = new BroadcastBean();

		bean.setBroadcastStatus("Inactive");

		List list = model.Search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {
			bean = (BroadcastBean) it.next();

			System.out.println(bean.getId());
			System.out.println(bean.getBroadcastCode());
			System.out.println(bean.getMessageTitle());
			System.out.println(bean.getMessageContent());
			System.out.println(bean.getBroadcastTime());
			System.out.println(bean.getBroadcastStatus());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());
		}
	}

}
