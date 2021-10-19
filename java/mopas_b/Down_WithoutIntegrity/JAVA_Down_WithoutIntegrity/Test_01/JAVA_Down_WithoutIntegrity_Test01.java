/*
Filename : KRD_213_Download_of_Code_Without_Integrity_Check__connect_tcp_URLClassLoader_0101_bad.java
CWEID    : CWE494
sinkname : URLClassLoader
sinkline : 111,
makedate : 2013 08 28
license  : Copyright KISA.
*/

package testcases.KRD_215_Download_of_Code_Without_Integrity_Check;

import testcasesupport.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.util.logging.Level;

import java.util.logging.Logger;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import java.util.logging.Logger;

public class KRD_215_Download_of_Code_Without_Integrity_Check__connect_tcp_URLClassLoader_0101_bad extends AbstractTestCase
{

    
    public void bad() throws Throwable
    {
        String data;

        Logger log_bad = Logger.getLogger("local-logger");

        data = ""; /* init data */

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

        URL classUrl = null;
        URLClassLoader ucl = null;
        try{
            
        	classUrl = new URL(data);
        	URL[] classUrls = { classUrl };
        	/* FLAW */
        	ucl = new URLClassLoader(classUrls);
        	Class c = ucl.loadClass("org.apache.commons.lang.ArrayUtils"); 
        	for(Field fld: c.getDeclaredFields()) {
        		System.out.println("Field name" + fld.getName());
        	}
        }
        catch(NoClassDefFoundError e)
        {
        	IO.logger.log(Level.WARNING, "Error in URLClassLoader", e);
        }
        catch(ClassNotFoundException e)
        {
        	IO.logger.log(Level.WARNING, "Error in URLClassLoader", e);
        }
        finally
        {
        	try
        	{
        		if(ucl != null)
        		{
        			ucl.close();
        		}
        	}catch(NoClassDefFoundError e)
        	{
        		IO.logger.log(Level.WARNING, "Error closing URLClassLoader", e);
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

