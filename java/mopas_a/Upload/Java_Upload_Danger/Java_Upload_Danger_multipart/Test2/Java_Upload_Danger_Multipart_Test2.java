/*------------------------------------------------------------------------------
 * PROJ : �������������ý���
 * NAME : FileUploadMgr.java
 * Copyright 2009 LG CNS All rights reserved
 *------------------------------------------------------------------------------
 *                  ��         ��         ��         ��
 *------------------------------------------------------------------------------
 * 2009. 04. 03      ������       �����ۼ�
 * 2010. 04. 01      ��  ��       ��������2�ܰ� ����Ʈ������ ���ε� ����
 *----------------------------------------------------------------------------*/
package cis.cpis2gi.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Enumeration;

import cis.common.core.BaseManager;
import cis.common.core.UserContext;
import cis.common.exception.BizException;
import cis.common.log.DebugLogger;
import cis.common.log.ErrorLogger;
import cis.common.util.FileUploadPath;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 *
 */
public class FileUploadMgr extends BaseManager {


    FileUploadPath fileUpPath = null;

    private static final String SAVE_PATH = "filepath.upload.root.cpis.img";
    private static final int SIZE_LIMIT = 5 * 1024 * 1024; // ���Ͼ��ε� �뷮 ����.. 5Mb
    private static final String[] EXTS = { "exe", "jsp", "php", "com", "vbs", "js", "asp" };

    public void control() throws Exception {

//        String context = UserContext.getRequest().getHeader("Content-Type");
//        String flag = UserContext.getRequest().getParameter("flag");
//
//        DebugLogger.log("context : " + context);
//        DebugLogger.log("flag : " + flag);
//        DebugLogger.log("contorl");
//        if (null != context) {
//            if (StringUtils.equals("Uld", flag)) {
                fileUpload();
//            }
//        }
    }

    // ���� ���ε�
    public void fileUpload() throws Exception {

        try {

            String uploadDir = createUploadDir(true);

            File directory = new File(FileUploadPath.getPath(SAVE_PATH) + uploadDir);
            DebugLogger.log("==========directory=======>>>"+directory.getAbsolutePath());
            if (!directory.exists()) {
              DebugLogger.log("==========directory.exists()=======>>>"+directory.exists());
              DebugLogger.log("==========directory.mkdirs()=======>>>"+directory.mkdirs());
            }

            MultipartRequest multi = new MultipartRequest(UserContext.getRequest(), FileUploadPath.getPath(SAVE_PATH)
                    + uploadDir, SIZE_LIMIT, "UTF-8", new DefaultFileRenamePolicy());

            Enumeration fileNames = multi.getFileNames(); // ���� �̸�

            String id = multi.getParameter("id");
            if (fileNames == null) {

            } else {

                String tmpStr = "";

                String sName = "";
                String uName = "";
                while (fileNames.hasMoreElements()) {
                    tmpStr = (String) fileNames.nextElement();
                    if(tmpStr != null){
                      if(tmpStr.equalsIgnoreCase("..") || tmpStr.equalsIgnoreCase("/")){
                        throw new BizException("���ϸ� Ư�����ڴ� ���Ե� �� �����ϴ�.");
                      }
                    }
                    sName = multi.getFilesystemName(tmpStr);

                    //�̹������� ÷�δ� �ѱ��� ��� �� ���� �� �����Ƿ� ���ϸ��� �����Ѵ�.
                    String ext = sName.substring( sName.lastIndexOf(".")+1);
                    if( ext.equalsIgnoreCase("gif")
                     || ext.equalsIgnoreCase("jpg")
                     || ext.equalsIgnoreCase("jpeg")
                     || ext.equalsIgnoreCase("png")){
                      File f = multi.getFile(tmpStr);
                      File nf = new File( f.getPath().substring(0, f.getPath().lastIndexOf( File.separator))+File.separator+System.currentTimeMillis()+sName.substring(sName.lastIndexOf(".")));

                      f.renameTo( nf);
                      sName = nf.getName();
                      uName = multi.getOriginalFileName(tmpStr);
                    }
                }

                String fileFullPath = FileUploadPath.getPath(SAVE_PATH) + uploadDir + sName;
                //�̹��� ������¡
                ImageTransUtil.imageTrans(fileFullPath, ImageTransUtil.SEDITOR_RESIZE, false );

                DebugLogger.log("ImageTransUtil start");
                DebugLogger.log("path : " + fileFullPath);
                saveInRequest("imgurl", "/common/jsp/seditor/ImageView.jsp");
                saveInRequest("path", "/cpis/img" + uploadDir + sName);
                saveInRequest("id", id);
            } // end if

        } catch (IOException e) {
          saveInRequest("error", e);
          ErrorLogger.log(e);
        } catch (RuntimeException e) {
          saveInRequest("error", e);
          ErrorLogger.log(e);
        }
    }

    /**
     * ���丮��
     */
    private String createUploadDir(boolean isLastSeparator) {
        Calendar cal = Calendar.getInstance();
        String yearDir = Integer.toString(cal.get(Calendar.YEAR));
        String monthDir = Integer.toString(cal.get(Calendar.MONTH) + 1);
        String dayDir = Integer.toString(cal.get(Calendar.DATE));

        if (monthDir.length() == 1) {
            monthDir = "0" + monthDir;
        }

        String returnPath = "/" + yearDir + "/" + monthDir + "/" + dayDir;

        if (isLastSeparator)
            returnPath += "/";

        return returnPath;
    }
}
