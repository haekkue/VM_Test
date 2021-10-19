/*
Java ¸Þ½îµå ¼±¾ð
*/

public class CSon
{
	public string m_strTest = null;

	public void bad0()
	{
		System.out.println("Test : " + m_strTest.Length); 
	}

	public void bad1()
	{
		/* Java */
	}

	public void bad2(int a)
	{ }

	public void bad3(int a)
	{}

	public void bad1(int a )
	{in;}

	public void bad4(int a, String aaa )
	{ 
		if (a == 1)
		{
			System.out.println("abc");
		}

		if (My.a == 1)
		{
			System.out.println("abc");
		}

		for (int i=0; i<1; i++)
		{
			System.out.println("abc" + i);
		}

		while (i)
		{
			System.out.println("abc" + i);
		}

		switch (i)
		{
		case 1:
			System.out.println("abc" + i);
			break;
		default:
			System.out.println("abc" + i);
			break;
		}

		try
		{
			
		}
		catch (IOException ioe)
		{
			System.out.println("abc" + ioe);
		}
	}

	public void bad5(int a, String aaa, double ccc)
	{ }

	public void bad6(int[] a)
	{ }

	public void bad7(int[] a, String[] bbb)
	{ }

	public void bad8(int[] a, String  [   ] bbb, double[] ddd)
	{ }

	public void bad9(int[] a, String  [   ] bbb, double[] ddd, MyMy aa)
	{ }

	public void bad10(int[] a, String  [   ] bbb, double[] ddd, MyMy [  ] aa)
	{ }

	public void bad11(int a[])
	{ }

	public void bad12(int a[], String bbb[])
	{ }

	public void bad13(int a[], String bbb[], double ddd[])
	{ }
}
