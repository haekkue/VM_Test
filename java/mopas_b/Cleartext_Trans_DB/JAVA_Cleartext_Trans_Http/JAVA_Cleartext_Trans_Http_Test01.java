public class CS
{

/*
HTTPS로만 서비스하는 경우 민감한 정보를 가진 쿠키를 전송하는 과정에서 보안 속성을 설정하지 않으면 공격자에게 정보가 노출될 수 있습니다.
*/
private final String ACCOUT_ID = "accout";

public void setupCookies_bad(HttpServletRequest req, HttpServletResponse res)
{
	String acctID = req.getParameter("accoutID");
	Cookie c = new Cookie(ACCOUNT_ID, acctID);
	// 보안약점 : 보안속성이 설정되지 않은 쿠키 
	res.addCookie(c);
}

/*
HTTPS로만 서비스하는 경우 민감한 정보를 가진 쿠키를 사용할 경우에는 반드시 Cookie 객체의 setSecure(true)를 호출하여야 합니다.
*/
public void setupCookies_good(HttpServletRequest req, HttpServletResponse res)
{
	String acctID = req.getParameter("accoutID");
	// 계정 유효성 점검 
	if (acctID == null || "".equals(acctID)) return;
	String filtered_ID = acctID.replaceAll("\r", "");

	Cookie c = new Cookie(ACCOUNT_ID, acctID);
	// 수정 : 민감한 정보를 가진 쿠키를 전송할 때에는 보안 속성을 설정하여야 한다.
	c.setSecure(true);
	res.addCookie(c);
}

}