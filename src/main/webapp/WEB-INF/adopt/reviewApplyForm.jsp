<%@page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 매니저 일때만 가능하게 -->
<html lang="ko-kr">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>유기동무 입양 상세보기 & 신청 수락/거절</title>
<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="/WEB-INF/navbar.jsp" %>
<body>
	<br>
	<table class="table table-hover table-borderless">
		<thead>
			<tr>
				<th scope="col">no</th>
				<th scope="col">동물 번호</th>
				<th scope="col">신청자 이름</th>
				<th scope="col">신청 날짜</th>
				<!-- 신청날짜를 보여주는 이유는 : 선착순의 느낌도 주기 위해서 -->
				<th scope="col">입양완료 여부</th>
			</tr>
		</thead>
		<tbody> 
	  	  <tr>
			<th>이름</th>
			<td>${apply.userName}</td>
		  </tr>
		  <tr>
			<th>사는 지역</th>
			<td>${apply.address}</td>
		  </tr>
		  <tr>
			<th>신청 반려동물</th>
			<td>${apply.animal_id}</td>
		  </tr>
		  <tr>
			<th>반려동물 유무</th>
			<td>${apply.have_pets}</td>
		  </tr>
		  <tr>
			<th>거주 환경</th>
			<td>${apply.living_environment}</td>
		  </tr>
		  <tr>
			<th>나의 조건</th>
			<td>${apply.content}</td>
		  </tr>
		  <tr>
		  	<th>
		  	<a class="btn btn-outline-danger" href="<c:url value='/adopt/acceptApply'/>" role="button">
		  	<c:param name='apply_id' value='${adopt.apply_id}'/>
		  	수락</a></th>
		  	<th>
		  	<a class="btn btn-outline-secondary" href="<c:url value='/adopt/viewApply'/>" role="button">거부</a></th>
		  </tr>
		</tbody>
	</table>	
	<br> 		     
	<!-- 수정 또는 삭제가  실패한 경우 exception 객체에 저장된 오류 메시지를 출력 -->
	<c:if test="${updateFailed}">
		<h6 class="text-danger"><c:out value="${exception.getMessage()}"/></h6>
    </c:if>    
</body>
</html>