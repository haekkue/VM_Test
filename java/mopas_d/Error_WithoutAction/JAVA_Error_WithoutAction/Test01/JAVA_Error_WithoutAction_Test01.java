/* TEMPLATE GENERATED TESTCASE FILE
Filename: CWE390_Error_Without_Action__npe_01.java
Label Definition File: CWE390_Error_Without_Action.label.xml
Template File: point-flaw-01.tmpl.java
*/
/*
* @description
* CWE: 390 Detection of Error Condition Without Action
* Sinks: npe
*    GoodSink: Report and rethrow NullPointerException
*    BadSink : Catch NullPointerException, but don't do anything about it
* Flow Variant: 01 Baseline
*
* */

package testcases.CWE390_Error_Without_Action;

import testcasesupport.*;

import javax.servlet.http.*;

public class CWE390_Error_Without_Action__npe_01 extends AbstractTestCase
{

    public void bad() throws Throwable
    {

        String sNull = null;
        try
        {
            String tmp = sNull.trim();
        }
        catch( NullPointerException e )
        {
            /*  FLAW: catch NullPointerException but without an action */
        }

    }

    public void good() throws Throwable
    {
        good1();
    }

    private void good1() throws Throwable
    {

        String sNull = null;
        try
        {
            String tmp = sNull.trim();
        }
        catch( NullPointerException e )
        {
            /* FIX: report NPE and rethrow */
            IO.writeLine("Error: " + e.getMessage());
            throw e;
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

