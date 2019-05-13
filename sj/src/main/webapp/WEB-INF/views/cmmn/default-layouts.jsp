<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"  prefix="tiles"%>
<!DOCTYPE html>
<html>
	<head>
		<tiles:insertAttribute name="header"/>
	</head>
	<body>
		<div class="wrapper">
	  		<tiles:insertAttribute name="left"/>
	  		<div class="main-panel">	
			  	<tiles:insertAttribute name="nav"/>
				<tiles:insertAttribute name="content"/>
			</div>
		</div>
	</body>
	
		<!--   Core JS Files and PerfectScrollbar library inside jquery.ui   -->
	<script src="/resources/js/jquery-ui.min.js" type="text/javascript"></script>
	<script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>

	<!--  Forms Validations Plugin -->
	<!-- <script src="/resources/js/jquery.validate.min.js"></script> -->

	<!--  Checkbox, Radio, Switch and Tags Input Plugins -->
	<script src="/resources/js/bootstrap-checkbox-radio-switch-tags.js"></script>

	<!-- Wizard Plugin    -->
	<script src="/resources/js/jquery.bootstrap.wizard.min.js"></script>

    <!--  Bootstrap Table Plugin    -->
	<!-- <script src="/resources/js/bootstrap-table.js"></script> -->

	<!--  Plugin for DataTables.net  -->
	<!-- <script src="/resources/js/jquery.datatables.js"></script> -->

    <!-- Light Bootstrap Dashboard Core javascript and methods -->
	<script src="/resources/js/light-bootstrap-dashboard.js"></script>
</html>