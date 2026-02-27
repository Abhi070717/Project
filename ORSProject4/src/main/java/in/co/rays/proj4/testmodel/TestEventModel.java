package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.EventBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EventModel;

public class TestEventModel {

	public static void main(String[] args) throws ParseException {

//		testNextPk();
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByVenue();
//		testSearch();

	}

	public static void testNextPk() {

		EventModel model = new EventModel();

		try {
			long i = model.nextPk();
			System.out.println("Next Pk : " + i);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	public static void testAdd() throws ParseException {

		EventBean bean = new EventBean();

		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

		try {
			bean.setTitle("Fresher Party");
			bean.setDescription("Student Celebration");
			bean.setEventDate(sdf.parse("2026-03-03"));
			bean.setStartTime("09:00 AM");
			bean.setEndTime("01:00 PM");
			bean.setVenue("Agrawal Public School");
			bean.setOrganizerName("vishvas Khurana");
			bean.setContactEmail("vishvas@gmail.com");
			bean.setContectMobile("9789456123");
			bean.setStatus("TO-DO");
			bean.setCreatedBy("root@gmail.com");
			bean.setModifiedBy("root@gmail.com");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			EventModel model = new EventModel();

			long pk;
			try {
				pk = model.add(bean);
				System.out.println("Event Added Successfully, PK = " + pk);
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testUpdate() throws ParseException {

		EventBean bean = new EventBean();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bean.setId(2);
			bean.setTitle("Teacher's Day");
			bean.setDescription("Teachers Celebration");
			bean.setEventDate(sdf.parse("2025-09-05"));
			bean.setStartTime("08:00 AM");
			bean.setEndTime("12:00 PM");
			bean.setVenue("Agrawal Public School");
			bean.setOrganizerName("Rituraj Gehlot");
			bean.setContactEmail("ritraj@gmail.com");
			bean.setContectMobile("7898565478");
			bean.setStatus("Done");
			bean.setCreatedBy("root@gmail.com");
			bean.setModifiedBy("root@gmail.com");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			EventModel model = new EventModel();

			try {
				model.update(bean);
				System.out.println("Event Updated Successfully");
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void testDelete() {

		EventBean bean = new EventBean();

		bean.setId(3);

		try {
			EventModel model = new EventModel();

			model.delete(bean);
			System.out.println("Event Deleted Successfully");

		} catch (DuplicateRecordException e) {
			e.printStackTrace();

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void testFindByPk() {

		EventModel model = new EventModel();

		try {
			EventBean bean = model.findByPk(1L);

			System.out.println("ID: " + bean.getId());
			System.out.println("Title: " + bean.getTitle());
			System.out.println("Description: " + bean.getDescription());
			System.out.println("Event Date: " + bean.getEventDate());
			System.out.println("Start Time: " + bean.getStartTime());
			System.out.println("End Time: " + bean.getEndTime());
			System.out.println("Venue: " + bean.getVenue());
			System.out.println("Organizer Name: " + bean.getOrganizerName());
			System.out.println("Contact Email: " + bean.getContactEmail());
			System.out.println("Contact Mobile: " + bean.getContectMobile());
			System.out.println("Status: " + bean.getStatus());
			System.out.println("Created By: " + bean.getCreatedBy());
			System.out.println("Modified By: " + bean.getModifiedBy());
			System.out.println("Created Datetime: " + bean.getCreatedDatetime());
			System.out.println("Modified Datetime: " + bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void testFindByVenue() {

		EventModel model = new EventModel();

		try {
			EventBean bean = model.findByVenue("Agrawal Public School");

			if (bean != null) {
				System.out.println("ID: " + bean.getId());
				System.out.println("Title: " + bean.getTitle());
				System.out.println("Description: " + bean.getDescription());
				System.out.println("Event Date: " + bean.getEventDate());
				System.out.println("Start Time: " + bean.getStartTime());
				System.out.println("End Time: " + bean.getEndTime());
				System.out.println("Venue: " + bean.getVenue());
				System.out.println("Organizer Name: " + bean.getOrganizerName());
				System.out.println("Contact Email: " + bean.getContactEmail());
				System.out.println("Contact Mobile: " + bean.getContectMobile());
				System.out.println("Status: " + bean.getStatus());
				System.out.println("Created By: " + bean.getCreatedBy());
				System.out.println("Modified By: " + bean.getModifiedBy());
				System.out.println("Created Datetime: " + bean.getCreatedDatetime());
				System.out.println("Modified Datetime: " + bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void testSearch() {

		EventBean bean = new EventBean();

		bean.setTitle("Teacher's Day");

		List list = new ArrayList();

		try {
			EventModel model = new EventModel();
			list = model.search(bean, 0, 0);

			Iterator it = list.iterator();

			while (it.hasNext()) {
				bean = (EventBean) it.next();
				System.out.println("ID: " + bean.getId());
				System.out.println("Title: " + bean.getTitle());
				System.out.println("Description: " + bean.getDescription());
				System.out.println("Event Date: " + bean.getEventDate());
				System.out.println("Start Time: " + bean.getStartTime());
				System.out.println("End Time: " + bean.getEndTime());
				System.out.println("Venue: " + bean.getVenue());
				System.out.println("Organizer Name: " + bean.getOrganizerName());
				System.out.println("Contact Email: " + bean.getContactEmail());
				System.out.println("Contact Mobile: " + bean.getContectMobile());
				System.out.println("Status: " + bean.getStatus());
				System.out.println("Created By: " + bean.getCreatedBy());
				System.out.println("Modified By: " + bean.getModifiedBy());
				System.out.println("Created Datetime: " + bean.getCreatedDatetime());
				System.out.println("Modified Datetime: " + bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
