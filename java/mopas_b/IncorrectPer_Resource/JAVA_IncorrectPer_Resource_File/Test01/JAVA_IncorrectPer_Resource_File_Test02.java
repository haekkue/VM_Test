/*
Filename : KRD_203_Incorrect_Permission_Assignment_for_Critical_Resource__basic_File_0101_bad.java
CWEID    : CWE732
sinkname : File
sinkline : 32,33,34,
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

public class KRD_203_Incorrect_Permission_Assignment_for_Critical_Resource__basic_File_0101_bad extends AbstractTestCase
{

 

    public void bad() throws Throwable
    {
        File file = new File("c:\\report.txt");
        /* FLAW */
		file.setExecutable(true, false);
		file.setReadable(true, false);
		file.setWritable(true, false);
		
		try {
    		// 보안약점 : 권한 설정없이 생성
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

