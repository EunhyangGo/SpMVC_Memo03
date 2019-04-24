<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>

var a = 30

/*  이곳에서 body tag에 바탕색을 red로 설정하는 js코드를 작성을 한다면
	js코드는 문법 오류를 낼 것이다.
	그리고 이후의 js코드는 모두 무시해버린다.
	그래서 표준 js코드에서는 tag에 어떤 속성을 변경하는 코드는
	문서 끝의 body, html이 끝난 이후에 작성을 한다.
	jq는 아래문장을 만나면 실행이 정지가 됨. 그리고 맨 마지막 실행..?
*/


// 이러한 불편때문에 jQuery는 document.ready라는 키워드를 만들었다.
// document.ready(function(){})문법은 
// 현재 문서의 html이 모두 완성된 다음에 function으로 지정된 영역의 코드를 실행하라
// 하는 것이다.
// 최초의 코드 : JQuery(documnet.ready(function(){}))
// 매번 jquery라고 쓰기 그래서 간편하게 $< 표시로 바꿔버림.
$(document).ready(function(){
	
})

// 위에랑 똑같은 코드. 3.1부터 사용가능
$(function(){
	//$(".q").on("click",function(){})
	//$(".q").bind("click",function(){})
	$(".q").click(function(){ /*this에 뭐가 담겨있는지 몰라서 data-num="1"이라는걸 추가함*/
		let num = $(this).attr("data-num") //임의로 attr(속성)에 이름을 붙일 수 있음
		let id = $(this).attr("id")
		//alert(num)
		//$("#as" + num).css("display","block") //누르면 p태그가 안보이다가 보인다
		$("#as"+num).toggleClass("block") //누르면 p태그가 보이고 한번더 누르면 p태그가 사라짐
	})
	
})
</script>
</head>
<body id="body">
	<header>
		<nav>
		
		</nav>
	</header>
	<style>
	.q{
		background-color:yellow;
		color: black;
		font-size: 30px;
		margin: 10px;
	}
	
	.as{
		background-color:white;
		color: black;
		font-size: 30px;
		margin: 10px;
		display:none;
	}
	
	.block{
		display:block;
	}
	</style>
	<section>
		<article>
			<div class="q" id="q1" data-num="1">DIV 1</div>
				<p class="as" id="as1">P1 Korea</p>
				
			<div class="q" id="q2" data-num="2">DIV 2</div>
				<p class="as" id="as2">P2 대한민국</p>
				
			<div class="q" id="q3" data-num="3"> DIV 3</div>
				<p class="as" id="as3">P3 우리나라</p>
		
		</article>
	</section>
	<footer>
	
	</footer>

</body>
<script>
document.getElementById("body").style.backgorundColor ="blue"
</script>
</html>