import java.net.*;
import java.io.*;
public class ClientTest {
    public static void main(String[] args)throws IOException{
        //[1]소켓 생성(클:port는 남는 임의의 포트, IP는 자기IP)
        String ip="172.16.55.32";
        int port=10002;             //포트번호는 서버와 일치
        Socket socket= new Socket(ip,port);
        socket.setReuseAddress(true);
        //Socket을 구했다 == 통신 대상이 명확해 졌다.
 
        //[2]Stream구하기(in, out)
        InputStream is = socket.getInputStream();
 
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        PrintWriter pw = new PrintWriter(osw);
 
        //키보드로 입력박고 네트워크로 보네기
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
 
        System.out.print("문자열 입력 : ");
        String line = null;
        while((line = br.readLine())!=null){
            if(line.equals("quit")){break;}
            System.out.print("문자열 입력 : ");
            pw.println(line);
            pw.flush();
        }
      //마무리
        br.close();
        pw.close();
        socket.close();
    }
}
