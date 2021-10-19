public class CS
{

public class CWE90_LDAP_Injection__console_readLine_01 extends AbstractTestCase
{
  public void bad() throws Throwable
  {
    String data = "";

    try {
      InputStreamReader isr = new InputStreamReader(System.in, "UTF-8");
      BufferedReader buffread = new BufferedReader(isr);
      data = buffread.readLine();
    }
     
    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://localhost:389");
    DirContext ctx = new InitialDirContext(env);

    String search = "(cn=" + data + ")";

	// 보안약점
    NamingEnumeration<SearchResult> answer = ctx.search("", search, null);
    while (answer.hasMore())
    {
      SearchResult sr = answer.next();
      Attributes a = sr.getAttributes();
      NamingEnumeration<?> attrs = a.getAll();
      while (attrs.hasMore())
      {
        Attribute attr = (Attribute) attrs.next();
        NamingEnumeration<?> values = attr.getAll();
        while(values.hasMore())
        {
          IO.writeLine(" Value: " + values.next().toString());
        }
      }
    }
  }
}

public class CWE90_LDAP_Injection__console_readLine_01 extends AbstractTestCase
{
  public void goodG2B() throws Throwable
  {
    String data = "";

    try {
      InputStreamReader isr = new InputStreamReader(System.in, "UTF-8");
      BufferedReader buffread = new BufferedReader(isr);
      data = buffread.readLine();
    }
     
    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://localhost:389");
    DirContext ctx = new InitialDirContext(env);

	// 수정
    String search = "(cn=" + data.replaceAll("\\*", "") + ")";

    NamingEnumeration<SearchResult> answer = ctx.search("", search, null);
    while (answer.hasMore())
    {
      SearchResult sr = answer.next();
      Attributes a = sr.getAttributes();
      NamingEnumeration<?> attrs = a.getAll();
      while (attrs.hasMore())
      {
        Attribute attr = (Attribute) attrs.next();
        NamingEnumeration<?> values = attr.getAll();
        while(values.hasMore())
        {
          IO.writeLine(" Value: " + values.next().toString());
        }
      }
    }
  }
}

}