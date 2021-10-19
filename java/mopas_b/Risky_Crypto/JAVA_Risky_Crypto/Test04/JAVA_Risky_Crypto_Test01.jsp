<%@ page language="java" contentType="text/html;charset=euc-kr" %>
<%@ page
	import="java.io.InputStream, 
			java.net.URL, 
			java.net.URLConnection,
			java.net.URLDecoder,
			java.net.URLEncoder,
			java.security.MessageDigest,
			javax.crypto.Cipher,
			javax.crypto.KeyGenerator,
			javax.crypto.SecretKey,
			javax.crypto.spec.IvParameterSpec,
			javax.crypto.spec.SecretKeySpec,
			javax.xml.namespace.QName,
			javax.xml.rpc.ParameterMode,
			org.apache.axis.client.Call,
			org.apache.axis.client.Service,
			org.apache.axis.encoding.XMLType,
			sun.misc.BASE64Decoder,
			sun.misc.BASE64Encoder"
%>

<%!

///	#############################################################################
///	#####
///	#####	한국신용정보주식회사			개인 실명확인 서비스 처리 스크립트
///	#####
///	#####	=====================================================================
///	#####
///	#####	Descriptions
///	#####		- 한신정에서 제공하는 Web Service를 호출한다.
///	#####
///	#####	---------------------------------------------------------------------
///	#####
///	#####	작성자 		: (주)한국신용정보 (www.nice.co.kr)
///	#####	원본참조	:
///	#####	원본파일	:
///	#####	작성일자	: 2006.02.01
///	#####
///	#############################################################################

public class OivsObject 
{
	public final static String 	DELIMETER_NORMAL	= "!#!";
	public final static String 	DELIMETER_DATA		= "!@!";
	public final static String 	SVC_TYPE_PERSONAL	= "0";
	public final static String 	SVC_TYPE_CORPERATE	= "1";
	public final static String 	SVC_TYPE_FOREIGNER	= "2";
	public final static int		CRND_SIZE			= 24;
	public final static int		SRND_SIZE			= 112;
	public final static String	CHARSET_DEFAULT		= "8859_1";
	public final static String	CHARSET_SERVER		= "utf-8";
	
	public final static String	URL_PING					= "http://secure.nuguya.com/nuguya/rlnmPing.do";
	public final static String 	URL_QNAME					= "http://service.oivs.nuguya.nice";
	public final static String 	URL_SECURESERVICE			= "http://secure.nuguya.com/nuguya/webservice/SecureService";
	public final static String 	URL_REALNAMESERVICE			= "http://secure.nuguya.com/nuguya/webservice/RealNameService";

	private Call callSecure 	= null;
	private Call callRealName	= null;
	
	private String hexPseudo[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	//	데이터 필드
	public String clientData	= "";
	public String niceId		= "";
	public String pingInfo		= "";
	public String userNm		= "";
	public String resIdNo		= "";
	public String inqRsn		= "";
	public String foreigner	= "";
	public String minor		= "";
	public String message		= "";
	public String retCd		= "";
	public String retDtlCd		= "";
	public String ckData		= "";
	public String skData		= "";
	public String identifier	= "";
	public String dupeinfo	= ""; 

	/**
	 * 기본 생성자로 필드 초기화화와 서비스 정보를 처리한다.
	 */
	public OivsObject()
	{
		initialize();
	}

	/*
	 * 필드를 초기화 처리한다.
	 */
	private void initialize()
	{
		this.pingInfo = webGetter( URL_PING );
		makeServiceCaller();
	}

	/*
	 * 서비스 호출에 필요한 Caller를 생성하여 설정한다.
	 */
	private void makeServiceCaller()
	{
		try
		{
			Service svc = new Service();
	        try{
				this.callSecure = (Call) svc.createCall();
				this.callRealName = (Call) svc.createCall();
				
				callSecure.setTargetEndpointAddress( new URL( URL_SECURESERVICE ) );
				callRealName.setTargetEndpointAddress( new URL( URL_REALNAMESERVICE ) );
			}catch( Exception ex )
			{
			}
		}
		catch( Exception ex )
		{
			this.message = "한국신용정보의 서비스에 접속할 수 없습니다. [nice.nuguya.oivs.inc - makeServiceCaller]";
			ex.printStackTrace();
		}
	}

	/*
	 * 지정된 URL을 호출하여 반환 정보를 반환한다.
	 */
	private String webGetter( String targetUrl )
	{
		URLConnection	conn = null;
		
		try
		{
			URL	url = new URL( targetUrl );
			conn = url.openConnection();
		}
		catch( Exception ex ) { return ""; }

		StringBuffer sbHtml = new StringBuffer();
		InputStream in = null;

		try
		{
			in = conn.getInputStream();
			int offset = 0;

			while( offset > -1 )
			{
				byte[] b = new byte[1024];
				offset = in.read( b );
				if ( offset > 0 ) sbHtml.append( new String( b, 0, offset ) );
			}
		}
		catch( Exception ex ) 
		{
			this.message = "한국신용정보의 서비스에 접속할 수 없습니다. [nice.nuguya.oivs.inc - makeServiceCaller]";
			return ""; 
		}
		finally 
		{ 
			try
			{ 
				if ( in != null ) in.close(); 
			} 
			catch( Exception ex ) {} 
		}
		
		if ( sbHtml.indexOf( "500" ) >= 0 )
			return "";
		else
			return sbHtml.toString().trim();
	}
	
	/*
	 * 전달된 문자열을 Decode하여 반환한다.
	 */
	private byte[] decode( String encodeString )
	{
		BASE64Decoder B64Decoder = new BASE64Decoder();
		byte[] bytDecodedData;
		
		try
		{
			bytDecodedData = B64Decoder.decodeBuffer( encodeString );
			return bytDecodedData;
		}
		catch( Exception ex )
		{
			this.message = "서비스에서 사용할 문자열을 변환(Decode)할 수 없습니다. [nice.nuguya.oivs.inc - decode]";
			return null;
		}
	}
	
	/*
	 * 전달된 byte[]를 Encode된 문자열로 반환한다.
	 */
	public String encode( byte[] plainData )
	{
		BASE64Encoder B64Encoder = new BASE64Encoder();
		String strEncodedString;
		
		try
		{
			strEncodedString = B64Encoder.encode( plainData );
			return strEncodedString;
		}
		catch( Exception ex )
		{
			this.message = "서비스에서 사용할 문자열을 변환(Encode)할 수 없습니다. [nice.nuguya.oivs.inc - encode]";
			return "";
		}
	}

	/*
	 * Triple-DES Decryption Method
	 */
	private String TriDES_DecryptString( byte[] key, byte[] encMessage )
	{
		byte[] bytDecryptedData;
		SecretKey sKey;
		Cipher DecryptCipher;

		try
		{
			//	키를 설정한다.
			sKey = new SecretKeySpec( key, "DESede" );

			//	Cipher를 생성하고 초기화 한다.
			DecryptCipher = Cipher.getInstance( "DESede/CBC/NoPadding" );
			DecryptCipher.init( Cipher.DECRYPT_MODE, sKey, new IvParameterSpec( key, 0, 8 ) );
			
			bytDecryptedData = DecryptCipher.doFinal( encMessage );
			return new String( bytDecryptedData, CHARSET_SERVER );
		}
		catch( Exception ex )
		{
			this.message = "서비스에서 사용할 문자열을 복호화처리 할 수 없습니다. [nice.nuguya.oivs.inc - TriDES_DecryptString]";
			return "";
		}
	}

	/*
	 * Triple-DES Encryption Method
	 */
	public byte[] TriDES_EncryptString( byte[] key, String plainMessage )
	{
		byte[] bytEncryptedData;
		SecretKey sKey;
		Cipher EncryptCipher;
		
		try
		{
			//	키를 설정한다.
			sKey = new SecretKeySpec( key, "DESede" );

			//	Cipher를 생성하고 초기화 한다.
			EncryptCipher = Cipher.getInstance( "DESede/CBC/NoPadding" );
			EncryptCipher.init( Cipher.ENCRYPT_MODE, sKey, new IvParameterSpec( key, 0, 8 ) );

			bytEncryptedData = convert8bytes( plainMessage.getBytes() );

			bytEncryptedData = EncryptCipher.doFinal( bytEncryptedData );
			return bytEncryptedData;
		}
		catch( Exception ex )
		{
			this.message = "서비스에서 사용할 문자열을 복호화처리 할 수 없습니다. [nice.nuguya.oivs.inc - TriDES_EncryptString]\\n[오류정보]\\n" + ex.getMessage();
			return null;
		}
	}
	
	public SecretKey generateRandomKey( int keySize )
	{
		SecretKey sKey;
		
		try
		{
			KeyGenerator kg = KeyGenerator.getInstance( "DESede" );
			kg.init( keySize );
			sKey = kg.generateKey();

			return sKey;
		}
		catch( Exception ex )
		{
			this.message = "정보 보안을 위한 보안키를 생성할 수 없습니다. [nice.nuguya.oivs.inc : callService(generateRandomKey)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오";
			return null;
		}
	}

	/*
	 * Byte Array를 Hex String으로 변환한다.
	 */
	public String convertByteArrayToHexString( byte[] sourceBytes )
	{
		byte ch = 0x00;
		int nIndex = 0;
		
		if ( sourceBytes == null || sourceBytes.length <= 0 ) return "";
		
		StringBuffer sbOut = new StringBuffer( sourceBytes.length * 2 );
		
		while ( nIndex < sourceBytes.length )
		{
			ch = (byte) (sourceBytes[nIndex] & 0xF0);			//	Strip Off high nibble
			ch = (byte) (ch >>> 4);								//	Strip the bits down
			ch = (byte) (ch & 0x0F);							//	high order bit is on
			sbOut.append( hexPseudo[ (int) ch ] );				//	Convert the nibble to a String Character
			ch = (byte) (sourceBytes[nIndex] & 0x0F);			//	Strip Off low nibble
			sbOut.append( hexPseudo[ (int) ch ] );				//	Convert the nibble to a String Character
			nIndex++;
		}
		
		return sbOut.toString();
	}
	
	/*
	 * 
	 */
	public String getHashedString( String plainString )
	{
		MessageDigest md;
		byte[] bytData, bytHashedData;

		try
		{
			bytData = plainString.getBytes();
			md = MessageDigest.getInstance( "MD5" );
			md.update( bytData );
			
			bytHashedData = md.digest();

			return this.convertByteArrayToHexString( bytHashedData );
		}
		catch( Exception ex )
		{
			this.message = "정보 보안을 위한 해쉬정보를 생성할 수 없습니다. [nice.nuguya.oivs.inc : callService(getHashedString)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오";
			return null;
		}
	}

	/*
	 * 서비스에서 전달된 정보를 분리하고 복호화한다.
	 */
	private String[] resolveReturnValue( String returnValue )
	{
		String[] aryValues = new String[] {};
		
		aryValues = returnValue.split(DELIMETER_NORMAL);

		if ( aryValues.length < 5 )
		{
			this.message = "실명확인 서비스에서 반환한 결과를 처리할 수 없습니다. [nice.nuguya.oivs : resolveReturnValue] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오";
			return aryValues;
		}
		
		if ( aryValues[4].indexOf( "_E_" ) >= 0 )
		{
			String decodedString = this.decode( aryValues[4].substring( 3, aryValues[4].length() ) ).toString();
			aryValues[4] = this.TriDES_DecryptString( this.skData.getBytes(), decodedString.getBytes() );
			aryValues[4] = aryValues[4].replaceAll( "/\1\2\3\4\5\6\7/g", "" );
		}
		
		return (aryValues);
	}

	private byte[] convert8bytes( byte[] srcBytes )
	{
		int nLength = ( srcBytes.length + 8 ) / 8;
		byte[] bytTarget = new byte[ nLength * 8 ];

		for( int nIndex = 0; nIndex < bytTarget.length; nIndex++ )
			bytTarget[nIndex] = 0x00;

		System.arraycopy( srcBytes, 0, bytTarget, 0, srcBytes.length );

		return bytTarget;
	}

	private byte[] addBytes( byte[] arg1, byte[] arg2 )
	{
		byte[] bytStream = new byte[ arg1.length + arg2.length ];

		System.arraycopy( arg1, 0, bytStream, 0, arg1.length );
		System.arraycopy( arg2, 0, bytStream, arg1.length, arg2.length );

		return bytStream;
	}

	/*
	 * 클라이언트 정보를 복호화하여 설정한다.
	 */

	public boolean desClientData()
	{
		String strCDESInfo = "";
		String[] aryValues = null;
		String strRtnValue = "";
		try
		{
			if ( this.clientData.equals( "" ) )
			{
				this.message = "사용자의 입력정보가 전달되지 않았습니다. [nice.nuguya.oivs.inc : desClientData(client data decryption)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오";
				return false;
			}

			strCDESInfo = new String( this.decode( this.clientData ), CHARSET_DEFAULT );
			aryValues = strCDESInfo.split(DELIMETER_NORMAL );

			if ( aryValues.length < 2 )
			{
				this.message = "사용자의 입력정보가 변형되었습니다. [nice.nuguya.oivs.inc : desClientData(client data decryption)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오";
				return false;
			}

			this.ckData = aryValues[0];
			strCDESInfo = aryValues[1];

			strCDESInfo = this.TriDES_DecryptString( this.ckData.getBytes( CHARSET_DEFAULT ), strCDESInfo.getBytes( CHARSET_DEFAULT ) );
			aryValues = strCDESInfo.split(DELIMETER_NORMAL );

			if ( aryValues.length < 4 )
			{
				this.message = "사용자의 입력정보가 부족합니다. [nice.nuguya.oivs.inc : desClientData(client data decryption)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오";
				return false;
			}
		
			this.userNm		= URLDecoder.decode( aryValues[0], CHARSET_SERVER );
			this.resIdNo	= aryValues[1];
			this.inqRsn		= aryValues[2];
			this.foreigner	= aryValues[3];

			return true;
		}
		catch( Exception ex )
		{
			this.message = "실명확인 서비스에서 오류가 발생하였습니다. [nice.nuguya.oivs.inc : desClientData(callSerivce exception)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오" +
				"\\n\\n[오류정보]\\n" + ex.getMessage();
			ex.printStackTrace();
			return false;
		}
	}
	/*
	 * 실명확인 서비스를 호출한다.
	 */
	public boolean callService()
	{
		String strCDESInfo = "";
		String[] aryValues = null;
		String strRtnValue = "";

		if ( this.pingInfo.equals( "" ) || this.niceId.equals( "" ) )
		{
			this.message = "한국신용정보 서비스를 이용하기 위한 정보가 없습니다. [nice.nuguya.oivs.inc : callService(check ping info)]\\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오";
			return false;
		}
		if( !this.message.equals( "" ))
			return false;
		try
		{

			SecretKey sKey = this.generateRandomKey( SRND_SIZE );
			if ( sKey == null ) return false;

			this.ckData = this.encode( sKey.getEncoded() ).substring( 0, 24 );
			String strCHashedInfo = this.getHashedString( this.niceId + this.pingInfo );

			byte[] bytEncData = this.TriDES_EncryptString( this.ckData.getBytes( CHARSET_DEFAULT ), this.niceId + DELIMETER_NORMAL + 
						this.pingInfo + DELIMETER_NORMAL + strCHashedInfo );

			strCDESInfo = CRND_SIZE + DELIMETER_NORMAL + this.encode( this.addBytes( this.ckData.getBytes( CHARSET_DEFAULT ), bytEncData ) );

			//	보안 호출을 처리한다.
			this.callSecure.setOperationName( new QName( URL_QNAME, "getServiceKey" ) );
			this.callSecure.addParameter( "arg", XMLType.XSD_STRING, ParameterMode.IN );
			this.callSecure.setReturnType( XMLType.XSD_STRING );

			strRtnValue = (String) this.callSecure.invoke( new Object[] { strCDESInfo } );
			aryValues = this.resolveReturnValue( strRtnValue );

			if ( aryValues == null || aryValues.length < 4 )
			{
				this.message = "실명확인 서비스와의 보안 통신에 필요한 정보가 부족합니다. [nice.nuguya.oivs.inc : callService(server getServiceKey)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오";
				return false;
			}
			else if ( ! aryValues[3].equals( "" ) )
			{
				this.message = "실명확인 서비스와의 보안 통신중에 오류가 발생하였습니다. [nice.nuguya.oivs.inc : callService(server getServiceKey)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오" +
						" \\n\\n[오류정보]\\n\\n" + aryValues[3];
				return false;
			}
			
			this.retCd		= aryValues[1];
			this.retDtlCd	= aryValues[2];

			if ( this.retCd.equals( "0" ) && this.retDtlCd.equals( "0" ) )
			{
				this.skData 		= aryValues[4];
				this.identifier		= aryValues[0];

				this.skData = this.TriDES_DecryptString( this.ckData.getBytes( CHARSET_DEFAULT ), this.decode( this.skData ) ).substring( 0, 24 );
				bytEncData = this.TriDES_EncryptString( this.skData.getBytes( CHARSET_DEFAULT ), this.niceId + DELIMETER_NORMAL + URLEncoder.encode( this.userNm, CHARSET_SERVER ) + DELIMETER_NORMAL + 
								this.resIdNo + DELIMETER_NORMAL + this.inqRsn + DELIMETER_NORMAL + this.foreigner );
				strCDESInfo = this.encode( this.addBytes( ( this.identifier + DELIMETER_NORMAL + SVC_TYPE_PERSONAL + DELIMETER_NORMAL ).getBytes(), this.encode( bytEncData ).getBytes() ) );

				//	서비스를 호출한다.
				this.callRealName.setOperationName( new QName( URL_QNAME, "checkRealName" ) );
				this.callRealName.addParameter( "arg", XMLType.XSD_STRING, ParameterMode.IN );
				this.callRealName.setReturnType( XMLType.XSD_STRING );
				
				strRtnValue = (String) this.callRealName.invoke( new Object[] { strCDESInfo } );
				aryValues = this.resolveReturnValue( strRtnValue );
				if ( aryValues == null || aryValues.length < 5 )
				{
					this.message = "실명확인 서비스의 결과 정보가 부족합니다. [nice.nuguya.oivs.inc : callService(server checkRealName)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오";
					return false;
				}
				else if ( ! aryValues[3].equals( "" ) )
				{
					this.message = "실명확인 서비스 진행 중에 오류가 발생하였습니다. [nice.nuguya.oivs.inc : callService(server checkRealName)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오" +
							" \\n\\n[오류정보]\\n\\n" + aryValues[3];
					return false;
				}

				if ( ! aryValues[0].equals( this.getHashedString( this.resIdNo ) ) )
				{
					this.message = "실명확인 서비스의 결과가 변형되어 정확한 정보가 아닙니다. [nice.nuguya.oivs.inc : callService(server checkRealName.. hash mismatch)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오";
					return false;
				}
				
				this.retCd		= aryValues[1];
				this.retDtlCd	= aryValues[2];
				this.message	= aryValues[3];

				aryValues = aryValues[4].split(DELIMETER_DATA );
				this.minor		= aryValues[0];
				if (aryValues.length > 1 )
					this.dupeinfo = aryValues[1];

				return true;
			}
			else
			{
				this.message = "실명확인 서비스와의 보안 통신을 계속 진행할 수 없습니다. [nice.nuguya.oivs : callService(server checkRealName)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오" +
						"\\n\\n[결과코드]\\n\\n" + aryValues[1] + "," + aryValues[2];
				return false;
			}
		}
		catch( Exception ex )
		{
			this.message = "실명확인 서비스에서 오류가 발생하였습니다. [nice.nuguya.oivs.inc : callService(callSerivce exception)] \\n\\n고객상담센터(☎ 1588-2486) 로 연락하십시오" +
				"\\n\\n[오류정보]\\n" + ex.getMessage();
			ex.printStackTrace();
			return false;
		}
	}
}
%>