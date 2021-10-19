/*
Filename : KRD_204_Use_Broken_Crypto__basic_digest_0101_good.java
CWEID    : CWE328_Reversible_One_Way_Hash
sinkname : digest
sinkline : 33,
makedate : 2013 06 20
license  : Copyright KISA.
*/

package testcases.KRD_204_Use_Broken_Crypto;

import testcasesupport.*;

import javax.servlet.http.*;
import java.io.*;
import java.security.MessageDigest;

public class KRD_204_Use_Broken_Crypto__basic_digest_0101_good extends AbstractTestCase
{


    public void good() throws Throwable
    {
        good1();
    }

    private void good1() throws Throwable
    {

        String input = "Test Input";

        /* FIX: Secure cryptographic hashing algorithm (SHA-512) */
        MessageDigest hash = MessageDigest.getInstance("SHA-512");
        byte[] hashv = hash.digest(input.getBytes("UTF-8")); /* INCIDENTAL FLAW: Hard-coded input to hash algorithm */

        IO.writeLine(IO.toHex(hashv));

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

