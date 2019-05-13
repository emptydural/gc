<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="user" value="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal}"/>

<form id="frm">
	<input type="hidden" id="pageName" name="pageName"/>
</form>
<form id="logoutForm" name="logoutForm" action="/logout" method="POST">
</form>

<div class="sidebar" data-color="orange" data-image="/resources/img/full-screen-image-3.jpg">
	 <div class="logo">
		<a href="/" class="logo-text">
			SJTEST
		</a>
	</div>
	<div class="logo logo-mini">
		<a href="/" class="logo-text">
			SJTEST
		</a>
	</div>
	<div class="sidebar-wrapper">
		<div class="user">
			<div class="photo">
				<img src="/resources/img/lazy.svg" />
			</div>
			<c:choose>
				<c:when test="${empty user.username}">
					<div class="info">
						<a data-toggle="collapse" href="#collapseExample" class="collapsed">
							NO USER
							<b class="caret"></b>
						</a>
						<div class="collapse" id="collapseExample">
							<ul class="nav">
								<li><a href="/login">LOGIN</a></li>
							</ul>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="info">
						<a data-toggle="collapse" href="#collapseExample" class="collapsed">
							${user.username}
							<b class="caret"></b>
						</a>
						<div class="collapse" id="collapseExample">
							<ul class="nav">
								<li id="logout"><a href="javascript:;">LOGOUT</a></li>
							</ul>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<ul class="nav">
			<li id="homecopy" class="menu">
				<a href="javascript:;">
					<i class="pe-7s-home"></i>
					<p>(TEST)error</p>
				</a>
			</li>
			
			<li id="nourl" class="menu">
				<a href="javascript:;">
					<i class="pe-7s-home"></i>
					<p>(TEST)noUrl</p>
				</a>
			</li>
		</ul>
	</div>
</div>

<script>

	$(function() {
		var pageName = "<c:out value="${param.pageName}"/>";
		 
		if (pageName != "") {
			$("#" + pageName).addClass("active");
		}
		
		/*메뉴 이동*/
		$(".menu").click(left, left.menuClick);
		/*로그아웃*/
		$("#logout").click(left.logout);
	});
	
	var left = {
		/*id에 의한 url 생성*/
		pageSubmitFn : function(menu) {
			$("#frm").attr("action", "/" + menu);
			$("#pageName").val(menu);
			
			$("#frm").submit();
		},
		/*메뉴 클릭시 id를 받아 위의 함수 호출*/
		menuClick : function(e) {
			var menu = $(this).attr("id");
			e.data.pageSubmitFn(menu);
		},
		/*로그아웃*/
		logout : function() {
			$('#logoutForm').submit();
		}
	};
	
</script>
