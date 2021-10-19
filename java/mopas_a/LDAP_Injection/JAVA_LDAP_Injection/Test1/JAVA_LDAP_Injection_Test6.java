public class CS
{

public class CWE90_LDAP_Injection__getParameter_Servlet_01 extends AbstractTestCaseServlet
{
  public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
  {
    String data;

    data = request.getParameter("name");

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

public class CWE90_LDAP_Injection__getParameter_Servlet_01 extends AbstractTestCaseServlet
{
  public void goodG2B(HttpServletRequest request, HttpServletResponse response) throws Throwable
  {
    String data;

    data = request.getParameter("name");

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