/*
Filename : KRD_216_Cleartext_Transmission_of_Sensitive_Information__Servlet_setSecure_0101_bad.java
CWEID    : CWE614
sinkname : setSecure
sinkline : 35,
makedate : 2013 07 09
license  : Copyright KISA.
*/

package testcases.KRD_206_Cleartext_Transmission_of_Sensitive_Information;

import testcasesupport.*;

import javax.servlet.http.*;
import java.io.*;


import javax.servlet.http.*;
import java.io.*;

public class KRD_206_Cleartext_Transmission_of_Sensitive_Information__Servlet_setSecure_0101_bad extends AbstractTestCaseServlet
{

    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String data;


        data = "test";



        Cookie c = new Cookie("SecretMessage", data);
        /* FLAW: secure flag not set */
       	response.addCookie(c); 
        

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

