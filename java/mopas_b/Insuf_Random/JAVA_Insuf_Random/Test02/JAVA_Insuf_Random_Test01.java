public class CS
{

	/* 
	java.lang.Math 클래스의 random() 메소드는 seed를 재설정할 수 없기 때문에 위험합니다. 
	*/
	public double bad()
	{      
		// 보안약점
		return Math.random();
	}
	
	/* 
	java.util.Random 클래스는 seed를 재설정하지 않아도 매번 다른 난수를 생성합니다. 
	*/
	public int good()
	{
		//  수정 : java.util.Random 클래스 사용
	  	Random r = new Random();
	  	// setSeed() 메소드를 사용해서 r을 예측 불가능한 long 타입으로 설정합니다. 
		r.setSeed(new Date().getTime());
		return (r.nextInt() % 6) + 1;
	}

}