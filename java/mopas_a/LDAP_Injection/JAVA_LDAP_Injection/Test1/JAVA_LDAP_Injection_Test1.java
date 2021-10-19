public class CS
{

/* 
name ������ ������ *�� ������ ��� ���� ���ڿ��� (name=*)�� �Ǿ� �׻� ���� �Ǿ�, �ǵ����� ���� ������ �߻���ų �� �ֽ��ϴ�. 
*/
public void ldap_bad1()
{
    Hashtable<String, String> env = new Hashtable<String, String>();
	env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://localhost:389");
	DirContext ctx = new InitialDirContext(env);

	Properties props = new Properties();
	String fileName = "ldap.properties";
	FileInputStream in = new FileInputStream(fileName);
	props.load(in);
	String name = props.getProperty("name");
	String fileter = "(name =" + name + ")";
	// ���Ⱦ���
	NamingEnumeration answer = ctx.search("ou=NewHires",fileter,new SearchControls());
	printSearchEnumeration(answer);
	ctx.close();
}

/* 
name ������ ������ *�� ������ ��� ���� ���ڿ��� (name=*)�� �Ǿ� �׻� ���� �Ǿ�, �ǵ����� ���� ������ �߻���ų �� �ֽ��ϴ�. 
*/
public void ldap_bad2()
{
    	Hashtable<String, String> env = new Hashtable<String, String>();
	env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://localhost:389");
	DirContext ctx = new InitialDirContext(env);

	Properties props = new Properties();
	String fileName = "ldap.properties";
	FileInputStream in = new FileInputStream(fileName);
	props.load(in);
	String name;
	// ���Ⱦ���
	NamingEnumeration answer = ctx.search("ou=NewHires","(name =" + props.getProperty("name") + ")",new SearchControls());
	printSearchEnumeration(answer);
	ctx.close();
}

/* 
replacAll �޼ҵ带 �̿��Ͽ� �˻��� ���� ���� ���ڿ��� ���Ǵ� �ܺ� �Է°����� ������ ���ڿ��� �����Ͽ� ���輺�� �κ������� ���ҽ�ŵ�ϴ�. 
*/
public void ldap_good()
{
    	Hashtable<String, String> env = new Hashtable<String, String>();
	env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://localhost:389");
	DirContext ctx = new InitialDirContext(env);

	Properties props = new Properties();
	String fileName = "ldap.properties";
	FileInputStream in = new FileInputStream(fileName);
	if (in == null || in.available() <= 0) return;
	props.load(in);
	if (props == null || props.isEmpty()) return;
	String name = props.getProperty("name");
	if (name == null || "".equals(name)) return;
	
	// ���� : replaceAll �޼ҵ�� �ܺ� �Է°� ���͸�
	String fileter = "(name =" + name.replaceAll("\\*","") + ")"; 
	NamingEnumeration answer = ctx.search("ou=NewHires",fileter,new SearchControls());
	printSearchEnumeration(answer);
	ctx.close();
}

}