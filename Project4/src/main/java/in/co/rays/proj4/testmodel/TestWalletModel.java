package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.WalletBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.WalletModel;

public class TestWalletModel {

	public static void main(String[] args) {

//		 testNextPk();
//		 testAdd();
//		testUpdate();
//		 testDelete();
//		 testFindByPk();
//		 testFindByCode();
		testSearch();
	}

	public static void testNextPk() {

		WalletModel model = new WalletModel();

		try {
			int pk = model.nextPk();
			System.out.println("NextPk : " + pk);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public static void testAdd() {

		WalletBean bean = new WalletBean();

		bean.setWalletCode("W003");
		bean.setUserName("Rohit");
		bean.setBalance((double) 2000);
		bean.setWalletStatus("Active");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		WalletModel model = new WalletModel();

		try {
			long i = model.add(bean);
			System.out.println("Data Stored in st_wallet : " + i);
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		WalletBean bean = new WalletBean();

		bean.setId(2);
		bean.setWalletCode("W004");
		bean.setUserName("Rohit kushwah");
		bean.setBalance((double) 5000);
		bean.setWalletStatus("Inactive");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		WalletModel model = new WalletModel();

		try {
			model.update(bean);
			System.out.println("Data Updated in st_wallet");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		WalletBean bean = new WalletBean();
		bean.setId(3);

		WalletModel model = new WalletModel();

		try {
			model.delete(bean);
			System.out.println("Data Deleted in st_wallet");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		WalletModel model = new WalletModel();

		try {
			WalletBean bean = model.findByPk(1);

			System.out.println("ID : " + bean.getId());
			System.out.println("Code : " + bean.getWalletCode());
			System.out.println("Name : " + bean.getUserName());
			System.out.println("Balance : " + bean.getBalance());
			System.out.println("Status : " + bean.getWalletStatus());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByCode() {

		WalletModel model = new WalletModel();

		try {
			WalletBean bean = model.findByCode("W004");

			System.out.println("ID : " + bean.getId());
			System.out.println("Code : " + bean.getWalletCode());
			System.out.println("Name : " + bean.getUserName());
			System.out.println("Balance : " + bean.getBalance());
			System.out.println("Status : " + bean.getWalletStatus());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("CreatedDatetime : " + bean.getCreatedDatetime());
			System.out.println("ModifiedDatetime : " + bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {

		WalletBean bean = new WalletBean();
		bean.setWalletStatus("Active");

		WalletModel model = new WalletModel();

		try {
			List<WalletBean> list = new ArrayList<>();

			list = model.search(bean, 0, 0);

			Iterator<WalletBean> it = list.iterator();

			while (it.hasNext()) {
				bean = it.next();

				System.out.println("ID : " + bean.getId());
				System.out.println("Code : " + bean.getWalletCode());
				System.out.println("Name : " + bean.getUserName());
				System.out.println("Balance : " + bean.getBalance());
				System.out.println("Status : " + bean.getWalletStatus());
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
