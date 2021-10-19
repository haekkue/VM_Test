public class CSample
{
	public static void main(String[] args) 
    {
		InputThread inThread = new InputThread("InputThread");
     	OutputThread outThread = new OutputThread("OutputThread");
        
	    inThread.connect(outThread.getOutput());
        inThread.start();
        outThread.start();
    }
}
 
class InputThread extends Thread
{
    PipedReader input = new PipedReader();
    StringWriter sw = new StringWriter(); 
    
    public void run() {
        try {
            
            int data = 0;
            
            while((data=input.read())!=-1){
                sw.write(data);//StringBuffer에 쓴다
            }
            
            System.out.println(getName()+ " received : " + sw.toString());
			// input 해제 구문이 없음
        } 
        catch(IOException e) {
			// ...
     	}
    }
    
    public void connect(PipedWriter output) {
		
		try {
            input.connect(output);
        }
   	    catch(IOException e) {
			// ...
		}
    }
}
 
class OutputThread extends Thread
{
	PipedWriter output = new PipedWriter();
    
    OutputThread(String name){
        super(name);//Thread(String name);
    }
    
    public void run() {
        try{
            String msg = "Hello";
            System.out.println(getName() + " sent : " + msg);
            output.write(msg);
            output.close();	// 예외 발생시 자원 해제 안 됨
        } catch(IOException e) {
			// ...
        }
    }
    
    public PipedWriter getOutput(){
        return output;
    }
    
    public void connect(PipedReader input) {
        try {
            output.connect(input);
        } 
        catch(IOException e) {
			// ...
        }
    }
}

class InputThread2 extends Thread
{
    PipedReader input = new PipedReader();
    StringWriter sw = new StringWriter(); 
    
    public void run() {
    	try {
            int data = 0;
            
            while((data=input.read())!=-1){
                sw.write(data);//StringBuffer에 쓴다
            }
            
            System.out.println(getName()+ " received : " + sw.toString());
        } 
        catch(IOException e) {
			// ...
        }
	    finally {
			input.close();		// 예외 발생 여부와 상관없이 자원 해제
		}
    }
    
	public void connect(PipedWriter output) {
		try {
        	input.connect(output);
     	}
   	    catch(IOException e) {
			// ...
		}
    }
}
 
class OutputThread2 extends Thread
{
	PipedWriter output = new PipedWriter();
      
       	OutputThread(String name){
        	super(name);//Thread(String name);
       	}
      
       	public void run() {
        	try{
                String msg = "Hello";
                System.out.println(getName() + " sent : " + msg);
                output.write(msg);
                // output.close();	// 예외 발생시 자원 해제 안 됨
            } catch(IOException e) {
			    // ...
            }
		finally {
			output.close();	// 예외 발생 여부와 상관없이 자원 해제 
		}	
    }
    
    public PipedWriter getOutput(){
        return output;
    }

    public void connect(PipedReader input) {
    	try {
        	output.connect(input);
     	} 
     	catch(IOException e) {
            // ...
     	}
  	}
}