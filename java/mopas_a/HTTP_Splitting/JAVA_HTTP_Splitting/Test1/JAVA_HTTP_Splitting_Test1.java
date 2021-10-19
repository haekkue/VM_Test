public class CS
{

/* 외부 입력값을 사용하여 반환되는 쿠키의 값을 설정하고 있습니다. 
그런데 공격자가 Wiley Hacker\r\nHTTP/1.1 200 OK\r\n를 authorName 변수의 값으로 설정할 경우, 의도하지 않은 두 개의 페이지가 전달되며, 두번째 응답 페이지는 공격자가 마음대로 수정 가능합니다. */
public void bad1(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
	response.setContentType("text/html");
	String author = request.getParameter("authorName");
	Cookie cookie = new Cookie("replidedAuthor", author);
	cookie.setMaxAge(1000);

	// 보안약점 : 외부 입력값이 검증없이 HTTP 응답헤더에 사용
	response.addCookie(cookie);
	RequestDispatcher frd = request.getRequestDispatcher("cookieTest.jsp");
	frd.forward(request, response);
}


/* 외부 입력값을 사용하여 반환되는 쿠키의 값을 설정하고 있습니다. 
그런데 공격자가 Wiley Hacker\r\nHTTP/1.1 200 OK\r\n를 authorName 변수의 값으로 설정할 경우, 의도하지 않은 두 개의 페이지가 전달되며, 두번째 응답 페이지는 공격자가 마음대로 수정 가능합니다. */
public void bad2(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
	response.setContentType("text/html");

	// 보안약점 : 외부 입력값이 검증없이 HTTP 응답헤더에 사용
	response.addCookie(new Cookie("replidedAuthor", request.getParameter("authorName")));
	RequestDispatcher frd = request.getRequestDispatcher("cookieTest.jsp");
	frd.forward(request, response);
}

/* 외부 입력값의 Null 여부를 체크하고, 헤더값이 두 개로 나누어 지는 것을 방지하기 위해 replaceAll()을 이용하여 개행문자555를 제거합니다. */
public void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
	response.setContentType("text/html");
	String author = request.getParameter("authorName");
	if (author == null || "".equals(author)) return;
	
	// 수정 : 외부 입력값 필터링
	String filtered_author = author.replaceAll("\r","").replaceAll("\n","");
	Cookie cookie = new Cookie("replidedAuthor", filtered_author);
	cookie.setMaxAge(1000);
	cookie.setSecure(true);
	response.addCookie(cookie);
	RequestDispatcher frd = request.getRequestDispatcher("cookieTest.jsp");
	frd.forward(request, response);
}

}