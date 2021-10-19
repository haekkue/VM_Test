/*
Filename : KRD_114_Reliance_on_Untrusted_Inputs_in_a_Security_Decision__Servlet_authenticated_equals_0101_good.java
CWEID    : CWE807
sinkname : equals
sinkline : 60,
makedate : 2012 12 24
license  : Copyright KISA.
*/

package testcases.KRD_113_Reliance_on_Untrusted_Inputs_in_a_Security_Decision;

import testcasesupport.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

import java.util.logging.Logger;

import java.net.URLEncoder;


import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

import java.util.logging.Logger;

public class KRD_113_Reliance_on_Untrusted_Inputs_in_a_Security_Decision__Servlet_authenticated_equals_0101_good extends AbstractTestCaseServlet
{

    

    public void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        goodG2B(request, response);
    }

    /* goodG2B() - uses goodsource and badsink */
    private void goodG2B(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
    	boolean authenticated = false;
    	
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username == null || password == null || !KrdUtil.isAuthenticatedUser(username, password))
        {
        	throw new Exception("Authentication Error occurred");
        }
        
        Cookie[] cookies = request.getCookies();
		for (int i =0; i< cookies.length; i++) 
		{
			Cookie c = cookies[i];
			/* FIX */
			if (c.getName().equals("authenticated") && Boolean.TRUE.equals(c.getValue())) {
				authenticated = true;
			}
			else
			{
				c = new Cookie("authenticated", "");
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

