public class CSample 
{
	public static void main(String[] args) 
  	{
    		if (args.length != 1) {
      			System.out.println("사용법 : java ByteArrayInputOutputTest filename");
      			System.exit(0);
    		}
  
    		FileInputStream  fis = null;
		ByteArrayInputStream  bais  = null;
		ByteArrayOutputStream  baos = null;  
    
		try {
      			fis  = new FileInputStream(args[0]);
      			baos = new ByteArrayOutputStream();
      			byte[] buffer  = new byte[512];
      			int readcount = 0;
 
      			//파일로부터 읽어들인 byte배열을 ByteArrayOutputStream에 쓴다.
      			while((readcount = fis.read(buffer)) != -1) {
         			//출력한 결과를 ByteArrayOutputStream의 내부저장 공간에 저장하는 부분.
        			baos.write(buffer, 0, readcount);
      			}
   
      			//ByteArrayOutputStream의 내부 저장공간에 저장된 바이트 배열을 반환.
      			byte[] fileArray = baos.toByteArray();
      			System.out.println("파일의 내용을 모두 읽어들여 byte[]로 만들었습니다.");
      			System.out.println("읽어들인 byte의 수 :" + fileArray.length);
 
      			//byte[]로부터 읽어들이는 ByteArrayInputStrem을 생성한다.
      			bais = new ByteArrayInputStream(fileArray);
      			//ByteArrayInputStream을 통하여 읽어들인 byte배열을 표준출력장치에 출력한다.

		      	while((readcount = bais.read(buffer)) != -1) {
        			System.out.write(buffer, 0, readcount);
      			}
 
      			System.out.println("\n\n");
      			System.out.println("읽어들인 내용을 모두 출력하였습니다.");   
    		} catch(Exception ex) {
      			System.out.println(ex);
    		} finally {
      			try {
        			fis.close();
      				//bais.close();		// 자원해제 구문이 주석처리
     				//baos.close();
      			} catch(IOException ioe) {
        			System.out.println(ioe);
     		 	}
    		}
  	}
}

public class CSample2
{
	public static void main(String[] args) 
  	{
    		if (args.length != 1) {
      			System.out.println("사용법 : java ByteArrayInputOutputTest filename");
      			System.exit(0);
    		}
  
    		FileInputStream  fis = null;
		ByteArrayInputStream  bais  = null;
		ByteArrayOutputStream  baos = null;  
    
		try {
      			fis  = new FileInputStream(args[0]);
      			baos = new ByteArrayOutputStream();
      			byte[] buffer  = new byte[512];
      			int readcount = 0;
 
      			//파일로부터 읽어들인 byte배열을 ByteArrayOutputStream에 쓴다.
      			while((readcount = fis.read(buffer)) != -1) {
         			//출력한 결과를 ByteArrayOutputStream의 내부저장 공간에 저장하는 부분.
        			baos.write(buffer, 0, readcount);
      			}
   
      			//ByteArrayOutputStream의 내부 저장공간에 저장된 바이트 배열을 반환.
      			byte[] fileArray = baos.toByteArray();
      			System.out.println("파일의 내용을 모두 읽어들여 byte[]로 만들었습니다.");
      			System.out.println("읽어들인 byte의 수 :" + fileArray.length);
 
      			//byte[]로부터 읽어들이는 ByteArrayInputStrem을 생성한다.
      			bais = new ByteArrayInputStream(fileArray);
      			//ByteArrayInputStream을 통하여 읽어들인 byte배열을 표준출력장치에 출력한다.

		      	while((readcount = bais.read(buffer)) != -1) {
        			System.out.write(buffer, 0, readcount);
      			}
 
      			System.out.println("\n\n");
      			System.out.println("읽어들인 내용을 모두 출력하였습니다.");   
    		} catch(Exception ex) {
      			System.out.println(ex);
    		} finally {
      			try {
        			fis.close();
      				bais.close();		// 자원 해제
     				baos.close();	// 자원 해제
      			} catch(IOException ioe) {
        			System.out.println(ioe);
     		 	}
    		}
  	}
}
