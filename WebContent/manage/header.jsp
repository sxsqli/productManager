<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/weblib/normalize-8.0.0.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/weblib/tool.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/weblib/jquery-1.8.3.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main.css">
</head>



<body>
	<div class="header">
		<div class="container">
			<h1>商品管理系统</h1>
			<div class="cf">
				<a class="col-2 fl" href="${pageContext.request.contextPath}/manage/index.jsp">首页</a>
				<a class="col-2 fl" href="${pageContext.request.contextPath}/manage/addCategory.jsp">添加分类</a>
				<a class="col-2 fl" href="${pageContext.request.contextPath}/CategoryServlet?op=find">查询分类</a>
				<a class="col-2 fl" href="${pageContext.request.contextPath}/manage/addProduct.jsp">添加商品</a>
				<a class="col-2 fl" href="${pageContext.request.contextPath}/manage/listProduct.jsp">查询商品</a>
			</div>
		</div>
	</div>