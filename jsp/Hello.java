package test;

public class Hello 
{
	public String m_strHello;

	Hello() {
		m_strHello = null;
	}	

	public String returnHello() {
		String hello = null;
		return hello;
	}

	public void Bad() {
		out.println(m_strHello.length());		//  Ȯ���� ����� 
	}

	publiv void Bad2() {
		out.println(returnHello());	
	}
}
