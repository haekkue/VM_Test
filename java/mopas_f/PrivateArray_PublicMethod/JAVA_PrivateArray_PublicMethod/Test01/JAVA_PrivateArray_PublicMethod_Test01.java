/* 
��� ���� colors�� private �迭��  ����Ǿ����� public���� ����� getColors() �޼ҵ带 ���� ������ ���� �� �ֽ��ϴ�. 
*/
public class CS
{
	private String[] colors;
	private String parret;
	
	// ���Ⱦ��� : private �迭�� public �޼ҵ尡 ��ȯ
	public String[] getColors() { return colors; }
	// ���� ...

	public String[] getColors2() {
		String[] ret = null;
		if (this.colors != null) {
			ret = new String[colors.length];
			
    }
    
    return ret;
  }
  
  public String getColor3(){
  	parret = "a";
  	
  	return parret;
	}
}