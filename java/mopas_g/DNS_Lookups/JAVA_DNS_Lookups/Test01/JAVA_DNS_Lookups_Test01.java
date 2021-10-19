public class CS
{
/* 
도메인명을 통해 해당 요청이 신뢰할 수 있는지를 검사합니다. 그러나, 공격자는 DNS 캐쉬 등을 조작해서 쉽게 이러한 보안 설정을 우회할 수 있습니다. 
*/
public void doGet_bad1(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
{
	boolean trusted = false;
	String ip = req.getRemoteAddr();

	InetAddress addr = InetAddress.getByName(ip);

	// 보안약점 : 호스트의 DNS 이름이 신뢰할 수 있는 도메인(trustme.com)에 속하는지 검사합니다.
	if (addr.getCanonicalHostName().endsWith("trustme.com") ) {
		trusted = true;
	}
	// 생략 ...
}

/*
DNS lookup에 의한 호스트 이름 비교를 하지 않고, IP 주소를 직접 비교하도록 수정합니다.
*/
public void doGet_good(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
{
 	String ip = req.getRemoteAddr();
  	if ( ip == null || "".equals(ip) )
    		return ;

  	String trustedAddr = "127.0.0.1";

	// 수정
  	if (ip.equals(trustedAddr) ) {
  	} 
	//...
}
}