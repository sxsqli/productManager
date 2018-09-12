package com.sxsqli.dao;

import java.sql.SQLException;
import java.util.List;

import com.sxsqli.domain.Category;

public interface CategoryDao {

	public abstract void addCategory(Category category) throws SQLException;

	public abstract List<Category> findCategory() throws SQLException;

	public abstract Category findCategoryById(String cid) throws SQLException;

	public abstract void deleteCategory(String cid) throws SQLException;

	public abstract void updateCategory(Category category) throws SQLException;

	public abstract Category findCategoryByName(String cname) throws SQLException;

}
