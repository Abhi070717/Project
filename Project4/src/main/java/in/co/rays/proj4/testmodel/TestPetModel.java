package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.List;

import in.co.rays.proj4.bean.PetBean;
import in.co.rays.proj4.model.PetModel;

public class TestPetModel {

	public static void main(String[] args) throws Exception {

//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPK();
//		testFindByName();
//		testSearch();
	}

	public static void testAdd() throws Exception {

		PetModel model = new PetModel();

		PetBean bean = new PetBean();

		bean.setPetName("Tommy");
		bean.setAnimalType("Dog");
		bean.setAge("3");
		bean.setAdoptionStatus("Available");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(System.currentTimeMillis()));
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		long pk = model.add(bean);

		System.out.println("Pet Added Successfully with ID = " + pk);
	}

	public static void testUpdate() throws Exception {

		PetModel model = new PetModel();

		PetBean bean = model.findByPK(2); // existing ID

		bean.setPetName("Bruno");
		bean.setAnimalType("Dog");
		bean.setAge("4");
		bean.setAdoptionStatus("Adopted");
		bean.setModifiedBy("Admin");
		bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

		model.update(bean);

		System.out.println("Pet Updated Successfully");
	}

	public static void testDelete() throws Exception {

		PetModel model = new PetModel();
		PetBean bean = new PetBean();
		bean.setId(3);
		model.delete(bean); // existing ID

		System.out.println("Pet Deleted Successfully");
	}

	public static void testFindByPK() throws Exception {

		PetModel model = new PetModel();

		PetBean bean = model.findByPK(1);

		if (bean != null) {
			System.out.println("ID: " + bean.getId());
			System.out.println("Name: " + bean.getPetName());
			System.out.println("Type: " + bean.getAnimalType());
			System.out.println("Age: " + bean.getAge());
			System.out.println("Status: " + bean.getAdoptionStatus());
		} else {
			System.out.println("Record Not Found");
		}
	}

	public static void testFindByName() throws Exception {

		PetModel model = new PetModel();

		PetBean bean = model.findByPetName("Tommy");

		if (bean != null) {
			System.out.println("ID: " + bean.getId());
			System.out.println("Name: " + bean.getPetName());
		} else {
			System.out.println("Record Not Found");
		}
	}

	public static void testSearch() throws Exception {

		PetModel model = new PetModel();

		PetBean bean = new PetBean();
		bean.setAnimalType("Dog"); // filter

		List<PetBean> list = model.search(bean, 0, 0);

		for (PetBean b : list) {
			System.out.println(b.getId() + " | " + b.getPetName() + " | " + b.getAnimalType());
		}
	}
}
