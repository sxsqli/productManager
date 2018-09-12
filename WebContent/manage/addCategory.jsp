<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ include file="/manage/header.jsp"%>
<script type="text/javascript">
	$(function() {
		$(".header a").eq(1).addClass("current");
		$("[type=submit]").click(function() {
			var cname = $("[type=text]").val();
			if ($(".warning:visible").length != 0 || !cname) {
				if (!cname) {
					$(".cname td").html("分类名不能为空");
					$(".cname").show();
				}
				alert("请正确填写信息");
				return false;
			}
		});
		$("[name=cname]").blur(function() {
			var cname = $(this).val();
			if (!cname) {
				$(".cname td").html("分类名不能为空");
				$(".cname").show();
				return;
			}
			$.get("${pageContext.request.contextPath}/CategoryServlet", {
				"op" : "check",
				"cname" : cname
			}, function(data) {
				if (data === "true") {
					$(".cname").hide();
				} else {
					$(".cname td").html("分类名已存在");
					$(".cname").show();
				}
			});
		});
	});
</script>
<div class="body">
	<div class="container">

		<form action="${pageContext.request.contextPath}/CategoryServlet" method="post">
			<input type="hidden" name="op" value="add">
			<table>
				<tr>
					<td>分类名称：</td>
					<td><input type="text" name="cname"></td>
				</tr>
				<tr class="warning cname">
					<td colspan="2"></td>
				</tr>
				<tr>
					<td>分类描述：</td>
					<td><textarea name="description"></textarea></td>
				</tr>
			</table>
			<input type="submit" value="提交">
		</form>

	</div>
</div>
<%@ include file="/manage/footer.jsp"%>