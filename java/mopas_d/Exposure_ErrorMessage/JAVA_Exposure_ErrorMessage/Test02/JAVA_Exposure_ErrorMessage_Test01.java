/**
 * Copyright(c) 2014
 * System             : 공군전술C4I체계
 * Program Id         : AdaoFileSenderServlet
 * Program Name       : 방공포병 첨부파일을 외부(상황도) 프로그램에서 볼 수 있도록 제공하는 클래스
 * @FileName          : mil.af.c4i.operation.execution.adamopspp.util.AdaoFileSenderServlet.java
 * Description        : 방공포병 첨부파일을 외부(상황도) 프로그램에서 볼 수 있도록 제공하는 클래스
 * @Author            : 전 우성
 * @Version           : 1.0.0
 * File name related  :
 * Class Name related :
 * Created Date       : 2013. 01.16
 * Updated Date       : 2013. 01.16
 * @LastModifier      : 전 우성
 * Updated content    : 최초작성
 * License            : 본 프로그램 소스는 공군 전술C4I 사업처의 서면에 의한 승인없이 타업체
 * 및 타 회사원에게 누설되어서는 안됨. 본 프로그램 소스는 공군 전술C4I 사업처의 사전 승인없이
 * 임의로 복제, 복사, 배포할 수 없음
 */
package mil.af.c4i.operation.execution.adamopspp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mil.af.c4i.smm.config.Configurator;
import mil.af.c4i.smm.config.ConfiguratorManager;

/**
 * 방공포병 첨부파일을 외부(상황도) 프로그램에서 볼 수 있도록 제공하는 클래스
 *
 * @author  전 우성
 * @version 1.0.0
 */
public class AdaoFileSenderServlet extends HttpServlet {
    private static Configurator conf   =ConfiguratorManager.getConfigurator();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("filename");
        String path = conf.getStringProperty("adao.file.upload.path", "");
        PrintWriter write =null;
        BufferedReader read =null;


        File f = new File(path+fileName);
        FileInputStream fis=null;
        OutputStream os = null;

        try {
            read  =new BufferedReader(new FileReader(path+fileName));
            write =new PrintWriter(response.getOutputStream(), true);
            String readLine =null;
            StringBuffer buff =new StringBuffer();

            while((readLine =read.readLine()) != null){
                buff.append(readLine);
                buff.append("\n");
            }
            write.println(buff.toString());
            write.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                write.close();
                read.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
