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
			        	//a2 = CUST_NO; //?????? ???? ???? ???? ????????, ?????? ????. ?????? ?????? ?? ???? ????, ???? ???? ????
			        	
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
        			//a2 = CUST_NO; //?????? ???? ???? ???? ????????, ?????? ????. ?????? ?????? ?? ???? ????, ???? ???? ????
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
			        			//a2 = CUST_NO; //?????? ???? ???? ???? ????????, ?????? ????. ?????? ?????? ?? ???? ????, ???? ???? ????
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
								//a2 = CUST_NO; //?????? ???? ???? ???? ????????, ?????? ????. ?????? ?????? ?? ???? ????, ???? ???? ????
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
	

	/*???? ????	????????	????????	Request????	Response????	????????	????                                                                                                            
	*???? ????	ITEMS_UPD	SKT=>??????	X	X	????	HTTP???????? ????                                                                                                                       
	???????? ???? 	CATEGORY_UPD	SKT=>??????	X	X	????	?????? ????                                                                                                                             
	?????? ????	EVENT_UPD	SKT=>??????	X	X	????	?????? ????                                                                                                                             
	????????????	ITEMINFO	SKT=>??????	X	X	????	?????? ?????? ?? ???? ????                                                                                                              
	????????/????????	ITEMSTOCKDT	SKT=>??????	X	X	????????	                                                                                                                        
	???? ???? ????	ITEMSTOCK	SKT=>??????	X	X	????????	                                                                                                                                
	????????????	ITEMSRCH	SKT=>??????	X	X	????????	                                                                                                                                
	???????? ??????	ITEMEVALLST	SKT=>??????	X	X	????????	                                                                                                                                
	???????? ????	ITEMEVAL	SKT=>??????	X	X	????	                                                                                                                                        
	????????		ORDINFO 	SKT=>??????	O	X	????????	                                                                                                                        
	????????	ORDRECV	SKT<=??????	O	X	????????	HTTP ????                                                                                                                               
	????????	ORDCONFIRM	SKT=>??????	X	O	????????	HTTP ????                                                                                                                       
	???? ??????	ORDSYNC	SKT=>??????	X	O	????	???? ??????                                                                                                                                     
	?????? ???? ????	CUSTINFO	SKT=>??????	O	O	????	                                                                                                                                
	????????/?????? ????	COUPONINFO	SKT=>??????	O	O	????	                                                                                                                                
	????????????????	COUPONINFODT	SKT=>??????	X	O	????	                                                                                                                                
	??????????	AIRSCHEDULE	SKT=>??????	X	X	??????	???????????? ????                                                                                                                       
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
	 * 	???? ????	??????		ITEM_CODE                                                                                                                                                                
		????????	??????	9	SKTCATEGORY_ID                                                                                                                                                          
		???????????? ID	??????	20	TPCATEGORY_ID                                                                                                                                                           
		???? ????	????	1	PRDCATEGORY_CODE                                                                                                                                                        
		??????	??????	100	ITEM_NAME                                                                                                                                                                       
		?????? ????	????		ITEM_IMG                                                                                                                                                                
		????	????		ITEM_PRICE                                                                                                                                                                      
		????????	????	1	SALE_STATE 1:??????2:????(??????)3:????????4.????????(???????? ????)                                                                                                    
	 *                                                                                                                                                                                                              
	 */	                                                                                                                                                                                                        
	private String EAI_ITEMS_UPD(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                           
	                                                                                                                                                                                                                
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                            
		// ?????????? ???? ?? ????????                                                                                                                                                                          
		try {                                                                                                                                                                                                   
			log.debug("entering 'EAI_ITEMS_UPD' method...");                                                                                                                                                
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                                       
			/*Request parameter ????*/                                                                                                                                              
			/*???? ????*/                         
			int SectID = 77415; // ?????? ???? ID
			List listEaiItemsUpdList = commerceMgr.listEaiItemsUpd(SectID);                                                                                                                                 
			//log.debug("??????======================>" + listEaiItemsUpdList);                                                                                                                               
			                                                                                                                                                                                                
			int dataCnt = listEaiItemsUpdList.size(); // ???? ?????? ?????? ??????                                                                                                                          
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
			return responsor.getErrorXmlData("?????? ???? ???? ????????::"+e, isEncryptResponse,key);                                                                                                       
		}                                                                                                                                                                                                       
	}                                                                                                                                                                                                               
                                                                                                                                                                                                                        
	private String EAI_CATEGORY_UPD(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                        
		/*                                                                                                                                                                                                      
		 *                                                                                                                                                                                                      
		???????????? ??	??????	40	TPCATEGORY_NAME		                                                                                                                                                
		???????????? ID	??????	20	TPCATEGORY_ID                                                                                                                                                           
		???? ????	??????	1	PRDCATEGORY_CODE	1:??????????2:????????????	                                                                                                                
		???? 1 ID	??????	20	TPCATEGORY_LEV1                                                                                                                                                         
		???? 2 ID	??????	20	TPCATEGORY_LEV2		Blank ????                                                                                                                                      
		???? 3 ID	??????	20	TPCATEGORY_LEV3		Blank ????                                                                                                                                      
		????????	????		ARRANGE	1 ~ N                                                                                                                                                           
		????????	????	1	ISACTIVE	1 : ????, 0 : ??????                                                                                                                                    
		 *                                                                                                                                                                                                      
		 */                                                                                                                                                                                                     
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                    
	                                                                                                                                                                                                                
		// ?????????? ???? ?? ????????                                                                                                                                                                  
		try {                                                                                                                                                                                           
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                       
			/*Request parameter ????*/                                                                                                                                                      
			List listEaiCateUpdList = commerceMgr.listEaiCateUpd();                                                                                                                                 
			                                                                                                                                                                                                
			int dataCnt = listEaiCateUpdList.size(); // ???? ?????? ?????? ??????                                                                                                                           
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
						return responsor.getErrorXmlData("?????? ???? ???? ????????.", isEncryptResponse,key);                                                                                  
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
			                                                                                                                                                                                                
	private String EAI_ITEMINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                            
			/*                                                                                                                                                                                              
			 *                                                                                                                                                                                              
			 Response                                                                                                                                                                                       
				??????	??????	100	ITEM_NAME		                                                                                                                                        
				???? ????	??????	60	MAKECO_NAME		                                                                                                                                
				????	????	????          10	ITEM_PRICE		                                                                                                                        
				???? ????	????	     2	NOREST_MONTH	?????? ?????? 0	                                                                                                                        
				??????	????    	10	SAVE_AMT	???? ?? ???? ?? ????	                                                                                                                
				??????	????		10    SHIP_AMT	?????? 0 ???? ???? ???????? ???? ???? 	???????????? ?????? ???? ??????.                                                                        
				?????? 1	??????	20	OPTION_NAME1		?????? Blank                                                                                                                    
				?????? 2	??????	20	OPTION_NAME2		?????? Blank                                                                                                                    
				?????? 3	??????	20	OPTION_NAME3		?????? Blank                                                                                                                    
				??????????	??????	4000	ITEM_EXPLAIN	?????? ?????? ????	                                                                                                                
				??????????????	????	1	ITEM_TECH	0 : ????1 : ????	                                                                                                                
				????????	        ????	1	SALE_STATE	1 : ??????2 : ????3 : ????????	????/???????? ?? ???? ????                                                                      
				????	                ????	1	ITEM_STOCK	0 : ????1 : ????	??????/?????????? ?????? ???? ??                                                                        
				?????? ????	????	1	ITEM_IMG	0 : ????1 : ????	                                                                                                                
				???????? ????	????		ITEMVALL_QTY	?????? 0 	                                                                                                                        
			 *                                                                                                                                                                                              
			 */                                                                                                                                                                                             
			                                                                                                                                      
			EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                    
                                                                                                                                                                                                                        
			// ?????????? ???? ?? ????????                                                                                                                                                                  
		try {				                                                                                                                                                                
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                               
				/*Request parameter ????*/                                                                                                                                                              
				String ITEM_CODE = responsor.getRequestDataString(0,"ITEM_CODE");                                                                                                                       
                                                                                                                                                                                                                        
				List listEaiItemsInfo = commerceMgr.listEaiItemsInfo(ITEM_CODE);                                                                                                                        
				                                                                                                                                                                                        
				int dataCnt = listEaiItemsInfo.size(); // ???? ?????? ?????? ??????                                                                                                                     
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
				return responsor.getErrorXmlData("?????? ???? ???? ????????."+e, isEncryptResponse,key);                                                                                                
			}                                                                                                                                                                                               
		}                                                                                                                                                                                                       
                                                                                                                                                                                                                        
	private String EAI_ITEMSTOCK(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                           
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				 Request                                                                                                                                                                                
					???? ????	??????	20	ITEM_CODE	?????? ????????	                                                                                                                
					??????	??????	20	OPTION_NAME	???? ????	CJ???? Blank                                                                                                            
					??????????	????		FETCH_CNT	?????? ????	                                                                                                                
				                                                                                                                                                                                        
				Response                                                                                                                                                                                
					???? ????	????	5	STOCK_QTY	???? ???? ???? 	Blank                                                                                                           
					????????(????????)	??????	3	UNIT_CODE		                                                                                                                
					??????(??????)	??????	100	UNIT_NAME		                                                                                                                        
					????	????		ITEM_PRICE		                                                                                                                                
				 *                                                                                                                                                                                      
				 */ 
		 
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ?????????? ???? ?? ????????                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
				/*Request parameter ????*/                                                                                                                                              
				String ITEM_CODE   = responsor.getRequestDataString(0,"ITEM_CODE");  
				
				//String MAKECO_NAME = responsor.getRequestDataString(0,"MAKECO_NAME");                                                                                                   
				//int FETCH_CNT = responsor.getRequestDataInt(0,"FETCH_CNT");                                                                                                             
						                                                                                                                                                                        
				List listEaiItemsStock = commerceMgr.listEaiItemsStock(ITEM_CODE);                                                                                                       
		
				int dataCnt = listEaiItemsStock.size(); // ???? ?????? ?????? ??????                                                                                                    
				responsor.initResponseList(dataCnt);			                                                                                                                
						                                                                                                                                                                        
				for (int i = 0 ; i < dataCnt ; i++){
					
				CommerceData CommerceList = (CommerceData) listEaiItemsStock.get(i);	                                                                                                        
				// hmall ???? ?????? ???????? ????  ????.2006.04.12.??????                                                                                                                            
				responsor.setResponseData(i,"STOCK_QTY",CommerceList.getStock_qty()+"");                                                                                                                     
				responsor.setResponseData(i,"UNIT_CODE",CommerceList.getUnit_code());                                                                                                    
				responsor.setResponseData(i,"UNIT_NAME",CommerceList.getUnit_name());                                                                                                                    
				responsor.setResponseData(i,"ITEM_PRICE",CommerceList.getItem_price()+"");                                                                                          
				}                                                                                                                                                                       
				return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
				} catch (Exception e) {                                                                                                                                                         
				//logger.error(e.getMessage(), e);                                                                                                                                        
				return responsor.getErrorXmlData("?????? ???? ???? ????????.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
                                                                                                                                                                                                                        
	private String EAI_ITEMSTOCKDT(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                         
				/*                                                                                                                                                                                      
				 Request                                                                                                                                                                                
					???? ????	??????	20	ITEM_CODE	?????? ????????	                                                                                                                
					????????(????????)	??????	20	UNIT_CODE	?????? ????????	                                                                                                                                
				Response                                                                                                                                                                                
					???? ????	????	5	STOCK_QTY		                                                                                                                        
					????????(????????)	??????	20	UNIT_CODE	?????? ????????	                                                                                                        
					????	????		ITEM_PRICE		Blank ????                                                                                                                                               
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ?????????? ???? ?? ????????                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
				/*Request parameter ????*/                                                                                                                                              
				String ITEM_CODE = responsor.getRequestDataString(0,"ITEM_CODE");                                                                                                       
				String UNIT_CODE = responsor.getRequestDataString(0,"UNIT_CODE");                                                                                                       
                                                                                                                                                                                                                       
                List listEaiItemStockdt = commerceMgr.listEaiItemStockdt(ITEM_CODE,UNIT_CODE);                                                                                                                  
						                                                                                                                                                                        
				int dataCnt = listEaiItemStockdt.size(); // ???? ?????? ?????? ??????                                                                                                   
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
							return responsor.getErrorXmlData("?????? ???? ???? ????????.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
	private String EAI_CUSTINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                            
		/*                                                                                                                                                                                      
		Request                                                                                                                                                                                 
			????????????	??????	13	RESIDENT_NO	??-?? ????                                                                                                                                                         
		Response                                                                                                                                                                                
			????????	??????	12	CUST_NO	??N???????? CJ?????? ????????.                                                                                                                                 
		 */                                                                     
		 //EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                
		// ?????????? ???? ?? ????????                                                                                                                                                          
		try {
			log.debug("entering 'EAI_CUSTINFO' method...");	
			log.debug("xmlData==>"+xmlData);
			responsor.analyzeRequest(xmlData, isEncryptRequest, key); 
			/*Request parameter ????*/     				
			String RESIDENT_NO = responsor.getRequestDataString(0,"RESIDENT_NO"); 
			log.debug("RESIDENT_NO======================>" + RESIDENT_NO);
			List listEaiCustInfo = commerceMgr.listEaiCustInfo(RESIDENT_NO);
			
			/*???? ????*/                                                                                                                                                           
			int dataCnt = 1; // ???? ?????? ?????? ??????                                                                                                   
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
			return responsor.getErrorXmlData("?????? ???? ???? ????????.==>"+e.toString(), isEncryptResponse,key);
	}                                                                                                                                                                                       
	}                                                                                                                                                                                                                                     
		                                                                                                                                                                                                        
	private String EAI_ORDINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                             
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				 Request                                                                                                                                                                                
					???? ????	??????	20	MC_ORDER_NO	M-Cart ???? ????	                                                                                                        
					???? ?? ????	??????		HP_NO		                                                                                                                                
					???? ????	??????		CUST_NO	?????? ????????	                                                                                                                        
					???? ????	????		TOT_SALE_PRICE	?? ????????	                                                                                                                
					???? ???? ????	????		TOT_RSALE_AMT	???? ?????? ????	                                                                                                        
					??????	??????		MC_COUPON_NAME	M-Cart ??????	Blank ????                                                                                                              
					????????	??????		MC_COUPON_NO	M-Cart ???? ????	Blank ????                                                                                              
					????????????	??????	1	MC_COUPON_SETTLE_YN	M-Cart ???? ????????,Y/N	Blank ????                                                                              
					???????? ????	????		MC_COUPON_AMT	M-Cart ???? ???? ????	Blank ????                                                                                              
					?????????? ????	????		MC_POINT_AMT	M-Cart ??????(??????)	Blank ????                                                                                              
					??????????????	??????		POST_NO	XXXXXX	                                                                                                                                
					??????????????	??????		POST_SEQ	???????? DB ???? ?????? ????????	Blank ????                                                                              
					?????? ???? 1	??????		POST_ADDR	???????? ???? / XXXXXX	                                                                                                        
					?????? ???? 2	??????		ADDR	????????	                                                                                                                        
					????????????1	??????		DDD	02	Blank ????                                                                                                                      
					????????????2	??????		TEL1	123	Blank ????                                                                                                                      
					????????????3	??????		TEL2	4567	Blank ????                                                                                                                      
					???? ????	????	1	PRDCATEGORY_CODE	1:??????????2:????????????3:SEA 	                                                                                
					???? ????????	??????		BASE_NO		Blank ????                                                                                                                      
					???? ???? 1	??????		BASE_ADDR	???????? ????	Blank ????                                                                                                      
					???? ???? 2	??????		BASE_ADDR_DETAIL	????????	Blank ????                                                                                              
					???? ????	??????		BASE_NAME	????	Blank ????                                                                                                              
					????????	??????		RESIDENTID	????????	Blank ????                                                                                                      
					???????? URL	??????		ORDER_URL	M-Cart ???????? ???? URL	                                                                                                
					???? URL	??????		SUCC_URL	M-Cart ???????? ?? ???? URL	                                                                                                
					???? URL	??????		FAIL_URL	M-Cart ???????? ?? ???? URL	                                                                                                
					???? Transaction ID	??????	30	TID	SKT???? ?????? ???? Key?????????? ???? ????	Blank ????                                                                      
					????????	??????		ITEM_CODE		ARRAY                                                                                                                   
					????????	????		ORDER_QTY		ARRAY                                                                                                                   
					????(????????)	??????		UNIT_CODE		ARRAY                                                                                                                   
					????????	????		ITEM_PRICE		ARRAY                                                                                                                   
				                                                                                                                                                                                        
				Response                                                                                                                                                                                
					???? ????	??????		MC_ORDER_NO	M-Cart ???? ????	                                                                                                        
					???? ????	??????		TP_ORDER_NO	?????? ???? ????                                                                                                                                     
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ?????????? ???? ?? ????????                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
				/*Request parameter ????*/                                                                                                                                              
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
							                                                                                                                                                                
					//???? ???? ???????? ???? ????.                                                                                                                                 
					HashMap loopData = (HashMap) loopDataList.get(k);                                                                                                               
					String ITEM_CODE = (String) loopData.get("ITEM_CODE");                                                                                                          
					int ORDER_QTY = Integer.parseInt((String) loopData.get("ITEM_CODE"));                                                                                           
					String UNIT_CODE = (String) loopData.get("ITEM_CODE");                                                                                                          
					int ITEM_PRICE = Integer.parseInt((String) loopData.get("ITEM_CODE"));                                                                                          
				}                                                                                                                                                                       
                                                                                                                                                                                                                        
				/*???? ????*/                                                                                                                                                           
				int dataCnt = 1; // 1?? ????                                                                                                                                            
				responsor.initResponseList(dataCnt);                                                                                                                                    
				for (int i = 0 ; i < dataCnt ; i++){                                                                                                                                    
				responsor.setResponseData(i,"MC_ORDER_NO","MC_ORDER_NO");                                                                                                       
				responsor.setResponseData(i,"TP_ORDER_NO","TP_ORDER_NO");                                                                                                       
				}                                                                                                                                                                       
					return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
					} catch (Exception e) {                                                                                                                                                         
					return responsor.getErrorXmlData("?????? ???? ???? ????????.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
                                                                                                                                                                                                                        
	private String EAI_ORDSYNC(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                             
		log.debug("entering 'EAI_ORDSYNC' method...");	
		/*                                                                                                                                                                                      
			 *                                                                                                                                                                                      
			Request                                                                                                                                                                                 
				??????	??????		TARGETDATE	YYYYMMDD??????????	                                                                                                                
			                                                                                                                                                                                        
			Response                                                                                                                                                                                
				???? ????	??????		MC_ORDER_NO		M-Cart ???? ????	                                                                                                
				???? ????	??????		TP_ORDER_NO		?????? ???? ????	                                                                                                
				???? ????	????			TOT_RSALE_AMT	???? ????	                                                                                                        
				???? ????	????			PAY_CARD_AMT	????????????????	Blank ????                                                                                      
						????			PAY_ONLINE_AMT	??????????????		Blank ????                                                                                      
						????			PAY_POINT_AMT	?????? ????????		Blank ????                                                                                      
						????			PAY_COUPON_AMT	???? ????????		Blank ????                                                                                      
				????????	??????		ORD_NAME		????????			Blank ????                                                                              
				?????????? 	??????	3	MEMCATEGORY	???????? ???????? + ???????? 7???? ????	Blank ????                                                                              
				??????????	??????		BANK_NAME	???????? ?? ?????? ??????	Blank ????                                                                                      
				????????	??????		BANK_ACCOUNT	???????? ?? ?????? ???? ???? ????	Blank ????                                                                              
				????????????	????		ORD_TYPE	1:SKT => ??????2:?????? => SKT	???????? ???? ????(????????) ???? SKT?? ???? (??????????????) ???????? ????                     
				???? ????	????	1		PRDCATEGORY_CODE	1:??????????2:????????????3:SEA 	                                                                        
				????????	??????		ITEM_CODE		ARRAY                                                                                                                   
				????????	????			ORDER_QTY		ARRAY                                                                                                           
				????(?????? ??)	??????		UNIT_CODE		ARRAY                                                                                                                   
				??????	??????		ITEM_NAME		ARRAY                                                                                                                           
				????????	????			ITEM_PRICE		ARRAY                                                                                                           
				????	????				ORDER_STATE	???????? : 10???????? : 20???????? : 40????/???? : -10	ARRAY                                                           
			 *                                                                                                                                                                                      
			 */                                                                                                                                                                                     
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                                    
		// ?????????? ???? ?? ????????                                                                                                                                                                                  
		try {                                                                                                                                                                                                   
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                                       
			/*Request parameter ????*/                                                                                                                                                                      
			String TARGETDATE = responsor.getRequestDataString(0,"TARGETDATE");                                                                                                                             
			///log.debug("TARGETDATE======================>" + TARGETDATE);                                                                                                                                                                                                            
			List listEaiOrdsync = commerceMgr.listEaiOrdsync(TARGETDATE);                                                                                                                                   
			//log.debug("??????======================>" + listEaiOrdsync);                                                                                                                                    
						                                                                                                                                                                        
			int dataCnt = listEaiOrdsync.size(); // ???? ?????? ?????? ??????                                                                                                                               
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
						return responsor.getErrorXmlData("?????? ???? ???? ????????.", isEncryptResponse,key);                                                                                  
				}                                                                                                                                                                                       
		}                                                                                                                                                                                               
                  
	private void EAI_ORDRECV(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                               
		/*                                                                                                                                                                                  
		Request                                                                                                                                                                                 
			??????	??????		TARGETDATE	YYYYMMDD??????????                                                                                                                                    
		Response                                                                                                                                                                                
			???? ????	??????		MC_ORDER_NO	M-Cart ???? ????	                                                                                                        
			???? ????	??????		TP_ORDER_NO	?????? ???? ????	                                                                                                        
			???? ????	????		TOT_RSALE_AMT	???? ????	                                                                                                                
			???? ????	????		PAY_CARD_AMT	????????????????	Blank ????                                                                                              
					????		PAY_ONLINE_AMT	??????????????	Blank ????                                                                                                      
					????		PAY_POINT_AMT	?????? ????????	Blank ????                                                                                                      
					????		PAY_COUPON_AMT	???? ????????	Blank ????                                                                                                      
			????????	??????		ORD_NAME	????????	Blank ????                                                                                                      
			?????????? 	??????	3	MEMCATEGORY	???????? ???????? + ???????? 7???? ????	Blank ????                                                                              
			??????????	??????		BANK_NAME	???????? ?? ?????? ??????	Blank ????                                                                                      
			????????	??????		BANK_ACCOUNT	???????? ?? ?????? ???? ???? ????	Blank ????                                                                              
			????????????	????		ORD_TYPE	1:SKT => ??????2:?????? => SKT	???????? ???? ????(????????) ???? SKT?? ???? (??????????????) ???????? ????                     
			???? ????	????	1	PRDCATEGORY_CODE	1:??????????2:????????????3:SEA 	                                                                                
			????????	??????		ITEM_CODE		ARRAY                                                                                                                   
			????????	????		ORDER_QTY		ARRAY                                                                                                                   
			????(?????? ??)	??????		UNIT_CODE		ARRAY                                                                                                                   
			??????	??????		ITEM_NAME		ARRAY                                                                                                                           
			????????	????		ITEM_PRICE		ARRAY                                                                                                                   
			????	????		ORDER_STATE	???????? : 10???????? : 20???????? : 40????/???? : -10	ARRAY                                                                                                       
		 */                                                                                                                                                                                     
		EAIRequestor requestor = null;  
		requestor = EAIProcessorFactory.createEAIRequestor(logger, businessCd, tpCode);  
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode); 
       
		// ?????????? ???? ?? ????????                                                                                                                                                          
		try {                                                                                                                                                                                   
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                       
			/*Request parameter ????*/                                                                                                                                                      
			String TARGETDATE = responsor.getRequestDataString(0,"TARGETDATE"); 
			
	                                                                                                                                                                                                                     
			List listEaiOrdrcv = commerceMgr.listEaiOrdrcv(TARGETDATE);                                                                                                                                   
			//log.debug("??????======================>" + listEaiOrdrcv);                                                                                                                                    
						                                                                                                                                                                        
			int dataCnt = listEaiOrdrcv.size(); // ???? ?????? ?????? ??????                                                                                                                               
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
				 
				
				int itemDataSize = 1; // ???? ???? ?? ????                                                                                                                              
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
                                                                                                                                                                                                                
			/*???? ????*/                                                                                                                                                                   
			                                                                                                                                                                                
		} catch (Exception e) {                                                                                                                                                                 
				throw new Exception();                                                                                                                                                  
		}                                                                                                                                                                                       
	}             
                                                                                                                                                                                                                        
		private String EAI_ORDCONFIRM(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                          
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				Request                                                                                                                                                                                 
					???? ????	??????	20	TARGETDATE	??????                                                                                                                                                               
				Response                                                                                                                                                                                
					???? ????	??????	20	TP_ORDER_NO	?????? ????????	                                                                                                                
					???? ????	??????	20	MC_ORDER_NO	M-CART ???? ????	                                                                                                        
					???? ????	??????		ITEM_CODE	?????? ???? ????	                                                                                                        
					???? ????	????		ORDER_QTY		                                                                                                                        
					????	????		ORDER_STATE	???????? : 10???????? : 20???????? : 40????/???? : -10	                                                                                
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ?????????? ???? ?? ????????                                                                                                                                                          
				try {                                                                                                                                                                                           
					responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                       
					/*Request parameter ????*/                                                                                                                                                      
					String TARGETDATE = responsor.getRequestDataString(0,"TARGETDATE"); 
					//log.debug("TARGETDATE======================>" + TARGETDATE); 
					List listEaiOrdconfirm = commerceMgr.listEaiOrdconfirm(TARGETDATE);                                                                                                                   
					//log.debug("??????======================>" + listEaiOrdconfirm);                                                                                                                    
								                                                                                                                                                                
					int dataCnt = listEaiOrdconfirm.size(); // ???? ?????? ?????? ??????                                                                                                               
						                                                                                                                                                                                
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
				return responsor.getErrorXmlData("?????? ???? ???? ????????.", isEncryptResponse,key);                                                                          
			  }                                                                                                                                                                                       
		}     
	
	private String EAI_AIRSCHEDULE(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                         
				/*                                                                                                                                                                                      
				 * ???? ?????? ???? ?????? SKT ?? ???? ????.                                                                                                                                            
				Request                                                                                                                                                                                 
					?????????? ????	??????	8	TARGETDATE_ST	YYYYMMDD	                                                                                                                
					?????????? ????	??????	8	TARGETDATE_END	YYYYMMDD	                                                                                                                
                                                                                                                                                                                                                        
				Response                                                                                                                                                                                
					????????	??????	30	TPAIR_NO		                                                                                                                        
					????????	??????		AIR_NAME		                                                                                                                        
					??????????	??????		START_DATE	YYYYMMDD HHMMSS	                                                                                                                
					??????????	??????		END_DATE	YYYYMMDD HHMMSS	                                                                                                                
					???? ????	??????	20	ITEM_CODE		                                                                                                                        
					???? ????	????	1	SALE_STATE	1:??????2:????(??????)3:????????4.????????(???????? ????)5:???? ???????? ????6:?????? ???? ????	                                
                 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ?????????? ???? ?? ????????                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key); 
				
				/*Request parameter ????*/                                                                                                                                              
				String TARGETDATE_ST  = responsor.getRequestDataString(0,"TARGETDATE_ST");                                                                                               
				String TARGETDATE_END = responsor.getRequestDataString(0,"TARGETDATE_END");
				
				List listEaiAirSchedule = commerceMgr.listEaiAirSchedule(TARGETDATE_ST,TARGETDATE_END);  
				
				int dataCnt = listEaiAirSchedule.size(); // ???? ?????? ?????? ??????                                                                                                   
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
							return responsor.getErrorXmlData("?????? ???? ???? ????????.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
			                                                                                                                                                         

	/* ----------------------------------------------------------------------------                                                                                                                                                                                     
	 *                                                                                                                                                                                      
		 Request
			??????	??????	30	KEYWORD		
			???? ????	????		SRCH_GB	0:??????1:?????????? 2:???????? ????3:???????? ????4:???????? ????5:??????	
			???? ????	??????		PRICE_MIN	???? ???? ???? ????	
			???? ????	??????		PRICE_MAX	???? ???? ???? ????	
			???? ???? 	????		SORT_TP	1 : ????????2 : ????????3 : ??????4 : ??????	
			?????? ????	????	3	PAGE_NO	1 ?????? ????	
			????????????	????		FETCH_CNT	?????? ????	

		Response
			???????? ??	????	5	SEARCH_CNT		
			????????	??????	9	ITEM_CODE		ARRAY
			??????	??????	128	ITEM_NAME		ARRAY
			???? ????	??????	60	MAKECO_NAME		Blank ???? : ARRAY
			????	????		ITEM_PRICE		ARRAY
		 *                                                                                                                                 
	 *                                                                                                                                                                                      
	 ----------------------------------------------------------------------------------*/      
	private String EAI_ITEMSRCH(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                            
		log.debug("entering 'EAI_ITEMSRCH' method...");	
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
	                                                                                                                                                                                                            
		// ?????????? ???? ?? ????????                                                                                                                                                          
		try {                                                                                                                                                                                   
			
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
			/*Request parameter ????*/                                                                                                                                              
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
			int FETCH_CNT = responsor.getRequestDataInt(0,"FETCH_CNT");  //?????? ??????   
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
			int dataCnt = 0; // Fetch_Cnt ?? ???? ???? ???? ????
			
			SearchCriteria criteria = new SearchCriteria();
			criteria.setQuery(KEYWORD);
			criteria.setResearchQuery("");  // ???? ?? ?????? ????
			criteria.setResearchFilter("");  // ???? ?? ?????? ????
			if(PRICE_MIN >0 && PRICE_MAX >0){
				criteria.setMinPrice(PRICE_MIN+"");
				criteria.setMaxPrice(PRICE_MAX+"");
			}	
			criteria.setStartNo((PAGE_NO - 1) * FETCH_CNT + 1); // ??????????
			criteria.setListSize(FETCH_CNT); // ?????? ??????

			if(SRCH_GB == 2) {
				isSearchItemCode = true;
			} 				
			if(SRCH_GB == 3) {
				makeco_name = KEYWORD;
			}				
			if(SRCH_GB == 5) {//???????? 0?? ?????? ?????? 
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

			/*???? ????*/ 
		 
			int searchCnt = resultSet.getItemsSize(); // ???? ???? ?? 
			log.debug("searchCnt..."+resultSet.getItemsSize());
			int dataCnt1 = 1; // 1 ???? - ?????? ?????? !!!
			responsor.initResponseList(dataCnt1);
			for (int i = 0 ; i < dataCnt1 ; i++){
			responsor.setResponseData(0,"SEARCH_CNT",resultSet.getTotalSize()); // ?????? ???? ???? ????   
			 
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
				return responsor.getErrorXmlData("?????? ???? ???? ????????."+e.toString(), isEncryptResponse,key);                                                                          
		}                                                                                                                                                                                       
	}                                                                                                                                                                                               
                                                                                                                                                                                                                        
	
	/*------------------------------------------------------------------------------
	 * 
	 Request
		??????	??????	30	KEYWORD		
		???? ????	????		SRCH_GB	0:??????1:?????????? 2:???????? ????3:???????? ????4:???????? ????5:??????	
		???? ????	??????		PRICE_MIN	???? ???? ???? ????	
		???? ????	??????		PRICE_MAX	???? ???? ???? ????	

	Response
		???????? ??	????	5	SEARCH_CNT				 * 
	 ----------------------------------------------------------------------------------*/
	private String EAI_ITEMSRCH_CNT(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{
		log.debug("entering 'EAI_ITEMSRCH_CNT' method...");
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);

		// ?????????? ???? ?? ????????
		try {
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);
				/*Request parameter ????*/
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
				int dataCnt = 0; // Fetch_Cnt ?? ???? ???? ???? ????
				
				SearchCriteria criteria = new SearchCriteria();
				criteria.setQuery(KEYWORD);
				criteria.setResearchQuery("");  // ???? ?? ?????? ????
				criteria.setResearchFilter("");  // ???? ?? ?????? ????
				if(PRICE_MIN >0 && PRICE_MAX >0){
					criteria.setMinPrice(PRICE_MIN+"");
					criteria.setMaxPrice(PRICE_MAX+"");
				}	
				criteria.setStartNo(1); // ??????????
				criteria.setListSize(100); // ?????? ?????? 
				if(SRCH_GB == 2) {
					isSearchItemCode = true;
				} 				
				if(SRCH_GB == 3) {
					makeco_name = KEYWORD;
				}
				if(SRCH_GB == 5) {//???????? 0?? ?????? ?????? 
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

				/*???? ????*/ 
				dataCnt = resultSet.getTotalSize();
				//log.debug("dataCnt..."+dataCnt);
				responsor.initResponseList(1);
				responsor.setResponseData(0,"SEARCH_CNT",dataCnt+"");

				return responsor.doResponse("",isEncryptResponse,key);
	
			} catch (Exception e) {
					return responsor.getErrorXmlData("?????? ???? ???? ????????."+e.toString(), isEncryptResponse,key);
		}
	}
	
	/*------------------------------------------------------------
	 * 
	 Request
		???? ????	??????	20	ITEM_CODE	?????? ????????	
		??????????	????		FETCH_CNT	?????? ????	
	 
	Response
		???? ????	??????	20	BBS_NO	??????????	
		????	??????	200	SUBJECT	??????????	
		????	????	3	ARRANGE	???? ????	
	 * 
	 -------------------------------------------------------------*/
	private String EAI_ITEMEVALLST(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{
		log.debug("entering 'EAI_ITEMEVALLST' method...");
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);

		// ?????????? ???? ?? ????????
		try {
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);
				/*Request parameter ????*/
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
					return responsor.getErrorXmlData("?????? ???? ???? ????????."+e.toString(), isEncryptResponse,key);
		}
	}	                                                                                                                                                               
 
	
	/*-----------------------------------------------------------------------
	 * 
	 Request
		???? ????	??????	9	ITEM_CODE		
		???? ????	??????	9	BBS_NO		
	 
	Response
		????	??????	200	SUBJECT	??????????	
		????	??????	4000	CONTENTS	?????? ?????? ????	
		??????	??????		CUST_NAME	??????/????????	Blank ????
		????	??????		CRT_DM	????????(yyyyMMdd)	
		????	????		CONT_PNT	???? ?????????? ???? 0 ~ 100	
	 * 
	 -------------------------------------------------------------------------*/
	private String EAI_ITEMEVAL(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{
		log.debug("entering 'EAI_ITEMEVAL' method...");
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);

		// ?????????? ???? ?? ????????
		try {
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);
				/*Request parameter ????*/
				String item_code = responsor.getRequestDataString(0,"ITEM_CODE");
				String entryid = responsor.getRequestDataString(0,"BBS_NO");
				Blog blog = new Blog();
				blog = commerceMgr.readBentry(item_code,entryid);			

				/*???? ????*/
				int dataCnt = 1; // 1?? ????
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
					return responsor.getErrorXmlData("?????? ???? ???? ????????."+e.toString(), isEncryptResponse,key);
		}
	}	                                                                                                                                                       
                                                                                                                                                                                                                        
                                                                                                                                                                                                                        
			private String EAI_EVENT_UPD(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                           
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				???? EVENT ID	??????	40	TPEVENT_ID	                                                                                                                                        
				?????? ??	??????	100	EVENT_NM	                                                                                                                                        
				?????? ??????	??????	14	EVENT_ST_DM	YYYYMMDDHHMMSS                                                                                                                          
				?????? ??????	??????	14	EVENT_END_DM	YYYYMMDDHHMMSS                                                                                                                          
				?????? URL	??????	300	EVENT_URL	http: ?? ?????? ???? URL                                                                                                                
				????????	????		ARRANGE	1 ~ N                                                                                                                                           
				????????	????	1	ISACTIVE	1 : ????, 0 : ??????                                                                                                                    
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
					EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                    
                                                                                                                                                                                                                        
					// ?????????? ???? ?? ????????                                                                                                                                                  
					try {                                                                                                                                                                           
							responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                       
							/*Request parameter ????*/                                                                                                                                      
							// Request ????                                                                                                                                                 
                                                                                                                                                                                                                        
							/*???? ????*/                                                                                                                                                   
							int dataCnt = 2; // ???? ?????? ?????? ??????                                                                                                                   
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
								return responsor.getErrorXmlData("?????? ???? ???? ????????.", isEncryptResponse,key);                                                                  
						}                                                                                                                                                                       
					}                                                                                                                                                                               
                                                                                                                                                                                                                        
			                                                                                                                                                                                                
			                                                                                                                                                                              
                                                                                                                                                                                                                        
			private String EAI_COUPONINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                          
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				Request                                                                                                                                                                                 
					????????	??????	30	CUST_NO	                                                                                                                                        
					??????????	??????		FETCH_CNT	                                                                                                                                
			                                                                                                                                                                                                
				Response                                                                                                                                                                                
					??????	????		SAVEAMT		                                                                                                                                        
					????????	??????		SEQ		ARRAY                                                                                                                           
					??????	??????		COUPON_NAME		ARRAY                                                                                                                           
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ?????????? ???? ?? ????????                                                                                                                                                          
				try {                                                                                                                                                                                   
						responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
						/*Request parameter ????*/                                                                                                                                              
						String CUST_NO = responsor.getRequestDataString(0,"CUST_NO");                                                                                                           
						int FETCH_CNT = responsor.getRequestDataInt(0,"FETCH_CNT");                                                                                                             
                                                                                                                                                                                                                        
						/*???? ????*/                                                                                                                                                           
						int dataCnt = 4; // Query ???? ???? FecthCnt                                                                                                                            
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
							return responsor.getErrorXmlData("?????? ???? ???? ????????.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
			private String EAI_COUPONINFODT(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                        
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				Request                                                                                                                                                                                 
					????????	??????	30	CUST_NO	                                                                                                                                        
					????SEQ	??????		SEQ	                                                                                                                                                
			                                                                                                                                                                                                
				Response                                                                                                                                                                                
					????????	??????	30	CUST_NO	?????? ????????	                                                                                                                        
					????????	??????		COUPON_NO		                                                                                                                        
					??????	??????		COUPON_NAME		                                                                                                                                
					???????? ???? ??????	??????		COUPON_DC_AMT	1000??5%	                                                                                                        
					??????????	??????		START_DATE	YYYYMMDD	                                                                                                                
					??????????	??????		END_DATE	YYYYMMDD	                                                                                                                
					????????????	??????		COUPON_APPLY	??????(????????)????????????	Blank ????                                                                                      
					????????????	????		USE_PRICE	1,000????	                                                                                                                
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// ?????????? ???? ?? ????????                                                                                                                                                          
				try {                                                                                                                                                                                   
						responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
						/*Request parameter ????*/                                                                                                                                              
						String CUST_NO = responsor.getRequestDataString(0,"CUST_NO");                                                                                                           
						String SEQ = responsor.getRequestDataString(0,"SEQ");                                                                                                                   
                                                                                                                                                                                                                        
						/*???? ????*/                                                                                                                                                           
						int dataCnt = 1; // ???? ????                                                                                                                                           
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
							return responsor.getErrorXmlData("?????? ???? ???? ????????.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
              
			// HTML?? ??????.
			private String getBlogHtmlContent(String contextpath) {
     			String reqUrl = "http://wibro.hyundaihmall.com/front/wbBlogHtmlR.do?Htmlfilename="+contextpath;
				return HttpUtils.getHtml(reqUrl);
			}					                                                                                                                                                                                                
}                                                                                                                                                                                                                       