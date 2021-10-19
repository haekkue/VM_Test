public class CS
{

/* 
�Ʒ� �ܺ� �Է°� data ������ �ƹ��� ���͸����� ���� �޽����� ��µǾ� XSS ������ ���̸� �����մϴ�.
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
		// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ȭ�鿡 ���� �޽����� ���
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
		// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ȭ�鿡 ���� �޽����� ���
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
		// ���Ⱦ��� : �ܺ� �Է°��� ���͸����� ȭ�鿡 ���� �޽����� ���
    		response.sendError(404, "<br>bad() - Parameter name has value " + (statement.executeQuery()).getString());
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
    		response.sendError(404, "<br>bad() - Parameter name has value " + data.replaceAll("<",""));
  	}
}

}