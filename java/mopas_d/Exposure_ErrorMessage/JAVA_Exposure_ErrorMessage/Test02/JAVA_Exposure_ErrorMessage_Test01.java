/**
 * Copyright(c) 2014
 * System             : ��������C4Iü��
 * Program Id         : AdaoFileSenderServlet
 * Program Name       : ������� ÷�������� �ܺ�(��Ȳ��) ���α׷����� �� �� �ֵ��� �����ϴ� Ŭ����
 * @FileName          : mil.af.c4i.operation.execution.adamopspp.util.AdaoFileSenderServlet.java
 * Description        : ������� ÷�������� �ܺ�(��Ȳ��) ���α׷����� �� �� �ֵ��� �����ϴ� Ŭ����
 * @Author            : �� �켺
 * @Version           : 1.0.0
 * File name related  :
 * Class Name related :
 * Created Date       : 2013. 01.16
 * Updated Date       : 2013. 01.16
 * @LastModifier      : �� �켺
 * Updated content    : �����ۼ�
 * License            : �� ���α׷� �ҽ��� ���� ����C4I ���ó�� ���鿡 ���� ���ξ��� Ÿ��ü
 * �� Ÿ ȸ������� �����Ǿ�� �ȵ�. �� ���α׷� �ҽ��� ���� ����C4I ���ó�� ���� ���ξ���
 * ���Ƿ� ����, ����, ������ �� ����
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
 * ������� ÷�������� �ܺ�(��Ȳ��) ���α׷����� �� �� �ֵ��� �����ϴ� Ŭ����
 *
 * @author  �� �켺
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
