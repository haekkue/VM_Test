public class CS
{

	/* 
	java.lang.Math Ŭ������ random() �޼ҵ�� seed�� �缳���� �� ���� ������ �����մϴ�. 
	*/
	public double bad()
	{      
		// ���Ⱦ���
		return Math.random();
	}
	
	/* 
	java.util.Random Ŭ������ seed�� �缳������ �ʾƵ� �Ź� �ٸ� ������ �����մϴ�. 
	*/
	public int good()
	{
		//  ���� : java.util.Random Ŭ���� ���
	  	Random r = new Random();
	  	// setSeed() �޼ҵ带 ����ؼ� r�� ���� �Ұ����� long Ÿ������ �����մϴ�. 
		r.setSeed(new Date().getTime());
		return (r.nextInt() % 6) + 1;
	}

}