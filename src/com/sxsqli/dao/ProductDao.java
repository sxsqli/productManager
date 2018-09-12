package com.sxsqli.dao;

import java.sql.SQLException;
import java.util.List;

import com.sxsqli.domain.Product;

public interface ProductDao {

	public abstract void addProduct(Product product) throws SQLException;

	public abstract List<Product> findProduct() throws SQLException;

	public abstract List<Product> findProductByCategoryid(String categoryid) throws SQLException;

	public abstract void deleteProduct(String pid) throws SQLException;

	public abstract Product findProductByPid(String pid) throws SQLException;

	public abstract void updateProduct(Product product) throws SQLException;

}
