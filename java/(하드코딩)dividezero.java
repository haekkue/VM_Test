/*
0 으로 나누기

취약점 예제 소스 
*/

class CTest
{
	// 파라미터 
	public boolean BAD01(int nDiv1) 
	{
		int nTemp1, nTemp2[10]={0,};

		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (100 / nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (100 % nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (100.11 / nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (100.13 % nDiv1) + "\n");

		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + ((100.13) % nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (nTemp1 % nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (nTemp2[1] % nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + ( (nTemp1) % nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + ( (nTemp2[1]) % nDiv1) + "\n");
	}

	// 외부입력
	public boolean BAD10() 
	{
		int nDiv1, nTemp1, nTemp2[10]={0,};
		//int nTemp1, nTemp2[10]={0,}, nDiv1;  [test] nDiv1 변수의 데이터 타입을 못 찾음

		try {
			finstr = new FileInputStream("../common/config.properties");
			props.load(finstr);

			String s_data = props.getProperty("pid");
			nDiv1 = Integer.parseInt(s_data.trim());	
		}	

		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (100 / nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (100 % nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (100.11 / nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (100.13 % nDiv1) + "\n");

		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + ((100.13) % nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (nTemp1 % nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (nTemp2[1] % nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + ( (nTemp1) % nDiv1) + "\n");
		IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + ( (nTemp2[1]) % nDiv1) + "\n");
	}


	// 파라미터 
	public boolean GOOD01(int nDiv1)
	{
		if( nDiv1 != 0 ) //0에 대한 확인이 필요함.
		{
			IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (100 % nDiv1) + "\n");
		}
		else
		{
			IO.writeLine("This would result in a modulo by zero");
		}
	}

	// 외부입력 
	public void GOOD10()
	{
		int nDiv1, nTemp1, nTemp2[10]={0,};

		try {
			finstr = new FileInputStream("../common/config.properties");
			props.load(finstr);

			String s_data = props.getProperty("pid");
			nDiv1 = Integer.parseInt(s_data.trim());	
		}	

		if( nDiv1 != 0 ) //0에 대한 확인이 필요함.
		{
			IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (100 % nDiv1) + "\n");
		}
		else
		{
			IO.writeLine("This would result in a modulo by zero");
		}
	}

	// 외부입력 
	public void GOOD11()
	{
		int nDiv1, nTemp1, nTemp2[10]={0,};

		try {
			finstr = new FileInputStream("../common/config.properties");
			props.load(finstr);

			String s_data = props.getProperty("pid");
			nDiv1 = Integer.parseInt(s_data.trim());	
		}	

		if( zeroCheck(nDiv1) ) //0에 대한 확인이 필요함.
		{
			IO.writeLine("100%" + String.valueOf(nDiv1) + " = " + (100 % nDiv1) + "\n");
		}
		else
		{
			IO.writeLine("This would result in a modulo by zero");
		}
	}

	public boolean zeroCheck(int nDiv1)
	{
		if( nDiv1 != 0 ) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

