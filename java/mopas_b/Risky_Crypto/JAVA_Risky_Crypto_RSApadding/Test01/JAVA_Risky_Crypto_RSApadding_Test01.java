public class CS
{

	public void bad()
	{
		Cipher rsa = null;
	
	 	 try {
	  		// 보안약점 : RSA 사용시 NoPadding 사용
	 		rsa = javax.crypto.Cipher.getInstance("RSA/NONE/NoPadding");
	  	} 
		catch (java.security.NoSuchAlgorithmException e) { 
			//... 
		}
	      	
		return rsa;
	}
	
	public void bad2() throws Throwable
	    {
	
	        final String CIPHER_INPUT = "ABCDEFG123456";
	
	        KeyGenerator keygen = KeyGenerator.getInstance("DES");
	
	        /* Perform initialization of KeyGenerator */
	        keygen.init(256);
	
	        SecretKey sk = keygen.generateKey();
	        byte[] raw = sk.getEncoded();
	
	        /* FLAW: Use a weak crypto algorithm, DES */
	        SecretKeySpec skSpec = new SecretKeySpec(raw, "DES");
	
	        Cipher c = Cipher.getInstance("RSA/NONE/NoPadding");
	        c.init(Cipher.ENCRYPT_MODE, skSpec);
	
	        byte[] encrypted = c.doFinal(CIPHER_INPUT.getBytes("UTF-8"));
	
	        IO.writeLine(IO.toHex(encrypted));
	
	    }
	    
	public void bad3() throws Throwable{
		Key key = generateKey("DESede", ByteUtils.toBytes("696d697373796f7568616e6765656e61696d697373796f75", 16));
	
	 
	    String transformation = "DESede/ECB/NoPadding";
	    Cipher cipher = Cipher.getInstance(transformation);
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	
	    String str = "hello123";
	
	    byte[] plain = str.getBytes();
	    byte[] encrypt = cipher.doFinal(plain);
	    System.out.println("원문 : " + ByteUtils.toHexString(plain));
	    System.out.println("암호 : " + ByteUtils.toHexString(encrypt));
	
	    cipher.init(Cipher.DECRYPT_MODE, key);
	
	    byte[] decrypt = cipher.doFinal(encrypt);
	    System.out.println("복호 : " + ByteUtils.toHexString(decrypt));
	
	}
	
	public void good()
	{
		Cipher rsa = null;
	
	 	 try {
	  		// 수정 : PKCS5 Padding 사용
	 		rsa = javax.crypto.Cipher.getInstance("RSA/CBC/PKCS5Padding");
	  	} 
		catch (java.security.NoSuchAlgorithmException e) { 
			//... 
		}
	      	
		return rsa;
	}

}