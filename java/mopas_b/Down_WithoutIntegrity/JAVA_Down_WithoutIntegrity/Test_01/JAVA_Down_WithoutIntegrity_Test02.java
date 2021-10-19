/*
Filename : KRD_213_Download_of_Code_Without_Integrity_Check__fromDB_URLClassLoader_0101_bad.java
CWEID    : CWE494
sinkname : URLClassLoader
sinkline : 115,
makedate : 2013 08 28
license  : Copyright KISA.
*/

package testcases.KRD_215_Download_of_Code_Without_Integrity_Check;

import testcasesupport.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

import java.util.logging.Logger;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


import javax.servlet.http.*;

import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.logging.Logger;

public class KRD_215_Download_of_Code_Without_Integrity_Check__fromDB_URLClassLoader_0101_bad extends AbstractTestCase
{

    
    public void bad() throws Throwable
    {
        String data;

        Logger log_bad = Logger.getLogger("local-logger");

        data = ""; /* init data */

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        BufferedReader buffread = null;
        InputStreamReader instrread = null;
        try {
            /* setup the connection */
            conn = IO.getDBConnection();

            /* prepare and execute a (hardcoded) query */
            statement = conn.prepareStatement("select name from users where id=1");
            rs = statement.executeQuery();
            rs.next();
            
            data = rs.getString(1);
        }
        catch( SQLException se )
        {
            IO.logger.log(Level.WARNING, "Error with SQL statement", se);
        }
        finally {
                /* Close database objects */
                try {
                    if( rs != null )
                    {
                        rs.close();
                    }
                }
                catch( SQLException se )
                {
                    IO.logger.log(Level.WARNING, "Error closing ResultSet", se);
                }

                try {
                    if( statement != null )
                    {
                        statement.close();
                    }
                }
                catch( SQLException se )
                {
                    IO.logger.log(Level.WARNING, "Error closing Statement", se);
                }

                try {
                    if( conn != null )
                    {
                        conn.close();
                    }
                }
                catch( SQLException se)
                {
                    IO.logger.log(Level.WARNING, "Error closing Connection", se);
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


/*
Filename : KRD_213_Download_of_Code_Without_Integrity_Check__fromDB_URLClassLoader_0101_good.java
CWEID    : CWE494
sinkname : URLClassLoader
sinkline : 137,
makedate : 2013 08 28
license  : Copyright KISA.
*/

package testcases.KRD_215_Download_of_Code_Without_Integrity_Check;

import testcasesupport.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

import java.util.logging.Logger;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


import javax.servlet.http.*;

import java.util.logging.Level;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.logging.Logger;

public class KRD_215_Download_of_Code_Without_Integrity_Check__fromDB_URLClassLoader_0101_good extends AbstractTestCase
{

    

    public void good() throws Throwable
    {
        good1();
    }

    /* goodG2B() - uses goodsource and badsink */
    private void good1() throws Throwable
    {
    	String fileName = "http://192.168.0.100/commons-lang-2.5.jar";
    	String txtFileName = "http://192.168.0.100/secret_dir/commons-lang-2.5.txt";
    	String storedPath = "c:\\commons-lang-2.5.jar";
    	byte[] hash = null;
    	String filehash = null;
    	String txthash = null;
    	FileOutputStream fos = null;
    	InputStream is = null;
    	BufferedReader in = null;
    	URL classUrl = null;
        URLClassLoader ucl = null;
    	
    	//get hash from file
    	try
    	{
    		URL URLclass = new URL(fileName);
    		fos = new FileOutputStream(storedPath);
    		is = URLclass.openStream();
    		byte[] buf = new byte[1024];
    		int len = 0;
    		MessageDigest md = MessageDigest.getInstance("sha-256");

    		while((len=is.read(buf))>0)
    		{
    			md.update(buf,0,len);
    		}
    		hash = md.digest();
    		filehash = IO.toHex(hash);
    	}
    	catch(NoSuchAlgorithmException e)
    	{
    		IO.logger.log(Level.WARNING, "Error in Algorithem", e);
    	}
    	catch(IOException e)
    	{
    		IO.logger.log(Level.WARNING, "Error in IO system", e);
    	}
    	finally
    	{
    		if(fos != null)
    		{
    			fos.close();
    		}
    		if(is != null)
    		{
    			is.close();
    		}
    	}
    	
    	//get harded hash from text file
    	try
    	{
    		URL URLtxt = new URL(txtFileName);
    		in = new BufferedReader(new InputStreamReader(URLtxt.openStream()));
    		
    		String line;
    		while((line=in.readLine()) != null)
    		{
    			txthash = line;
    		}
    	}
    	catch(MalformedURLException e)
    	{
    		IO.logger.log(Level.WARNING, "Error in url exception", e);
    	}
    	catch(IOException e)
    	{
    		IO.logger.log(Level.WARNING, "Error in IO system", e);
    	}
    	finally
    	{
    		if(in != null)
    		{
    			in.close();
    		}
    	}
    	
    	//compare hashes
    	/* FIX */
    	if(filehash.equals(txthash))
    	{
    		try{	
            	classUrl = new URL(fileName);
            	URL[] classUrls = { classUrl };
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

