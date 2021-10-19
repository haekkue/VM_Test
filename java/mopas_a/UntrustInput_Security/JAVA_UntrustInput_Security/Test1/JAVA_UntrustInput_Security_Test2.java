public class KRD_113_Reliance_on_Untrusted_Inputs_in_a_Security_Decision__Servlet_authenticated_equals_0101_bad extends AbstractTestCaseServlet
{
    
    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        boolean authenticated = false;

        Cookie[] cookies = request.getCookies();
		for (int i =0; i< cookies.length; i++) 
		{
			Cookie c = cookies[i];
			/* FLAW */
			if (c.getName().equals("authenticated") && Boolean.TRUE.equals(c.getValue())) {
				authenticated = true;
			}
		}


    }


    

    /* Below is the main(). It is only used when building this testcase on
       its own for testing or for building a binary to use in testing binary
       analysis tools. It is not used when compiling all the testcases as one
       application, which is how source code analysis tools are tested. */
    public static void main(String[] args) throws ClassNotFoundException,
           InstantiationException, IllegalAccessException
    {
        mainFromParent(args);
    }
}