package com.veilingsite.shared.domain;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Category implements Serializable{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long categoryId;
    @Id
    public String title;
    public String parentCategory;

    public Category() {
    }
    
    public Category(String title, String parentId) {
    	this.title = title;
    	this.parentCategory = parentId;
    }

    public Long getCategoryId() {
    	return categoryId;
    }

    public String getTitle() {
    	return title;
    }

    public void setTitle(String s) {
    	this.title = s;
    }

    public String getParent() {
    	return parentCategory;
    }

    public void setParent(String parentId) {
    	this.parentCategory = parentId;
    }
}