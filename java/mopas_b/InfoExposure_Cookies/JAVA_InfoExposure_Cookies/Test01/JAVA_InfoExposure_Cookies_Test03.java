/*
Filename : KRD_210_Information_Exposure_Through_Persistent_Cookies__fromDB_setMaxAge_0101_good.java
CWEID    : CWE539
sinkname : setMaxAge
sinkline : 67,
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

public class KRD_212_Information_Exposure_Through_Persistent_Cookies__fromDB_setMaxAge_0101_good extends AbstractTestCase
{

    

    public void good() throws Throwable
    {
        good1();
    }

    /* goodG2B() - uses goodsource and badsink */
    private void good1() throws Throwable
    {
        String data;

        java.util.logging.Logger log_good = java.util.logging.Logger.getLogger("local-logger");

        /* FIX: Use a hardcoded string */
        data = "0";




        String name = "Name";
        String value = "ABC";
        Cookie hc = new Cookie(name, value);
        
        /* FLAW */
        //Sets the maximum age of the cookie in seconds.
        hc.setMaxAge(Integer.valueOf(data));
        System.out.println("Maximum age of the cookie is: " + hc.getMaxAge());

		// ¼öÁ¤
		hc.setMaxAge(0);
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

