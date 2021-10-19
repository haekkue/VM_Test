/*
Filename : KRD_604_Private_Array_Typed_Field_Returned_From_A_Public_Method__basic_return_0101_good.java
CWEID    : CWE495
sinkname : return
sinkline : 66,
makedate : 2013 07 02
license  : Copyright KISA.
*/

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

public class KRD_604_Private_Array_Typed_Field_Returned_From_A_Public_Method__basic_return_0101_good extends AbstractTestCase
{



    private byte[] foo = null;
    public byte[] getFoo()
    {
        byte[] tmp_array = null;
    	if(foo != null)
    	{
    		tmp_array = new byte[foo.length];
    		for(int i=0;i<foo.length;i++)
    		{
    			tmp_array[i] = foo[i];
    		}
    	}
        return tmp_array;
    }

    public void good() throws Throwable

    {

        goodB2G();
    }

    
    
    private void goodB2G() throws Throwable
    {
        /* FIX */
        byte[] tmp_data = getFoo();


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

