package com.veilingsite.shared.domain;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Category {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int categoryId;
    public String title;
    public int parentCategory;

    public Category() {
    }
    
    public Category(String title, int parentId) {
    	this.title = title;
    	this.parentCategory = parentId;
    }

    public int getCategoryId() {
    	return categoryId;
    }

    public String getTitle() {
    	return title;
    }

    public void setTitle(String s) {
    	this.title = s;
    }

    public int getParent() {
    	return parentCategory;
    }

    public void setParent(int parentId) {
    	this.parentCategory = parentId;
    }
}