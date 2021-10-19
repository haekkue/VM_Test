/*
널포인터역참조 - Inner class 처리
*/

class Outer
{
	private int a=10;
	public void disp()
	{
		System.out.println( "Outer a : " +a);
   		//////////// Local Inner Class ////////////
		class InnerLocal
		{
			private int c=30;
			public int d=null;
			public void disp()
			{
				System.out.println("InnerLocal a :" + a);
				System.out.println("InnerLocal c :" + c);
			}
		} //class InnerLocal
		InnerLocal il = new InnerLocal();
		il.disp();
		System.out.println("InnerLocal d :" + il.d.length);
   }
   
	//////////// Member Inner Class ////////////
	class InnerMember
	{
		private int b=20;
		public void disp()
		{
			System.out.println(" InnerMember a : " +a);
			// 	외부클래스의 멤버를 참조
			System.out.println(" InnerMember b : " +b);
		}
	}// class InnerMember
   
	//////////// Static Inner Class ////////////
	static class InnerStatic
	{
		private static int d = 40;
		public void disp()
		{
			//System.out.println("InnerStatic a : " + a);        //static은 static끼리만 접근하기 때문에 에러가 난다.
			System.out.println("InnerStatic d : " + d);
		}
	} // Static Inner Class
} //class Outer

abstract class InnerAnony
{
	int e = 50;
	public abstract void disp(); //추상클래스는 객체 생성 X
} //추상 메서드를 포함하고 있는 추상 메서드

public class Test
{
	public static void main(String[] args)
    {
		//Member Inner class
		Outer.InnerMember im = new Outer(). new InnerMember();
		im.disp();
		   
		//Local Inner class
		Outer out = new Outer();
		out.disp();
		   
		//Static Inner class
		Outer.InnerStatic is = new Outer.InnerStatic();
		is.disp();
		   
		// Anonymous Inner Class
		//1.
		InnerAnony ia = new InnerAnony()
		//추상메서드는 미완성이기때문에 익명내부클래스를 사용해서 완성시킨다.
		{
			public void disp()
			{
				System.out.println("InnerAnony e : " + e);
			}
		};
		ia.disp();
		   
		//2
		new InnerAnony()
		{
			public void disp()
			{
				System.out.println( "Inner Anony e : " + e);
			}
		}.disp(); //객체.메서드이기 때문에 이런 식으로 쓰인다.
    }
}