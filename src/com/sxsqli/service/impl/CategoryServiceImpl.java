package com.sxsqli.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.sxsqli.dao.CategoryDao;
import com.sxsqli.dao.impl.CategoryDaoImpl;
import com.sxsqli.domain.Category;
import com.sxsqli.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	private CategoryDao categoryDao = new CategoryDaoImpl();

	@Override
	public void addCategory(Category category) throws SQLException {
		categoryDao.addCategory(category);
	}

	@Override
	public List<Category> findCategory() throws SQLException {
		return categoryDao.findCategory();
	}

	@Override
	public Category findCategoryById(String cid) throws SQLException {
		Category category = categoryDao.findCategoryById(cid);
		if (category == null)
			throw new SQLException("Category not found");
		return category;
	}

	@Override
	public void deleteCategory(String cid) throws SQLException {
		categoryDao.deleteCategory(cid);
	}

	@Override
	public void updateCategory(Category category) throws SQLException {
		categoryDao.updateCategory(category);
	}

	@Override
	public boolean checkCategory(String cname) throws SQLException {
		Category category = categoryDao.findCategoryByName(cname);
		if (category == null) {
			return true;
		}
		return false;
	}
}
