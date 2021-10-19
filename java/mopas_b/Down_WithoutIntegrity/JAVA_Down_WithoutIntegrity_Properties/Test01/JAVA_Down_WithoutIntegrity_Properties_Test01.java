import java.io.FileInputStream; 
import java.io.IOException; 
import java.util.Enumeration; 
import java.util.Properties; 
import java.util.ResourceBundle; 


public class ReadPropFile { 

    
    public static void getData(String fileName) { 
        
        try { 
            
            Properties propertiesFile = new Properties(); 
            propertiesFile.load(new FileInputStream(fileName)); 
            String studentName = propertiesFile.getProperty("name"); 
            String roll = propertiesFile.getProperty("roll"); 
            System.out.println("Student Name :: "+studentName); 
            System.out.println("Roll Number :: "+roll); 
            
            //Fetch all the Properties. 
            
            String key; 
            Enumeration e = propertiesFile.propertyNames(); 
            
            while (e.hasMoreElements()) { 
                key = (String)e.nextElement(); 
                System.out.println(key+" "+propertiesFile.getProperty(key)); 
            }
            
        } catch(IOException e) { 
            e.printStackTrace(); 
        } 
    }
    
    public void main() { 
        
        Properties propertiesFile = new Properties(); 
        String fileName = propertiesFile.getProperty("fileName");  
        getData(fileName);
        
    }
} 

