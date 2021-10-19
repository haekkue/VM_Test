public class CS
{

public class CWE90_LDAP_Injection__getQueryString_Servlet_01 extends AbstractTestCaseServlet
{
    /* uses badsource and badsink */
    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String data;

        data = ""; /* initialize data in case id is not in query string */

        /* POTENTIAL FLAW: Parse id param out of the URL querystring (without using getParameter()) */
        {
            StringTokenizer st = new StringTokenizer(request.getQueryString(), "&");
            while (st.hasMoreTokens())
            {
                String token = st.nextToken(); /* a token will be like "id=foo" */
                if(token.startsWith("id="))   /* check if we have the "id" parameter" */
                {
                    data = token.substring(3); /* set data to "foo" */
                    break; /* exit while loop */
                }
            }
        }

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:389");
        DirContext ctx = new InitialDirContext(env);

        /* POTENTIAL FLAW: data concatenated into LDAP search, which could result in LDAP Injection */
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

public class CWE90_LDAP_Injection__getQueryString_Servlet_01 extends AbstractTestCaseServlet
{
    public void goodG2B(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String data;

        data = "";

        {
            StringTokenizer st = new StringTokenizer(request.getQueryString(), "&");
            while (st.hasMoreTokens())
            {
                String token = st.nextToken();
                if(token.startsWith("id=")) 
                {
                    data = token.substring(3);
                    break;
                }
            }
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