public class CS
{

/* 
name 변수의 값으로 *을 전달할 경우 필터 문자열은 (name=*)가 되어 항상 참이 되어, 의도하지 않은 동작을 발생시킬 수 있습니다. 
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
	// 보안약점
	NamingEnumeration answer = ctx.search("ou=NewHires",fileter,new SearchControls());
	printSearchEnumeration(answer);
	ctx.close();
}

/* 
name 변수의 값으로 *을 전달할 경우 필터 문자열은 (name=*)가 되어 항상 참이 되어, 의도하지 않은 동작을 발생시킬 수 있습니다. 
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
	// 보안약점
	NamingEnumeration answer = ctx.search("ou=NewHires","(name =" + props.getProperty("name") + ")",new SearchControls());
	printSearchEnumeration(answer);
	ctx.close();
}

/* 
replacAll 메소드를 이용하여 검색을 위한 필터 문자열로 사용되는 외부 입력값에서 위험한 문자열을 제거하여 위험성을 부분적으로 감소시킵니다. 
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
	
	// 수정 : replaceAll 메소드로 외부 입력값 필터링
	String fileter = "(name =" + name.replaceAll("\\*","") + ")"; 
	NamingEnumeration answer = ctx.search("ou=NewHires",fileter,new SearchControls());
	printSearchEnumeration(answer);
	ctx.close();
}

}