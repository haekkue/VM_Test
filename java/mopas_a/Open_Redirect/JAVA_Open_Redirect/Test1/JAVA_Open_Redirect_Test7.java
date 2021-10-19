public class CS
{

/*
�Ʒ� �ڵ�� �����ڰ� ������ ���� ��ũ�� ����ڰ� �����ϵ��� �����ν� �ǽ� ����Ʈ ������ �����ϵ��� �� �� �ֽ��ϴ�.
<a href="http://bank.example.com/redirect?url=http://attacker.example.net">Clikc</a>
*/
protected void bad1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	String query = request.getQueryString();
	if (query.contains("url"))
	{
		String url = request.getParameter("url");
		
		// ���Ⱦ��� :  �ܺ� �Է°� url ������ �������� seneRedirct �ڵ� ���� �޼ҵ� ���ڷ� ���
		response.sendRedirect(url);
	}
	// ...
}

protected void bad2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	// ���Ⱦ��� :  �ܺ� �Է°� url ������ �������� seneRedirct �ڵ� ���� �޼ҵ� ���ڷ� ���
	response.sendRedirect(request.getParameter("url"));
	// ...
}

/*
�ܺη� ������ URL�� �����ε��� ȭ��Ʈ ����Ʈ�� �ۼ��� ��, �� �߿��� �����ϰ� �����ν� �������� ���� ����Ʈ���� ������ �����մϴ�.
*/
protected void good(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	// �ٸ� ������ �̵��ϴ� URL ����Ʈ�� ����
	String allowURL[] = { "http:url1.com", "http://url2.com", "http://url3.com"};

	String query = request.getQueryString();
	if (query.contains("url"))
	{
		String url = request.getParameter("url");

		// ���� : �ܺ� �Է°� url ������ ȭ��Ʈ ����Ʈ url�� ���Ͽ� seneRedirct �ڵ� ���� �޼ҵ� ���ڷ� ���
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