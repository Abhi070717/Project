package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import in.co.rays.proj4.bean.ParkingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ParkingModel;

public class TestParkingModel {

	public static void main(String[] args) throws ParseException {

//		testNextPk();
		testAdd();
	}

	public static void testNextPk() {
		
		ParkingModel model = new ParkingModel();
		try {
			long pk = model.nextPK();
			System.out.println("pk" + pk);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void testAdd() throws ParseException {

		ParkingModel model = new ParkingModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ParkingBean bean = new ParkingBean();

		bean.setVehicleNumber("MP11AU2020");
		bean.setSlotNumber("1st slot");
		bean.setEntryTime(sdf.parse("2026-03-12"));
		bean.setExitTime(sdf.parse("2026-03-13"));
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		long pk;
		try {
			pk = model.add(bean);
			System.out.println("Parking Added Successfully with ID = " + pk);
		} catch (ApplicationException e) {
		} catch (DuplicateRecordException e) {
		}

	}
//	public static void testUpdate() throws ParseException {
//
//		ParkingModel model = new ParkingModel();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		ParkingBean bean = new ParkingBean();
//
//		bean.setVehicleNumber("MP11AU2020");
//		bean.setSlotNumber("1st slot");
//		bean.setEntryTime(sdf.parse("2026-03-12"));
//		bean.setExitTime(sdf.parse("2026-03-13"));
//		bean.setCreatedBy("Admin");
//		bean.setModifiedBy("Admin");
//		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
//		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));
//
//		long pk;
//		try {
//			pk = model.add(bean);
//			System.out.println("Parking Added Successfully with ID = " + pk);
//		} catch (ApplicationException e) {
//		} catch (DuplicateRecordException e) {
//		}
//
//	}
}