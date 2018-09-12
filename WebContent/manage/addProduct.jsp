<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<script type="text/javascript">
	$(function() {
		$(".header a").eq(3).addClass("current");
		$.getJSON("${pageContext.request.contextPath}/CategoryServlet", {
			"op" : "get"
		}, function(data) {
			$(data).each(function() {
				$("select").append("<option value='"+this.cid+"'>"+this.cname+"</option>");
			});
		});
		$("[name=pname]").blur(function() {
			var pname = $(this).val();
			if(!pname){
				$(".pname").show();
			} else {
				$(".pname").hide();
			}
		});
		$("[name=price]").blur(function() {
			var price = $(this).val();
			if (!price) {
				$(".price td").html("商品价格不能为空");
				$(".price").show();
			} else if (!Number(price)) {
				$(".price td").html("商品价格只能是数字");
				$(".price").show();
			} else {
				$(".price").hide();
			}
		});
		$("select").blur(function() {
			var gategoryid = $("select").val();
			if (gategoryid == 0) {
				$(".categoryid").show();
			}else {
				$(".categoryid").hide();
			}
		});
		$("[type=submit]").click(function() {
			var pname = $("[name=pname]").val();
			var price = $("[name=price]").val();
			var gategoryid = $("select").val();
			if ($(".warning:visible").length != 0 || !pname || !price
					|| gategoryid == 0) {
				if (!pname) {
					$(".pname").show();
				}
				if (!price) {
					$(".price td").html("商品价格不能为空");
					$(".price").show();
				}
				if (gategoryid == 0) {
					$(".categoryid").show();
				}
				alert("请正确填写信息");
				return false;
			}
		});
	});
</script>
<div class="body">
	<div class="container">
	
		<form action="${pageContext.request.contextPath}/ProductServlet?op=add" method="post"  enctype="multipart/form-data">
			<!-- <input type="hidden" name="op" value="add"> -->
			<table>
				<tr>
					<td>商品名称：</td>
					<td><input type="text" name="pname"></td>
				</tr>
				<tr class="warning pname">
					<td colspan="2">商品名不能为空</td>
				</tr>
				<tr>
					<td>商品分类：</td>
					<td>
						<select name="categoryid">
							<option value="0">--------------------请选择--------------------</option>
						</select>
					</td>
				</tr>
				<tr class="warning categoryid">
					<td colspan="2">请选择分类，若无分类请先添加分类</td>
				</tr>
				<tr>
					<td>商品价格：</td>
					<td><input type="text" name="price"></td>
				</tr>
				<tr class="warning price">
					<td colspan="2"></td>
				</tr>
				<tr>
					<td>商品描述：</td>
					<td><textarea name="pdescription"></textarea></td>
				</tr>
				<tr>
					<td>商品图片：</td>
					<td><input type="file" name="file01"></td>
				</tr>
			</table>
			<input type="submit" value="提交">
		</form>
		
	</div>
</div>
<%@ include file="/manage/footer.jsp"%>