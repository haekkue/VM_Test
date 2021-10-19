/*
Filename : KRD_210_Information_Exposure_Through_Persistent_Cookies__fromDB_setMaxAge_0101_bad.java
CWEID    : CWE539
sinkname : setMaxAge
sinkline : 115,
makedate : 2012 12 24
license  : Copyright KISA.
*/

package testcases.KRD_212_Information_Exposure_Through_Persistent_Cookies;

import testcasesupport.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

import java.util.logging.Logger;

import java.net.*;


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

public class KRD_212_Information_Exposure_Through_Persistent_Cookies__fromDB_setMaxAge_0101_bad extends AbstractTestCase
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




        String name = "Name";
        String value = "ABC";
        Cookie hc = new Cookie(name, value);

        /* FLAW */
        //Sets the maximum age of the cookie in seconds.
        hc.setMaxAge(Integer.valueOf(data));
        System.out.println("Maximum age of the cookie is: " + hc.getMaxAge());


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

