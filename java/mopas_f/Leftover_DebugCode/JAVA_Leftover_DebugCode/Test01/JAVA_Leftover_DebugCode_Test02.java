/*
Filename : KRD_602_Leftover_Debug_Code__Servlet_equals_0101_bad.java
CWEID    : CWE489
sinkname : equals
sinkline : 42,
makedate : 2012 12 24
license  : Copyright KISA.
*/

package testcases.KRD_602_Leftover_Debug_Code;

import testcasesupport.*;

import javax.servlet.http.*;
import java.io.*;


import javax.servlet.http.*;
import java.io.*;

public class KRD_602_Leftover_Debug_Code__Servlet_equals_0101_bad extends AbstractTestCaseServlet
{

    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String data;


        data = "1";



        if (request.getParameter("username") == null ||
        request.getParameter("password") == null)
        {
            return;
        }

        Boolean isAuthenticated = false;
        HttpSession session = request.getSession(true);
        /* FLAW: debug flag bypasses authentication mechanism */
        if (request.getParameter("debug") != null &&
        request.getParameter("debug").equals(data))
        {
            session.setAttribute("username", "admin");
            session.setAttribute("isAdmin", "true");
            isAuthenticated = true;
        }
        else
        {
            /* INCIDENTAL: hardcoded username and password */
            if (request.getParameter("username").equals("testuser") &&
            request.getParameter("password").equals("012345679ABCDEF"))
            {
                session.setAttribute("username", "testuser");
                session.setAttribute("isAdmin", "false");
                isAuthenticated = true;
            }
            else
            {
                isAuthenticated = false;
            }
        }

        if (isAuthenticated)
        {
            response.getWriter().println("You are authenticated!");
        }
        else
        {
            response.getWriter().println("Sorry, bad username or password.");
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

