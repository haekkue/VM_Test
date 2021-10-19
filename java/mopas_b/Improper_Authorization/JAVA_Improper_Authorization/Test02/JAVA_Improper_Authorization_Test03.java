public class CS
{

/*
아래 예제에서는 인증이 성공적으로 끝나면, 어떤 메시지도 조회가능하다. 즉 타인의 메 시지 정보를 볼 수가 있다.
*/
public void bad(HttpServletRequest request, HttpServletResponse response)
{
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	if (username==null || password==null || !isAuthenticatedUser(usename, password))
	{
		throw new Exception("invalid username or password");
	}

	String msgId = request.getParameter("msgId");
	if ( msgId == null )
	{
		throw new MyException("데이터 오류");
	}
	Message msg = LookupMessageObject(msgId);
	// 보안약점
	if ( msg != null )
	{
		out.println("From" + msg.getUserName());
		out.println("Subject" + msg.getSubField());
		out.println("\n" + msg.getBodyField());
	}
}

/*
인증한 사용자와 메시지 박스 사용자가 일치했을 경우에만 해당 메시지를 조회할 수 있도록 한다.
*/
public void good(HttpServletRequest request, HttpServletResponse response)
{
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	if (username==nill || password==null || !isAuthenticatedUser(usename, password))
	{
		throw new MyException("인증 에러");
	}

	String msgId = request.getParameter("msgId");
	if ( msgId == null )
	{
		throw new MyException("데이터 오류");
	}
	Message msg = LookupMessageObject(msgId);

	// 수정
	if ( msg != null && username.equals(msg.getUserName()) )
	{
		out.println("From" + msg.getUserName());
		out.println("Subject" + msg.getSubField());
		out.println("\n" + msg.getBodyField());
	}
	else { throw new MyException("권한 에러"); }
}

}