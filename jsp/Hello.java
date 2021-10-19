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
		out.println(m_strHello.length());		//  확실히 취약점 
	}

	publiv void Bad2() {
		out.println(returnHello());	
	}
}
