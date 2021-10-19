public class CBad extends HttpServlet 
{
  	private String name;
  	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    		name = request.getParameter("name");
    		//...
		// 보안약점 : 경쟁 조건에 의해 계정이 노출될 수 있음
    		out.println(name + ", thanks for visiting!");
  	}
}
public class CGood extends HttpServlet 
{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    		// 수정 : 지역변수로 변경한다.
    		String name = request.getParameter("name");
    		if (name == null || "".equals(name)) return;
		// 생략 ...
    		out.println(name + ", thanks for visiting!");
  	}
}