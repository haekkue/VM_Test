/* TEMPLATE GENERATED TESTCASE FILE
Filename: CWE390_Error_Without_Action__reader_01.java
Label Definition File: CWE390_Error_Without_Action.label.xml
Template File: point-flaw-01.tmpl.java
*/
/*
* @description
* CWE: 390 Detection of Error Condition Without Action
* Sinks: reader
*    GoodSink: Report and rethrow FileNotFoundException
*    BadSink : Catch FileNotFoundException, but don't do anything about it
* Flow Variant: 01 Baseline
*
* */

package testcases.CWE390_Error_Without_Action;

import testcasesupport.*;

import javax.servlet.http.*;

import java.io.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CWE390_Error_Without_Action__reader_01 extends AbstractTestCase
{

    public void bad() throws Throwable
    {

        File f = new File("C:\\doesntexistandneverwill.txt");
        FileInputStream fis = null;
        InputStreamReader isread = null;
        BufferedReader reader = null;

        try
        {
            fis = new FileInputStream(f);
            isread = new InputStreamReader(fis, "UTF-8");
            reader = new BufferedReader(isread);
        }
        catch( FileNotFoundException fe )
        {
            /* FLAW: do nothing if the file doesn't exist */
        }
        catch( IOException Ie )
        {
            if( reader != null )
                {
                    reader.close();
                }
        }
        catch( Exception e )
        {
            /* FLAW: do nothing if the file doesn't exist */
        }
        finally
        {
            /* Close stream reading objects */
            try {
                if( reader != null )
                {
                    reader.close();
                }
            }
            catch( IOException ioe )
            {
                IO.logger.log(Level.WARNING, "Error closing BufferedReader", ioe);
            }

            try {
                if( isread != null )
                {
                    isread.close();
                }
            }
            catch( IOException ioe )
            {
                IO.logger.log(Level.WARNING, "Error closing InputStreamReader", ioe);
            }

            try {
                if( fis != null )
                {
                    fis.close();
                }
            }
            catch( IOException ioe )
            {
                IO.logger.log(Level.WARNING, "Error closing FileInputStream", ioe);
            }
        }

    }

    public void good() throws Throwable
    {
        good1();
    }

    private void good1() throws Throwable
    {

        File f = new File("C:\\doesntexistandneverwill.txt");
        FileInputStream fis = null;
        InputStreamReader isread = null;
        BufferedReader reader = null;

        try
        {
            fis = new FileInputStream(f);
            isread = new InputStreamReader(fis, "UTF-8");
            reader = new BufferedReader(isread);
        }
        catch( FileNotFoundException fe )
        {
            /* FIX: report read failure and rethrow */
            IO.writeLine("Error reading path: " + fe.getMessage());
            throw fe;
        }
        finally
        {
            /* Close stream reading objects */
            try {
                if( reader != null )
                {
                    reader.close();
                }
            }
            catch( IOException ioe )
            {
                IO.logger.log(Level.WARNING, "Error closing BufferedReader", ioe);
            }

            try {
                if( isread != null )
                {
                    isread.close();
                }
            }
            catch( IOException ioe )
            {
                IO.logger.log(Level.WARNING, "Error closing InputStreamReader", ioe);
            }

            try {
                if( fis != null )
                {
                    fis.close();
                }
            }
            catch( IOException ioe )
            {
                IO.logger.log(Level.WARNING, "Error closing FileInputStream", ioe);
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

