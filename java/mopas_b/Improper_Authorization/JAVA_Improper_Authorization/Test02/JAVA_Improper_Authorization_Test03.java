public class CS
{

/*
�Ʒ� ���������� ������ ���������� ������, � �޽����� ��ȸ�����ϴ�. �� Ÿ���� �� ���� ������ �� ���� �ִ�.
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
		throw new MyException("������ ����");
	}
	Message msg = LookupMessageObject(msgId);
	// ���Ⱦ���
	if ( msg != null )
	{
		out.println("From" + msg.getUserName());
		out.println("Subject" + msg.getSubField());
		out.println("\n" + msg.getBodyField());
	}
}

/*
������ ����ڿ� �޽��� �ڽ� ����ڰ� ��ġ���� ��쿡�� �ش� �޽����� ��ȸ�� �� �ֵ��� �Ѵ�.
*/
public void good(HttpServletRequest request, HttpServletResponse response)
{
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	if (username==nill || password==null || !isAuthenticatedUser(usename, password))
	{
		throw new MyException("���� ����");
	}

	String msgId = request.getParameter("msgId");
	if ( msgId == null )
	{
		throw new MyException("������ ����");
	}
	Message msg = LookupMessageObject(msgId);

	// ����
	if ( msg != null && username.equals(msg.getUserName()) )
	{
		out.println("From" + msg.getUserName());
		out.println("Subject" + msg.getSubField());
		out.println("\n" + msg.getBodyField());
	}
	else { throw new MyException("���� ����"); }
}

}