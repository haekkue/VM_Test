/* 
userRoles 필드는 private이지만, public인 setUserRoles()를 통해 외부의 배열이 할당되면, 사실상 public 필드가 됩니다. 
*/
public class U247 extends HttpServlet 
{
	private String[] userRoles;
	private String[] pwd;
	private String tmp;
	private Array arr;

  	public void setUserRoles(String[] userRoles1) {
		// 보안약점
		this.userRoles = userRoles1;
  	}
	
		public void setValues(String[] userRoles1, String[] pwd, String tmp) {
		// 보안약점
			this.userRoles = userRoles1;
			this.pwd = pwd;
			this.tmp = tmp;
  	}
  	
  	public void setArray(Array arr) {
		// 보안약점
		this.arr = arr;
  	}
}

