<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ include file="/manage/header.jsp"%>
<script type="text/javascript">
	$(function() {
		$(".header a").eq(2).addClass("current");
		$("td").has("a").each(function() {
			$(this).children("a:last").click(function() {
				var flag = confirm("是否确定删除？");
				if(!flag){
					return false;
				}
			});
		});
	});
</script>
<div class="body">
	<div class="container">
	
		<table>
			<tr>
				<td>分类名称</td>
				<td>分类描述</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${categories}" var="category">
				<tr>
					<td>${category.cname}</td>
					<td>${category.description}</td>
					<td>
						<a href="${pageContext.request.contextPath}/CategoryServlet?op=findById&cid=${category.cid}">修改</a>
						<a href="${pageContext.request.contextPath}/CategoryServlet?op=delete&cid=${category.cid}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	
	</div>
</div>
<%@ include file="/manage/footer.jsp"%>