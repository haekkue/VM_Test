public class CBad extends HttpServlet 
{
  	private String name;
  	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    		name = request.getParameter("name");
    		//...
		// ���Ⱦ��� : ���� ���ǿ� ���� ������ ����� �� ����
    		out.println(name + ", thanks for visiting!");
  	}
}
public class CGood extends HttpServlet 
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    		// ���� : ���������� �����Ѵ�.
    		String name = request.getParameter("name");
    		if (name == null || "".equals(name)) return;
		// ���� ...
    		out.println(name + ", thanks for visiting!");
  	}
}