<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="content">
	<div class="container-fluid">
		<form id="loginForm" action="/loginprocessing" method="POST" style="height:100%;">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="row">
			    <div class="col-md-8">
			        <div class="card">
			            <div class="header">
			                <h4 class="title">LOGIN</h4>
			            </div>
			            <div class="content">
			                <div class="row">
			                    <div class="col-md-8">
			                        <div class="form-group">
			                            <label>ID</label>
			                            <input type="text" class="form-control" name="username" placeholder="Input ID">
			                        </div>
			                    </div>
			                </div>
			                <div class="row">
			                    <div class="col-md-8">
			                        <div class="form-group">
			                            <label>PASSWORD</label>
			                            <input type="password" class="form-control" name="password" placeholder="Input password">
			                        </div>
			                    </div>
			                </div>
			                <div class="row">
			                    <div class="col-md-8">
			                        <div class="form-group">
			                            <span>AUCTIONWINI 테스트 회원 ID/PASSWORD를 그대로 사용합니다 </span>
			                            <br/>
			                            <span>ex)Openbuyer2 / 1111</span>
			                        </div>
			                    </div>
			                </div>
			                <button type="submit" class="btn btn-info btn-fill pull-right btn_login">Login</button>
			                <div class="clearfix"></div>
			            </div>
			        </div>
			    </div>
			</div>
		</form>
	</div>
</div>