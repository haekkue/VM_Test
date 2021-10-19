/*
Filename : KRD_114_Uncontrolled_Format_String__Environment_printf_0101_bad.java
CWEID    : CWE134
sinkname : printf
sinkline : 39,
makedate : 2013 07 02
license  : Copyright KISA.
*/

package testcases.KRD_115_Uncontrolled_Format_String;

import testcasesupport.*;

import javax.servlet.http.*;

import java.util.Hashtable;
import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.logging.Logger;

public class KRD_115_Uncontrolled_Format_String__Environment_printf_0101_bad extends AbstractTestCase
{

    
    public void bad() throws Throwable
    {
        String data;

        Logger log_bad = Logger.getLogger("local-logger");

        /* get environment variable ADD */
        data = System.getenv("TEST109");

        if (data != null)
        {
            /* POTENTIAL FLAW: uncontrolled string formatting */
            System.out.printf(data);
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

