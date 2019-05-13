<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="frm">
	<input type="hidden" id="pageName" name="pageName"/>
	<input type="hidden" id="arr" name="arr"/>
</form>

<div class="sidebar" data-color="orange" data-image="img/full-screen-image-3.jpg">
     <div class="logo">
        <a href="/" class="logo-text">
            TEST
        </a>
    </div>
	<div class="logo logo-mini">
		<a href="/" class="logo-text">
			TEST
		</a>
	</div>
   	<div class="sidebar-wrapper">
        <div class="user">
            <div class="photo">
                <img src="/resources/img/lazy.svg" />
            </div>
            <div class="info">
                <!-- <a data-toggle="collapse" href="#collapseExample" class="collapsed">
                    TEST
                    <b class="caret"></b>
                </a>
                <div class="collapse" id="collapseExample">
                    <ul class="nav">
                        <li><a href="#">My Profile</a></li>
                        <li><a href="#">Edit Profile</a></li>
                        <li><a href="#">Settings</a></li>
                    </ul>
                </div> -->
            </div>
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
		
		$(".menu").click(function() {
			var menu = $(this).attr("id");
			left.pageSubmitFn(menu);	
		});
	});
	
	var left = {
		pageSubmitFn : function(menu) {
			$("#frm").attr("action", "/" + menu);
			$("#pageName").val(menu);
			
			$("#frm").submit();
		}		
	};
	
</script>
