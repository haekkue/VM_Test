public class CS
{

/* 
아래 외부 입력값 data 변수가 아무런 필터링없이 에러 메시지로 출력되어 XSS 공격이 빌미를 제공합니다.
*/
public void bad1(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;

  	Connection conn = null;
  	PreparedStatement statement = null;
  	ResultSet rs = null;
  	try {
    		conn = IO.getDBConnection(); 
    		statement = conn.prepareStatement("select name from users where id=0");
    		rs = statement.executeQuery();
    		data = rs.getString(1); 
  	}

  	if (data != null)
  	{
		// 보안약점 : 외부 입력값이 필터링없이 화면에 에러 메시지로 출력
    		response.sendError(404, "<br>bad() - Parameter name has value " + data);
  	}
}

public void bad2(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;

  	Connection conn = null;
  	PreparedStatement statement = null;
  	ResultSet rs = null;

	System.in.read(data);

  	if (data != null)
  	{
		// 보안약점 : 외부 입력값이 필터링없이 화면에 에러 메시지로 출력
    		response.sendError(404, "<br>bad() - Parameter name has value " + data);
  	}
}

public void bad3(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;

  	Connection conn = null;
  	PreparedStatement statement = null;
  	ResultSet rs = null;
  	try {
    		conn = IO.getDBConnection(); 
    		statement = conn.prepareStatement("select name from users where id=0");
  	}

  	if (data != null)
  	{
		// 보안약점 : 외부 입력값이 필터링없이 화면에 에러 메시지로 출력
    		response.sendError(404, "<br>bad() - Parameter name has value " + (statement.executeQuery()).getString());
  	}
}

/*
외부 입력값은 반드시 필터링 처리하여 출력합니다.
*/
public void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;

  	Connection conn = null;
  	PreparedStatement statement = null;
  	ResultSet rs = null;
  	try {
    		conn = IO.getDBConnection();
    		statement = conn.prepareStatement("select name from users where id=0");
    		rs = statement.executeQuery();
    		data = rs.getString(1); 
  	}

  	if (data != null)
  	{
		// 수정 : 외부 입력값의 특수문자를 필터링
    		response.sendError(404, "<br>bad() - Parameter name has value " + data.replaceAll("<",""));
  	}
}

}