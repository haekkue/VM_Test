/* 
userRoles �ʵ�� private������, public�� setUserRoles()�� ���� �ܺ��� �迭�� �Ҵ�Ǹ�, ��ǻ� public �ʵ尡 �˴ϴ�. 
*/
public class U247 extends HttpServlet 
{
	private String[] userRoles;
	private String[] pwd;
	private String tmp;
	private Array arr;

  	public void setUserRoles(String[] userRoles1) {
		// ���Ⱦ���
		this.userRoles = userRoles1;
  	}
	
		public void setValues(String[] userRoles1, String[] pwd, String tmp) {
		// ���Ⱦ���
			this.userRoles = userRoles1;
			this.pwd = pwd;
			this.tmp = tmp;
  	}
  	
  	public void setArray(Array arr) {
		// ���Ⱦ���
		this.arr = arr;
  	}
}

