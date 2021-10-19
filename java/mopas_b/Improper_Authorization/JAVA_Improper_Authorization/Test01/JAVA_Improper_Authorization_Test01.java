public class CTest
{

/* 
외부의 입력인 name 값이 필터가 아닌 동적인 LDAP 쿼리에서 사용자명으로 사용되었으며, 사용자 인증을 위한 별도의 접근제어 방법이 사용되지 않고 있습니다. 
이는 Anonymous Binding을 허용하는 것으로 볼 수 있습니다. 따라서 임의 사용자의 정보를 외부에서 접근할 수 있게 됩니다. 
*/
public void bad(String sSingleId, int iFlag, String sServiceProvider, String sUid, String sPwd)
{
	env.put(Context.INITIAL_CONTEXT_FACTORY, CommonMySingleConst.INITCTX);
	env.put(Context.PROVIDER_URL, sServiceProvider);
	// 보안약점 : 사용자 인증을 사용하지 않음 
  	env.put(Context.SECURITY_AUTHENTICATION, "none");
  	env.put(Context.SECURITY_PRINCIPAL, sUid);
  	env.put(Context.SECURITY_CREDENTIALS, sPwd);
  	// 생략 ...
}

/* 
사용자 ID와 password를 컨텍스트에 설정한 후 접근하도록 접근제어를 사용합니다. 
*/
public void good(String sSingleId, int iFlag, String sServiceProvider, String sUid, String sPwd)
{
  	env.put(Context.PROVIDER_URL, sServiceProvider);
	// 수정
  	env.put(Context.SECURITY_AUTHENTICATION, "simple");
  	env.put(Context.SECURITY_PRINCIPAL, sUid);
  	env.put(Context.SECURITY_CREDENTIALS, sPwd);
  	// 생략 ...
}


}