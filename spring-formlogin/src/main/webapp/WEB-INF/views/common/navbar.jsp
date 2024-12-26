<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="tags.jsp" %>
<nav class="navbar navbar-expand-lg bg-body-tertiary mb-3">
	<div class="container">
		<a class="navbar-brand" href="/home">애플리케이션</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link" aria-current="page" href="/home">홈</a>
				</li>
			</ul>
			<ul class="navbar-nav">
				<security:authorize access="!isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link" aria-current="page" href="/signup">회원가입</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" aria-current="page" href="/login">로그인</a>
					</li>
				</security:authorize>
				<security:authorize access="isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link" aria-current="page" href="/logout">로그아웃</a>
					</li>
				</security:authorize>
			</ul>
		</div>
	</div>
</nav>