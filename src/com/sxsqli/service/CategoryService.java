package com.sxsqli.service;

import java.sql.SQLException;
import java.util.List;

import com.sxsqli.domain.Category;

public interface CategoryService {

	public abstract void addCategory(Category category) throws SQLException;

	public abstract List<Category> findCategory() throws SQLException;

	public abstract Category findCategoryById(String cid) throws SQLException;

	public abstract void deleteCategory(String cid) throws SQLException;

	public abstract void updateCategory(Category category) throws SQLException ;

	public abstract boolean checkCategory(String cname) throws SQLException;

}
