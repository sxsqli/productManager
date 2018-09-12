package com.sxsqli.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.sxsqli.domain.Category;
import com.sxsqli.service.CategoryService;
import com.sxsqli.service.impl.CategoryServiceImpl;
import com.sxsqli.utils.MessageUtils;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CatagroyServlet
 */
public class CategoryServlet extends HttpServlet {
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
			addCategory(request, response);
		} else if ("find".equals(op)) {
			findCategory(request, response);
		} else if ("findById".equals(op)) {
			findCategoryById(request, response);
		} else if ("delete".equals(op)) {
			deleteCategory(request, response);
		} else if ("update".equals(op)) {
			updateCategory(request, response);
		} else if ("check".equals(op)) {
			checkCategory(request, response);
		} else if ("get".equals(op)) {
			getCategory(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/manage/index.jsp");
		}
	}

	private void getCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			CategoryService categoryService = new CategoryServiceImpl();
			List<Category> categories = categoryService.findCategory();
			JSONArray jsonArray = JSONArray.fromObject(categories);
			response.getWriter().print(jsonArray);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// 检查分类名称是否可用，true为可用，false为不可用
	private void checkCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String cname = request.getParameter("cname");
			CategoryService categoryService = new CategoryServiceImpl();
			boolean flag = categoryService.checkCategory(cname);
			response.getWriter().print(flag);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			response.getWriter().print(false);
		}
	}

	private void addCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Category category = createCategory(request);
			CategoryService categoryService = new CategoryServiceImpl();
			categoryService.addCategory(category);
			MessageUtils.forwardToMessage(request, response,
					"添加分类成功，<a href='" + request.getContextPath() + "/manage/addCategory.jsp'>继续添加</a>");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			MessageUtils.forwardToMessage(request, response, "添加分类失败，请联系管理员");
		}
	}

	private void findCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			CategoryService categoryService = new CategoryServiceImpl();
			List<Category> categories = categoryService.findCategory();
			request.setAttribute("categories", categories);
			request.getRequestDispatcher("/manage/listCategory.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			MessageUtils.forwardToMessage(request, response, "查询分类失败，请联系管理员");
		}
	}

	private void findCategoryById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String cid = request.getParameter("cid");
			CategoryService categoryService = new CategoryServiceImpl();
			Category category = categoryService.findCategoryById(cid);
			request.setAttribute("category", category);
			request.getRequestDispatcher("/manage/editCategory.jsp").forward(request, response);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			MessageUtils.forwardToMessage(request, response, "查询分类失败，请联系管理员");
		}
	}

	private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String cid = request.getParameter("cid");
			CategoryService categoryService = new CategoryServiceImpl();
			categoryService.deleteCategory(cid);
			findCategory(request, response);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			MessageUtils.forwardToMessage(request, response, "删除分类失败<br>请尝试删除此分类下所有商品后再次操作");
		}
	}

	private void updateCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Category category = createCategory(request);
			CategoryService categoryService = new CategoryServiceImpl();
			categoryService.updateCategory(category);
			MessageUtils.forwardToMessage(request, response,
					"修改分类成功，<a href='" + request.getContextPath() + "/CategoryServlet?op=find'>返回查看</a>");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			MessageUtils.forwardToMessage(request, response, "修改分类失败，请联系管理员");
		}
	}

	// 从request中获取参数放入bean中返回
	private Category createCategory(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		Category category = new Category();
		BeanUtils.populate(category, request.getParameterMap());
		return category;
	}

}
