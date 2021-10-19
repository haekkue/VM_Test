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
			        	//a2 = CUST_NO; //암호화 풀때 위를 주석 처리하고, 한줄을 쓴다. 암호화 할때는 이 줄을 주석, 위를 주석 풀기
			        	
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
        			//a2 = CUST_NO; //암호화 풀때 위를 주석 처리하고, 한줄을 쓴다. 암호화 할때는 이 줄을 주석, 위를 주석 풀기
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
			        			//a2 = CUST_NO; //암호화 풀때 위를 주석 처리하고, 한줄을 쓴다. 암호화 할때는 이 줄을 주석, 위를 주석 풀기
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
								//a2 = CUST_NO; //암호화 풀때 위를 주석 처리하고, 한줄을 쓴다. 암호화 할때는 이 줄을 주석, 위를 주석 풀기
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
	

	/*연동 이름	연동코드	연동방식	Request암호	Response암호	변경유형	비고                                                                                                            
	*상품 등록	ITEMS_UPD	SKT=>제휴몰	X	X	변경	HTTP소켓으로 수정                                                                                                                       
	카테고리 등록 	CATEGORY_UPD	SKT=>제휴몰	X	X	신규	홈쇼핑 전용                                                                                                                             
	이벤트 등록	EVENT_UPD	SKT=>제휴몰	X	X	신규	홈쇼핑 전용                                                                                                                             
	상품정보조회	ITEMINFO	SKT=>제휴몰	X	X	변경	홈쇼핑 구분자 및 항목 수정                                                                                                              
	상품옵션/재고조회	ITEMSTOCKDT	SKT=>제휴몰	X	X	일부변경	                                                                                                                        
	옵션 상세 조회	ITEMSTOCK	SKT=>제휴몰	X	X	일부변경	                                                                                                                                
	상품검색결과	ITEMSRCH	SKT=>제휴몰	X	X	일부변경	                                                                                                                                
	이용후기 리스트	ITEMEVALLST	SKT=>제휴몰	X	X	일부변경	                                                                                                                                
	이용후기 상세	ITEMEVAL	SKT=>제휴몰	X	X	없음	                                                                                                                                        
	주문전송		ORDINFO 	SKT=>제휴몰	O	X	일부변경	                                                                                                                        
	주문결과	ORDRECV	SKT<=제휴몰	O	X	일부변경	HTTP 소켓                                                                                                                               
	주문완료	ORDCONFIRM	SKT=>제휴몰	X	O	일부변경	HTTP 소켓                                                                                                                       
	주문 동기화	ORDSYNC	SKT=>제휴몰	X	O	신규	정산 처리용                                                                                                                                     
	제휴몰 회원 확인	CUSTINFO	SKT=>제휴몰	O	O	없음	                                                                                                                                
	할인쿠폰/적립금 정보	COUPONINFO	SKT=>제휴몰	O	O	없음	                                                                                                                                
	할인쿠폰상세조회	COUPONINFODT	SKT=>제휴몰	X	O	없음	                                                                                                                                
	방송편성표	AIRSCHEDULE	SKT=>제휴몰	X	X	구분자	모바일홈쇼핑 전용                                                                                                                       
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
	 * 	상품 번호	문자열		ITEM_CODE                                                                                                                                                                
		카테고리	문자열	9	SKTCATEGORY_ID                                                                                                                                                          
		업체카테고리 ID	문자열	20	TPCATEGORY_ID                                                                                                                                                           
		판매 유형	숫자	1	PRDCATEGORY_CODE                                                                                                                                                        
		상품명	문자열	100	ITEM_NAME                                                                                                                                                                       
		이미지 여부	숫자		ITEM_IMG                                                                                                                                                                
		가격	숫자		ITEM_PRICE                                                                                                                                                                      
		판매상태	숫자	1	SALE_STATE 1:판매중2:품절(진열됨)3:판매중지4.일시중지(일시재고 부족)                                                                                                    
	 *                                                                                                                                                                                                              
	 */	                                                                                                                                                                                                        
	private String EAI_ITEMS_UPD(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                           
	                                                                                                                                                                                                                
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                            
		// 요청데이터 분석 및 응답처리                                                                                                                                                                          
		try {                                                                                                                                                                                                   
			log.debug("entering 'EAI_ITEMS_UPD' method...");                                                                                                                                                
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                                       
			/*Request parameter 처리*/                                                                                                                                              
			/*응답 처리*/                         
			int SectID = 77415; // 가져올 매장 ID
			List listEaiItemsUpdList = commerceMgr.listEaiItemsUpd(SectID);                                                                                                                                 
			//log.debug("리스트======================>" + listEaiItemsUpdList);                                                                                                                               
			                                                                                                                                                                                                
			int dataCnt = listEaiItemsUpdList.size(); // 쿼리 결과에 수량이 정해짐                                                                                                                          
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
			return responsor.getErrorXmlData("처리중 에러 발생 했습니다::"+e, isEncryptResponse,key);                                                                                                       
		}                                                                                                                                                                                                       
	}                                                                                                                                                                                                               
                                                                                                                                                                                                                        
	private String EAI_CATEGORY_UPD(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                        
		/*                                                                                                                                                                                                      
		 *                                                                                                                                                                                                      
		업체카테고리 명	문자열	40	TPCATEGORY_NAME		                                                                                                                                                
		업체카테고리 ID	문자열	20	TPCATEGORY_ID                                                                                                                                                           
		판매 유형	문자열	1	PRDCATEGORY_CODE	1:모바일카트2:모바일홈쇼핑	                                                                                                                
		레벨 1 ID	문자열	20	TPCATEGORY_LEV1                                                                                                                                                         
		레벨 2 ID	문자열	20	TPCATEGORY_LEV2		Blank 허용                                                                                                                                      
		레벨 3 ID	문자열	20	TPCATEGORY_LEV3		Blank 허용                                                                                                                                      
		정렬순서	숫자		ARRANGE	1 ~ N                                                                                                                                                           
		진열여부	숫자	1	ISACTIVE	1 : 진열, 0 : 미진열                                                                                                                                    
		 *                                                                                                                                                                                                      
		 */                                                                                                                                                                                                     
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                    
	                                                                                                                                                                                                                
		// 요청데이터 분석 및 응답처리                                                                                                                                                                  
		try {                                                                                                                                                                                           
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                       
			/*Request parameter 처리*/                                                                                                                                                      
			List listEaiCateUpdList = commerceMgr.listEaiCateUpd();                                                                                                                                 
			                                                                                                                                                                                                
			int dataCnt = listEaiCateUpdList.size(); // 쿼리 결과에 수량이 정해짐                                                                                                                           
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
						return responsor.getErrorXmlData("처리중 에러 발생 했습니다.", isEncryptResponse,key);                                                                                  
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
			                                                                                                                                                                                                
	private String EAI_ITEMINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                            
			/*                                                                                                                                                                                              
			 *                                                                                                                                                                                              
			 Response                                                                                                                                                                                       
				상품명	문자열	100	ITEM_NAME		                                                                                                                                        
				제조 업체	문자열	60	MAKECO_NAME		                                                                                                                                
				가격	숫자	숫자          10	ITEM_PRICE		                                                                                                                        
				할부 정보	숫자	     2	NOREST_MONTH	할부가 없으면 0	                                                                                                                        
				적립금	숫자    	10	SAVE_AMT	구매 후 부여 적 립금	                                                                                                                
				배송비	숫자		10    SHIP_AMT	없으면 0 착불 또는 선불이면 해당 금액 	상품가격에는 포함이 되지 않는다.                                                                        
				옵션명 1	문자열	20	OPTION_NAME1		없으면 Blank                                                                                                                    
				옵션명 2	문자열	20	OPTION_NAME2		없으면 Blank                                                                                                                    
				옵션명 3	문자열	20	OPTION_NAME3		없으면 Blank                                                                                                                    
				상품기술서	문자열	4000	ITEM_EXPLAIN	가능한 최대값 설정	                                                                                                                
				상품기술서여부	숫자	1	ITEM_TECH	0 : 없음1 : 있음	                                                                                                                
				판매상태	        숫자	1	SALE_STATE	1 : 판매중2 : 품절3 : 판매중지	품절/판매중지 시 진열 안됨                                                                      
				재고	                숫자	1	ITEM_STOCK	0 : 없음1 : 있음	판매중/재고없음시 화면에 진열 됨                                                                        
				이미지 여부	숫자	1	ITEM_IMG	0 : 없음1 : 있음	                                                                                                                
				이용후기 개수	숫자		ITEMVALL_QTY	없으면 0 	                                                                                                                        
			 *                                                                                                                                                                                              
			 */                                                                                                                                                                                             
			                                                                                                                                      
			EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                    
                                                                                                                                                                                                                        
			// 요청데이터 분석 및 응답처리                                                                                                                                                                  
		try {				                                                                                                                                                                
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                               
				/*Request parameter 처리*/                                                                                                                                                              
				String ITEM_CODE = responsor.getRequestDataString(0,"ITEM_CODE");                                                                                                                       
                                                                                                                                                                                                                        
				List listEaiItemsInfo = commerceMgr.listEaiItemsInfo(ITEM_CODE);                                                                                                                        
				                                                                                                                                                                                        
				int dataCnt = listEaiItemsInfo.size(); // 쿼리 결과에 수량이 정해짐                                                                                                                     
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
				return responsor.getErrorXmlData("처리중 에러 발생 했습니다."+e, isEncryptResponse,key);                                                                                                
			}                                                                                                                                                                                               
		}                                                                                                                                                                                                       
                                                                                                                                                                                                                        
	private String EAI_ITEMSTOCK(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                           
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				 Request                                                                                                                                                                                
					상품 번호	문자열	20	ITEM_CODE	제휴몰 상품번호	                                                                                                                
					옵션명	문자열	20	OPTION_NAME	옵션 이름	CJ몰은 Blank                                                                                                            
					가져올개수	숫자		FETCH_CNT	가져올 개수	                                                                                                                
				                                                                                                                                                                                        
				Response                                                                                                                                                                                
					재고 수량	숫자	5	STOCK_QTY	주문 가능 수량 	Blank                                                                                                           
					단품코드(옵션코드)	문자열	3	UNIT_CODE		                                                                                                                
					단품명(옵션명)	문자열	100	UNIT_NAME		                                                                                                                        
					가격	숫자		ITEM_PRICE		                                                                                                                                
				 *                                                                                                                                                                                      
				 */ 
		 
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// 요청데이터 분석 및 응답처리                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
				/*Request parameter 처리*/                                                                                                                                              
				String ITEM_CODE   = responsor.getRequestDataString(0,"ITEM_CODE");  
				
				//String MAKECO_NAME = responsor.getRequestDataString(0,"MAKECO_NAME");                                                                                                   
				//int FETCH_CNT = responsor.getRequestDataInt(0,"FETCH_CNT");                                                                                                             
						                                                                                                                                                                        
				List listEaiItemsStock = commerceMgr.listEaiItemsStock(ITEM_CODE);                                                                                                       
		
				int dataCnt = listEaiItemsStock.size(); // 쿼리 결과에 수량이 정해짐                                                                                                    
				responsor.initResponseList(dataCnt);			                                                                                                                
						                                                                                                                                                                        
				for (int i = 0 ; i < dataCnt ; i++){
					
				CommerceData CommerceList = (CommerceData) listEaiItemsStock.get(i);	                                                                                                        
				// hmall 재고 체크는 결제시에 처리  한다.2006.04.12.윤선웅                                                                                                                            
				responsor.setResponseData(i,"STOCK_QTY",CommerceList.getStock_qty()+"");                                                                                                                     
				responsor.setResponseData(i,"UNIT_CODE",CommerceList.getUnit_code());                                                                                                    
				responsor.setResponseData(i,"UNIT_NAME",CommerceList.getUnit_name());                                                                                                                    
				responsor.setResponseData(i,"ITEM_PRICE",CommerceList.getItem_price()+"");                                                                                          
				}                                                                                                                                                                       
				return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
				} catch (Exception e) {                                                                                                                                                         
				//logger.error(e.getMessage(), e);                                                                                                                                        
				return responsor.getErrorXmlData("처리중 에러 발생 했습니다.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
                                                                                                                                                                                                                        
	private String EAI_ITEMSTOCKDT(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                         
				/*                                                                                                                                                                                      
				 Request                                                                                                                                                                                
					상품 번호	문자열	20	ITEM_CODE	제휴몰 상품번호	                                                                                                                
					단품코드(옵션코드)	문자열	20	UNIT_CODE	제휴몰 단품번호	                                                                                                                                
				Response                                                                                                                                                                                
					재고 수량	숫자	5	STOCK_QTY		                                                                                                                        
					단품코드(옵션코드)	문자열	20	UNIT_CODE	제휴몰 단품코드	                                                                                                        
					가격	숫자		ITEM_PRICE		Blank 허용                                                                                                                                               
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// 요청데이터 분석 및 응답처리                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
				/*Request parameter 처리*/                                                                                                                                              
				String ITEM_CODE = responsor.getRequestDataString(0,"ITEM_CODE");                                                                                                       
				String UNIT_CODE = responsor.getRequestDataString(0,"UNIT_CODE");                                                                                                       
                                                                                                                                                                                                                       
                List listEaiItemStockdt = commerceMgr.listEaiItemStockdt(ITEM_CODE,UNIT_CODE);                                                                                                                  
						                                                                                                                                                                        
				int dataCnt = listEaiItemStockdt.size(); // 쿼리 결과에 수량이 정해짐                                                                                                   
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
							return responsor.getErrorXmlData("처리중 에러 발생 했습니다.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
	private String EAI_CUSTINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                            
		/*                                                                                                                                                                                      
		Request                                                                                                                                                                                 
			주민등록번호	문자열	13	RESIDENT_NO	“-“ 없음                                                                                                                                                         
		Response                                                                                                                                                                                
			회원번호	문자열	12	CUST_NO	“N”인경우 CJ홈쇼핑 비회원임.                                                                                                                                 
		 */                                                                     
		 //EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                
		// 요청데이터 분석 및 응답처리                                                                                                                                                          
		try {
			log.debug("entering 'EAI_CUSTINFO' method...");	
			log.debug("xmlData==>"+xmlData);
			responsor.analyzeRequest(xmlData, isEncryptRequest, key); 
			/*Request parameter 처리*/     				
			String RESIDENT_NO = responsor.getRequestDataString(0,"RESIDENT_NO"); 
			log.debug("RESIDENT_NO======================>" + RESIDENT_NO);
			List listEaiCustInfo = commerceMgr.listEaiCustInfo(RESIDENT_NO);
			
			/*응답 처리*/                                                                                                                                                           
			int dataCnt = 1; // 쿼리 결과에 수량이 정해짐                                                                                                   
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
			return responsor.getErrorXmlData("처리중 에러 발생 했습니다.==>"+e.toString(), isEncryptResponse,key);
	}                                                                                                                                                                                       
	}                                                                                                                                                                                                                                     
		                                                                                                                                                                                                        
	private String EAI_ORDINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                             
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				 Request                                                                                                                                                                                
					주문 번호	문자열	20	MC_ORDER_NO	M-Cart 주문 번호	                                                                                                        
					회원 폰 번호	문자열		HP_NO		                                                                                                                                
					회원 번호	문자열		CUST_NO	제휴몰 회원번호	                                                                                                                        
					주문 금액	숫자		TOT_SALE_PRICE	총 주문금액	                                                                                                                
					주문 결제 금액	숫자		TOT_RSALE_AMT	실제 결제할 금액	                                                                                                        
					쿠폰명	문자열		MC_COUPON_NAME	M-Cart 쿠폰명	Blank 허용                                                                                                              
					쿠폰번호	문자열		MC_COUPON_NO	M-Cart 쿠폰 번호	Blank 허용                                                                                              
					쿠폰정산여부	문자열	1	MC_COUPON_SETTLE_YN	M-Cart 쿠폰 정산여부,Y/N	Blank 허용                                                                              
					쿠폰사용 금액	숫자		MC_COUPON_AMT	M-Cart 쿠폰 사용 금액	Blank 허용                                                                                              
					적립금사용 금액	숫자		MC_POINT_AMT	M-Cart 적립금(포인트)	Blank 허용                                                                                              
					배송지우편번호	문자열		POST_NO	XXXXXX	                                                                                                                                
					배송지일련번호	문자열		POST_SEQ	우편번호 DB 상에 배송지 일련번호	Blank 허용                                                                              
					배송지 주소 1	문자열		POST_ADDR	우편번호 주소 / XXXXXX	                                                                                                        
					배송지 주소 2	문자열		ADDR	상세주소	                                                                                                                        
					일반전화번호1	문자열		DDD	02	Blank 허용                                                                                                                      
					일반전화번호2	문자열		TEL1	123	Blank 허용                                                                                                                      
					일반전화번호3	문자열		TEL2	4567	Blank 허용                                                                                                                      
					판매 유형	숫자	1	PRDCATEGORY_CODE	1:모바일카트2:모바일홈쇼핑3:SEA 	                                                                                
					기본 우편번호	문자열		BASE_NO		Blank 허용                                                                                                                      
					기본 주소 1	문자열		BASE_ADDR	우편번호 주소	Blank 허용                                                                                                      
					기본 주소 2	문자열		BASE_ADDR_DETAIL	상세주소	Blank 허용                                                                                              
					기본 이름	문자열		BASE_NAME	이름	Blank 허용                                                                                                              
					주민번호	문자열		RESIDENTID	주민번호	Blank 허용                                                                                                      
					주문처리 URL	문자열		ORDER_URL	M-Cart 주문결과 수신 URL	                                                                                                
					성공 URL	문자열		SUCC_URL	M-Cart 주문완료 시 이동 URL	                                                                                                
					실패 URL	문자열		FAIL_URL	M-Cart 주문실패 시 이동 URL	                                                                                                
					결재 Transaction ID	문자열	30	TID	SKT에서 결재시 결제 Key홈쇼핑에서 사용 안함	Blank 허용                                                                      
					상품코드	문자열		ITEM_CODE		ARRAY                                                                                                                   
					주문수량	숫자		ORDER_QTY		ARRAY                                                                                                                   
					옵션(단품코드)	문자열		UNIT_CODE		ARRAY                                                                                                                   
					상품가격	숫자		ITEM_PRICE		ARRAY                                                                                                                   
				                                                                                                                                                                                        
				Response                                                                                                                                                                                
					주문 번호	문자열		MC_ORDER_NO	M-Cart 주문 번호	                                                                                                        
					주문 번호	문자열		TP_ORDER_NO	제휴몰 주문 번호                                                                                                                                     
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// 요청데이터 분석 및 응답처리                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
				/*Request parameter 처리*/                                                                                                                                              
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
							                                                                                                                                                                
					//주문 상품 리스트를 읽어 온다.                                                                                                                                 
					HashMap loopData = (HashMap) loopDataList.get(k);                                                                                                               
					String ITEM_CODE = (String) loopData.get("ITEM_CODE");                                                                                                          
					int ORDER_QTY = Integer.parseInt((String) loopData.get("ITEM_CODE"));                                                                                           
					String UNIT_CODE = (String) loopData.get("ITEM_CODE");                                                                                                          
					int ITEM_PRICE = Integer.parseInt((String) loopData.get("ITEM_CODE"));                                                                                          
				}                                                                                                                                                                       
                                                                                                                                                                                                                        
				/*응답 처리*/                                                                                                                                                           
				int dataCnt = 1; // 1로 고정                                                                                                                                            
				responsor.initResponseList(dataCnt);                                                                                                                                    
				for (int i = 0 ; i < dataCnt ; i++){                                                                                                                                    
				responsor.setResponseData(i,"MC_ORDER_NO","MC_ORDER_NO");                                                                                                       
				responsor.setResponseData(i,"TP_ORDER_NO","TP_ORDER_NO");                                                                                                       
				}                                                                                                                                                                       
					return responsor.doResponse("",isEncryptResponse,key);                                                                                                                  
					} catch (Exception e) {                                                                                                                                                         
					return responsor.getErrorXmlData("처리중 에러 발생 했습니다.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
                                                                                                                                                                                                                        
	private String EAI_ORDSYNC(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                             
		log.debug("entering 'EAI_ORDSYNC' method...");	
		/*                                                                                                                                                                                      
			 *                                                                                                                                                                                      
			Request                                                                                                                                                                                 
				요청일	문자열		TARGETDATE	YYYYMMDD주문발생일	                                                                                                                
			                                                                                                                                                                                        
			Response                                                                                                                                                                                
				주문 번호	문자열		MC_ORDER_NO		M-Cart 주문 번호	                                                                                                
				주문 번호	문자열		TP_ORDER_NO		제휴몰 주문 번호	                                                                                                
				결제 금액	숫자			TOT_RSALE_AMT	결제 금액	                                                                                                        
				결제 수단	숫자			PAY_CARD_AMT	신용카드결제금액	Blank 허용                                                                                      
						숫자			PAY_ONLINE_AMT	무통장결제금액		Blank 허용                                                                                      
						숫자			PAY_POINT_AMT	포인트 결제금액		Blank 허용                                                                                      
						숫자			PAY_COUPON_AMT	쿠폰 결제금액		Blank 허용                                                                                      
				주문인명	문자열		ORD_NAME		주문자명			Blank 허용                                                                              
				회원유형값 	문자열	3	MEMCATEGORY	주문번호 앞두자리 + 주민번호 7번째 자리	Blank 허용                                                                              
				입금은행명	문자열		BANK_NAME	은행입금 시 입금할 은행명	Blank 허용                                                                                      
				입금계좌	문자열		BANK_ACCOUNT	은행입금 시 입금할 은행 계좌 번호	Blank 허용                                                                              
				주문발생유형	숫자		ORD_TYPE	1:SKT => 제휴몰2:제휴몰 => SKT	제휴몰을 통한 주문(베너방식) 인지 SKT를 통한 (모바일카트구매) 방식인지 여부                     
				판매 유형	숫자	1		PRDCATEGORY_CODE	1:모바일카트2:모바일홈쇼핑3:SEA 	                                                                        
				상품코드	문자열		ITEM_CODE		ARRAY                                                                                                                   
				주문수량	숫자			ORDER_QTY		ARRAY                                                                                                           
				옵션(단품코 드)	문자열		UNIT_CODE		ARRAY                                                                                                                   
				상품명	문자열		ITEM_NAME		ARRAY                                                                                                                           
				상품가격	숫자			ITEM_PRICE		ARRAY                                                                                                           
				상태	숫자				ORDER_STATE	주문접수 : 10결제완료 : 20배송완료 : 40취소/반품 : -10	ARRAY                                                           
			 *                                                                                                                                                                                      
			 */                                                                                                                                                                                     
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                                                    
		// 요청데이터 분석 및 응답처리                                                                                                                                                                                  
		try {                                                                                                                                                                                                   
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                                       
			/*Request parameter 처리*/                                                                                                                                                                      
			String TARGETDATE = responsor.getRequestDataString(0,"TARGETDATE");                                                                                                                             
			///log.debug("TARGETDATE======================>" + TARGETDATE);                                                                                                                                                                                                            
			List listEaiOrdsync = commerceMgr.listEaiOrdsync(TARGETDATE);                                                                                                                                   
			//log.debug("리스트======================>" + listEaiOrdsync);                                                                                                                                    
						                                                                                                                                                                        
			int dataCnt = listEaiOrdsync.size(); // 쿼리 결과에 수량이 정해짐                                                                                                                               
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
						return responsor.getErrorXmlData("처리중 에러 발생 했습니다.", isEncryptResponse,key);                                                                                  
				}                                                                                                                                                                                       
		}                                                                                                                                                                                               
                  
	private void EAI_ORDRECV(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                               
		/*                                                                                                                                                                                  
		Request                                                                                                                                                                                 
			요청일	문자열		TARGETDATE	YYYYMMDD주문발생일                                                                                                                                    
		Response                                                                                                                                                                                
			주문 번호	문자열		MC_ORDER_NO	M-Cart 주문 번호	                                                                                                        
			주문 번호	문자열		TP_ORDER_NO	제휴몰 주문 번호	                                                                                                        
			결제 금액	숫자		TOT_RSALE_AMT	결제 금액	                                                                                                                
			결제 수단	숫자		PAY_CARD_AMT	신용카드결제금액	Blank 허용                                                                                              
					숫자		PAY_ONLINE_AMT	무통장결제금액	Blank 허용                                                                                                      
					숫자		PAY_POINT_AMT	포인트 결제금액	Blank 허용                                                                                                      
					숫자		PAY_COUPON_AMT	쿠폰 결제금액	Blank 허용                                                                                                      
			주문인명	문자열		ORD_NAME	주문자명	Blank 허용                                                                                                      
			회원유형값 	문자열	3	MEMCATEGORY	주문번호 앞두자리 + 주민번호 7번째 자리	Blank 허용                                                                              
			입금은행명	문자열		BANK_NAME	은행입금 시 입금할 은행명	Blank 허용                                                                                      
			입금계좌	문자열		BANK_ACCOUNT	은행입금 시 입금할 은행 계좌 번호	Blank 허용                                                                              
			주문발생유형	숫자		ORD_TYPE	1:SKT => 제휴몰2:제휴몰 => SKT	제휴몰을 통한 주문(베너방식) 인지 SKT를 통한 (모바일카트구매) 방식인지 여부                     
			판매 유형	숫자	1	PRDCATEGORY_CODE	1:모바일카트2:모바일홈쇼핑3:SEA 	                                                                                
			상품코드	문자열		ITEM_CODE		ARRAY                                                                                                                   
			주문수량	숫자		ORDER_QTY		ARRAY                                                                                                                   
			옵션(단품코 드)	문자열		UNIT_CODE		ARRAY                                                                                                                   
			상품명	문자열		ITEM_NAME		ARRAY                                                                                                                           
			상품가격	숫자		ITEM_PRICE		ARRAY                                                                                                                   
			상태	숫자		ORDER_STATE	주문접수 : 10결제완료 : 20배송완료 : 40취소/반품 : -10	ARRAY                                                                                                       
		 */                                                                                                                                                                                     
		EAIRequestor requestor = null;  
		requestor = EAIProcessorFactory.createEAIRequestor(logger, businessCd, tpCode);  
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode); 
       
		// 요청데이터 분석 및 응답처리                                                                                                                                                          
		try {                                                                                                                                                                                   
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                       
			/*Request parameter 처리*/                                                                                                                                                      
			String TARGETDATE = responsor.getRequestDataString(0,"TARGETDATE"); 
			
	                                                                                                                                                                                                                     
			List listEaiOrdrcv = commerceMgr.listEaiOrdrcv(TARGETDATE);                                                                                                                                   
			//log.debug("리스트======================>" + listEaiOrdrcv);                                                                                                                                    
						                                                                                                                                                                        
			int dataCnt = listEaiOrdrcv.size(); // 쿼리 결과에 수량이 정해짐                                                                                                                               
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
				 
				
				int itemDataSize = 1; // 주문 상품 수 만큼                                                                                                                              
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
                                                                                                                                                                                                                
			/*응답 처리*/                                                                                                                                                                   
			                                                                                                                                                                                
		} catch (Exception e) {                                                                                                                                                                 
				throw new Exception();                                                                                                                                                  
		}                                                                                                                                                                                       
	}             
                                                                                                                                                                                                                        
		private String EAI_ORDCONFIRM(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                          
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				Request                                                                                                                                                                                 
					주문 번호	문자열	20	TARGETDATE	대상일                                                                                                                                                               
				Response                                                                                                                                                                                
					주문 번호	문자열	20	TP_ORDER_NO	제휴몰 주문번호	                                                                                                                
					주문 번호	문자열	20	MC_ORDER_NO	M-CART 주문 번호	                                                                                                        
					상품 번호	문자열		ITEM_CODE	제휴몰 상품 번호	                                                                                                        
					상품 수량	숫자		ORDER_QTY		                                                                                                                        
					상태	숫자		ORDER_STATE	주문접수 : 10결제완료 : 20배송완료 : 40취소/반품 : -10	                                                                                
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// 요청데이터 분석 및 응답처리                                                                                                                                                          
				try {                                                                                                                                                                                           
					responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                                       
					/*Request parameter 처리*/                                                                                                                                                      
					String TARGETDATE = responsor.getRequestDataString(0,"TARGETDATE"); 
					//log.debug("TARGETDATE======================>" + TARGETDATE); 
					List listEaiOrdconfirm = commerceMgr.listEaiOrdconfirm(TARGETDATE);                                                                                                                   
					//log.debug("리스트======================>" + listEaiOrdconfirm);                                                                                                                    
								                                                                                                                                                                
					int dataCnt = listEaiOrdconfirm.size(); // 쿼리 결과에 수량이 정해짐                                                                                                               
						                                                                                                                                                                                
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
				return responsor.getErrorXmlData("처리중 에러 발생 했습니다.", isEncryptResponse,key);                                                                          
			  }                                                                                                                                                                                       
		}     
	
	private String EAI_AIRSCHEDULE(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                         
				/*                                                                                                                                                                                      
				 * 결재 완료시 해당 정보를 SKT 에 전송 한다.                                                                                                                                            
				Request                                                                                                                                                                                 
					방송검색일 시작	문자열	8	TARGETDATE_ST	YYYYMMDD	                                                                                                                
					방송검색일 종료	문자열	8	TARGETDATE_END	YYYYMMDD	                                                                                                                
                                                                                                                                                                                                                        
				Response                                                                                                                                                                                
					방송번호	문자열	30	TPAIR_NO		                                                                                                                        
					방송제목	문자열		AIR_NAME		                                                                                                                        
					사용시작일	문자열		START_DATE	YYYYMMDD HHMMSS	                                                                                                                
					사용종료일	문자열		END_DATE	YYYYMMDD HHMMSS	                                                                                                                
					상품 코드	문자열	20	ITEM_CODE		                                                                                                                        
					상품 상태	숫자	1	SALE_STATE	1:판매중2:품절(진열됨)3:판매중지4.일시중지(일시재고 부족)5:방송 시간대만 판매6:시작일 이후 판매	                                
                 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// 요청데이터 분석 및 응답처리                                                                                                                                                          
		try {                                                                                                                                                                                   
				responsor.analyzeRequest(xmlData, isEncryptRequest, key); 
				
				/*Request parameter 처리*/                                                                                                                                              
				String TARGETDATE_ST  = responsor.getRequestDataString(0,"TARGETDATE_ST");                                                                                               
				String TARGETDATE_END = responsor.getRequestDataString(0,"TARGETDATE_END");
				
				List listEaiAirSchedule = commerceMgr.listEaiAirSchedule(TARGETDATE_ST,TARGETDATE_END);  
				
				int dataCnt = listEaiAirSchedule.size(); // 쿼리 결과에 수량이 정해짐                                                                                                   
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
							return responsor.getErrorXmlData("처리중 에러 발생 했습니다.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
			                                                                                                                                                         

	/* ----------------------------------------------------------------------------                                                                                                                                                                                     
	 *                                                                                                                                                                                      
		 Request
			검색어	문자열	30	KEYWORD		
			검색 종류	숫자		SRCH_GB	0:키워드1:상품명검색 2:상품코드 검색3:제조사명 검색4:모델번호 검색5:상품평	
			최소 가격	문자열		PRICE_MIN	일반 검색 최소 가격	
			최대 가격	문자열		PRICE_MAX	일반 검색 최대 가격	
			정렬 유형 	숫자		SORT_TP	1 : 상품명순2 : 제조사순3 : 가격순4 : 인기순	
			페이지 번호	숫자	3	PAGE_NO	1 이상의 정수	
			페이지사이즈	숫자		FETCH_CNT	가져올 개수	

		Response
			검색결과 수	숫자	5	SEARCH_CNT		
			상품코드	문자열	9	ITEM_CODE		ARRAY
			상품명	문자열	128	ITEM_NAME		ARRAY
			제조 업체	문자열	60	MAKECO_NAME		Blank 허용 : ARRAY
			가격	숫자		ITEM_PRICE		ARRAY
		 *                                                                                                                                 
	 *                                                                                                                                                                                      
	 ----------------------------------------------------------------------------------*/      
	private String EAI_ITEMSRCH(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                                            
		log.debug("entering 'EAI_ITEMSRCH' method...");	
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
	                                                                                                                                                                                                            
		// 요청데이터 분석 및 응답처리                                                                                                                                                          
		try {                                                                                                                                                                                   
			
			responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
			/*Request parameter 처리*/                                                                                                                                              
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
			int FETCH_CNT = responsor.getRequestDataInt(0,"FETCH_CNT");  //페이지 사이즈   
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
			int dataCnt = 0; // Fetch_Cnt 를 넘지 않는 쿼리 개수
			
			SearchCriteria criteria = new SearchCriteria();
			criteria.setQuery(KEYWORD);
			criteria.setResearchQuery("");  // 결과 내 재검색 쿼리
			criteria.setResearchFilter("");  // 결과 내 재검색 필터
			if(PRICE_MIN >0 && PRICE_MAX >0){
				criteria.setMinPrice(PRICE_MIN+"");
				criteria.setMaxPrice(PRICE_MAX+"");
			}	
			criteria.setStartNo((PAGE_NO - 1) * FETCH_CNT + 1); // 시작페이지
			criteria.setListSize(FETCH_CNT); // 페이지 사이즈

			if(SRCH_GB == 2) {
				isSearchItemCode = true;
			} 				
			if(SRCH_GB == 3) {
				makeco_name = KEYWORD;
			}				
			if(SRCH_GB == 5) {//상품평이 0개 이상인 상품만 
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

			/*응답 처리*/ 
		 
			int searchCnt = resultSet.getItemsSize(); // 검색 결과 수 
			log.debug("searchCnt..."+resultSet.getItemsSize());
			int dataCnt1 = 1; // 1 고정 - 루핑은 한번만 !!!
			responsor.initResponseList(dataCnt1);
			for (int i = 0 ; i < dataCnt1 ; i++){
			responsor.setResponseData(0,"SEARCH_CNT",resultSet.getTotalSize()); // 원래의 검색 결과 갯수   
			 
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
				return responsor.getErrorXmlData("처리중 에러 발생 했습니다."+e.toString(), isEncryptResponse,key);                                                                          
		}                                                                                                                                                                                       
	}                                                                                                                                                                                               
                                                                                                                                                                                                                        
	
	/*------------------------------------------------------------------------------
	 * 
	 Request
		검색어	문자열	30	KEYWORD		
		검색 종류	숫자		SRCH_GB	0:키워드1:상품명검색 2:상품코드 검색3:제조사명 검색4:모델번호 검색5:상품평	
		최소 가격	문자열		PRICE_MIN	일반 검색 최소 가격	
		최대 가격	문자열		PRICE_MAX	일반 검색 최대 가격	

	Response
		검색결과 수	숫자	5	SEARCH_CNT				 * 
	 ----------------------------------------------------------------------------------*/
	private String EAI_ITEMSRCH_CNT(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{
		log.debug("entering 'EAI_ITEMSRCH_CNT' method...");
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);

		// 요청데이터 분석 및 응답처리
		try {
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);
				/*Request parameter 처리*/
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
				int dataCnt = 0; // Fetch_Cnt 를 넘지 않는 쿼리 개수
				
				SearchCriteria criteria = new SearchCriteria();
				criteria.setQuery(KEYWORD);
				criteria.setResearchQuery("");  // 결과 내 재검색 쿼리
				criteria.setResearchFilter("");  // 결과 내 재검색 필터
				if(PRICE_MIN >0 && PRICE_MAX >0){
					criteria.setMinPrice(PRICE_MIN+"");
					criteria.setMaxPrice(PRICE_MAX+"");
				}	
				criteria.setStartNo(1); // 시작페이지
				criteria.setListSize(100); // 페이지 사이즈 
				if(SRCH_GB == 2) {
					isSearchItemCode = true;
				} 				
				if(SRCH_GB == 3) {
					makeco_name = KEYWORD;
				}
				if(SRCH_GB == 5) {//상품평이 0개 이상인 상품만 
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

				/*응답 처리*/ 
				dataCnt = resultSet.getTotalSize();
				//log.debug("dataCnt..."+dataCnt);
				responsor.initResponseList(1);
				responsor.setResponseData(0,"SEARCH_CNT",dataCnt+"");

				return responsor.doResponse("",isEncryptResponse,key);
	
			} catch (Exception e) {
					return responsor.getErrorXmlData("처리중 에러 발생 했습니다."+e.toString(), isEncryptResponse,key);
		}
	}
	
	/*------------------------------------------------------------
	 * 
	 Request
		상품 코드	문자열	20	ITEM_CODE	제휴몰 상품번호	
		가져올개수	숫자		FETCH_CNT	가져올 개수	
	 
	Response
		제목 코드	문자열	20	BBS_NO	게시물번호	
		제목	문자열	200	SUBJECT	상품평제목	
		순서	숫자	3	ARRANGE	정렬 순서	
	 * 
	 -------------------------------------------------------------*/
	private String EAI_ITEMEVALLST(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{
		log.debug("entering 'EAI_ITEMEVALLST' method...");
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);

		// 요청데이터 분석 및 응답처리
		try {
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);
				/*Request parameter 처리*/
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
					return responsor.getErrorXmlData("처리중 에러 발생 했습니다."+e.toString(), isEncryptResponse,key);
		}
	}	                                                                                                                                                               
 
	
	/*-----------------------------------------------------------------------
	 * 
	 Request
		상품 코드	문자열	9	ITEM_CODE		
		제목 코드	문자열	9	BBS_NO		
	 
	Response
		제목	문자열	200	SUBJECT	상품평제목	
		내용	문자열	4000	CONTENTS	가능한 최대값 설정	
		고객명	문자열		CUST_NAME	고객명/등록자명	Blank 허용
		날짜	문자열		CRT_DM	생성일자(yyyyMMdd)	
		점수	숫자		CONT_PNT	부여 점수백분율 표현 0 ~ 100	
	 * 
	 -------------------------------------------------------------------------*/
	private String EAI_ITEMEVAL(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{
		log.debug("entering 'EAI_ITEMEVAL' method...");
		EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);

		// 요청데이터 분석 및 응답처리
		try {
				responsor.analyzeRequest(xmlData, isEncryptRequest, key);
				/*Request parameter 처리*/
				String item_code = responsor.getRequestDataString(0,"ITEM_CODE");
				String entryid = responsor.getRequestDataString(0,"BBS_NO");
				Blog blog = new Blog();
				blog = commerceMgr.readBentry(item_code,entryid);			

				/*응답 처리*/
				int dataCnt = 1; // 1개 고정
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
					return responsor.getErrorXmlData("처리중 에러 발생 했습니다."+e.toString(), isEncryptResponse,key);
		}
	}	                                                                                                                                                       
                                                                                                                                                                                                                        
                                                                                                                                                                                                                        
			private String EAI_EVENT_UPD(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                           
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				업체 EVENT ID	문자열	40	TPEVENT_ID	                                                                                                                                        
				이벤트 명	문자열	100	EVENT_NM	                                                                                                                                        
				이벤트 시작일	문자열	14	EVENT_ST_DM	YYYYMMDDHHMMSS                                                                                                                          
				이벤트 종료일	문자열	14	EVENT_END_DM	YYYYMMDDHHMMSS                                                                                                                          
				이벤트 URL	문자열	300	EVENT_URL	http: 를 포함한 절대 URL                                                                                                                
				정렬순서	숫자		ARRANGE	1 ~ N                                                                                                                                           
				진열여부	숫자	1	ISACTIVE	1 : 진열, 0 : 미진열                                                                                                                    
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
					EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                    
                                                                                                                                                                                                                        
					// 요청데이터 분석 및 응답처리                                                                                                                                                  
					try {                                                                                                                                                                           
							responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                       
							/*Request parameter 처리*/                                                                                                                                      
							// Request 없음                                                                                                                                                 
                                                                                                                                                                                                                        
							/*응답 처리*/                                                                                                                                                   
							int dataCnt = 2; // 쿼리 결과에 수량이 정해짐                                                                                                                   
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
								return responsor.getErrorXmlData("처리중 에러 발생 했습니다.", isEncryptResponse,key);                                                                  
						}                                                                                                                                                                       
					}                                                                                                                                                                               
                                                                                                                                                                                                                        
			                                                                                                                                                                                                
			                                                                                                                                                                              
                                                                                                                                                                                                                        
			private String EAI_COUPONINFO(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                          
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				Request                                                                                                                                                                                 
					고객번호	문자열	30	CUST_NO	                                                                                                                                        
					가져올개수	문자열		FETCH_CNT	                                                                                                                                
			                                                                                                                                                                                                
				Response                                                                                                                                                                                
					적립금	숫자		SAVEAMT		                                                                                                                                        
					쿠폰번호	문자열		SEQ		ARRAY                                                                                                                           
					쿠폰명	문자열		COUPON_NAME		ARRAY                                                                                                                           
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// 요청데이터 분석 및 응답처리                                                                                                                                                          
				try {                                                                                                                                                                                   
						responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
						/*Request parameter 처리*/                                                                                                                                              
						String CUST_NO = responsor.getRequestDataString(0,"CUST_NO");                                                                                                           
						int FETCH_CNT = responsor.getRequestDataInt(0,"FETCH_CNT");                                                                                                             
                                                                                                                                                                                                                        
						/*응답 처리*/                                                                                                                                                           
						int dataCnt = 4; // Query 개수 또는 FecthCnt                                                                                                                            
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
							return responsor.getErrorXmlData("처리중 에러 발생 했습니다.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
                                                                                                                                                                                                                        
			private String EAI_COUPONINFODT(String xmlData,String businessCd,boolean isEncryptRequest, boolean isEncryptResponse ) throws Exception{                                                        
				/*                                                                                                                                                                                      
				 *                                                                                                                                                                                      
				Request                                                                                                                                                                                 
					고객번호	문자열	30	CUST_NO	                                                                                                                                        
					쿠폰SEQ	문자열		SEQ	                                                                                                                                                
			                                                                                                                                                                                                
				Response                                                                                                                                                                                
					고객번호	문자열	30	CUST_NO	제휴몰 고객번호	                                                                                                                        
					쿠폰번호	문자열		COUPON_NO		                                                                                                                        
					쿠폰명	문자열		COUPON_NAME		                                                                                                                                
					할인금액 또는 할인률	문자열		COUPON_DC_AMT	1000원5%	                                                                                                        
					사용시작일	문자열		START_DATE	YYYYMMDD	                                                                                                                
					사용종료일	문자열		END_DATE	YYYYMMDD	                                                                                                                
					쿠폰적용상품	문자열		COUPON_APPLY	전상품(일부제외)특정상품적용	Blank 허용                                                                                      
					사용가능금액	숫자		USE_PRICE	1,000이상	                                                                                                                
				 *                                                                                                                                                                                      
				 */                                                                                                                                                                                     
				EAIResponsor responsor = EAIProcessorFactory.createEAIResponsor(logger, businessCd, tpCode);                                                                                            
                                                                                                                                                                                                                        
				// 요청데이터 분석 및 응답처리                                                                                                                                                          
				try {                                                                                                                                                                                   
						responsor.analyzeRequest(xmlData, isEncryptRequest, key);                                                                                                               
						/*Request parameter 처리*/                                                                                                                                              
						String CUST_NO = responsor.getRequestDataString(0,"CUST_NO");                                                                                                           
						String SEQ = responsor.getRequestDataString(0,"SEQ");                                                                                                                   
                                                                                                                                                                                                                        
						/*응답 처리*/                                                                                                                                                           
						int dataCnt = 1; // 한개 고정                                                                                                                                           
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
							return responsor.getErrorXmlData("처리중 에러 발생 했습니다.", isEncryptResponse,key);                                                                          
				}                                                                                                                                                                                       
			}                                                                                                                                                                                               
              
			// HTML로 읽어옴.
			private String getBlogHtmlContent(String contextpath) {
     			String reqUrl = "http://wibro.hyundaihmall.com/front/wbBlogHtmlR.do?Htmlfilename="+contextpath;
				return HttpUtils.getHtml(reqUrl);
			}					                                                                                                                                                                                                
}                                                                                                                                                                                                                       