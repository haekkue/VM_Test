public class CS
{

/*
아래 코드는 공격자가 다음과 같은 링크를 희생자가 접근하도록 함으로써 피싱 사이트 등으로 접근하도록 할 수 있습니다.
<a href="http://bank.example.com/redirect?url=http://attacker.example.net">Clikc</a>
*/
protected void bad1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	String query = request.getQueryString();
	if (query.contains("url"))
	{
		String url = request.getParameter("url");
		
		// 보안약점 :  외부 입력값 url 변수가 검증없이 seneRedirct 자동 연결 메소드 인자로 사용
		response.sendRedirect(url);
	}
	// ...
}

protected void bad2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	// 보안약점 :  외부 입력값 url 변수가 검증없이 seneRedirct 자동 연결 메소드 인자로 사용
	response.sendRedirect(request.getParameter("url"));
	// ...
}

/*
외부로 연결한 URL과 도메인들은 화이트 리스트로 작성한 후, 그 중에서 선택하게 함으로써 안전하지 않은 사이트로의 접근을 차단합니다.
*/
protected void good(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	// 다른 페이지 이동하는 URL 리스트를 만듬
	String allowURL[] = { "http:url1.com", "http://url2.com", "http://url3.com"};

	String query = request.getQueryString();
	if (query.contains("url"))
	{
		String url = request.getParameter("url");

		// 수정 : 외부 입력값 url 변수를 화이트 리스트 url과 비교하여 seneRedirct 자동 연결 메소드 인자로 사용
		for (int i=0; i<allowURL.length; i++)
		{
			if (url == allowURL[i]) {
				response.sendRedirect(url);
				break;
			}
		}
	}
	// ...
}

}