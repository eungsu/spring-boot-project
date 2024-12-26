<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common/tags.jsp" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>애플리케이션</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" >
</head>
<body>
<%@ include file="common/navbar.jsp" %>
<div class="container">
	<div class="row">
		<div class="col-12">
			<h1>로그인</h1>
			<p>사용자이름과 비밀번호를 입력하고 로그인하세요</p>
			
			<c:choose>
				<c:when test="${param.error eq 'fail' }">
					<div class="alert alert-danger">
						<strong>로그인 실패</strong> 아이디 혹은 비밀번호가 올바르지 않습니다.
					</div>
				</c:when>
				<c:when test="${param.error eq 'unauthorized' }">
					<div class="alert alert-danger">
						<strong>인증 요구</strong> 사용자 인증(로그인)이 필요합니다.
					</div>
				</c:when>
				<c:when test="${param.error eq 'forbidden' }">
					<div class="alert alert-danger">
						<strong>접근 거부</strong> 아이디 혹은 비밀번호가 올바르지 않습니다.
					</div>
				</c:when>
			</c:choose>
			
			<form class="border bg-light p-3" action="login" method="post">
				<div class="form-group mb-3">
					<label class="form-label">사용자이름</label>
					<input type="text" class="form-control" name="username" value="hong" />
				</div>
				<div class="form-group mb-3">
					<label class="form-label">비밀번호</label>
					<input type="text" class="form-control" name="password" value="zxcv1234" />
				</div>
				<div class="text-end">
					<button type="submit" class="btn btn-primary">로그인</button>
				</div>
			</form>
		</div>
	</div>
	<div class="row mb-3"></div>
		<div class="col-10">
			<a href="/oauth2/authorization/google" class="btn btn-outline-primary">구글 로그인</a>
			<a href="/oauth2/authorization/kakao" class="btn btn-outline-primary">카카오 로그인</a>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>