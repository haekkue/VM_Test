/* 
�Էµ� �迭�� ���۷����� �ƴ�, �迭�� ���� private �迭�� �Ҵ������ν� private ����μ��� ���ٱ����� ���� �����ݴϴ�. 
*/
public class S247 extends HttpServlet 
{
	private String[] userRoles;

  	public void setUserRoles(String[] userRoles2, String[] pass, String tmp) {
		// ����
 		this.userRoles = new String[userRoles2.length];
    		for(int i = 0; i < userRoles2.length; ++i)
        		this.userRoles[i] = userRoles2[i];
  	}
	// ���� ...
}