/* 
입력된 배열의 레퍼런스가 아닌, 배열의 값을 private 배열에 할당함으로써 private 멤버로서의 접근권한을 유지 시켜줍니다. 
*/
public class S247 extends HttpServlet 
{
	private String[] userRoles;

  	public void setUserRoles(String[] userRoles2, String[] pass, String tmp) {
		// 수정
 		this.userRoles = new String[userRoles2.length];
    		for(int i = 0; i < userRoles2.length; ++i)
        		this.userRoles[i] = userRoles2[i];
  	}
	// 생략 ...
}