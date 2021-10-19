public class CS
{  
    public void bad() throws Throwable
    {
        String a;
        String b;
        int c;
        int d;
        
        c = Integer.MAX_VALUE;
        a = System.getProperty("user.home"); 
        
        try {
        	data = Integer.parseInt(s_data.trim());
        	
        	b = a + (String)d;
        	b = "1" + c;
        	c = (int)a + d;
        	a = c + d.toString();
        	a += b;
        	a -= c;
        	c *= d;
        	d /= c;
        	c %= d;
        	c *= (int)b;
        	d &= c;
        	a |= d;
        	a ^= b;
        	c++;
        	d--;
        	++c;
        	--d;
        } 
        catch( IOException ioe )
        {
            IO.logger.log(Level.WARNING, "Error with stream reading", ioe);
        }    
    }
}