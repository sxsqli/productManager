package com.sxsqli.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sxsqli.dao.ProductDao;
import com.sxsqli.domain.Product;
import com.sxsqli.utils.JdbcUtils;

public class ProductDaoImpl implements ProductDao {
	private QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());

	@Override
	public void addProduct(Product product) throws SQLException {
		queryRunner.update("insert into product (pname,price,path,pdescription,categoryid) values (?,?,?,?,?)",
				product.getPname(), product.getPrice(), product.getPath(), product.getPdescription(),
				product.getCategoryid());
	}

	@Override
	public List<Product> findProduct() throws SQLException {
		return queryRunner.query("select * from product", new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findProductByCategoryid(String categoryid) throws SQLException {
		return queryRunner.query("select * from product where categoryid = ?",
				new BeanListHandler<Product>(Product.class), categoryid);
	}

	@Override
	public void deleteProduct(String pid) throws SQLException {
		queryRunner.update("delete from product where pid = ?", pid);
	}

	@Override
	public Product findProductByPid(String pid) throws SQLException {
		return queryRunner.query("select * from product where pid = ?", new BeanHandler<Product>(Product.class), pid);
	}

	@Override
	public void updateProduct(Product product) throws SQLException {
		queryRunner.update("update product set pname = ?,price = ?,pdescription = ?,categoryid = ? where pid = ?",
				product.getPname(), product.getPrice(), product.getPdescription(), product.getCategoryid(),
				product.getPid());
	}

}
