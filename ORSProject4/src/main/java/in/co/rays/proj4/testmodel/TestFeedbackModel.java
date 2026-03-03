package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.FeedbackBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.model.FeedbackModel;

public class TestFeedbackModel {

	public static void main(String[] args) throws ParseException {

//		testNextPk();
//		testAdd();
//		testUpdate();
		testDelete();
//		testFindByPk();
//		testFindByCode();
//		testSearch();

	}

	public static void testNextPk() {

		FeedbackModel model = new FeedbackModel();

		try {
			long pk = model.nextPk();
			System.out.println("Next Pk : " + pk);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	public static void testAdd() throws ParseException {

		FeedbackBean bean = new FeedbackBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setFeedbackCode("abh1212");
		bean.setUserName("ravja sen");
		bean.setComments("best Service");
		bean.setRating(4);
		bean.setFeedbackDate(sdf.parse("2026-02-18"));
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		FeedbackModel model = new FeedbackModel();

		try {
			long pk = model.add(bean);
			System.out.println("Feedback Added Successfully, Pk = " + pk);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() throws ParseException {

		FeedbackBean bean = new FeedbackBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setId(3);
		bean.setFeedbackCode("abh1212");
		bean.setUserName("rajveer sen");
		bean.setComments("Complete Facilities Provided");
		bean.setRating(5);
		bean.setFeedbackDate(sdf.parse("2026-02-13"));
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		FeedbackModel model = new FeedbackModel();

		try {
			model.update(bean);
			System.out.println("Certificate Updated Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() throws ParseException {

		FeedbackBean bean = new FeedbackBean();

		bean.setId(3);

		FeedbackModel model = new FeedbackModel();

		try {
			model.delete(bean);
			System.out.println("Feedback Delete Successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {

		FeedbackModel model = new FeedbackModel();

		try {
			FeedbackBean bean = model.findByPK(1L);
			System.out.println("ID : " + bean.getId());
			System.out.println("Feedback_Code : " + bean.getFeedbackCode());
			System.out.println("User Name : " + bean.getUserName());
			System.out.println("Comments : " + bean.getComments());
			System.out.println("Rating : " + bean.getRating());
			System.out.println("Feedback Date : " + bean.getFeedbackDate());
			System.out.println("Created By : " + bean.getCreatedBy());
			System.out.println("Modified By : " + bean.getModifiedBy());
			System.out.println("Created DateTime : " + bean.getCreatedDatetime());
			System.out.println("Modified DateTime : " + bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByCode() {

		FeedbackModel model = new FeedbackModel();

		try {
			FeedbackBean bean = model.findByCode("ABC1211");
			System.out.println("ID : " + bean.getId());
			System.out.println("Feedback_Code : " + bean.getFeedbackCode());
			System.out.println("User_Name : " + bean.getUserName());
			System.out.println("Comments : " + bean.getComments());
			System.out.println("Rating : " + bean.getRating());
			System.out.println("Feedback_Date : " + bean.getFeedbackDate());
			System.out.println("CreatedBy : " + bean.getCreatedBy());
			System.out.println("ModifiedBy : " + bean.getModifiedBy());
			System.out.println("Created DateTime : " + bean.getCreatedDatetime());
			System.out.println("Modified DateTime : " + bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() {

		FeedbackBean bean = new FeedbackBean();
		bean.setFeedbackCode("abh1212");

		FeedbackModel model = new FeedbackModel();

		List<FeedbackBean> list = new ArrayList<FeedbackBean>();

		try {
			list = model.Search(bean, 0, 0);

			Iterator<FeedbackBean> it = list.iterator();

			while (it.hasNext()) {
				bean = it.next();
				System.out.println("ID : " + bean.getId());
				System.out.println("Feedback_Code : " + bean.getFeedbackCode());
				System.out.println("User_Name : " + bean.getUserName());
				System.out.println("Comments : " + bean.getComments());
				System.out.println("Rating : " + bean.getRating());
				System.out.println("Feedback_Date : " + bean.getFeedbackDate());
				System.out.println("Created By : " + bean.getCreatedBy());
				System.out.println("Modified By : " + bean.getModifiedBy());
				System.out.println("Created DateTime : " + bean.getCreatedDatetime());
				System.out.println("Modified DateTime : " + bean.getModifiedDatetime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}