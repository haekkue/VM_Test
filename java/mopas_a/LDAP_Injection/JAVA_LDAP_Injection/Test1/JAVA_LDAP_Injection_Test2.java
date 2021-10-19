public class CS
{

public void good()
{
    Hashtable<String, String> env = new Hashtable<String, String>();
	env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://localhost:389");
	DirContext ctx = new InitialDirContext(env);

  	try {
  	        Properties props;
    		javax.naming.directory.DirContext ctx = new InitialDirContext(env);
    		String name = props.getProperty("ldap.properties");
    		// 입력값에 대한 BasicAtrtribute를 생성한다.
    		BasicAttribute attr = new BasicAttribute("name", name);
    		// 보안약점 : 외부 입력값이 검증없이 LDAP search() 매소드에 인자로 사용이 된다.
    		// "LDAP 처리" 룰에서 탐지 
    		NamingEnumeration answer = ctx.search("ou=NewHires", attr.getID(), new SearchControls());
    		printSearchEnumeration(answer);
   	 	ctx.close();
    	} catch (NamingException e) { }
}

}