package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RecipeBean;
import in.co.rays.proj4.model.RecipeModel;

public class TestRecipeModel {

	public static void main(String[] args) throws Exception {

//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPK();
//		testFindByRecipeName();
		testSearch();

	}

	public static void testAdd() throws Exception {

		RecipeModel model = new RecipeModel();
		RecipeBean bean = new RecipeBean();

		bean.setRecipeName("Pizza");
		bean.setIngredients("Flour, Cheese, Sauce");
		bean.setCookingTime(new Timestamp(new Date().getTime()));
		bean.setDifficultyLevel("Medium");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		long pk = model.add(bean);

		System.out.println("Add Successfully ID = " + pk);
	}

	public static void testUpdate() throws Exception {

		RecipeModel model = new RecipeModel();
		RecipeBean bean = new RecipeBean();

		bean.setId(2);
		bean.setRecipeName("Veg Pizza");
		bean.setIngredients("Flour, Cheese, Veggies");
		bean.setCookingTime(new Timestamp(new Date().getTime()));
		bean.setDifficultyLevel("Easy");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.update(bean);

		System.out.println("Update Successfully");
	}

	public static void testDelete() throws Exception {

		RecipeModel model = new RecipeModel();

		model.delete(3);

		System.out.println("Delete Successfully");
	}

	public static void testFindByPK() throws Exception {

		RecipeModel model = new RecipeModel();

		RecipeBean bean = model.findByPK(2);

		if (bean != null) {

			System.out.println("ID : " + bean.getId());
			System.out.println("Recipe Name : " + bean.getRecipeName());
			System.out.println("Ingredients : " + bean.getIngredients());
			System.out.println("Cooking Time : " + bean.getCookingTime());
			System.out.println("Difficulty : " + bean.getDifficultyLevel());
			System.out.println("Created By : " + bean.getCreatedBy());
			System.out.println("Modified By : " + bean.getModifiedBy());
			System.out.println("Created DateTime : " + bean.getCreatedDatetime());
			System.out.println("Modified DateTime : " + bean.getModifiedDatetime());

		}
	}

	public static void testFindByRecipeName() throws Exception {

		RecipeModel model = new RecipeModel();

		RecipeBean bean = model.findByRecipeName("veg Pizza");

		if (bean != null) {

			System.out.println("ID : " + bean.getId());
			System.out.println("Recipe Name : " + bean.getRecipeName());
			System.out.println("Ingredients : " + bean.getIngredients());
			System.out.println("Cooking Time : " + bean.getCookingTime());
			System.out.println("Difficulty : " + bean.getDifficultyLevel());
			System.out.println("Created By : " + bean.getCreatedBy());
			System.out.println("Modified By : " + bean.getModifiedBy());
			System.out.println("Created DateTime : " + bean.getCreatedDatetime());
			System.out.println("Modified DateTime : " + bean.getModifiedDatetime());

		}
	}

	public static void testSearch() throws Exception {

		RecipeModel model = new RecipeModel();
		RecipeBean bean = new RecipeBean();

		bean.setRecipeName("pizza");

		List<RecipeBean> list = model.search(bean, 0, 0);

		Iterator<RecipeBean> it = list.iterator();

		while (it.hasNext()) {

			RecipeBean rb = (RecipeBean) it.next();

			System.out.println("ID : " + rb.getId());
			System.out.println("Recipe Name : " + rb.getRecipeName());
			System.out.println("Ingredients : " + rb.getIngredients());
			System.out.println("Cooking Time : " + bean.getCookingTime());
			System.out.println("Difficulty : " + rb.getDifficultyLevel());
			System.out.println("Created By : " + bean.getCreatedBy());
			System.out.println("Modified By : " + bean.getModifiedBy());
			System.out.println("Created DateTime : " + bean.getCreatedDatetime());
			System.out.println("Modified DateTime : " + bean.getModifiedDatetime());
		}

	}
}
