public class CS
{
/* 
�����θ��� ���� �ش� ��û�� �ŷ��� �� �ִ����� �˻��մϴ�. �׷���, �����ڴ� DNS ĳ�� ���� �����ؼ� ���� �̷��� ���� ������ ��ȸ�� �� �ֽ��ϴ�. 
*/
public void doGet_bad1(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
{
	boolean trusted = false;
	String ip = req.getRemoteAddr();

	InetAddress addr = InetAddress.getByName(ip);

	// ���Ⱦ��� : ȣ��Ʈ�� DNS �̸��� �ŷ��� �� �ִ� ������(trustme.com)�� ���ϴ��� �˻��մϴ�.
	if (addr.getCanonicalHostName().endsWith("trustme.com") ) {
		trusted = true;
	}
	// ���� ...
}

/*
DNS lookup�� ���� ȣ��Ʈ �̸� �񱳸� ���� �ʰ�, IP �ּҸ� ���� ���ϵ��� �����մϴ�.
*/
public void doGet_good(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
{
 	String ip = req.getRemoteAddr();
  	if ( ip == null || "".equals(ip) )
    		return ;

  	String trustedAddr = "127.0.0.1";

	// ����
  	if (ip.equals(trustedAddr) ) {
  	} 
	//...
}
}