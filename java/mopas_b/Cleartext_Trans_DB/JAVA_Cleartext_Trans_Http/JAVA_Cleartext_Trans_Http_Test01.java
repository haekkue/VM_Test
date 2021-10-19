public class CS
{

/*
HTTPS�θ� �����ϴ� ��� �ΰ��� ������ ���� ��Ű�� �����ϴ� �������� ���� �Ӽ��� �������� ������ �����ڿ��� ������ ����� �� �ֽ��ϴ�.
*/
private final String ACCOUT_ID = "accout";

public void setupCookies_bad(HttpServletRequest req, HttpServletResponse res)
{
	String acctID = req.getParameter("accoutID");
	Cookie c = new Cookie(ACCOUNT_ID, acctID);
	// ���Ⱦ��� : ���ȼӼ��� �������� ���� ��Ű 
	res.addCookie(c);
}

/*
HTTPS�θ� �����ϴ� ��� �ΰ��� ������ ���� ��Ű�� ����� ��쿡�� �ݵ�� Cookie ��ü�� setSecure(true)�� ȣ���Ͽ��� �մϴ�.
*/
public void setupCookies_good(HttpServletRequest req, HttpServletResponse res)
{
	String acctID = req.getParameter("accoutID");
	// ���� ��ȿ�� ���� 
	if (acctID == null || "".equals(acctID)) return;
	String filtered_ID = acctID.replaceAll("\r", "");

	Cookie c = new Cookie(ACCOUNT_ID, acctID);
	// ���� : �ΰ��� ������ ���� ��Ű�� ������ ������ ���� �Ӽ��� �����Ͽ��� �Ѵ�.
	c.setSecure(true);
	res.addCookie(c);
}

}