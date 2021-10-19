/*
Filename : KRD_403_Improper_Check_for_Unusual_or_Exceptional_Conditions__connect_tcp_2_File_0101_bad.java
CWEID    : CWE754
sinkname : File
sinkline : 118,
makedate : 2012 12 24
license  : Copyright KISA.
*/

package testcases.KRD_403_Improper_Check_for_Unusual_or_Exceptional_Conditions;

import testcasesupport.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import testcasesupport.AbstractTestCase;
import testcasesupport.IO;

import java.util.logging.Logger;


import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import java.util.logging.Logger;

public class KRD_403_Improper_Check_for_Unusual_or_Exceptional_Conditions__connect_tcp_2_File_0101_bad extends AbstractTestCase
{

    public void bad() throws Throwable
    {
        String data;

        Logger log_bad = Logger.getLogger("local-logger");

        data = "FileReaderDemo.java"; /* init data */

        Socket sock = null;
        BufferedReader buffread = null;
        InputStreamReader instrread = null;
        try {
            /* Read data using an outbound tcp connection */
            sock = new Socket("192.168.0.100", 9000);

            /* read input from socket */
            instrread = new InputStreamReader(sock.getInputStream(), "UTF-8");
            buffread = new BufferedReader(instrread);

            data = buffread.readLine();
        }
        catch( IOException ioe )
        {
            log_bad.warning("Error with stream reading");
        }
        finally {
        	/* clean up stream reading objects */
        	try {
        		if( buffread != null )
        		{
        			buffread.close();
        		}
        	}
        	catch( IOException ioe )
        	{
        		IO.logger.log(Level.WARNING, "Error closing BufferedReader", ioe);
        	}

        	try {
        		if( instrread != null )
        		{
        			instrread.close();
        		}
        	}
        	catch( IOException ioe )
        	{
        		IO.logger.log(Level.WARNING, "Error closing InputStreamReader", ioe);
        	}

        	/* clean up socket objects */
        	try {
        		if( sock != null )
        		{
        			sock.close();
        		}
        	}
        	catch( IOException e )
        	{
        		IO.logger.log(Level.WARNING, "Error closing Socket", e);
        	}
        }

		FileReader fr = null;
		BufferedReader br = null;
		try 
		{
			if(data != null)
			{
				fr = new FileReader(data);
			}
			br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null) 
			{
	            System.out.println(s);
			}
		} catch (Exception e) 
		{
			/* FLAW */
			IO.logger.log(Level.WARNING, "Unknown error occurred");
		}
		finally
		{
			try
			{
				if(br != null)
				{
					br.close();
				}
			}
			catch(IOException ioe)
			{
				IO.logger.log(Level.WARNING, "Error closing bufferReader", ioe);
			}
			try
			{
				if(fr != null)
				{
					fr.close();
				}
			}
			catch(IOException ioe)
			{
				IO.logger.log(Level.WARNING, "Error closing fileReader", ioe);
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

