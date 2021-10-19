/*
널포인터역참조
  1. 부모 멤버변수 사용 
  2. 부모, 자식 멤버 변수 이름 중복
  3. this/base 사용
  4. 다른 클래스 멤버 변수 이름 중복
*/

class CSon
{
	String m_strTest = null;

	public void bad()
	{
		System.out.println("Test : " + m_strTest.length());	// m_strTest(멤버) 취약점, m_strTest 멤버변수 연결 확인
	}
 }

class CTom
{
	String m_strMyMy = null;
	String m_strTest = null;

	public void bad2(String strTest, String strMyMy)
	{
		String strTemp = null;
		strTest = null;
		strMyMy = null;
		System.out.println("Test : " + strTest.length());   		// strTest(파라미터) 취약점, strTest 파라미터변수 연결 확인
		System.out.println("Test : " + strMyMy.length());   		// strMyMy(파라미터) 취약점, strMyMy 파라미터변수 연결 확인
	}
}

class CSam extends CTom
{
   String m_strTest = null;

   public void bad3(String[] args)
   {
	   String strTest = null;
	   System.out.println("Test : " + m_strTest.length());			// m_strTest(멤버) 취약점, strTest 멤버변수 연결 확인
	   System.out.println("Test : " + m_strMyMy.length());			// m_strMyMy(부모멤버) 취약점, m_strMyMy 부모멤버변수 연결 확인
	   System.out.println("Test : " + this.m_strTest.length());		// m_strTest(this,멤버) 취약점, strTest 멤버변수 연결 확인
	   System.out.println("Test : " + super.m_strTest.length());	// m_strTest(부모멤버) 취약점, m_strTest 부모멤버변수 연결 확인
	   System.out.println("Test : " + strTest.length());			// strTest(지역) 취약점, strTest 지역변수 연결 확인
	}
}

public class Test
{
	public static void main(String[] args)
	{
		System.out.println("Hello World !!");
	}
}
