package com.hmall.web.commerce;                                                                                                                                                                                      
                                                                                                                                                                                                                        
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.web.bind.RequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.hmall.model.commerce.CommerceData;
import com.hmall.model.cyber.SellBuy; 
import com.hmall.service.CommerceMgr;
import com.svl.framework.eai.EAIRequestor;
import com.svl.framework.eai.EAIResponsor;
import com.svl.mhs.eai.lib.EAIProcessorFactory;

import com.hmall.util.BaseUtils;
import com.hmall.util.HttpUtils;
import com.hmall.util.PageHolder;
import com.hmall.util.search.Filter;
import com.hmall.util.search.ResultSet;
import com.hmall.util.search.SearchCriteria;
import com.hmall.util.search.SearchItem;
import com.hmall.util.search.Searcher;

import com.hmall.util.*;
import com.hmall.service.ShopMgr;
import com.hmall.model.display.Blog;

                                                                                                                                                                                                                        
public class CommerceController extends MultiActionController {                                                                                                                                                         
	                                                                                                                                                                                                                
	private final Log log = LogFactory.getLog(getClass());                                                                                                                                                          
	private static final long serialVersionUID = 4318038903087744399L;                                                                                                                                              
	/* (non-Javadoc)                                                                                                                                                                                                
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)                                                                                   
	 */                                                                                                                                                                                                             
	private static String tpCode = "0000100050";                                                                                                                                                                    
	private static String key = "AFESFL983LSFET95";                                                                                                                                                                  
	protected static Logger logger;                                                                                                                                                                                 
	                                                                                                                                                                                                                
	private CommerceMgr commerceMgr;                                                                                                                                                                                
	                                                                                                                                                                                                                
	                                                                                                                                                                                                                
	public void setCommerceMgr(CommerceMgr commerceMgr) {                                                                                                                                                           
		this.commerceMgr = commerceMgr;                                                                                                                                                                         
	}                                                                                                                                                                                                               
	                                                                                                                                                                                                                 
	
	public ModelAndView CouponGate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'CouponGate' method..."); 
		
		BASE64Encoder encoder = new BASE64Encoder();
        BASE64Decoder decoder = new BASE64Decoder();
		
		Map model = new HashMap();
        String a1, a2, a3;
        byte[] b1, b2;
	        
        String RESULT;
		String ERR_DTL;
		
		String JSP_GB = request.getParameter("JSP_GB");
		if (JSP_GB == null)
		{
			RESULT = "0";
        	ERR_DTL = "2";
        	model.put("RESULT", RESULT);
        	model.put("ERR_DTL", ERR_DTL);
		}
		
		else if (JSP_GB.equals("CUSTINFO"))
		{
			String RESIDENT_NO = request.getParameter("RESIDENT_NO");

			if (RESIDENT_NO == null)
			{
				RESULT = "0";
				ERR_DTL = "2";	
				model.put("RESULT", RESULT);
				model.put("ERR_DTL", ERR_DTL);
			}
			
			else if (RESIDENT_NO.equals(""))
			{
				RESULT = "0";
				ERR_DTL = "2";	
				model.put("RESULT", RESULT);
				model.put("ERR_DTL", ERR_DTL);
			}

			else
			{
				a2 = RESIDENT_NO;
				b2 = decoder.decodeBuffer(a2);
		        a3 = new String(b2);
		        String resid_no = a3;
		        String checkCN = commerceMgr.checkCustNo(resid_no);
			        
			        if ("1".equals(checkCN))
			        {
			        	String CUST_NO = commerceMgr.readCustNo(resid_no);

			        	a1 = CUST_NO;
			        	b1 = a1.getBytes();
			        	a2 = encoder.encode(b1);
			        	//a2 = CUST_NO; //��ȣȭ Ǯ�� ���� �ּ� ó���ϰ�, ������ ����. ��ȣȭ �Ҷ��� �� ���� �ּ�, ���� �ּ� Ǯ��
			        	
			        	model.put("CUST_NO", a2);
			        	RESULT = "1";
			        	model.put("RESULT", RESULT);
			        }
			        
			        else
			        {
			        	RESULT = "0";
			        	ERR_DTL = "4";
			        	model.put("RESULT", RESULT);
			        	model.put("ERR_DTL", ERR_DTL);
			        }
			}
			
		}
		
		else if (JSP_GB.equals("COUPONISSUE"))
		{
			String des_CUST_NO = request.getParameter("CUST_NO");
			String COUPON_NO = request.getParameter("COUPON_NO");

			if (des_CUST_NO != null && COUPON_NO != null && !des_CUST_NO.equals("") && !COUPON_NO.equals("") && "00004508".equals(COUPON_NO))
			{
				a2 = des_CUST_NO;
				b2 = decoder.decodeBuffer(a2);
		        a3 = new String(b2);
		        String CUST_NO = a3;

		        if (CUST_NO == null || CUST_NO.equals(""))
		        {
		        	a1 = CUST_NO;
        			b1 = a1.getBytes();
        			a2 = encoder.encode(b1);
        			//a2 = CUST_NO; //��ȣȭ Ǯ�� ���� �ּ� ó���ϰ�, ������ ����. ��ȣȭ �Ҷ��� �� ���� �ּ�, ���� �ּ� Ǯ��
		        	RESULT = "0";
					ERR_DTL = "2";	

					model.put("RESULT", RESULT);
					model.put("ERR_DTL", ERR_DTL);
		        }
		        
		        else 
		        {	

			        String findCust = commerceMgr.findCustNo(CUST_NO);
			        
			        if ("1".equals(findCust))
			        {
			        	String chCoup = commerceMgr.checkCoupon(CUST_NO, COUPON_NO);
			        	if (chCoup == null)
			        	{
			        		String insertYN = commerceMgr.insertCoupon(CUST_NO, COUPON_NO);
			        		
			        		if (insertYN.equals("Y"))
			        		{
			        			RESULT = "1";
			        			a1 = CUST_NO;
			        			b1 = a1.getBytes();
			        			a2 = encoder.encode(b1);
			        			//a2 = CUST_NO; //��ȣȭ Ǯ�� ���� �ּ� ó���ϰ�, ������ ����. ��ȣȭ �Ҷ��� �� ���� �ּ�, ���� �ּ� Ǯ��
			        			model.put("RESULT", RESULT);
			        			model.put("CUST_NO", a2);
			        		}
			        		
			        		else
			        		{
			        			RESULT = "0";
	      						ERR_DTL = "6";
								a1 = CUST_NO;
								b1 = a1.getBytes();
								a2 = encoder.encode(b1);
								//a2 = CUST_NO; //��ȣȭ Ǯ�� ���� �ּ� ó���ϰ�, ������ ����. ��ȣȭ �Ҷ��� �� ���� �ּ�, ���� �ּ� Ǯ��
								model.put("RESULT", RESULT);
								model.put("CUST_NO", a2);
			        		}
			        	}
			        	
			        	else
			        	{
			        		RESULT = "2";
				        	ERR_DTL = "6";
				        	model.put("RESULT", RESULT);
				        	model.put("ERR_DTL", ERR_DTL);
			        	}
			        }
			        
			        else
			        {
			        	RESULT = "0";
			        	ERR_DTL = "4";
			        	model.put("RESULT", RESULT);
			        	model.put("ERR_DTL", ERR_DTL);
			        }
		        }   
			}
			else
			{
				RESULT = "0";
	        	ERR_DTL = "2";	        	
	        	model.put("RESULT", RESULT);
	        	model.put("ERR_DTL", ERR_DTL);	        	
			}
		}
		
		else if (JSP_GB == null){
			
			RESULT = "0";
        	ERR_DTL = "2";        	
        	model.put("RESULT", RESULT);
        	model.put("ERR_DTL", ERR_DTL);        	
 		}
		
		else
		{
			RESULT = "0";
        	ERR_DTL = "2";        	
        	model.put("RESULT", RESULT);
        	model.put("ERR_DTL", ERR_DTL);        	
 		}
		return new ModelAndView ("/commerce/CouponGate", model);        
		
	}
	

	/*���� �̸�	�����ڵ�	�������	Request��ȣ	Response��ȣ	��������	���                                                                                                            
	*��ǰ ���	ITEMS_UPD	SKT=>���޸�	X	X	����	HTTP�������� ����                                                                                                                       
	ī�װ� ��� 	CATEGORY_UPD	SKT=>���޸�	X	X	�ű�	Ȩ���� ����                                                                                                                             
	�̺�Ʈ ���	EVENT_UPD	SKT=>���޸�	X	X	�ű�	Ȩ���� ����                                                                                                                             
	��ǰ������ȸ	ITEMINFO	SKT=>���޸�	X	X	����	Ȩ���� ������ �� �׸� ����                                                                                                              
	��ǰ�ɼ�/�����ȸ	ITEMSTOCKDT	SKT=>���޸�	X	X	�Ϻκ���	                                                                                                                        
	�ɼ� �� ��ȸ	ITEMSTOCK	SKT=>���޸�	X	X	�Ϻκ���	                                                                                                                                
	��ǰ�˻����	ITEMSRCH	SKT=>���޸�	X	X	�Ϻκ���	                                                                                                                                
	�̿��ı� ����Ʈ	ITEMEVALLST	SKT=>���޸�	X	X	�Ϻκ���	                                                                                                                                
	�̿��ı� ��	ITEMEVAL	SKT=>���޸�	X	X	����	                                                                                                                                        
	�ֹ�����		ORDINFO 	SKT=>���޸�	O	X	�Ϻκ���	                                                                                                                        
	�ֹ����	ORDRECV	SKT<=���޸�	O	X	�Ϻκ���	HTTP ����                                                                                                                               
	�ֹ��Ϸ�	ORDCONFIRM	SKT=>���޸�	X	O	�Ϻκ���	HTTP ����                                                                                                                       
	�ֹ� ����ȭ	ORDSYNC	SKT=>���޸�	X	O	�ű�	���� ó����                                                                                                                                     
	���޸� ȸ�� Ȯ��	CUSTINFO	SKT=>���޸�	O	O	����	                                                                                                                                
	��������/������ ����	COUPONINFO	SKT=>���޸�	O	O	����	                                                                                                                                
	������������ȸ	COUPONINFODT	SKT=>���޸�	X	O	����	                                                                                                                                
	�����ǥ	AIRSCHEDULE	SKT=>���޸�	X	X	������	�����Ȩ���� ����                                                                                                                       
	*/       
	public ModelAndView cmNateDataChkL(HttpServletRequest request, HttpServletResponse response) throws Exception {                                                                                                 
		log.debug("entering 'cmNateDataChkL' method...");                                                                                                                                                       
		String xmlData = request.getParameter("xmldata");                                                                                                                                                       
		if(xmlData == null) {                                                                                                                                                                                   
			xmlData = "";			                                                                                                                                                                
		}                                                                                                                                                                                                       
		String businessCd = request.getParameter("jsp_gb");    
		
		if(businessCd ==null) {                                                                                                                                                                                 
			businessCd = "";                                                                                                                                                                                
		}                                                                                                                                                                                                       
		String returnData = "";                                                                                                                                                                                 
		Map model = new HashMap();                                                                                                                                                                              
		logger = Logger.getLogger("mcpmhs.log.eai.webservice");                                                                                                                                                 
		                                                                                                                                                                                                        
		if (xmlData.equals("")){                                                                                                                                                                                
			xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +                                                                                                                                        
				" <MC_ROOT><HEADER><BUSINESSCD>"+ businessCd +"</BUSINESSCD><TPCODE>"+ tpCode +"</TPCODE><EDI_SEND_DT><![CDATA[20060329104806]]></EDI_SEND_DT><EDI_SEND_QTY>0</EDI_SEND_QTY></HEADER>"+ 
				"<DATA><TARGETDATE>20060501</TARGETDATE></DATA>" +                                                                                                                                      
				"</MC_ROOT>";		                                                                                                                                                                
		}		                                                                                                                                                                                        
		if (businessCd.equals("ITEMS_UPD"))		returnData = EAI_ITEMS_UPD(xmlData,businessCd,false, false);                                                                                            
		if (businessCd.equals("CATEGORY_UPD"))	returnData = EAI_CATEGORY_UPD(xmlData,businessCd,false, false);                                                                                                 
		if (businessCd.equals("EVENT_UPD"))		returnData = EAI_EVENT_UPD(xmlData,businessCd,false, false);                                                                                            
		if (businessCd.equals("ITEMINFO"))		returnData = EAI_ITEMINFO(xmlData,businessCd,false, false);                                                                                             
		if (businessCd.equals("ITEMSTOCKDT"))	returnData = EAI_ITEMSTOCKDT(xmlData,businessCd,false, false);                                                                                                  
		if (businessCd.equals("ITEMSTOCK"))		returnData = EAI_ITEMSTOCK(xmlData,businessCd,false, false);                                                                                            
		if (businessCd.equals("ITEMSRCH"))		returnData = EAI_ITEMSRCH(xmlData,businessCd,false, false);
		if (businessCd.equals("ITEMSRCH_CNT"))	returnData = EAI_ITEMSRCH_CNT(xmlData,businessCd,false, false);
		if (businessCd.equals("ITEMEVALLST"))	returnData = EAI_ITEMEVALLST(xmlData,businessCd,false, false);                                                                                                  
		if (businessCd.equals("ITEMEVAL"))		returnData = EAI_ITEMEVAL(xmlData,businessCd,false, false);                                                                                             
		if (businessCd.equals("ORDINFO"))		returnData = EAI_ORDINFO(xmlData,businessCd,true, false);                                                                                               
		if (businessCd.equals("ORDRECV"))		EAI_ORDRECV(xmlData,businessCd,true, false);                                                                                                            
		//if (businessCd.equals("ORDCONFIRM"))	returnData = EAI_ORDCONFIRM(xmlData,businessCd,false, false);                                                                                                    
		if (businessCd.equals("ORDCONFIRM"))	returnData = EAI_ORDCONFIRM(xmlData,businessCd,false, true);                                                                                                    
		//if (businessCd.equals("ORDSYNC"))		returnData = EAI_ORDSYNC(xmlData,businessCd,false, false);                                                                                               
		if (businessCd.equals("ORDSYNC"))		returnData = EAI_ORDSYNC(xmlData,businessCd,false, true);                                                                                               
		if (businessCd.equals("CUSTINFO"))		returnData = EAI_CUSTINFO(xmlData,businessCd,true, true);                                                                                                
		if (businessCd.equals("COUPONINFO"))	returnData = EAI_COUPONINFO(xmlData,businessCd,true, true);                                                                                                     
		if (businessCd.equals("COUPONINFODT"))	returnData = EAI_COUPONINFODT(xmlData,businessCd,true, false);                                                                                                  
		if (businessCd.equals("AIRSCHEDULE"))	returnData = EAI_AIRSCHEDULE(xmlData,businessCd,false, false);                                                                                                  
		//log.debug("first==>"+returnData);                                                                                                                                                                     
		//returnData = URLEncoder.encode(returnData, "EUC_KR");                                                                                                                                                 
		model.put("returnData", returnData);                                                                                                                                                                    
		//log.debug("returnData==>"+ returnData);                                                                                                                                                                       
		return new ModelAndView ("/commerce/cmNateDataChkL", model);                                                                                                                                            
	}                                                                                                                                                                                                               
                                                                                                                                                                                                                        
	/*                                                                                                                                                                                                              
	 * 	��ǰ ��ȣ	���ڿ�		ITEM_CODE                                                                                                                                                                
		ī�װ�	���ڿ�	9	SKTCATEGORY_ID                                                                                                                                                          
		��üī�װ� ID	���ڿ�	20	TPCATEGORY_ID                                                                                                                                                           
		�Ǹ� ����	����	1	PRDCATEGORY_CODE                                                                                                                                                        
		��ǰ��	���ڿ�	100	ITEM_NAME                                                                                                                                                                       
		�̹��� ����	����		ITEM_IMG                                                                                                                                                                
		����	����		ITEM_PRICE                                                                                                                                                                      
		�ǸŻ���	����	1	SALE_STATE 1:�Ǹ���2:ǰ��(������)3:�Ǹ�����4.�Ͻ�����(�Ͻ���� ����)                                                                                                    
	 *                                                                                                                                                                                                              
	 */	                                                                                                                                                                                                        
	private String EAI_ITEMS_UPD(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                           
	                                                                                                                                                                                                                
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                            
		// ��û������ �м� �� ����ó��                                                                                                                                                                          
		try {                                                                                                                                                                                                   
			log.debug("entering 'EAI_ITEMS_UPD' method...");                                                                                                                                                
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                                       
			/*Request parameter ó��*/                                                                                                                                              
			/*���� ó��*/                         
			int SectID = 77415; // ������ ���� ID
			List listEaiItemsUpdList = commerceMgr.listEaiItemsUpd(SectID);                                                                                                                                 
			//log.debug("����Ʈ======================>" + listEaiItemsUpdList);                                                                                                                               
			                                                                                                                                                                                                
			int dataCnt = listEaiItemsUpdList.size(); // ���� ����� ������ ������                                                                                                                          
			responsor.initResponseList(dataCnt);                                                                                                                                                            
			for (int i = 0; i < listEaiItemsUpdList.size(); i++) {                                                                                                                                          
				
				CommerceData CommerceList = (CommerceData) listEaiItemsUpdList.get(i);                                                                                                                                                                                        
				 				
				responsor.setResponseData(i,"ITEM_CODE",CommerceList.getItem_code());                                                                                                                       
				responsor.setResponseData(i,"SKTCATEGORY_ID",CommerceList.getMccategory_id());                                                                                                                     
				responsor.setResponseData(i,"TPCATEGORY_ID",CommerceList.getCategory_id());                                                                                                                                        
				responsor.setResponseData(i,"PRDCATEGORY_CODE",CommerceList.getPrdcategory_code());                                                                                                                                      
				responsor.setResponseData(i,"ITEM_NAME",CommerceList.getItem_name());                                                                                                                       
				responsor.setResponseData(i,"ITEM_IMG",CommerceList.getItem_img_yn());                                            
				responsor.setResponseData(i,"ITEM_PRICE",CommerceList.getItem_price()+"");                                                                                                                  
				responsor.setResponseData(i,"SALE_STATE",CommerceList.getSale_state());
				//responsor.setResponseData(i,"IMG_URL","http://image.hyundaihmall.com/static/image/product/ext/"+CommerceList.getLarge_ext_img());
				responsor.setResponseData(i,"IMG_URL","http://image.hyundaihmall.com/static/image/product/large/"+CommerceList.getZoom_img());
				responsor.setResponseData(i,"ARRANGE",CommerceList.getItemseq());
			}                                                                                                                                                                                               
			return responsor.doResponse("",isEncryptResponse,key);                                                                                                                                          
		} catch (Exception e) {                                                                                                                                                                                 
			return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�::"+e, isEncryptResponse,key);                                                                                                       
		}                                                                                                                                                                                                       
	}                                                                                                                                                                                                               
                                                                                                                                                                                                                        
	private String EAI_CATEGORY_UPD(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                        
		/*                                                                                                                                                                                                      
		 *                                                                                                                                                                                                      
		��üī�װ� ��	���ڿ�	40	TPCATEGORY_NAME		                                                                                                                                                
		��üī�װ� ID	���ڿ�	20	TPCATEGORY_ID                                                                                                                                                           
		�Ǹ� ����	���ڿ�	1	PRDCATEGORY_CODE	1:�����īƮ2:�����Ȩ����	                                                                                                                
		���� 1 ID	���ڿ�	20	TPCATEGORY_LEV1                                                                                                                                                         
		���� 2 ID	���ڿ�	20	TPCATEGORY_LEV2		Blank ���                                                                                                                                      
		���� 3 ID	���ڿ�	20	TPCATEGORY_LEV3		Blank ���                                                                                                                                      
		���ļ���	����		ARRANGE	1 ~ N                                                                                                                                                           
		��������	����	1	ISACTIVE	1 : ����, 0 : ������                                                                                                                                    
		 *                                                                                                                                                                                                      
		 */                                                                                                                                                                                                     
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                    
	                                                                                                                                                                                                                
		// ��û������ �м� �� ����ó��                                                                                                                                                                  
		try {                                                                                                                                                                                           
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                       
			/*Request parameter ó��*/                                                                                                                                                      
			List listEaiCateUpdList = commerceMgr.listEaiCateUpd();                                                                                                                                 
			                                                                                                                                                                                                
			int dataCnt = listEaiCateUpdList.size(); // ���� ����� ������ ������                                                                                                                           
			responsor.initResponseList(dataCnt);
			
			for (int i = 0 ; i < dataCnt ; i++){ 
				
				CommerceData CommerceList = (CommerceData) listEaiCateUpdList.get(i); 
						
				responsor.setResponseData(i,"TPCATEGORY_NAME",CommerceList.getCategory_name());                                                                                                       
				responsor.setResponseData(i,"TPCATEGORY_ID",CommerceList.getCategory_id());                                                                                                           
				responsor.setResponseData(i,"PRDCATEGORY_CODE",CommerceList.getPrdcategory_code());                                                                                                       
				responsor.setResponseData(i,"TPCATEGORY_LEV1",CommerceList.getCategory_lev1());                                                                                                       
				responsor.setResponseData(i,"TPCATEGORY_LEV2",CommerceList.getCategory_lev2());                                                                                                       
				responsor.setResponseData(i,"TPCATEGORY_LEV3",CommerceList.getCategory_lev3());                                                                                                       
				responsor.setResponseData(i,"ARRANGE",CommerceList.getArrange());                                                                                                                               
				responsor.setResponseData(i,"ISACTIVE",CommerceList.getIsactive());                                                                                                                              
					}                                                                                                                                                                               
					return responsor.doResponse("",isEncryptResponse,key);                                                                                                                          
				} catch (Exception e) {                                                                                                                                                                 
						return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�.", isEncryptResponse,key);                                                                                  
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
			                                                                                                                                                                                                
	private String EAI_ITEMINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                            
			/*                                                                                                                                                                                              
			 *                                                                                                                                                                                              
			 Response                                                                                                                                                                                       
				��ǰ��	���ڿ�	100	ITEM_NAME		                                                                                                                                        
				���� ��ü	���ڿ�	60	MAKECO_NAME		                                                                                                                                
				����	����	����          10	ITEM_PRICE		                                                                                                                        
				�Һ� ����	����	     2	NOREST_MONTH	�Һΰ� ������ 0	                                                                                                                        
				������	����    	10	SAVE_AMT	���� �� �ο� �� ����	                                                                                                                
				��ۺ�	����		10    SHIP_AMT	������ 0 ���� �Ǵ� �����̸� �ش� �ݾ� 	��ǰ���ݿ��� ������ ���� �ʴ´�.                                                                        
				�ɼǸ� 1	���ڿ�	20	OPTION_NAME1		������ Blank                                                                                                                    
				�ɼǸ� 2	���ڿ�	20	OPTION_NAME2		������ Blank                                                                                                                    
				�ɼǸ� 3	���ڿ�	20	OPTION_NAME3		������ Blank                                                                                                                    
				��ǰ�����	���ڿ�	4000	ITEM_EXPLAIN	������ �ִ밪 ����	                                                                                                                
				��ǰ���������	����	1	ITEM_TECH	0 : ����1 : ����	                                                                                                                
				�ǸŻ���	        ����	1	SALE_STATE	1 : �Ǹ���2 : ǰ��3 : �Ǹ�����	ǰ��/�Ǹ����� �� ���� �ȵ�                                                                      
				���	                ����	1	ITEM_STOCK	0 : ����1 : ����	�Ǹ���/�������� ȭ�鿡 ���� ��                                                                        
				�̹��� ����	����	1	ITEM_IMG	0 : ����1 : ����	                                                                                                                
				�̿��ı� ����	����		ITEMVALL_QTY	������ 0 	                                                                                                                        
			 *                                                                                                                                                                                              
			 */                                                                                                                                                                                             
			                                                                                                                                      
			EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                    
                                                                                                                                                                                                                        
			// ��û������ �м� �� ����ó��                                                                                                                                                                  
		try {				                                                                                                                                                                
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                               
				/*Request parameter ó��*/                                                                                                                                                              
				String ITEM_CODE = responsor.getRequestDataString(0,"ITEM_CODE");                                                                                                                       
                                                                                                                                                                                                                        
				List listEaiItemsInfo = commerceMgr.listEaiItemsInfo(ITEM_CODE);                                                                                                                        
				                                                                                                                                                                                        
				int dataCnt = listEaiItemsInfo.size(); // ���� ����� ������ ������                                                                                                                     
				responsor.initResponseList(dataCnt);     
				String explain = null;
				                                                                                                                                                                                        
				for (int i = 0 ; i < dataCnt ; i++){                                                                                                                                                    
				
					CommerceData CommerceList = (CommerceData) listEaiItemsInfo.get(i);
	
					responsor.setResponseData(i,"ITEM_NAME",CommerceList.getItem_name());                                                                                                               
					responsor.setResponseData(i,"MAKECO_NAME",CommerceList.getMakeco_name());                                                                                                           
					responsor.setResponseData(i,"ITEM_PRICE",CommerceList.getItem_price()+"");                                                                                                          
					responsor.setResponseData(i,"NOREST_MONTH",CommerceList.getNorest_month());                                                                                                                                  
					responsor.setResponseData(i,"SAVE_AMT",CommerceList.getSave_amt()+"");                                                                                                               
					responsor.setResponseData(i,"SHIP_AMT",CommerceList.getShip_amt()+"");                                                                                                                                     
					responsor.setResponseData(i,"OPTION_NAME1",CommerceList.getCategory_lev1());                                                                                                                                 
					responsor.setResponseData(i,"OPTION_NAME2",CommerceList.getCategory_lev2());                                                                                                                                 
					responsor.setResponseData(i,"OPTION_NAME3",CommerceList.getCategory_lev3());  
					explain = CommerceList.getExplain_note();
					if(explain == null || explain.length()==0){
						explain = CommerceList.getExplain_note043();
					}
					//log.debug("explain======================>" + explain); 
					responsor.setResponseData(i,"ITEM_EXPLAIN",explain);                                                                                                         
					responsor.setResponseData(i,"SALE_STATE",CommerceList.getSale_state());                                                                                                                                    
					responsor.setResponseData(i,"ITEM_STOCK",CommerceList.getStock_qty()+"");                                                                                                                                    
					responsor.setResponseData(i,"ITEM_IMG",CommerceList.getOrd_type());  
					responsor.setResponseData(i,"ITEMVALL_QTY",CommerceList.getIsactive());
					responsor.setResponseData(i,"IMG_URL","http://image.hyundaihmall.com/static/image/product/large/"+CommerceList.getZoom_img());
				}                                                                                                                                                                                       
				return responsor.doResponse("",isEncryptResponse,key);                                                                                                                                  
			}                                                                                                                                                                                               
			catch (Exception e) {                                                                                                                                                                           
				return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�."+e, isEncryptResponse,key);                                                                                                
			}                                                                                                                                                                                               
		}                                                                                                                                                                                                       
                                                                                                                                                                                                                        
	private String EAI_ITEMSTOCK(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                           
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				 Request                                                                                                                                                                                
					��ǰ ��ȣ	���ڿ�	20	ITEM_CODE	���޸� ��ǰ��ȣ	                                                                                                                
					�ɼǸ�	���ڿ�	20	OPTION_NAME	�ɼ� �̸�	CJ���� Blank                                                                                                            
					�����ð���	����		FETCH_CNT	������ ����	                                                                                                                
				                                                                                                                                                                                        
				Response                                                                                                                                                                                
					��� ����	����	5	STOCK_QTY	�ֹ� ���� ���� 	Blank                                                                                                           
					��ǰ�ڵ�(�ɼ��ڵ�)	���ڿ�	3	UNIT_CODE		                                                                                                                
					��ǰ��(�ɼǸ�)	���ڿ�	100	UNIT_NAME		                                                                                                                        
					����	����		ITEM_PRICE		                                                                                                                                
				 *                                                                                                                                                                                      
				 */ 
		 
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ��û������ �м� �� ����ó��                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
				/*Request parameter ó��*/                                                                                                                                              
				String ITEM_CODE   = responsor.getRequestDataString(0,"ITEM_CODE");  
				
				//String MAKECO_NAME = responsor.getRequestDataString(0,"MAKECO_NAME");                                                                                                   
				//int FETCH_CNT = responsor.getRequestDataInt(0,"FETCH_CNT");                                                                                                             
						                                                                                                                                                                        
				List listEaiItemsStock = commerceMgr.listEaiItemsStock(ITEM_CODE);                                                                                                       
		
				int dataCnt = listEaiItemsStock.size(); // ���� ����� ������ ������                                                                                                    
				responsor.initResponseList(dataCnt);			                                                                                                                
						                                                                                                                                                                        
				for (int i = 0 ; i < dataCnt ; i++){
					
				CommerceData CommerceList = (CommerceData) listEaiItemsStock.get(i);	                                                                                                        
				// hmall ��� üũ�� �����ÿ� ó��  �Ѵ�.2006.04.12.������                                                                                                                            
				responsor.setResponseData(i,"STOCK_QTY",CommerceList.getStock_qty()+"");                                                                                                                     
				responsor.setResponseData(i,"UNIT_CODE",CommerceList.getUnit_code());                                                                                                    
				responsor.setResponseData(i,"UNIT_NAME",CommerceList.getUnit_name());                                                                                                                    
				responsor.setResponseData(i,"ITEM_PRICE",CommerceList.getItem_price()+"");                                                                                          
				}                                                                                                                                                                       
				return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
				} catch (Exception e) {                                                                                                                                                         
				//logger.error(e.getMessage(), e);                                                                                                                                        
				return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
                                                                                                                                                                                                                        
	private String EAI_ITEMSTOCKDT(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                         
				/*                                                                                                                                                                                      
				 Request                                                                                                                                                                                
					��ǰ ��ȣ	���ڿ�	20	ITEM_CODE	���޸� ��ǰ��ȣ	                                                                                                                
					��ǰ�ڵ�(�ɼ��ڵ�)	���ڿ�	20	UNIT_CODE	���޸� ��ǰ��ȣ	                                                                                                                                
				Response                                                                                                                                                                                
					��� ����	����	5	STOCK_QTY		                                                                                                                        
					��ǰ�ڵ�(�ɼ��ڵ�)	���ڿ�	20	UNIT_CODE	���޸� ��ǰ�ڵ�	                                                                                                        
					����	����		ITEM_PRICE		Blank ���                                                                                                                                               
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ��û������ �м� �� ����ó��                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
				/*Request parameter ó��*/                                                                                                                                              
				String ITEM_CODE = responsor.getRequestDataString(0,"ITEM_CODE");                                                                                                       
				String UNIT_CODE = responsor.getRequestDataString(0,"UNIT_CODE");                                                                                                       
                                                                                                                                                                                                                       
                List listEaiItemStockdt = commerceMgr.listEaiItemStockdt(ITEM_CODE,UNIT_CODE);                                                                                                                  
						                                                                                                                                                                        
				int dataCnt = listEaiItemStockdt.size(); // ���� ����� ������ ������                                                                                                   
				responsor.initResponseList(dataCnt);			                                                                                                                
						                                                                                                                                                                        
				for (int i = 0 ; i < dataCnt ; i++){                                                                                                                                    
							                                                                                                                                                                
				CommerceData CommerceList = (CommerceData) listEaiItemStockdt.get(i);                                                                                                           
							                                                                                                                                                                
				responsor.setResponseData(i,"STOCK_QTY", CommerceList.getStock_qty()+"");                                                                                                                     
				responsor.setResponseData(i,"UNIT_CODE", CommerceList.getUnit_code());
				responsor.setResponseData(i,"UNIT_NAME",CommerceList.getUnit_name());   
				responsor.setResponseData(i,"ITEM_PRICE",CommerceList.getItem_price()+"");                                                                                          
				}                                                                                                                                                                       
						return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
					} catch (Exception e) {                                                                                                                                                         
							return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
	private String EAI_CUSTINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                            
		/*                                                                                                                                                                                      
		Request                                                                                                                                                                                 
			�ֹε�Ϲ�ȣ	���ڿ�	13	RESIDENT_NO	��-�� ����                                                                                                                                                         
		Response                                                                                                                                                                                
			ȸ����ȣ	���ڿ�	12	CUST_NO	��N���ΰ�� CJȨ���� ��ȸ����.                                                                                                                                 
		 */                                                                     
		 //EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                
		// ��û������ �м� �� ����ó��                                                                                                                                                          
		try {
			log.debug("entering 'EAI_CUSTINFO' method...");	
			log.debug("xmlData==>"+xmlData);
			responsor.analyzeRequest(xmlData, isEncryptRequest, key); 
			/*Request parameter ó��*/     				
			String RESIDENT_NO = responsor.getRequestDataString(0,"RESIDENT_NO"); 
			log.debug("RESIDENT_NO======================>" + RESIDENT_NO);
			List listEaiCustInfo = commerceMgr.listEaiCustInfo(RESIDENT_NO);
			
			/*���� ó��*/                                                                                                                                                           
			int dataCnt = 1; // ���� ����� ������ ������                                                                                                   
			responsor.initResponseList(dataCnt);	    
			
			for (int i = 0 ; i < dataCnt ; i++){     
				CommerceData CommerceList = (CommerceData) listEaiCustInfo.get(i);  
				log.debug("CommerceList.getCust_no()======================>" + CommerceList.getCust_no());
				responsor.setResponseData(i,"CUST_NO",CommerceList.getCust_no()); 
				//responsor.setResponseData(i,"CUST_NO","200208111951");
				//responsor.setResponseData(i,"CUST_NAME",CommerceList.getCust_name()); 
				
			}                                                                                                                                                                       
			return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
		} catch (Exception e) {                                                                                                                                                         
			return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�.==>"+e.toString(), isEncryptResponse,key);
	}                                                                                                                                                                                       
	}                                                                                                                                                                                                                                     
		                                                                                                                                                                                                        
	private String EAI_ORDINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                             
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				 Request                                                                                                                                                                                
					�ֹ� ��ȣ	���ڿ�	20	MC_ORDER_NO	M-Cart �ֹ� ��ȣ	                                                                                                        
					ȸ�� �� ��ȣ	���ڿ�		HP_NO		                                                                                                                                
					ȸ�� ��ȣ	���ڿ�		CUST_NO	���޸� ȸ����ȣ	                                                                                                                        
					�ֹ� �ݾ�	����		TOT_SALE_PRICE	�� �ֹ��ݾ�	                                                                                                                
					�ֹ� ���� �ݾ�	����		TOT_RSALE_AMT	���� ������ �ݾ�	                                                                                                        
					������	���ڿ�		MC_COUPON_NAME	M-Cart ������	Blank ���                                                                                                              
					������ȣ	���ڿ�		MC_COUPON_NO	M-Cart ���� ��ȣ	Blank ���                                                                                              
					�������꿩��	���ڿ�	1	MC_COUPON_SETTLE_YN	M-Cart ���� ���꿩��,Y/N	Blank ���                                                                              
					������� �ݾ�	����		MC_COUPON_AMT	M-Cart ���� ��� �ݾ�	Blank ���                                                                                              
					�����ݻ�� �ݾ�	����		MC_POINT_AMT	M-Cart ������(����Ʈ)	Blank ���                                                                                              
					����������ȣ	���ڿ�		POST_NO	XXXXXX	                                                                                                                                
					������Ϸù�ȣ	���ڿ�		POST_SEQ	�����ȣ DB �� ����� �Ϸù�ȣ	Blank ���                                                                              
					����� �ּ� 1	���ڿ�		POST_ADDR	�����ȣ �ּ� / XXXXXX	                                                                                                        
					����� �ּ� 2	���ڿ�		ADDR	���ּ�	                                                                                                                        
					�Ϲ���ȭ��ȣ1	���ڿ�		DDD	02	Blank ���                                                                                                                      
					�Ϲ���ȭ��ȣ2	���ڿ�		TEL1	123	Blank ���                                                                                                                      
					�Ϲ���ȭ��ȣ3	���ڿ�		TEL2	4567	Blank ���                                                                                                                      
					�Ǹ� ����	����	1	PRDCATEGORY_CODE	1:�����īƮ2:�����Ȩ����3:SEA 	                                                                                
					�⺻ �����ȣ	���ڿ�		BASE_NO		Blank ���                                                                                                                      
					�⺻ �ּ� 1	���ڿ�		BASE_ADDR	�����ȣ �ּ�	Blank ���                                                                                                      
					�⺻ �ּ� 2	���ڿ�		BASE_ADDR_DETAIL	���ּ�	Blank ���                                                                                              
					�⺻ �̸�	���ڿ�		BASE_NAME	�̸�	Blank ���                                                                                                              
					�ֹι�ȣ	���ڿ�		RESIDENTID	�ֹι�ȣ	Blank ���                                                                                                      
					�ֹ�ó�� URL	���ڿ�		ORDER_URL	M-Cart �ֹ���� ���� URL	                                                                                                
					���� URL	���ڿ�		SUCC_URL	M-Cart �ֹ��Ϸ� �� �̵� URL	                                                                                                
					���� URL	���ڿ�		FAIL_URL	M-Cart �ֹ����� �� �̵� URL	                                                                                                
					���� Transaction ID	���ڿ�	30	TID	SKT���� ����� ���� KeyȨ���ο��� ��� ����	Blank ���                                                                      
					��ǰ�ڵ�	���ڿ�		ITEM_CODE		ARRAY                                                                                                                   
					�ֹ�����	����		ORDER_QTY		ARRAY                                                                                                                   
					�ɼ�(��ǰ�ڵ�)	���ڿ�		UNIT_CODE		ARRAY                                                                                                                   
					��ǰ����	����		ITEM_PRICE		ARRAY                                                                                                                   
				                                                                                                                                                                                        
				Response                                                                                                                                                                                
					�ֹ� ��ȣ	���ڿ�		MC_ORDER_NO	M-Cart �ֹ� ��ȣ	                                                                                                        
					�ֹ� ��ȣ	���ڿ�		TP_ORDER_NO	���޸� �ֹ� ��ȣ                                                                                                                                     
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ��û������ �м� �� ����ó��                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
				/*Request parameter ó��*/                                                                                                                                              
				String MC_ORDER_NO = responsor.getRequestDataString(0,"MC_ORDER_NO");                                                                                                   
				String HP_NO = responsor.getRequestDataString(0,"HP_NO");                                                                                                               
				int TOT_SALE_PRICE = responsor.getRequestDataInt(0,"TOT_SALE_PRICE");                                                                                                   
				int TOT_RSALE_AMT = responsor.getRequestDataInt(0,"TOT_RSALE_AMT");                                                                                                     
				String MC_COUPON_NAME = responsor.getRequestDataString(0,"MC_COUPON_NAME");                                                                                             
				String MC_COUPON_NO = responsor.getRequestDataString(0,"MC_COUPON_NO");                                                                                                 
				int MC_COUPON_AMT = responsor.getRequestDataInt(0,"MC_COUPON_AMT");                                                                                                     
				int MC_POINT_AMT = responsor.getRequestDataInt(0,"MC_POINT_AMT");                                                                                                       
				String POST_NO = responsor.getRequestDataString(0,"POST_NO");                                                                                                           
				String POST_SEQ = responsor.getRequestDataString(0,"POST_SEQ");                                                                                                         
				String POST_ADDR = responsor.getRequestDataString(0,"POST_ADDR");                                                                                                       
				String ADDR = responsor.getRequestDataString(0,"ADDR");                                                                                                                 
				String DDD = responsor.getRequestDataString(0,"DDD");                                                                                                                   
				String TEL1 = responsor.getRequestDataString(0,"TEL1");                                                                                                                 
				String TEL2 = responsor.getRequestDataString(0,"TEL2");                                                                                                                 
				int PRDCATEGORY_CODE = responsor.getRequestDataInt(0,"PRDCATEGORY_CODE");                                                                                               
				String BASE_NO = responsor.getRequestDataString(0,"BASE_NO");                                                                                                           
				String BASE_ADDR = responsor.getRequestDataString(0,"BASE_ADDR");                                                                                                       
				String BASE_ADDR_DETAIL = responsor.getRequestDataString(0,"BASE_ADDR_DETAIL");                                                                                         
				String BASE_NAME = responsor.getRequestDataString(0,"BASE_NAME");                                                                                                       
				                                                                                                                                                                        
				String RESIDENTID = responsor.getRequestDataString(0,"RESIDENTID");                                                                                                     
				String ORDER_URL = responsor.getRequestDataString(0,"ORDER_URL");                                                                                                       
				String SUCC_URL = responsor.getRequestDataString(0,"SUCC_URL");                                                                                                         
				String FAIL_URL = responsor.getRequestDataString(0,"FAIL_URL");                                                                                                         
				String TID = responsor.getRequestDataString(0,"TID");                                                                                                                   
						                                                                                                                                                                        
				List loopDataList = responsor.getRequestDataMap(0);                                                                                                                     
						                                                                                                                                                                        
				for (int k = 0 ; k < loopDataList.size() ; k++){                                                                                                                        
							                                                                                                                                                                
					//�ֹ� ��ǰ ����Ʈ�� �о� �´�.                                                                                                                                 
					HashMap loopData = (HashMap) loopDataList.get(k);                                                                                                               
					String ITEM_CODE = (String) loopData.get("ITEM_CODE");                                                                                                          
					int ORDER_QTY = Integer.parseInt((String) loopData.get("ITEM_CODE"));                                                                                           
					String UNIT_CODE = (String) loopData.get("ITEM_CODE");                                                                                                          
					int ITEM_PRICE = Integer.parseInt((String) loopData.get("ITEM_CODE"));                                                                                          
				}                                                                                                                                                                       
                                                                                                                                                                                                                        
				/*���� ó��*/                                                                                                                                                           
				int dataCnt = 1; // 1�� ����                                                                                                                                            
				responsor.initResponseList(dataCnt);                                                                                                                                    
				for (int i = 0 ; i < dataCnt ; i++){                                                                                                                                    
				responsor.setResponseData(i,"MC_ORDER_NO","MC_ORDER_NO");                                                                                                       
				responsor.setResponseData(i,"TP_ORDER_NO","TP_ORDER_NO");                                                                                                       
				}                                                                                                                                                                       
					return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
					} catch (Exception e) {                                                                                                                                                         
					return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
                                                                                                                                                                                                                        
	private String EAI_ORDSYNC(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                             
		log.debug("entering 'EAI_ORDSYNC' method...");	
		/*                                                                                                                                                                                      
			 *                                                                                                                                                                                      
			Request                                                                                                                                                                                 
				��û��	���ڿ�		TARGETDATE	YYYYMMDD�ֹ��߻���	                                                                                                                
			                                                                                                                                                                                        
			Response                                                                                                                                                                                
				�ֹ� ��ȣ	���ڿ�		MC_ORDER_NO		M-Cart �ֹ� ��ȣ	                                                                                                
				�ֹ� ��ȣ	���ڿ�		TP_ORDER_NO		���޸� �ֹ� ��ȣ	                                                                                                
				���� �ݾ�	����			TOT_RSALE_AMT	���� �ݾ�	                                                                                                        
				���� ����	����			PAY_CARD_AMT	�ſ�ī������ݾ�	Blank ���                                                                                      
						����			PAY_ONLINE_AMT	����������ݾ�		Blank ���                                                                                      
						����			PAY_POINT_AMT	����Ʈ �����ݾ�		Blank ���                                                                                      
						����			PAY_COUPON_AMT	���� �����ݾ�		Blank ���                                                                                      
				�ֹ��θ�	���ڿ�		ORD_NAME		�ֹ��ڸ�			Blank ���                                                                              
				ȸ�������� 	���ڿ�	3	MEMCATEGORY	�ֹ���ȣ �յ��ڸ� + �ֹι�ȣ 7��° �ڸ�	Blank ���                                                                              
				�Ա������	���ڿ�		BANK_NAME	�����Ա� �� �Ա��� �����	Blank ���                                                                                      
				�Աݰ���	���ڿ�		BANK_ACCOUNT	�����Ա� �� �Ա��� ���� ���� ��ȣ	Blank ���                                                                              
				�ֹ��߻�����	����		ORD_TYPE	1:SKT => ���޸�2:���޸� => SKT	���޸��� ���� �ֹ�(���ʹ��) ���� SKT�� ���� (�����īƮ����) ������� ����                     
				�Ǹ� ����	����	1		PRDCATEGORY_CODE	1:�����īƮ2:�����Ȩ����3:SEA 	                                                                        
				��ǰ�ڵ�	���ڿ�		ITEM_CODE		ARRAY                                                                                                                   
				�ֹ�����	����			ORDER_QTY		ARRAY                                                                                                           
				�ɼ�(��ǰ�� ��)	���ڿ�		UNIT_CODE		ARRAY                                                                                                                   
				��ǰ��	���ڿ�		ITEM_NAME		ARRAY                                                                                                                           
				��ǰ����	����			ITEM_PRICE		ARRAY                                                                                                           
				����	����				ORDER_STATE	�ֹ����� : 10�����Ϸ� : 20��ۿϷ� : 40���/��ǰ : -10	ARRAY                                                           
			 *                                                                                                                                                                                      
			 */                                                                                                                                                                                     
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                                    
		// ��û������ �м� �� ����ó��                                                                                                                                                                                  
		try {                                                                                                                                                                                                   
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                                       
			/*Request parameter ó��*/                                                                                                                                                                      
			String TARGETDATE = responsor.getRequestDataString(0,"TARGETDATE");                                                                                                                             
			///log.debug("TARGETDATE======================>" + TARGETDATE);                                                                                                                                                                                                            
			List listEaiOrdsync = commerceMgr.listEaiOrdsync(TARGETDATE);                                                                                                                                   
			//log.debug("����Ʈ======================>" + listEaiOrdsync);                                                                                                                                    
						                                                                                                                                                                        
			int dataCnt = listEaiOrdsync.size(); // ���� ����� ������ ������                                                                                                                               
			 responsor.initResponseList(dataCnt);					                                                                                                                        
		                                                                                                                                                                                                        
			 for (int i = 0 ; i < dataCnt ; i++){                                                                                                                                                           
	                                                                                                                                                                                                                    
				CommerceData CommerceList = (CommerceData) listEaiOrdsync.get(i);	                                                                                                                
				
				responsor.setResponseData(i,"MC_ORDER_NO",CommerceList.getMc_order_no());                                                                                                               
				responsor.setResponseData(i,"TP_ORDER_NO",CommerceList.getTp_order_no());                                                                                                                  
				responsor.setResponseData(i,"TOT_RSALE_AMT",CommerceList.getTot_rsale_amt()+"");                                                                                                        
				responsor.setResponseData(i,"PAY_CARD_AMT","0");                                                                                                                                          
				responsor.setResponseData(i,"PAY_ONLINE_AMT","0");                                                                                                                                        
				responsor.setResponseData(i,"PAY_POINT_AMT","0");                                                                                                                                         
				responsor.setResponseData(i,"PAY_COUPON_AMT","0");                                                                                                                                        
				responsor.setResponseData(i,"ORD_NAME",CommerceList.getOrd_name());                                                                                                                                             
				responsor.setResponseData(i,"MEMCATEGORY","");                                                                                                                                          
				responsor.setResponseData(i,"BANK_NAME","");                                                                                                                   
				responsor.setResponseData(i,"BANK_ACCOUNT","");                                                                                                             
				responsor.setResponseData(i,"ORD_TYPE",CommerceList.getOrd_type());                                                                                                                     
				responsor.setResponseData(i,"PRDCATEGORY_CODE",CommerceList.getPrdcategory_code());                                                                                                                                      
				
				 List itemDataList = new ArrayList();                                                                                                                                                    
				//int itemDataSize = 4;                                                                                                                                                                   
				//for (int j = 0; j < itemDataSize; j++) {                                                                                                                                                
					HashMap itemData = new HashMap();                                                                                                                                       
					itemData.put("ITEM_CODE"  ,CommerceList.getItem_code());                                                                                                                  
					itemData.put("ORDER_QTY"  ,CommerceList.getOrder_qty()+"");                                                                                                                                          
					itemData.put("UNIT_CODE"  ,CommerceList.getUnit_code());                                                                                                                  
					itemData.put("ITEM_NAME"  ,CommerceList.getItem_name());                                                                                                                  
					itemData.put("ITEM_PRICE" ,CommerceList.getItem_price()+"");                                                                                                                
					itemData.put("ORDER_STATE",CommerceList.getOrder_state()+"");                                                                                                           
					itemDataList.add(itemData);                                                                                                                                             
						//}                                                                                                                                                                       
					responsor.setResponseDataMap(i,itemDataList);                                                                                                                           
				}                                                                                                                                                                               
				return responsor.doResponse("",isEncryptResponse,key);                                                                                                                          
				} catch (Exception e) { 
					log.debug("error======================>" + e.toString()); 
						return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�.", isEncryptResponse,key);                                                                                  
				}                                                                                                                                                                                       
		}                                                                                                                                                                                               
                  
	private void EAI_ORDRECV(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                               
		/*                                                                                                                                                                                  
		Request                                                                                                                                                                                 
			��û��	���ڿ�		TARGETDATE	YYYYMMDD�ֹ��߻���                                                                                                                                    
		Response                                                                                                                                                                                
			�ֹ� ��ȣ	���ڿ�		MC_ORDER_NO	M-Cart �ֹ� ��ȣ	                                                                                                        
			�ֹ� ��ȣ	���ڿ�		TP_ORDER_NO	���޸� �ֹ� ��ȣ	                                                                                                        
			���� �ݾ�	����		TOT_RSALE_AMT	���� �ݾ�	                                                                                                                
			���� ����	����		PAY_CARD_AMT	�ſ�ī������ݾ�	Blank ���                                                                                              
					����		PAY_ONLINE_AMT	����������ݾ�	Blank ���                                                                                                      
					����		PAY_POINT_AMT	����Ʈ �����ݾ�	Blank ���                                                                                                      
					����		PAY_COUPON_AMT	���� �����ݾ�	Blank ���                                                                                                      
			�ֹ��θ�	���ڿ�		ORD_NAME	�ֹ��ڸ�	Blank ���                                                                                                      
			ȸ�������� 	���ڿ�	3	MEMCATEGORY	�ֹ���ȣ �յ��ڸ� + �ֹι�ȣ 7��° �ڸ�	Blank ���                                                                              
			�Ա������	���ڿ�		BANK_NAME	�����Ա� �� �Ա��� �����	Blank ���                                                                                      
			�Աݰ���	���ڿ�		BANK_ACCOUNT	�����Ա� �� �Ա��� ���� ���� ��ȣ	Blank ���                                                                              
			�ֹ��߻�����	����		ORD_TYPE	1:SKT => ���޸�2:���޸� => SKT	���޸��� ���� �ֹ�(���ʹ��) ���� SKT�� ���� (�����īƮ����) ������� ����                     
			�Ǹ� ����	����	1	PRDCATEGORY_CODE	1:�����īƮ2:�����Ȩ����3:SEA 	                                                                                
			��ǰ�ڵ�	���ڿ�		ITEM_CODE		ARRAY                                                                                                                   
			�ֹ�����	����		ORDER_QTY		ARRAY                                                                                                                   
			�ɼ�(��ǰ�� ��)	���ڿ�		UNIT_CODE		ARRAY                                                                                                                   
			��ǰ��	���ڿ�		ITEM_NAME		ARRAY                                                                                                                           
			��ǰ����	����		ITEM_PRICE		ARRAY                                                                                                                   
			����	����		ORDER_STATE	�ֹ����� : 10�����Ϸ� : 20��ۿϷ� : 40���/��ǰ : -10	ARRAY                                                                                                       
		 */                                                                                                                                                                                     
		EAIRequestor requestor = null;  
		requestor = EAIProcessorFactory.createEAIRequestor(logger, businessCd, tpCode);  
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode); 
       
		// ��û������ �м� �� ����ó��                                                                                                                                                          
		try {                                                                                                                                                                                   
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                       
			/*Request parameter ó��*/                                                                                                                                                      
			String TARGETDATE = responsor.getRequestDataString(0,"TARGETDATE"); 
			
	                                                                                                                                                                                                                     
			List listEaiOrdrcv = commerceMgr.listEaiOrdrcv(TARGETDATE);                                                                                                                                   
			//log.debug("����Ʈ======================>" + listEaiOrdrcv);                                                                                                                                    
						                                                                                                                                                                        
			int dataCnt = listEaiOrdrcv.size(); // ���� ����� ������ ������                                                                                                                               
			 responsor.initResponseList(dataCnt); 
			 
			for (int i = 0 ; i < dataCnt ; i++){  
				
				CommerceData CommerceList = (CommerceData) listEaiOrdrcv.get(i);
				
				requestor.setRequestData(i,"MC_ORDER_NO",CommerceList.getMc_order_no());                                                                                                                
				requestor.setRequestData(i,"TP_ORDER_NO",CommerceList.getOrder_no());                                                                                                                
				requestor.setRequestData(i,"TOT_RSALE_AMT",CommerceList.getTot_rsale_amt()+"");                                                                                                                      
				requestor.setRequestData(i,"PAY_CARD_AMT",CommerceList.getPay_card_amt()+"");                                                                                                                       
				requestor.setRequestData(i,"PAY_ONLINE_AMT",CommerceList.getPay_online_amt()+"");                                                                                                                         
				requestor.setRequestData(i,"PAY_POINT_AMT",CommerceList.getPay_point_amt()+"");                                                                                                                          
				requestor.setRequestData(i,"PAY_COUPON_AMT",CommerceList.getMc_coupon_amt()+"");                                                                                                                         
				requestor.setRequestData(i,"ORD_NAME",CommerceList.getCust_name());                                                                                                                      
				requestor.setRequestData(i,"MEMCATEGORY","");                                                                                                                        
				requestor.setRequestData(i,"BANK_NAME",CommerceList.getBank_name());                                                                                                                    
				requestor.setRequestData(i,"BANK_ACCOUNT",CommerceList.getBank_account());                                                                                                              
				requestor.setRequestData(i,"ORD_TYPE",CommerceList.getOrd_type());                                                                                                                               
				requestor.setRequestData(i,"PRDCATEGORY_CODE",CommerceList.getPrdcategory_code());                                                                                                                
                                                                                                                                                                                                                
				List itemDataList = new ArrayList();                                                                                                                                    
				 
				
				int itemDataSize = 1; // �ֹ� ��ǰ �� ��ŭ                                                                                                                              
				for (int j = 0; j < itemDataSize; j++) {                                                                                                                                
					HashMap itemData = new HashMap();                                                                                                                               
					itemData.put("ITEM_CODE",CommerceList.getItem_code());                                                                                                                          
					itemData.put("ORDER_QTY",CommerceList.getOrder_qty()+"");                                                                                                                                  
					itemData.put("UNIT_CODE",CommerceList.getUnit_code());                                                                                                                              
					itemData.put("ITEM_NAME",CommerceList.getItem_name());                                                                                                                          
					itemData.put("ITEM_PRICE",CommerceList.getItem_price()+"");                                                                                                                             
					itemData.put("ORDER_STATE",CommerceList.getOrder_state()+"");                                                                                                                               
					itemDataList.add(itemData);                                                                                                                                     
				}                                                                                                                                                                       
				requestor.SetRequestDataMap(i,(List)itemDataList);                                                                                                                      
			}                                                                                                                                                                               
			String url = "http://mhstest.boomboom.co.kr/";                                                                                                                                  
			String returnXml = requestor.doRequest(url, true, key);                                                                                                                         
                                                                                                                                                                                                                
			requestor.analyzeResponse(returnXml, false, null);                                                                                                                              
			if (requestor.getResponseReturnCd() == 0)                                                                                                                                       
					throw new Exception();                                                                                                                                          
                                                                                                                                                                                                                
			/*���� ó��*/                                                                                                                                                                   
			                                                                                                                                                                                
		} catch (Exception e) {                                                                                                                                                                 
				throw new Exception();                                                                                                                                                  
		}                                                                                                                                                                                       
	}             
                                                                                                                                                                                                                        
		private String EAI_ORDCONFIRM(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                          
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				Request                                                                                                                                                                                 
					�ֹ� ��ȣ	���ڿ�	20	TARGETDATE	�����                                                                                                                                                               
				Response                                                                                                                                                                                
					�ֹ� ��ȣ	���ڿ�	20	TP_ORDER_NO	���޸� �ֹ���ȣ	                                                                                                                
					�ֹ� ��ȣ	���ڿ�	20	MC_ORDER_NO	M-CART �ֹ� ��ȣ	                                                                                                        
					��ǰ ��ȣ	���ڿ�		ITEM_CODE	���޸� ��ǰ ��ȣ	                                                                                                        
					��ǰ ����	����		ORDER_QTY		                                                                                                                        
					����	����		ORDER_STATE	�ֹ����� : 10�����Ϸ� : 20��ۿϷ� : 40���/��ǰ : -10	                                                                                
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ��û������ �м� �� ����ó��                                                                                                                                                          
				try {                                                                                                                                                                                           
					responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                       
					/*Request parameter ó��*/                                                                                                                                                      
					String TARGETDATE = responsor.getRequestDataString(0,"TARGETDATE"); 
					//log.debug("TARGETDATE======================>" + TARGETDATE); 
					List listEaiOrdconfirm = commerceMgr.listEaiOrdconfirm(TARGETDATE);                                                                                                                   
					//log.debug("����Ʈ======================>" + listEaiOrdconfirm);                                                                                                                    
								                                                                                                                                                                
					int dataCnt = listEaiOrdconfirm.size(); // ���� ����� ������ ������                                                                                                               
						                                                                                                                                                                                
					responsor.initResponseList(dataCnt);                                                                                                                                            
					for (int i = 0 ; i < dataCnt ; i++){                                                                                                                                            
							                                                                                                                                                                        
					CommerceData CommerceList = (CommerceData) listEaiOrdconfirm.get(i);                                                                                                       
					
					responsor.setResponseData(i,"MC_ORDER_NO",CommerceList.getMc_order_no());                                                                                                               
					responsor.setResponseData(i,"TP_ORDER_NO",CommerceList.getTp_order_no());                                                                                                              
					responsor.setResponseData(i,"ITEM_CODE",  CommerceList.getItem_code());                                                                                                                   
					responsor.setResponseData(i,"ORDER_QTY",  CommerceList.getOrder_qty());                                                                                                                             
					responsor.setResponseData(i,"ORDER_STATE",CommerceList.getOrder_state()+"");
				}                                                                                                                                                                       
					return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
			} catch (Exception e) {                                                                                                                                                         
				return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�.", isEncryptResponse,key);                                                                          
			  }                                                                                                                                                                                       
		}     
	
	private String EAI_AIRSCHEDULE(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                         
				/*                                                                                                                                                                                      
				 * ���� �Ϸ�� �ش� ������ SKT �� ���� �Ѵ�.                                                                                                                                            
				Request                                                                                                                                                                                 
					��۰˻��� ����	���ڿ�	8	TARGETDATE_ST	YYYYMMDD	                                                                                                                
					��۰˻��� ����	���ڿ�	8	TARGETDATE_END	YYYYMMDD	                                                                                                                
                                                                                                                                                                                                                        
				Response                                                                                                                                                                                
					��۹�ȣ	���ڿ�	30	TPAIR_NO		                                                                                                                        
					�������	���ڿ�		AIR_NAME		                                                                                                                        
					��������	���ڿ�		START_DATE	YYYYMMDD HHMMSS	                                                                                                                
					���������	���ڿ�		END_DATE	YYYYMMDD HHMMSS	                                                                                                                
					��ǰ �ڵ�	���ڿ�	20	ITEM_CODE		                                                                                                                        
					��ǰ ����	����	1	SALE_STATE	1:�Ǹ���2:ǰ��(������)3:�Ǹ�����4.�Ͻ�����(�Ͻ���� ����)5:��� �ð��븸 �Ǹ�6:������ ���� �Ǹ�	                                
                 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ��û������ �м� �� ����ó��                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key); 
				
				/*Request parameter ó��*/                                                                                                                                              
				String TARGETDATE_ST  = responsor.getRequestDataString(0,"TARGETDATE_ST");                                                                                               
				String TARGETDATE_END = responsor.getRequestDataString(0,"TARGETDATE_END");
				
				List listEaiAirSchedule = commerceMgr.listEaiAirSchedule(TARGETDATE_ST,TARGETDATE_END);  
				
				int dataCnt = listEaiAirSchedule.size(); // ���� ����� ������ ������                                                                                                   
				responsor.initResponseList(dataCnt);			                    
				
				for (int i = 0 ; i < dataCnt ; i++){
					
					CommerceData CommerceList = (CommerceData) listEaiAirSchedule.get(i); 
					
					responsor.setResponseData(i,"TPAIR_NO"  ,CommerceList.getProg_code());                                                                                                             
					responsor.setResponseData(i,"AIR_NAME"  ,CommerceList.getProg_name());                                                                                                             
					responsor.setResponseData(i,"START_DATE",CommerceList.getStart_date());                                                                                                     
					responsor.setResponseData(i,"END_DATE"  ,CommerceList.getEnd_date());                                                                                                       
					responsor.setResponseData(i,"ITEM_CODE" ,CommerceList.getItem_code());                                                                                                           
					responsor.setResponseData(i,"SALE_STATE",CommerceList.getSale_state());  
				}                                                                                                                                                                       
						return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
					} catch (Exception e) {                                                                                                                                                         
							return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
			                                                                                                                                                         

	/* ----------------------------------------------------------------------------                                                                                                                                                                                     
	 *                                                                                                                                                                                      
		 Request
			�˻���	���ڿ�	30	KEYWORD		
			�˻� ����	����		SRCH_GB	0:Ű����1:��ǰ��˻� 2:��ǰ�ڵ� �˻�3:������� �˻�4:�𵨹�ȣ �˻�5:��ǰ��	
			�ּ� ����	���ڿ�		PRICE_MIN	�Ϲ� �˻� �ּ� ����	
			�ִ� ����	���ڿ�		PRICE_MAX	�Ϲ� �˻� �ִ� ����	
			���� ���� 	����		SORT_TP	1 : ��ǰ���2 : �������3 : ���ݼ�4 : �α��	
			������ ��ȣ	����	3	PAGE_NO	1 �̻��� ����	
			������������	����		FETCH_CNT	������ ����	

		Response
			�˻���� ��	����	5	SEARCH_CNT		
			��ǰ�ڵ�	���ڿ�	9	ITEM_CODE		ARRAY
			��ǰ��	���ڿ�	128	ITEM_NAME		ARRAY
			���� ��ü	���ڿ�	60	MAKECO_NAME		Blank ��� : ARRAY
			����	����		ITEM_PRICE		ARRAY
		 *                                                                                                                                 
	 *                                                                                                                                                                                      
	 ----------------------------------------------------------------------------------*/      
	private String EAI_ITEMSRCH(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                            
		log.debug("entering 'EAI_ITEMSRCH' method...");	
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
	                                                                                                                                                                                                            
		// ��û������ �м� �� ����ó��                                                                                                                                                          
		try {                                                                                                                                                                                   
			
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
			/*Request parameter ó��*/                                                                                                                                              
			int PRICE_MIN = responsor.getRequestDataInt(0,"PRICE_MIN");
			log.debug("PRICE_MIN..."+PRICE_MIN);
			int PRICE_MAX = responsor.getRequestDataInt(0,"PRICE_MAX");
			log.debug("PRICE_MAX..."+PRICE_MAX);
			int PAGE_NO = responsor.getRequestDataInt(0,"PAGE_NO");
			log.debug("PAGE_NO..."+PAGE_NO);
			if(PAGE_NO == 0) {
				PAGE_NO = 1;
			}
			String KEYWORD = responsor.getRequestDataString(0,"KEYWORD"); 
			log.debug("KEYWORD..."+KEYWORD);
			int FETCH_CNT = responsor.getRequestDataInt(0,"FETCH_CNT");  //������ ������   
			log.debug("FETCH_CNT..."+FETCH_CNT);
			if(FETCH_CNT==0){
				FETCH_CNT = 15;
			}
			int SRCH_GB = responsor.getRequestDataInt(0,"SRCH_GB");  
			log.debug("SRCH_GB..."+SRCH_GB);
			int SORT_TP = responsor.getRequestDataInt(0,"SORT_TP"); 
			log.debug("SORT_TP..."+SORT_TP);
			String sortField = "";
			char sortOrder;
			boolean isSearchItemCode = false;
			String makeco_name = "";				
			Searcher searcher = new Searcher();
			ResultSet resultSet = new ResultSet();
			int dataCnt = 0; // Fetch_Cnt �� ���� �ʴ� ���� ����
			
			SearchCriteria criteria = new SearchCriteria();
			criteria.setQuery(KEYWORD);
			criteria.setResearchQuery("");  // ��� �� ��˻� ����
			criteria.setResearchFilter("");  // ��� �� ��˻� ����
			if(PRICE_MIN >0 && PRICE_MAX >0){
				criteria.setMinPrice(PRICE_MIN+"");
				criteria.setMaxPrice(PRICE_MAX+"");
			}	
			criteria.setStartNo((PAGE_NO - 1) * FETCH_CNT + 1); // ����������
			criteria.setListSize(FETCH_CNT); // ������ ������

			if(SRCH_GB == 2) {
				isSearchItemCode = true;
			} 				
			if(SRCH_GB == 3) {
				makeco_name = KEYWORD;
			}				
			if(SRCH_GB == 5) {//��ǰ���� 0�� �̻��� ��ǰ�� 
				criteria.addFilter(new Filter("REVIEWNO", "0", Filter.NAND));
			}
			criteria.setSearchExplain(false);
			criteria.setSearchItemCode(isSearchItemCode);
			
			if(SORT_TP == 1) {
				sortField = "ITEM_CODE";
				sortOrder = 'A';
			} 
			else if(SORT_TP == 2) {
				sortField = "MAKECO_NAME";
				sortOrder = 'A';
			}
			else if(SORT_TP == 3) {
				sortField = "SALE_PRICE";
				sortOrder = 'A';
			}
			else {
				sortField = "QTY";
				sortOrder = 'D';
			}
			criteria.setSortField(sortField);
			criteria.setSortOrder(sortOrder);
			criteria.setMakeCompName(makeco_name);
			// query
			resultSet.setItems(searcher.search(criteria));
			resultSet.setSects(searcher.getSects());
			resultSet.setPriceRanges(searcher.getPriceRanges());
			resultSet.setQueryInfoItem(searcher.getQueryInfo());
			resultSet.setFilterInfoItem(searcher.getFilterInfo());
			resultSet.setTotalSize(searcher.getTotalSize());
			resultSet.setRecommendKeywords(searcher.getRecommendKeywords());

			/*���� ó��*/ 
		 
			int searchCnt = resultSet.getItemsSize(); // �˻� ��� �� 
			log.debug("searchCnt..."+resultSet.getItemsSize());
			int dataCnt1 = 1; // 1 ���� - ������ �ѹ��� !!!
			responsor.initResponseList(dataCnt1);
			for (int i = 0 ; i < dataCnt1 ; i++){
			responsor.setResponseData(0,"SEARCH_CNT",resultSet.getTotalSize()); // ������ �˻� ��� ����   
			 
			List itemDataList = new ArrayList();
			List items = resultSet.getItems(); 
			for (int j = 0; j < searchCnt; j++) {
				SearchItem item = new SearchItem(); 
				item = (SearchItem)items.get(j);	 
			  	HashMap itemData = new HashMap();
				itemData.put("ITEM_CODE",item.getItem_code());
					//log.debug("ITEM_CODE...("+j+")"+item.getItem_code());
				itemData.put("ITEM_NAME",item.getItem_name());
					//log.debug("ITEM_NAME...("+j+")"+item.getItem_name());
				itemData.put("MAKECO_NAME",item.getMakeco_name());
					//log.debug("MAKECO_NAME...("+j+")"+item.getMakeco_name());
				itemData.put("ITEM_PRICE",item.getSale_price()+"");
					//log.debug("ITEM_PRICE...("+j+")"+item.getSale_price());
				itemDataList.add(itemData);
			 }
			 responsor.setResponseDataMap(0,itemDataList);
			}
			return responsor.doResponse("",isEncryptResponse,key); 
			
		} catch (Exception e) {                                                                                                                                                         
				return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�."+e.toString(), isEncryptResponse,key);                                                                          
		}                                                                                                                                                                                       
	}                                                                                                                                                                                               
                                                                                                                                                                                                                        
	
	/*------------------------------------------------------------------------------
	 * 
	 Request
		�˻���	���ڿ�	30	KEYWORD		
		�˻� ����	����		SRCH_GB	0:Ű����1:��ǰ��˻� 2:��ǰ�ڵ� �˻�3:������� �˻�4:�𵨹�ȣ �˻�5:��ǰ��	
		�ּ� ����	���ڿ�		PRICE_MIN	�Ϲ� �˻� �ּ� ����	
		�ִ� ����	���ڿ�		PRICE_MAX	�Ϲ� �˻� �ִ� ����	

	Response
		�˻���� ��	����	5	SEARCH_CNT				 * 
	 ----------------------------------------------------------------------------------*/
	private String EAI_ITEMSRCH_CNT(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{
		log.debug("entering 'EAI_ITEMSRCH_CNT' method...");
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);

		// ��û������ �м� �� ����ó��
		try {
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);
				/*Request parameter ó��*/
				String KEYWORD = responsor.getRequestDataString(0,"KEYWORD");
				//log.debug("KEYWORD..."+KEYWORD);
				int SRCH_GB = responsor.getRequestDataInt(0,"SRCH_GB");
				//log.debug("SRCH_GB..."+SRCH_GB);
				int PRICE_MIN = responsor.getRequestDataInt(0,"PRICE_MIN");
				//log.debug("PRICE_MIN..."+PRICE_MIN);
				int PRICE_MAX = responsor.getRequestDataInt(0,"PRICE_MAX");
				//log.debug("PRICE_MAX..."+PRICE_MAX);
				
				boolean isSearchItemCode = false;
				String makeco_name = "";				
				Searcher searcher = new Searcher();
				ResultSet resultSet = new ResultSet();
				int dataCnt = 0; // Fetch_Cnt �� ���� �ʴ� ���� ����
				
				SearchCriteria criteria = new SearchCriteria();
				criteria.setQuery(KEYWORD);
				criteria.setResearchQuery("");  // ��� �� ��˻� ����
				criteria.setResearchFilter("");  // ��� �� ��˻� ����
				if(PRICE_MIN >0 && PRICE_MAX >0){
					criteria.setMinPrice(PRICE_MIN+"");
					criteria.setMaxPrice(PRICE_MAX+"");
				}	
				criteria.setStartNo(1); // ����������
				criteria.setListSize(100); // ������ ������ 
				if(SRCH_GB == 2) {
					isSearchItemCode = true;
				} 				
				if(SRCH_GB == 3) {
					makeco_name = KEYWORD;
				}
				if(SRCH_GB == 5) {//��ǰ���� 0�� �̻��� ��ǰ�� 
					criteria.addFilter(new Filter("REVIEWNO", "0", Filter.NAND));
				}
				criteria.setSearchExplain(false);
				criteria.setSearchItemCode(isSearchItemCode);
				criteria.setSortField("");
				criteria.setSortOrder('D');
				criteria.setMakeCompName(makeco_name);
				// query
				resultSet.setItems(searcher.search(criteria));
				resultSet.setSects(searcher.getSects());
				resultSet.setPriceRanges(searcher.getPriceRanges());
				resultSet.setQueryInfoItem(searcher.getQueryInfo());
				resultSet.setFilterInfoItem(searcher.getFilterInfo());
				resultSet.setTotalSize(searcher.getTotalSize());
				resultSet.setRecommendKeywords(searcher.getRecommendKeywords());

				/*���� ó��*/ 
				dataCnt = resultSet.getTotalSize();
				//log.debug("dataCnt..."+dataCnt);
				responsor.initResponseList(1);
				responsor.setResponseData(0,"SEARCH_CNT",dataCnt+"");

				return responsor.doResponse("",isEncryptResponse,key);
	
			} catch (Exception e) {
					return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�."+e.toString(), isEncryptResponse,key);
		}
	}
	
	/*------------------------------------------------------------
	 * 
	 Request
		��ǰ �ڵ�	���ڿ�	20	ITEM_CODE	���޸� ��ǰ��ȣ	
		�����ð���	����		FETCH_CNT	������ ����	
	 
	Response
		���� �ڵ�	���ڿ�	20	BBS_NO	�Խù���ȣ	
		����	���ڿ�	200	SUBJECT	��ǰ������	
		����	����	3	ARRANGE	���� ����	
	 * 
	 -------------------------------------------------------------*/
	private String EAI_ITEMEVALLST(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{
		log.debug("entering 'EAI_ITEMEVALLST' method...");
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);

		// ��û������ �м� �� ����ó��
		try {
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);
				/*Request parameter ó��*/
				String ITEM_CODE = responsor.getRequestDataString(0,"ITEM_CODE");
				//log.debug("ITEM_CODE..."+ITEM_CODE);
				int FETCH_CNT = responsor.getRequestDataInt(0,"FETCH_CNT");
				//log.debug("FETCH_CNT..."+FETCH_CNT);
				List itemReviewList = commerceMgr.listItemReview(ITEM_CODE, 1, FETCH_CNT);
				int rowCnt = itemReviewList.size();
				if(rowCnt > FETCH_CNT){
					rowCnt = FETCH_CNT;
				}
				//log.debug("size..."+itemReviewList.size());
				responsor.initResponseList(rowCnt);				
				for (int i = 0 ; i < rowCnt ; i++){
					Blog blog = new Blog();
					blog = (Blog)itemReviewList.get(i);
					responsor.setResponseData(i,"BBS_NO",blog.getEntryid());
					responsor.setResponseData(i,"SUBJECT",blog.getTitle());
					responsor.setResponseData(i,"ARRANGE",i);
				}   
				return responsor.doResponse("",isEncryptResponse,key);
				
			} catch (Exception e) {
					return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�."+e.toString(), isEncryptResponse,key);
		}
	}	                                                                                                                                                               
 
	
	/*-----------------------------------------------------------------------
	 * 
	 Request
		��ǰ �ڵ�	���ڿ�	9	ITEM_CODE		
		���� �ڵ�	���ڿ�	9	BBS_NO		
	 
	Response
		����	���ڿ�	200	SUBJECT	��ǰ������	
		����	���ڿ�	4000	CONTENTS	������ �ִ밪 ����	
		����	���ڿ�		CUST_NAME	����/����ڸ�	Blank ���
		��¥	���ڿ�		CRT_DM	��������(yyyyMMdd)	
		����	����		CONT_PNT	�ο� ��������� ǥ�� 0 ~ 100	
	 * 
	 -------------------------------------------------------------------------*/
	private String EAI_ITEMEVAL(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{
		log.debug("entering 'EAI_ITEMEVAL' method...");
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);

		// ��û������ �м� �� ����ó��
		try {
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);
				/*Request parameter ó��*/
				String item_code = responsor.getRequestDataString(0,"ITEM_CODE");
				String entryid = responsor.getRequestDataString(0,"BBS_NO");
				Blog blog = new Blog();
				blog = commerceMgr.readBentry(item_code,entryid);			

				/*���� ó��*/
				int dataCnt = 1; // 1�� ����
				responsor.initResponseList(dataCnt);
				for (int i = 0 ; i < dataCnt ; i++){
					responsor.setResponseData(i,"SUBJECT",blog.getTitle());
					responsor.setResponseData(i,"CONTENTS",getBlogHtmlContent(blog.getContentpath()));
					responsor.setResponseData(i,"CUST_NAME",blog.getBlogid());
					//log.debug("CUST_NAME..."+blog.getBlogid());
					responsor.setResponseData(i,"CRT_DM",blog.getDay());
					//log.debug("CRT_DM..."+blog.getDay());
					responsor.setResponseData(i,"CONT_PNT",blog.getReviewpoint()*20);
					//log.debug("CONT_PNT..."+blog.getReviewpoint());
				} 
				return responsor.doResponse("",isEncryptResponse,key);
			} catch (Exception e) {
					return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�."+e.toString(), isEncryptResponse,key);
		}
	}	                                                                                                                                                       
                                                                                                                                                                                                                        
                                                                                                                                                                                                                        
			private String EAI_EVENT_UPD(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                           
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				��ü EVENT ID	���ڿ�	40	TPEVENT_ID	                                                                                                                                        
				�̺�Ʈ ��	���ڿ�	100	EVENT_NM	                                                                                                                                        
				�̺�Ʈ ������	���ڿ�	14	EVENT_ST_DM	YYYYMMDDHHMMSS                                                                                                                          
				�̺�Ʈ ������	���ڿ�	14	EVENT_END_DM	YYYYMMDDHHMMSS                                                                                                                          
				�̺�Ʈ URL	���ڿ�	300	EVENT_URL	http: �� ������ ���� URL                                                                                                                
				���ļ���	����		ARRANGE	1 ~ N                                                                                                                                           
				��������	����	1	ISACTIVE	1 : ����, 0 : ������                                                                                                                    
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
					EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                    
                                                                                                                                                                                                                        
					// ��û������ �м� �� ����ó��                                                                                                                                                  
					try {                                                                                                                                                                           
							responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                       
							/*Request parameter ó��*/                                                                                                                                      
							// Request ����                                                                                                                                                 
                                                                                                                                                                                                                        
							/*���� ó��*/                                                                                                                                                   
							int dataCnt = 2; // ���� ����� ������ ������                                                                                                                   
							responsor.initResponseList(dataCnt);                                                                                                                            
							for (int i = 0 ; i < dataCnt ; i++){                                                                                                                            
								responsor.setResponseData(i,"TPEVENT_ID","TPEVENT_ID");                                                                                                 
								responsor.setResponseData(i,"EVENT_NM","EVENT_NM");                                                                                                     
								responsor.setResponseData(i,"EVENT_ST_DM","20060425000000");                                                                                            
								responsor.setResponseData(i,"EVENT_END_DM","20060525235959");                                                                                           
								responsor.setResponseData(i,"EVENT_URL","http://www.sss.co.kr/event_04.jsp");                                                                           
								responsor.setResponseData(i,"ARRANGE",1);                                                                                                               
								responsor.setResponseData(i,"ISACTIVE",1);                                                                                                              
							}                                                                                                                                                               
							return responsor.doResponse("",isEncryptResponse,key);                                                                                                          
						} catch (Exception e) {                                                                                                                                                 
								return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�.", isEncryptResponse,key);                                                                  
						}                                                                                                                                                                       
					}                                                                                                                                                                               
                                                                                                                                                                                                                        
			                                                                                                                                                                                                
			                                                                                                                                                                              
                                                                                                                                                                                                                        
			private String EAI_COUPONINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                          
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				Request                                                                                                                                                                                 
					����ȣ	���ڿ�	30	CUST_NO	                                                                                                                                        
					�����ð���	���ڿ�		FETCH_CNT	                                                                                                                                
			                                                                                                                                                                                                
				Response                                                                                                                                                                                
					������	����		SAVEAMT		                                                                                                                                        
					������ȣ	���ڿ�		SEQ		ARRAY                                                                                                                           
					������	���ڿ�		COUPON_NAME		ARRAY                                                                                                                           
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ��û������ �м� �� ����ó��                                                                                                                                                          
				try {                                                                                                                                                                                   
						responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
						/*Request parameter ó��*/                                                                                                                                              
						String CUST_NO = responsor.getRequestDataString(0,"CUST_NO");                                                                                                           
						int FETCH_CNT = responsor.getRequestDataInt(0,"FETCH_CNT");                                                                                                             
                                                                                                                                                                                                                        
						/*���� ó��*/                                                                                                                                                           
						int dataCnt = 4; // Query ���� �Ǵ� FecthCnt                                                                                                                            
						responsor.initResponseList(dataCnt);                                                                                                                                    
						for (int i = 0 ; i < dataCnt ; i++){                                                                                                                                    
							responsor.setResponseData(i,"SAVEAMT",2000);                                                                                                                                                
							List itemDataList = new ArrayList();                                                                                                                            
							int itemDataSize = 4;                                                                                                                                           
							for (int j = 0; j < itemDataSize; j++) {                                                                                                                        
								HashMap itemData = new HashMap();                                                                                                                       
								itemData.put("SEQ","111");                                                                                                                              
								itemData.put("COUPON_NAME","COUPON_NAME");                                                                                                              
								itemDataList.add(itemData);                                                                                                                             
							}                                                                                                                                                               
							responsor.setResponseDataMap(i,itemDataList);                                                                                                                   
						}                                                                                                                                                                       
						return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
					} catch (Exception e) {                                                                                                                                                         
							return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
			private String EAI_COUPONINFODT(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                        
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				Request                                                                                                                                                                                 
					����ȣ	���ڿ�	30	CUST_NO	                                                                                                                                        
					����SEQ	���ڿ�		SEQ	                                                                                                                                                
			                                                                                                                                                                                                
				Response                                                                                                                                                                                
					����ȣ	���ڿ�	30	CUST_NO	���޸� ����ȣ	                                                                                                                        
					������ȣ	���ڿ�		COUPON_NO		                                                                                                                        
					������	���ڿ�		COUPON_NAME		                                                                                                                                
					���αݾ� �Ǵ� ���η�	���ڿ�		COUPON_DC_AMT	1000��5%	                                                                                                        
					��������	���ڿ�		START_DATE	YYYYMMDD	                                                                                                                
					���������	���ڿ�		END_DATE	YYYYMMDD	                                                                                                                
					���������ǰ	���ڿ�		COUPON_APPLY	����ǰ(�Ϻ�����)Ư����ǰ����	Blank ���                                                                                      
					��밡�ɱݾ�	����		USE_PRICE	1,000�̻�	                                                                                                                
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ��û������ �м� �� ����ó��                                                                                                                                                          
				try {                                                                                                                                                                                   
						responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
						/*Request parameter ó��*/                                                                                                                                              
						String CUST_NO = responsor.getRequestDataString(0,"CUST_NO");                                                                                                           
						String SEQ = responsor.getRequestDataString(0,"SEQ");                                                                                                                   
                                                                                                                                                                                                                        
						/*���� ó��*/                                                                                                                                                           
						int dataCnt = 1; // �Ѱ� ����                                                                                                                                           
						responsor.initResponseList(dataCnt);                                                                                                                                    
						for (int i = 0 ; i < dataCnt ; i++){                                                                                                                                    
							responsor.setResponseData(i,"CUST_NO","CUST_NO");                                                                                                               
							responsor.setResponseData(i,"COUPON_NO","COUPON_NO");                                                                                                           
							responsor.setResponseData(i,"COUPON_NAME","COUPON_NAME");                                                                                                       
							responsor.setResponseData(i,"COUPON_DC_AMT",2000);                                                                                                              
							responsor.setResponseData(i,"START_DATE","20060502");                                                                                                           
							responsor.setResponseData(i,"END_DATE","20060502");                                                                                                             
							responsor.setResponseData(i,"COUPON_APPLY","COUPON_APPLY");                                                                                                     
							responsor.setResponseData(i,"USE_PRICE",1000);                                                                                                                  
						}                                                                                                                                                                       
						return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
					} catch (Exception e) {                                                                                                                                                         
							return responsor.getErrorXmlData("ó���� ���� �߻� �߽��ϴ�.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
              
			// HTML�� �о��.
			private String getBlogHtmlContent(String contextpath) {
     			String reqUrl = "http://wibro.hyundaihmall.com/front/wbBlogHtmlR.do?Htmlfilename="+contextpath;
				return HttpUtils.getHtml(reqUrl);
			}					                                                                                                                                                                                                
}                                                                                                                                                                                                                       