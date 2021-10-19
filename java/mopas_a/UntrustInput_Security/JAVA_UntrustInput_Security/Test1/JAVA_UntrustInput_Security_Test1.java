public class CS
{

    /* 
    ������� role�� ������ �� ����� �������� ��Ű�� role�� �Ҵ�� ���� ����ϰ� �־�, �� ���� ����ڿ� ���� ����Ǵ� ��� ����� role���� �ǵ����� ���� ������ �Ҵ�� �� �ֽ��ϴ�. 
    */
    public void bad(HttpServletRequest request, HttpServletResponse response)
    {
    	Cookie[] cookies = request.getCookies();
    	for (int i=0; cookies != null && i<cookies.length; i++)
    	{
    		Cookie c = cookies[i];
    		if (c.getName().equals("role"))
    		{
    			// ���Ⱦ��� : �ܺ� �Է°��� �Ҵ�
    			userRole = c.getValue();
    		}
    	}
    }
    
    /* 
    ����� ����, �������� �� ���Ȱ����� ����ϴ� ���� ����� �Է°��� ������� �ʰ� ���� ���ǰ��� Ȱ���մϴ�. 
    */
    public void good(HttpServletRequest request, HttpServletResponse response)
    {
    	// ...
    	// ����
    	HttpSession session = context.getSession(id);
    	String userRole  = (String)session.getValue("role");
    	// ...
    }
}
