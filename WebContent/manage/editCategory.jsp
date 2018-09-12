<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ include file="/manage/header.jsp"%>
<script type="text/javascript">
	$(function() {
		$("[type=submit]").click(function() {
			var cname = $("[type=text]").val();
			if(!cname){
				alert("分类名不能为空");
				return false;
			}
		});
	});
</script>
<div class="body">
	<div class="container">

		<form action="${pageContext.request.contextPath}/CategoryServlet" method="post">
			<input type="hidden" name="op" value="update">
			<input type="hidden" name="cid" value="${category.cid}">
			<table>
				<tr>
					<td>分类名称：</td>
					<td><input type="text" name="cname" value="${category.cname}"></td>
				</tr>
				<tr>
					<td>分类描述：</td>
					<td><textarea name="description">${category.description}</textarea></td>
				</tr>
			</table>
			<input type="submit" value="提交">
		</form>

	</div>
</div>
<%@ include file="/manage/footer.jsp"%>