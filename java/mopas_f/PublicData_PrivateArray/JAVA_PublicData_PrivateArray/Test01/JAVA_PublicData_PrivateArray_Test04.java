/* 
userRoles 필드는 private이지만, public인 setUserRoles()를 통해 외부의 배열이 할당되면, 사실상 public 필드가 됩니다. 
*/
public class U247 extends HttpServlet 
{

	private Array arr;


  	public void setArray(Array arr) {
		// 보안약점
		this.arr = arr;
  	}
}

