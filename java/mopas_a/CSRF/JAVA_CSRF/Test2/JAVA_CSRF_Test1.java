package a;
import a.test22;
public class test11
{
	/* 
	����ڰ� ��û�� ������ ������ ��û���� ���θ� �������� ����ϹǷ� ���Ⱦ����� �߻��� �� �ֽ��ϴ�. 
	*/
	private void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
		String data;
		data = request.getParameter("name");
	
		// ... ����
	
		// ���Ⱦ��� 
		response.getWriter().write(data);
	}
	
	/* 
	����ڰ� ��û�� ������ ������ ��û���� ���θ� ����� ���ٱ��� ������ ���Ե� ��ū�� �̿��Ͽ� ���������� ���Ե� ��ū���� ��û�� ���Ե� ��ū���� �񱳸� ���� Ȯ���մϴ�.
	��ū�� ���� �� ���� ������ �����մϴ�. 
	*/
	private void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
		String data;
		
		data = request.getParameter("name");
	
		HttpSession session = request.getSession(true);
		String trustedToken = (String) session.getAttribute("token");
		
		// ���� : ���Ǻ����� "token"�� ��û������ "token"�� �� 
		if (request.getParamter("token") == null || !request.getParameter("token").equals(trustedToken))
		{
			return;
		}
		test22.bad();
		
		response.getWriter().write(data);
		
		
	}

}