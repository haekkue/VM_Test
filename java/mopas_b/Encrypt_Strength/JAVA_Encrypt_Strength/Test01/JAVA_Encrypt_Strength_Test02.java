/*
Filename : KRD_207_Cryptographic_Issues__basic_initialize_0102_bad.java
CWEID    : CWE310
sinkname : initialize
sinkline : 36,
makedate : 2012 11 19
license  : Copyright KISA.
*/

package testcases.KRD_208_Cryptographic_Issues;

import testcasesupport.*;

import java.io.OutputStreamWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;


import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

import java.util.logging.Logger;

public class KRD_208_Cryptographic_Issues__basic_initialize_0102_bad extends AbstractTestCase
{

    
    public void bad() throws Throwable
    {
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        /* FLAW */
        kpGen.initialize(1024);
        KeyPair          pair = kpGen.generateKeyPair();


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

