package com.example.fruitchecker;

public class FruitModel {
    private String id;
    private String carbohydrates;
    private String calories;
    private String protein;
    private String fat;
    private String sugar;

    public FruitModel() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }
    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }
    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    @Override
    public String toString() {
        return  id + " - Calories = " + carbohydrates + ", Carbohydrates = " + carbohydrates +  ", Protein = " + carbohydrates + ", Fat = " + carbohydrates + ", Sugar = " + carbohydrates;
    }
}
