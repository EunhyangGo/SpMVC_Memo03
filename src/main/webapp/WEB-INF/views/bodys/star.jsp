<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.star-w{
		width: 205px; /* star.png의 사진 크기*/
	}
	.star-w, .star-r{
		display : inline-block;
		height: 39px; /*별 두줄있는데 반으로 짤라서 회색별만 나타나게됨*/
		background: url( "<c:url value='/images/star.png'/>" )no-repeat;
	}
	
	.star-r{
		background-position: left bottom; /* span이 두개가 겹쳐있는데 두번째 span을 위쪽을 자르고 아래만 보이게 하라는 거, 그러니까 별 중에 빨간색만 보이는 것*/
		line-height : 0;
		vertical-align: top;
		width: 50%;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(function(){
	$("#stars").change(function(){
		let v = $(this).val()
		let w = parseInt(v) * 10
		
		//let w = (parseInt(v) % 10) +1) *10//0부터 10까지만 주겠다는말, +1을 붙이면 1부터 10까지, 10%만 주겟다는 말
		
		$(".star-r").css("width",w+"%")
	}) 
})
</script>
</head>
<body>
<h3>별점주기</h3>
<div>
 	<span class="star-w">
 		<span class="star-r"></span>
 	</span>
</div>
<p>점수 주기:
<input type="range" id="stars" min="0" max="10"> <!-- 점수를 입력하는 칸이 나타남 -->
<p><input type="date">
</body>
</html>