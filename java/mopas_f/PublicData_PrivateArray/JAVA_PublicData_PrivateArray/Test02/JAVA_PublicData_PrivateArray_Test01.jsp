<%
/* 
userRoles �ʵ�� private������, public�� setUserRoles()�� ���� �ܺ��� �迭�� �Ҵ�Ǹ�, ��ǻ� public �ʵ尡 �˴ϴ�. 
*/
public class U247 extends HttpServlet 
{
	private String[] userRoles;

  	public void setUserRoles(String[] userRoles) {
		// ���Ⱦ���
		this.userRoles = userRoles;
  	}
	//... 
}
%>