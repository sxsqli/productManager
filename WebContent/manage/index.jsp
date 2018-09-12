<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
<%@ include file="/manage/header.jsp"%>
<script type="text/javascript">
	$(function() {
		$(".header a").eq(0).addClass("current");
		$.getJSON("${pageContext.request.contextPath}/ProductServlet", {
			"op" : "find",
			"categoryid" : "${param.categoryid}"
		}, function(data) {
			$(data).each(function() {
				$(".body .shows").append("<div class='show fl'>"
						+"<img src='${pageContext.request.contextPath}"+this.path+"' onerror='$(this).hide();'/>"
						+this.pname+"&nbsp;&nbsp;￥"+this.price
						+"</div>");
			});
		});
	});
</script>
<div class="body">
	<div class="container">
	
		<h1>欢迎使用</h1>
		<div class="shows cf"></div>
		
	</div>
</div>
<%@ include file="/manage/footer.jsp"%>