/*
널포인터역참조 - Inner class 처리
*/

class Outer
{
	private String m_strA=null;

	public void bad()
	{
		System.out.println( "Outer strA : " +m_strA.length());	// m_strA(멤버) 취약점, m_strA 멤버변수 연결 확인

		// 내부클래스 - 메쏘드 안에서 선언 
		class InnerLocal
		{
			public String m_strA =null;
			public String m_strB = null;

			public void bad()
			{
				System.out.println("InnerLocal strA :" + m_strA.length());	// m_strA(멤버) 취약점, m_strA 멤버변수 연결 확인
				System.out.println("InnerLocal strB :" + m_strB.length());	// m_strB(멤버) 취약점, m_strB 멤버변수 연결 확인
			}
		}

		// 내부클래스 변수
		InnerLocal il = new InnerLocal();
		il.bad();
		System.out.println("InnerLocal strB :" + il.m_strB.length());		// m_strB(멤버) 취약점, m_strB 멤버변수 연결 확인
   }
   
	// 내부클래스 - 클래스 안에서 선언
	class InnerMember
	{
		public String m_strB=null;

		public void bad()
		{
			System.out.println(" InnerMember m_strA : " + m_strA);			// m_strA(외부멤버) 취약점, m_strA 외부멤버변수 연결 확인
			System.out.println(" InnerMember m_strB : " + m_strB);			// m_strB(멤버) 취약점, m_strB 멤버변수 연결 확인
		}
	}
   
	// 정적내부클래스 
	static class InnerStatic
	{
		public static String m_strD = null;

		public void bad()
		{
			//System.out.println("InnerStatic m_strA : " + m_strA);        	//static은 static끼리만 접근하기 때문에 에러가 난다.
			System.out.println("InnerStatic m_strD : " + m_strD);			// m_strD(멤버) 취약점, m_strD 멤버변수 연결 확인
		}
	}
}

public class Test
{
	public static void main(String[] args)
    {
		//Member Inner class
		Outer.InnerMember im = new Outer(). new InnerMember();
		im.bad();
		   
		//Local Inner class
		Outer out = new Outer();
		out.bad();
		   
		//Static Inner class
		Outer.InnerStatic is = new Outer.InnerStatic();
		is.bad();
		System.out.println("InnerStatic m_strD : " + Outer.InnerStatic.m_strD);	// m_strD(정적멤버) 취약점, m_strD 정적멤버변수 연결 확인
    }
}