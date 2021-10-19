/*
Filename : KRD_605_Public_Data_Assigned_to_Private_Array_Typed_Field__connect_tcp_2_assign_0101_bad.java
CWEID    : CWE496
sinkname : assign
sinkline : 109,
makedate : 2012 12 24
license  : Copyright KISA.
*/

package testcases.KRD_605_Public_Data_Assigned_to_Private_Array_Typed_Field;

import testcasesupport.*;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import java.util.logging.Logger;

public class KRD_605_Public_Data_Assigned_to_Private_Array_Typed_Field__connect_tcp_2_assign_0101_bad extends AbstractTestCase
{


    private byte[] foo = {};
    public void setFoo(byte[] msg)
    {
		/* FLAW */
    	foo = msg;
    }

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

        /* FLAW */
		setFoo(data.getBytes());


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

