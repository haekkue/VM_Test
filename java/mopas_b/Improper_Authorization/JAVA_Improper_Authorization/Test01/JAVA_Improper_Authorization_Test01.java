public class CTest
{

/* 
�ܺ��� �Է��� name ���� ���Ͱ� �ƴ� ������ LDAP �������� ����ڸ����� ���Ǿ�����, ����� ������ ���� ������ �������� ����� ������ �ʰ� �ֽ��ϴ�. 
�̴� Anonymous Binding�� ����ϴ� ������ �� �� �ֽ��ϴ�. ���� ���� ������� ������ �ܺο��� ������ �� �ְ� �˴ϴ�. 
*/
public void bad(String sSingleId, int iFlag, String sServiceProvider, String sUid, String sPwd)
{
	env.put(Context.INITIAL_CONTEXT_FACTORY, CommonMySingleConst.INITCTX);
	env.put(Context.PROVIDER_URL, sServiceProvider);
	// ���Ⱦ��� : ����� ������ ������� ���� 
  	env.put(Context.SECURITY_AUTHENTICATION, "none");
  	env.put(Context.SECURITY_PRINCIPAL, sUid);
  	env.put(Context.SECURITY_CREDENTIALS, sPwd);
  	// ���� ...
}

/* 
����� ID�� password�� ���ؽ�Ʈ�� ������ �� �����ϵ��� ������� ����մϴ�. 
*/
public void good(String sSingleId, int iFlag, String sServiceProvider, String sUid, String sPwd)
{
  	env.put(Context.PROVIDER_URL, sServiceProvider);
	// ����
  	env.put(Context.SECURITY_AUTHENTICATION, "simple");
  	env.put(Context.SECURITY_PRINCIPAL, sUid);
  	env.put(Context.SECURITY_CREDENTIALS, sPwd);
  	// ���� ...
}


}