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
                sw.write(data);//StringBuffer�� ����
            }
            
            System.out.println(getName()+ " received : " + sw.toString());
			// input ���� ������ ����
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
            output.close();	// ���� �߻��� �ڿ� ���� �� ��
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
                sw.write(data);//StringBuffer�� ����
            }
            
            System.out.println(getName()+ " received : " + sw.toString());
        } 
        catch(IOException e) {
			// ...
        }
	    finally {
			input.close();		// ���� �߻� ���ο� ������� �ڿ� ����
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
                // output.close();	// ���� �߻��� �ڿ� ���� �� ��
            } catch(IOException e) {
			    // ...
            }
		finally {
			output.close();	// ���� �߻� ���ο� ������� �ڿ� ���� 
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