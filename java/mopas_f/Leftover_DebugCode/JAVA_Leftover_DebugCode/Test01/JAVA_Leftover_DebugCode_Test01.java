/* 
main() 메소드 내에 화면에 출력하는 디버깅 코드를 포함하고 있습니다. 
J2EE의 경우 main() 메소드 사용이 필요 없으며, 개발자들이 콘솔 응용프로그램으로 화면에 디버깅 코드를 사용하는 경우가 일반적입니다. 
*/
public class U489 extends HttpServlet 
{
	
	// 보안약점 : 테스트를 위한 main()함수나 디버깅용 로그 출력문 등이 남아 있다.
  	public static void main(String args[]) {
    		System.err.printf("Print debug code");
  	}
  	// 생략 ...
}


