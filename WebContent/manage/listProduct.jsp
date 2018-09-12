<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<script type="text/javascript">
	$(function() {
		$(".header a").eq(4).addClass("current");
		$.getJSON("${pageContext.request.contextPath}/CategoryServlet", {
			"op" : "get"
		}, function(data) {
			$("#categories").append("<a class='current' href='javascrit:;' onclick='$(this).addClass(\"current\");$(this).siblings().removeClass(\"current\");getProduct();return false;'>所有分类</a>");
			$(data).each(function() {
				$("#categories").append("<a href='javascrit:;' onclick='$(this).addClass(\"current\");$(this).siblings().removeClass(\"current\");getProduct("+this.cid+");return false;'>"+this.cname+"</a>");
			});
		});
		$("#deleteAll").click(function() {
			if(!confirm("是否确定删除？")){
				return false;
			}
			$(":checked").each(function() {
				var pid = $(this).val();
				if(pid != "on"){
					deleteProduct(pid,this);
				}
			});
		});
		$("#all").click(function() {
			var flag = this.checked;
			$("[type=checkbox]").slice(1).each(function() {
				this.checked = flag;
			});
		});
	});
	function deleteProduct(pid,thisA) {
		$.get("${pageContext.request.contextPath}/ProductServlet",{
			"op" : "delete",
			"pid" : pid
		},function(data){
			if(data == "success"){
				$(thisA).parent().parent().remove();
			}else{
				alert("删除\""+$(thisA).parent().siblings().eq(1).html()+"\"失败，请联系管理员");
			}
		});
	}
	function getProduct(cid) {
		cid = cid || "";
		$.getJSON("${pageContext.request.contextPath}/ProductServlet", {
			"op" : "find",
			"categoryid" : cid
		}, function(data) {
			$("table tr").slice(1).remove();
			$(data).each(function() {
				$("table").append(
					"<tr>"
						+"<td></td>"
						+"<td></td>"
						+"<td></td>"
						+"<td></td>"
						+"<td></td>"
						+"<td></td>"
					+"</tr>");
				var $tds = $("tr:last").children();
				$tds.eq(0).html("<input type='checkbox' value='"+this.pid+"' onclick='$(\"#all\").get(0).checked=false;'>");
				$tds.eq(1).html(this.pname);
				$tds.eq(2).html(this.price);
				$tds.eq(3).html(this.pdescription);
				$tds.eq(4).html("<img src='${pageContext.request.contextPath}"+this.path+"' onerror='$(this).hide();'/>");
				$tds.eq(5).html("<a href='${pageContext.request.contextPath}/manage/editProduct.jsp?pid="+this.pid+"'>修改</a>"
					+"<a href='javascrip:;' onclick='if(!confirm(\"是否确定删除？\")){return false;}deleteProduct("+this.pid+",this);'>删除</a>");
			});
		});
	}
	getProduct();
</script>
<div class="body">
	<div class="container">
	
		<div id="categories"></div>
		<table>
			<tr>
				<td><input type="checkbox" id="all"><a href="javascript:;" id="deleteAll">删除所选</a></td>
				<td>商品名称</td>
				<td>商品价格</td>
				<td>商品描述</td>
				<td>商品图片</td>
				<td>操作</td>
			</tr>
		</table>
	
	</div>
</div>
<%@ include file="/manage/footer.jsp"%>