/*
Filename : KRD_105_Unrestricted_Upload_of_File_with_Dangerous_Type__Servlet_connect_tcp_fileItem_0101_good.java
CWEID    : CWE434
sinkname : getOriginalFilename
sinkline : 83,
makedate : 2012 12 24
license  : Copyright KISA.
*/

package testcases.KRD_105_Unrestricted_Upload_of_File_with_Dangerous_Type;

import testcasesupport.*;

import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import com.oreilly.servlet.MultipartRequest;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.io.*;


import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

import java.util.logging.Logger;

public class KRD_105_Unrestricted_Upload_of_File_with_Dangerous_Type__Servlet_basic_fileItem_0101_good extends AbstractTestCaseServlet
{

    

    public void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        goodG2B(request, response);
    }

    /* goodG2B() - uses goodsource and badsink */
    private void goodG2B(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        ServletContext context = this.getServletContext();
        String path = context.getRealPath("/upload");
        File dir = new File(path);
        if(!dir.exists()) dir.mkdir();
        if (isMultipart) {
        	FileItemFactory factory = new DiskFileItemFactory();
        	ServletFileUpload upload = new ServletFileUpload(factory);

        	try {
        		List items = upload.parseRequest(request);
        		Iterator iterator = items.iterator();
        		while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    String FileName ="";
                    String FileNametemp = "";
                    if(item.getFieldName().equals("file"))
                    {
                    	FileNametemp = item.getName();
                    	StringTokenizer st = new StringTokenizer(FileNametemp, "\\");
                    	while(st.hasMoreTokens())
                    	{
                    		FileNametemp = st.nextToken();
                    	}
                    }
        			
        			/* FIX */
        			if(FileNametemp != null && !((FileNametemp.endsWith(".asp") || FileNametemp.endsWith(".jsp") || 
        			   FileNametemp.endsWith(".html") || FileNametemp.endsWith(".htm") || FileNametemp.endsWith(".php"))))
        			{
        				File uploadFile = new File(dir,FileName);
        				item.write(uploadFile);
        			}
        		}
        	} catch (FileUploadException e) {
                throw new FileUploadException("File Upload Exception has been occurred");
            } 
        }
    }

    /* Below is the main(). It is only used when building this testcase on
       its own for testing or for building a binary to use in testing binary
       analysis tools. It is not used when compiling all the testcases as one
       application, which is how source code analysis tools are tested. */
    public static void main(String[] args) throws ClassNotFoundException,
           InstantiationException, IllegalAccessException
    {
        mainFromParent(args);
    }
}

