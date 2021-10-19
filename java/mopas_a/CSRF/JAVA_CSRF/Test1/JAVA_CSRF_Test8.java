public class CS
{
/* 
사용자가 요청한 내용이 위조된 요청인지 여부를 검증없이 사용하므로 보안약점이 발생할 수 있습니다. 
*/
private void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
	String data;
	data = request.getParameter("name");

	// ... 생략

	// 보안약점 
	response.getWriter().write(data);
}

/* 
사용자가 요청한 내용이 위조된 요청인지 여부를 사용자 접근권한 정보가 포함된 토큰을 이용하여 세션정보에 포함된 토큰값과 요청에 포함된 토큰값의 비교를 통해 확인합니다.
토큰값 생성 및 도출 과정은 생략합니다. 
*/
private void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
	String data;
	data = request.getParameter("name");

	HttpSession session = request.getSession(true);
	String trustedToken = (String) session.getAttribute("token");
	
	// 수정 : 세션변수값 "token"과 요청변수값 "token"과 비교 
	if (request.getParamter("token") == null || !request.getParameter("token").equals(trustedToken))
	{
		return;
	}
	response.getWriter().write(data);
}

}