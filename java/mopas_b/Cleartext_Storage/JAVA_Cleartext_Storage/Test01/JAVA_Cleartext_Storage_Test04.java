/*
Filename : KRD_216_Cleartext_Transmission_of_Sensitive_Information__Servlet_setSecure_0101_good.java
CWEID    : CWE614
sinkname : setSecure
sinkline : 46,
makedate : 2012 12 24
license  : Copyright KISA.
*/

package testcases.KRD_206_Cleartext_Transmission_of_Sensitive_Information;

import testcasesupport.*;

import javax.servlet.http.*;
import java.io.*;


import javax.servlet.http.*;
import java.io.*;

public class KRD_206_Cleartext_Transmission_of_Sensitive_Information__Servlet_setSecure_0101_good extends AbstractTestCaseServlet
{


    public void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {

        goodB2G(request, response);
    }

    
    
    private void goodB2G(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String data;


        data = "test";



        Cookie c = new Cookie("SecretMessage", data);
        if( request.isSecure() )
        {
        	/* FIX: adds "secure" flag/attribute to cookie */
            c.setSecure(true); 
            response.addCookie(c);
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

