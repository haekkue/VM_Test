/*
Filename : KRD_203_Incorrect_Permission_Assignment_for_Critical_Resource__basic_File_0101_good.java
CWEID    : CWE732
sinkname : File
sinkline : 40,41,42,
makedate : 2012 08 27
license  : Copyright KISA.
*/

package testcases.KRD_203_Incorrect_Permission_Assignment_for_Critical_Resource;

import testcasesupport.*;

import javax.servlet.http.*;
import java.io.*;
import java.net.*;
import java.util.logging.Logger;


import javax.servlet.http.*;
import java.io.*;

public class KRD_203_Incorrect_Permission_Assignment_for_Critical_Resource__basic_File_0101_good extends AbstractTestCase
{
	
 

    public void good() throws Throwable
    {
        goodG2B();
    }
	
	

    /* goodG2B() - uses goodsource and badsink */
    private void goodG2B() throws Throwable
    {
        File file = new File("c:/report.txt");
        /* FIX */
        file.setExecutable(false, false);
        file.setReadable(false, false);
		file.setWritable(false, false);
		
		try {
    		
    		boolean success = file.mkdir();
    		if ( success )
    		{
    			IO.writeLine("Directory created");
    			File file = new File(dir.getAbsolutePath() + "\\newFile.txt");
    			file.createNewFile();
    		}
    	}
    	catch ( Exception e )
    	{
    		System.out.println(e.getMessage());
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

