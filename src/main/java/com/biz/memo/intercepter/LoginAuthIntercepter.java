package com.biz.memo.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.biz.memo.model.MemberVO;

// intercepter하도록 만든 클래스
public class LoginAuthIntercepter extends HandlerInterceptorAdapter {

	@Override
	//httpservlet에는 response or request정보가 담겨있다.
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		
		// request정보중에 login정보가 있는가를 검사해보고
		// login정보가 있으면(not null)이면 Controller에게 제어권을
		// 넘기고 그렇지 않으면 login_form을 보여서
		// login을 하도록 하겠다.
		
		// 1. HttpServletRequest에서 필요한 session정보만 추출하자
		// 	 preHandler는 Overried된 method이기 때문에 마음대로
		// 	 매개변수를 바꿀 수 없어서 선행작업이 필요하다.
		HttpSession session = request.getSession();
		
		// 가. 혹시 Login정보에 Role정보가 필요할때 
		MemberVO memberVO = (MemberVO)session.getAttribute("LOGIN_INFO"); //memberVO정보
		
		// 나. 그냥 Login되었는가만 보고싶을때(알고싶을때)
		Object object = session.getAttribute("LOGIN_INFO");
		
		// 2. 추출한 Login정보가 null이 아닌가를 검사
		if(memberVO ==null) { // login이 안되어있으면
			// login_form으로 redirect
			
			response.sendRedirect("login");
			return false;
		}else {
			// memberVO의 Role항목이 있다면 해당 Role을 검사해서 
			/*
			 * if(memberVO.getM_role() =="USER"){
			 * 		//일반사용자
			 *		// 만약 관리자와 관련된 것을 요청했으면
			 *		login_form으로 redirect
			 * }else(memberVO.getM_role() =="ADMIN") {
			 * 		//관리자이면 계속 진행
			 * }
			 */
		}
		//return super.preHandle(request, response, handler);
		/* 최종 return값이 true이면 Controller에게 request정보를 전달해서
		 *	 계속 업무를 수행하라
		 * 
		 * 최종 return값이 false이면 더이상 진행하지 말고 
		 * 모든것을 멈추어라
		 */
		return true; 
		//리턴되는 값이 아주 중요, true하면 dispatcher는 권한을 controller에게 전달
		// false면 모든게 다 정지되고 사라짐
	}

}
