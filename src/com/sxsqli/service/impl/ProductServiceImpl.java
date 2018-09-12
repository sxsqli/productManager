package com.sxsqli.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.sxsqli.dao.ProductDao;
import com.sxsqli.dao.impl.ProductDaoImpl;
import com.sxsqli.domain.Product;
import com.sxsqli.service.ProductService;

public class ProductServiceImpl implements ProductService {
	private ProductDao productDao = new ProductDaoImpl();

	@Override
	public void addProduct(Product product) throws SQLException {
		productDao.addProduct(product);
	}

	@Override
	public List<Product> findProduct() throws SQLException {
		return productDao.findProduct();
	}

	@Override
	public List<Product> findProductByCategoryid(String categoryid) throws SQLException {
		return productDao.findProductByCategoryid(categoryid);
	}

	@Override
	public void deleteProduct(String pid) throws SQLException {
		productDao.deleteProduct(pid);
	}

	@Override
	public Product findProductByPid(String pid) throws SQLException {
		Product product = productDao.findProductByPid(pid);
		if (product == null) {
			throw new SQLException("Product not found");
		}
		return product;
	}

	@Override
	public void updateProduct(Product product) throws SQLException {
		productDao.updateProduct(product);
	}

}
