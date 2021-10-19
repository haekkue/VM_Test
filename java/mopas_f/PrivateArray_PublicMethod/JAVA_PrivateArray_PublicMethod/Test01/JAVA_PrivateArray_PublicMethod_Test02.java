/* 
멤버 변수 colors는 private 배열리스트로  선언되었지만 public으로 선언된 getColors() 메소드를 통해 참조를 얻을 수 있습니다. 
*/
public class CS
{
	private ArrayList colors = new ArrayList();
	// 보안약점 
	public ArrayList getColors() {
		return colors;
	}
	// 생략 ...
}