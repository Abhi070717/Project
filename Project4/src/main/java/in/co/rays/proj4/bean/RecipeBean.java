package in.co.rays.proj4.bean;

import java.sql.Timestamp;

public class RecipeBean extends BaseBean {

	private long id;
	private String recipeName;
	private String ingredients;
	private Timestamp cookingTime;
	private String difficultyLevel;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public Timestamp getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(Timestamp cookingTime) {
		this.cookingTime = cookingTime;
	}

	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return ingredients;
	}

}
