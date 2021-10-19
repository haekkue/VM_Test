/*
Filename : KRD_214_Improper_Restriction_Of_Excessive_Authentication_Attempts__Servlet_Captcha_0101_bad.java
CWEID    : CWE285
sinkname : Captcha
sinkline : 31,
makedate : 2013 07 09
license  : Copyright KISA.
*/

package testcases.KRD_216_Improper_Restriction_Of_Excessive_Authentication_Attempts;

import testcasesupport.*;

import testcasesupport.KrdUtil;
import java.util.logging.Logger;



import javax.servlet.http.*;
import java.io.*;

public class KRD_216_Improper_Restriction_Of_Excessive_Authentication_Attempts__Servlet_Captcha_0101_bad extends AbstractTestCaseServlet
{

    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        /* FLAW */
        if (username == null || password == null || !KrdUtil.isAuthenticatedUser(username, password))
        {
        	response.getWriter().println("Invalid username, password<br/>");
            response.getWriter().println("<A href='/KRD_Debugging/KRD_214_Improper_Restriction_Of_Excessive_Authentication_Attempts.htm'>Login Again</A></body></html>");
        }
        else
    	{
    		response.getWriter().println(username + " has successfully logged in!");
    	}
    }


    
    

    /* Below is the main(). It is only used when building this testcase on
       its own for testing or for building a binary to usezzzzzzzzz in testing binary
       analysis tools. It is not used when compiling all the testcases as one
       application, which is how source code analysis tools are tested. */
    public static void main(String[] args) throws ClassNotFoundException,
           InstantiationException, IllegalAccessException
    {
        mainFromParent(args);
    }
}

