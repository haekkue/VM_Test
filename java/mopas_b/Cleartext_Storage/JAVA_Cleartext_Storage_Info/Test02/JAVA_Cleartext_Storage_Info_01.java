public class CS
{

    private static final long serialVersionUID = 5701119142172238601L;  
	private String x1 = "7013191421722";  	// 잘못된 주민번호


	private String s1 = "7809251324011";    	// 주민번호
	private String s2;							// 7011191421722
	private String s3;							// no7011191421722
	private String s4;							// no 7011191421722
	private String s5;							// 번호7011191421722
	private String s6;							//7011191421722번호
	private String s7;							// 701119-1421722
	private String s8;							// 701119-1421722nono
	
    public void bad () {
        
        // 주민번호 테스트 
        String j1 = "7809251324011";  
        String j2 = "780925-1324011";
        String j3 = "780925 1324011";
        long j4 = 7809251324011; 
        
        //외국인 번호 테스트
        String f1 = "7001015801796";  
        String f2 = "700101-5801796";
        String f3 = "700101 5801796";
        long f4 = 7001015801796; 
        
        // 사업자 번호
        String b1 = "1058675455";  
        String b2 = "105-86-75455";
        String b3 = "105 86 75455";
        long b4 = 1058675455; 
    }
	
}