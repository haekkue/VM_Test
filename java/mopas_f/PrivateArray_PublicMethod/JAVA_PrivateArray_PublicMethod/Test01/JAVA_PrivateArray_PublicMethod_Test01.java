/* 
멤버 변수 colors는 private 배열로  선언되었지만 public으로 선언된 getColors() 메소드를 통해 참조를 얻을 수 있습니다. 
*/
public class CS
{
	private String[] colors;
	private String parret;
	
	// 보안약점 : private 배열을 public 메소드가 반환
	public String[] getColors() { return colors; }
	// 생략 ...

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