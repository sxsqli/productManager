package com.sxsqli.web.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.sxsqli.domain.Product;
import com.sxsqli.exception.UploadException;
import com.sxsqli.service.ProductService;
import com.sxsqli.service.impl.ProductServiceImpl;
import com.sxsqli.utils.MessageUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if ("add".equals(op)) {
			addProduct(request, response);
		} else if ("find".equals(op)) {
			String categoryid = request.getParameter("categoryid");
			if ("".equals(categoryid) || categoryid == null) {
				findProduct(request, response);
			} else {
				findProductByCategoryid(request, response, categoryid);
			}
		} else if ("delete".equals(op)) {
			deleteProduct(request, response);
		} else if ("findByPid".equals(op)) {
			findProductByPid(request, response);
		} else if ("update".equals(op)) {
			updateProduct(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/manage/index.jsp");
		}
	}

	private void updateProduct(HttpServletRequest request, HttpServletResponse response) {
		try {
			Product product = new Product();
			BeanUtils.populate(product, request.getParameterMap());
			ProductService productService = new ProductServiceImpl();
			productService.updateProduct(product);
			MessageUtils.forwardToMessage(request, response,
					"修改商品成功，<a href='" + request.getContextPath() + "/manage/listProduct.jsp'>返回查看</a>");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void findProductByPid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String pid = request.getParameter("pid");
			ProductService productService = new ProductServiceImpl();
			Product product = productService.findProductByPid(pid);
			JSONObject jsonObject = JSONObject.fromObject(product);
			response.getWriter().print(jsonObject);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String pid = request.getParameter("pid");
			ProductService productService = new ProductServiceImpl();
			Product product = productService.findProductByPid(pid);
			productService.deleteProduct(pid);
			response.getWriter().print("success");
			String path = product.getPath();
			if (path != null) {
				File productImg = new File(getServletContext().getRealPath(path));
				if (productImg.exists()) {
					productImg.delete();
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			response.getWriter().print("error");
		}
	}

	private void findProductByCategoryid(HttpServletRequest request, HttpServletResponse response, String categoryid)
			throws IOException {
		try {
			ProductService productService = new ProductServiceImpl();
			List<Product> Products = productService.findProductByCategoryid(categoryid);
			JSONArray jsonArray = JSONArray.fromObject(Products);
			response.getWriter().print(jsonArray);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void findProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			ProductService productService = new ProductServiceImpl();
			List<Product> Products = productService.findProduct();
			JSONArray jsonArray = JSONArray.fromObject(Products);
			response.getWriter().print(jsonArray);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private void addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Product product = new Product();
			upload(request, product);
			ProductService productService = new ProductServiceImpl();
			productService.addProduct(product);
			MessageUtils.forwardToMessage(request, response,
					"添加商品成功，<a href='" + request.getContextPath() + "/manage/addProduct.jsp'>继续添加</a>");
		} catch (UploadException e) {
			System.out.println(e.getMessage());
			MessageUtils.forwardToMessage(request, response, e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			MessageUtils.forwardToMessage(request, response, "添加商品失败，请联系管理员");
		}
	}

	/*
	 * 提取request中的数据，如果是图片文件则上传文件后将路径存入bean中，如果是普通表单项则存入bean中
	 * 请求方式错误或文件类型错误抛出UploadException
	 */
	private void upload(HttpServletRequest request, Product product) throws Exception {
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new UploadException("请求方式错误");
		}

		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		@SuppressWarnings("unchecked")
		List<FileItem> fileItems = servletFileUpload.parseRequest(request);

		for (FileItem fileItem : fileItems) {
			if (fileItem.isFormField()) {
				// 普通表单项

				// 获取对应field的name/value，一定要指定为UTF-8
				String fieldName = fileItem.getFieldName();
				String fieldValue = fileItem.getString("UTF-8");

				// 利用工具设置对应属性
				BeanUtils.setProperty(product, fieldName, fieldValue);
			} else if (fileItem.getSize() > 0) {
				// 文件上传表单项

				// 文件是否为图片
				if (!fileItem.getContentType().startsWith("image")) {
					throw new UploadException("文件类型错误");
				}

				// 获取文件名
				String fileName = fileItem.getName();
				// IE的fileName会带盘符，进行切割，剩下文件名
				// fileName = fileName.substring(fileName.lastIndexOf("\\")
				// + 1);

				// 生成随机不重复文件名
				fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(fileName);

				// 生成对应日期的文件夹路径
				String now = new SimpleDateFormat("yyyyMMdd").format(new Date());
				String path = getServletContext().getRealPath("/manage/productImg/" + now);
				File filePath = new File(path);

				File fileImg = new File(filePath, fileName);
				if (!filePath.exists()) {
					filePath.mkdirs();
				}
				/*
				 * (弃用)手动生成不重复的文件名，遇到重复文件名追加（i） else { for (int i = 1;
				 * fileImg.exists(); i++) { fileImg = new File(filePath,
				 * fileName.replaceAll("[.]", "(" + i + ").")); } }
				 */

				// 写入文件
				fileItem.write(fileImg);

				// 手动设置图片存储路径
				product.setPath("/manage/productImg/" + now + "/" + fileName);
			}
		}
	}

}
