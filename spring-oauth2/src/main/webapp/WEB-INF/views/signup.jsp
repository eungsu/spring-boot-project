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
			<h1>회원가입</h1>
			<p>사용자 정보를 입력하고, 회원가입하세요</p>
			
			<form class="border bg-light p-3" action="signup" method="post">
				<div class="form-group mb-3">
					<label class="form-label">사용자이름</label>
					<input type="text" class="form-control" name="username" value="hong" />
				</div>
				<div class="form-group mb-3">
					<label class="form-label">비밀번호</label>
					<input type="text" class="form-control" name="password" value="zxcv1234" />
				</div>
				<div class="form-group mb-3">
					<label class="form-label">이메일</label>
					<input type="text" class="form-control" name="email" value="hong@gmail.com" />
				</div>
				<div class="form-group mb-3">
					<label class="form-label">닉네임</label>
					<input type="text" class="form-control" name="nickname" value="홍길동" />
				</div>
				<div class="text-end">
					<button type="submit" class="btn btn-primary">회원가입</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>