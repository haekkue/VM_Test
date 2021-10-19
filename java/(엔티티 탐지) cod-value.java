/*
변수 구분되는거 확인
*/

class CMy
{
	String m_strTest1 = null;
	private String m_strTest2 = null, m_strTest3, m_strTest4;
	public ServletRequest srReq;
	public ServletRequest srRequest;
}

class CTest
{
	public void TEST1(ServletRequest srReq, Connection conParam)
	{
		CMy cmY;
		ServletRequest srRequest;
		Strings lastName1, lastName2, lastName3, lastName4, lastName5, lastName6,lastName7[10];
		int[] nAbc1 = {1,2,3,4,5};
		int[][] nAbc2 = new int [][]{{1,2}, {4,5}};
		int[][] nAbc3 = {{1,2,3},{4,5},{6}};

		String lastName8 = srReq.getParameter("       ");
		int userId1 = Integer.parseInt(srReq.getParameter("        "));
		int userId2[] = Integer.parseInt(srReq.getParameter("        "));
		int userId3[10] = Integer.parseInt(srReq.getParameter("        "));
		int userId4[a] = Integer.parseInt(srReq.getParameter("        "));
		lastName1 = srReq.getParameter("       ");
		lastName2 += srReq.getParameter("       ");
		lastName3 = srReq;
		lastName4 += srRequest.getParameter("       ");
		lastName5 += cmY.srReq.getParameter("       ");
		lastName6 += cmY.srRequest.getParameter("       ");
		lastName7[1] += cmY.srReq.getParameter("       ");

		lastName3 += "abc";
		lastName3 = srReq.getParameter("       ");

		String query = "UPDATE userData INTO lastName=? WHERE userid = ?";
		PreparedStatement statement = conParam.prepareStatement(query);
		statement.setString(1, lastName1);
		statement.setInt(2, userId1);
		statement.executeUpdate();

		String lastName9 += srReq.getParameter("       ");

		System.out.println("abc1" + nAbc1.length);
		System.out.println("abc1" + nAbc2[0].length);
		System.out.println("abc1" + nAbc2[1].length);
		System.out.println("abc1" + nAbc3[0].length + nAbc3[1].length);

		System.out.println("abc1" + nAbc2[0][0]);
		System.out.println("abc1" + nAbc3[0][0]);
	}

	public void TEST2()
	{
		int nTest1, nDiv2, nTest2[10]={0,};      //  [swc] nDiv1 변수의 데이터 타입을 찾음		
		int nTemp1, nTemp2[10]={0,}, nDiv1;  // [swc] nDiv1 변수의 데이터 타입을 못 찾음
	}

	public void TEST3(int[] nPam1, String strPam2, int[][] nPam3, double dbPam1[][], double dbPam2[], double[][] dbPam3)
	{
		System.out.println("abc1" + nPam1.length + nPam1[0]);
		System.out.println("abc1" + strPam2.length + nPam1[1]);
		System.out.println("abc1" + nPam3.length + nPam3[1][0]);
		System.out.println("abc1" + dbPam1.length + dbPam1[1] + dbPam1[0][1]);
		System.out.println("abc1" + dbPam2.length + dbPam2[1]);
		System.out.println("abc1" + dbPam3.length + dbPam1[1] + dbPam3[0][1]);
	}
}

