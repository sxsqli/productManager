package com.sxsqli.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sxsqli.dao.CategoryDao;
import com.sxsqli.domain.Category;
import com.sxsqli.utils.JdbcUtils;

public class CategoryDaoImpl implements CategoryDao {
	private QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());

	@Override
	public void addCategory(Category category) throws SQLException {
		queryRunner.update("insert into category (cname,description) values (?,?)", category.getCname(),
				category.getDescription());
	}

	@Override
	public List<Category> findCategory() throws SQLException {
		return queryRunner.query("select * from category", new BeanListHandler<Category>(Category.class));
	}

	@Override
	public Category findCategoryById(String cid) throws SQLException {
		return queryRunner.query("select * from category where cid = ?", new BeanHandler<Category>(Category.class),
				cid);
	}

	@Override
	public void deleteCategory(String cid) throws SQLException {
		queryRunner.update("delete from category where cid = ?", cid);
	}

	@Override
	public void updateCategory(Category category) throws SQLException {
		queryRunner.update("update category set cname = ?,description = ? where cid = ?", category.getCname(),
				category.getDescription(), category.getCid());
	}

	@Override
	public Category findCategoryByName(String cname) throws SQLException {
		return queryRunner.query("select * from category where cname = ?", new BeanHandler<Category>(Category.class), cname);
	}

}
