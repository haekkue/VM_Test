public class CS
{

public class CWE90_LDAP_Injection__Property_01 extends AbstractTestCase
{
  public void bad() throws Throwable
  {
    String data;

    data = System.getProperty("user.home");

    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://localhost:389");
    DirContext ctx = new InitialDirContext(env);

    String search = "(cn=" + data + ")";

	// ���Ⱦ���
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

public class CWE90_LDAP_Injection__Property_01 extends AbstractTestCase
{
  public void goodG2B() throws Throwable
  {
    String data;

    data = System.getProperty("user.home");

    Hashtable<String, String> env = new Hashtable<String, String>();
    env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://localhost:389");
    DirContext ctx = new InitialDirContext(env);

	// ����
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