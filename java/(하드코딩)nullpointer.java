/*
널 포인터 

취약점 예제 소스 
*/

class CTest
{
	// 파라미터 
	public boolean BAD01(String strPa1) 
	{
		String strNames[] = strPa1.split(" ");	// 널에러발생

		if (strNames.length != 2) {
			return false;
		}

		return 
			(isCapitalized(strNames[0]) && isCapitalized(strNames[1]));
	}

	// 파라미터 
	public boolean BAD02(String strPa1) 
	{
		strPa1 = null;

		String strNames[] = strPa1.split(" ");	// 널에러발생

		if (strNames.length != 2) {
			return false;
		}

		return 
			(isCapitalized(strNames[0]) && isCapitalized(strNames[1]));
	}

	// 파라미터 
	public boolean BAD03(String strPa1) 
	{
		String strNames1] = strPa1.split(" ");		// 널에러발생

		String strNames2[] = strPa1.ToString();	// 널에러발생

		String strNames3[] = strPa1.length();		// 널에러발생

		strPa1 = "abc";
		String strNames4[] = strPa1.length();		// 널에러발생안함

		return 
			(isCapitalized(strNames1[0]) && isCapitalized(strNames1[1]));
	}

	// 파라미터
	public boolean BAD04(String strPa1) 
	{
		String strTemp = null;

		strPa1 = strTemp;
	
		String strNames[] = strPa1.split(" ");	// 널에러발생하는데 탐지 못 함 

		if (strNames.length != 2) {
			return false;
		}

		return 
			(isCapitalized(strNames[0]) && isCapitalized(strNames[1]));
	}

	// 변수
	public boolean BAD10() 
	{
		String strTemp = null;

		String strNames[] = strTemp.split(" ");	// 널에러발생 

		if (strNames.length != 2) {
			return false;
		}

		return 
			(isCapitalized(strNames[0]) && isCapitalized(strNames[1]));
	}

	// 변수 
	public boolean BAD11() 
	{
		String strPa1;

		strPa1 = null;

		String strNames[] = strPa1.split(" ");	// 널에러발생

		if (strNames.length != 2) {
			return false;
		}

		return 
			(isCapitalized(strNames[0]) && isCapitalized(strNames[1]));
	}

	// 변수 
	public boolean BAD12() 
	{
		String strPa1 = null;

		String strNames1] = strPa1.split(" ");		// 널에러발생

		String strNames2[] = strPa1.ToString();	// 널에러발생

		String strNames3[] = strPa1.length();		// 널에러발생

		strPa1 = "abc";
		String strNames4[] = strPa1.length();		// 널에러발생안함

		return 
			(isCapitalized(strNames1[0]) && isCapitalized(strNames1[1]));
	}

	// 변수 
	public boolean BAD13() 
	{
		String strPa1, strTest;

		strPa1 = null;

		strTest = strPa1;

		String strNames[] = strTest.split(" ");	// 널에러발생

		if (strNames.length != 2) {
			return false;
		}

		return 
			(isCapitalized(strNames[0]) && isCapitalized(strNames[1]));
	}

	// 변수
	public void BAD14()
	{
		String str3=null, String str4=null;

		//nullCheck(str3);
		String strTrim3 = str3.trim();

		//nullCheck(str4);
		String strTrim4 = str4.trim();
	}
	
	// 파라미터 
	public boolean GOOD01(String strPa1) 
	{
		strPa1 = "ABC DEF";

		String strNames[] = strPa1.split(" ");	// 널에러발생

		if (strNames.length != 2) {
			return false;
		}

		return 
			(isCapitalized(strNames[0]) && isCapitalized(strNames[1]));
	}

	// 파라미터
	public boolean GOOD02(String strPa1) 
	{
		String strTemp = "abc";

		strPa1 = strTemp;
	
		String strNames[] = strPa1.split(" ");	

		if (strNames.length != 2) {
			return false;
		}

		return 
			(isCapitalized(strNames[0]) && isCapitalized(strNames[1]));
	}

	// 파라미터
	public void GOOD03(String str3, String str4)
	{
		nullCheck(str3);
		String strTrim3 = str3.trim();

		nullCheck(str4);
		String strTrim4 = str4.trim();
	}

	public String nullCheck(String strCk)
	{
		String strTemp = "ddddd";
		if (strCk == null)
		{
			strCk = strTemp;
		}
		return strCk;
	}

	// 파라미터
	public void GOOD04(String str3, String str4)
	{
		nullCheck(str3);
		String strTrim3 = str3.trim();

		nullCheck(str4);
		String strTrim4 = str4.trim();
	}

	// 변수 
	public void GOOD10()
	{
		Socket socket = null; 
		try { 
			socket = new Socket(machine, daytimeport); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			String time = reader.readLine(); 
			System.out.printf("%s says it is %s %n", machine, time); 
		} 
		catch (UnknownHostException e) 
		{ 
			e.printStackTrace(); 
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
		finally 
		{ 
			try { 
				//  socket.close(); 
			} 
			catch (IOException e) 
			{ 
				e.printStackTrace(); 
			} 
		} 
	}

	// 변수
	public void GOOD11()
	{
		String str3, String str4;

		nullCheck(str3);
		String strTrim3 = str3.trim();

		nullCheck(str4);
		String strTrim4 = str4.trim();
	}
	
	// 변수 
	public void GOOD12()
	{
		WorkBook wb = new WorkBook();
		wb.getSheet();

		RangeStyle headerStyle = wb.getRangeStyle();
		headerStyle.setFontBold(true);

		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(new File(path));
			wb.write(fout);
		} 
		finally {
			wb.releaseLock();
			wb = null;		// 뒤에 null이 할당되었는데 앞에서 잡히면 안 됨
			if(fout != null){
				fout.close();
			}
		}
	}		
}

