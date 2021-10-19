package com.hmall.web.htv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.web.bind.RequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

import com.hmall.exception.ServiceException;
import com.hmall.exception.XmlException;
import com.hmall.model.common.ItemTagCode;
import com.hmall.model.common.MakeComp;
import com.hmall.model.display.BrandBigBanner;
import com.hmall.model.display.ItemStatus;
import com.hmall.model.display.KinEntry;
import com.hmall.model.display.Sect;
import com.hmall.model.item.CompXml;
import com.hmall.model.item.CompXmlDtl;
import com.hmall.model.item.Item;
import com.hmall.model.item.ItemDetail;
import com.hmall.model.item.ItemPrice;
import com.hmall.model.item.Mpeg;
import com.hmall.model.item.NetworkItemOur;
import com.hmall.model.item.Product;
import com.hmall.model.promotion.GiftPromom;
import com.hmall.model.event.SectEvent;

import com.hmall.service.HtvMgr;
import com.hmall.service.Ch3DMgr;
import com.hmall.service.ShopMgr;
import com.hmall.service.HomeShopMgr;
import com.hmall.service.EventMgr;
import com.hmall.util.BaseUtils;
import com.hmall.util.HttpUtils;
import com.hmall.util.PageHolder;
import com.hmall.util.ewrap.SendMailUtil;
import com.hmall.util.ewrap.WebLogicUtil;
import com.hmall.util.search.Filter;
import com.hmall.util.search.SearchCriteria;
import com.hmall.util.search.Searcher;
import com.hmall.util.ewrap.WebLogicUtil;

/**
 * @author Han Sung Hoon (2006.11.01)
 */
public class Ch3DController extends MultiActionController {

	private final Log log = LogFactory.getLog(getClass());  

	private ShopMgr shopMgr; 
	
	private HtvMgr htvMgr;

	private Ch3DMgr ch3DMgr;	
	
	private HomeShopMgr homeShopMgr;		
	
	private EventMgr eventMgr;
	
	public void setShopMgr(ShopMgr shopMgr) {  
		this.shopMgr = shopMgr;
	}
	
	public void setHtvMgr(HtvMgr htvMgr) { 
		this.htvMgr = htvMgr;
	}

	public void setCh3DMgr(Ch3DMgr ch3DMgr) { 
		this.ch3DMgr = ch3DMgr;
	}	

	public void setHomeShopMgr(HomeShopMgr homeShopMgr) { 
		this.homeShopMgr = homeShopMgr;
	}		

	public void setEventMgr(EventMgr eventMgr) {
		this.eventMgr = eventMgr;
	}

	/**
	 * 메인페이지에 나타낼 각종 상품들의 목록을 가져온다.
	 * 작성일시 : 2008.06.10 
	 * 작성자   : 한성훈 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView mainPageItemL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'mainPageItemL' method...");
		Map model = new HashMap();
		String mode = request.getParameter("mode");
		if(mode == null || mode.equals("")) { mode = "local"; }
		
		if(mode.equals("preview"))
		{
			//서버에 파일을 만들고, 읽어온다.
			loadMainPageItemList(mode, model);
		}else if(mode.equals("local")){
			if(checkLocalMainPageItemFile() == false)
			{
				//서버에 파일을 만들고, 읽어온다.
				loadMainPageItemList(mode, model);
				
			}else{
				//local에 저장되어 있는  xml을 로드하여 보여준다.
				return loadLocalMainPageItemList(model);
			}
		}else if(mode.equals("send")){
			loadMainPageItemList(mode, model);			
			sendMainPageItemXmlFile();
		}
		return new ModelAndView("mainPageItemL", model);
	}
	
	private void loadMainPageItemList(String mode, Map model) throws Exception
	{
		//main 화면 개편 (2012.10.22 - by aloysia)
		//Brand Zone - 기획전영역10(기획전영역  1~6)			
		List listSectEvent1 = ch3DMgr.listSectEvent(1000, "00", 6);
		
		List eventItemList1 = null;
		List eventItemList2 = null;
		List eventItemList3 = null;
		List eventItemList4 = null;
		List eventItemList5 = null;
		List eventItemList6 = null;
		
		String eventsect_id1 = "";
		String eventsect_id2 = "";
		String eventsect_id3 = "";
		String eventsect_id4 = "";
		String eventsect_id5 = "";
		String eventsect_id6 = "";
		
		log.debug("listSectEvent.size() ====>" + listSectEvent1.size());

		//log.debug("===================================================================================");
		//log.debug("대분류 매장별 기획전 4번 영역 데이터 상품 목록을 가져온다.");
		if (listSectEvent1 != null && listSectEvent1.size() > 0) {
			for (int i = 0; listSectEvent1 != null && i < listSectEvent1.size(); i++) {
				SectEvent event = (SectEvent) listSectEvent1.get(i);
				if (i==0)  { 
					eventsect_id1  = event.getEventsect_id().toString();			
				} else if (i==1) {
					eventsect_id2  = event.getEventsect_id().toString();
				} else if (i==2) {
					eventsect_id3  = event.getEventsect_id().toString();
				} else if (i==3) {
					eventsect_id4  = event.getEventsect_id().toString();
				} else if (i==4) {
					eventsect_id5  = event.getEventsect_id().toString();
				} else if (i==5) {
					eventsect_id6  = event.getEventsect_id().toString();	
				}
			} 
		}
	
		if ( eventsect_id1 != null && !eventsect_id1.equals(""))  {
			eventItemList1 = ch3DMgr.listMainEventItem(Long.parseLong(eventsect_id1), 4);
		} 
	
		if ( eventsect_id2 != null && !eventsect_id2.equals(""))  {
			eventItemList2 = ch3DMgr.listMainEventItem(Long.parseLong(eventsect_id2), 4);
		} 
		
		if ( eventsect_id3 != null && !eventsect_id3.equals(""))  {
			eventItemList3 = ch3DMgr.listMainEventItem(Long.parseLong(eventsect_id3), 4);
		} 
		
		if ( eventsect_id4 != null && !eventsect_id4.equals(""))  {
			eventItemList4 = ch3DMgr.listMainEventItem(Long.parseLong(eventsect_id4), 4);
		} 
		
		if ( eventsect_id5 != null && !eventsect_id5.equals(""))  {
			eventItemList5 = ch3DMgr.listMainEventItem(Long.parseLong(eventsect_id5), 4);
		} 
		
		if ( eventsect_id6 != null && !eventsect_id6.equals(""))  {
			eventItemList6 = ch3DMgr.listMainEventItem(Long.parseLong(eventsect_id6), 4);
		} 
			
		if (eventItemList1!=null){
			model.put("eventItemList1", eventItemList1);
		}
		if (eventItemList2!=null){
			model.put("eventItemList2", eventItemList2);
		}
		if (eventItemList3!=null){
			model.put("eventItemList3", eventItemList3);
		}
		if (eventItemList4!=null){
			model.put("eventItemList4", eventItemList4);
		}
		if (eventItemList5!=null){
			model.put("eventItemList5", eventItemList5);
		}
		if (eventItemList6!=null){
			model.put("eventItemList6", eventItemList6);
		}

		//현대백화점  ///////////////////////////////////
		//영캐주얼 -상품추천-HOT ITEM 1영역
		List mainItemList1 = ch3DMgr.listMainPageItemB(1005, "01", "1", 6);
		model.put("mainItemList1",mainItemList1);
		
		//진/유니섹스 -상품추천-HOT ITEM 2영역
		List mainItemList2 = ch3DMgr.listMainPageItemB(1005, "01", "2", 6);
		model.put("mainItemList2",mainItemList2);
		
		//여성의류/란제리 -상품추천-HOT ITEM 3영역
		List mainItemList3 = ch3DMgr.listMainPageItemB(1005, "01", "3", 6);
		model.put("mainItemList3",mainItemList3);
		
		//남성정장/셔츠 -상품추천-HOT ITEM 4영역
		List mainItemList4 = ch3DMgr.listMainPageItemB(1005, "01", "4", 6);
		model.put("mainItemList4",mainItemList4);
		
		//남성캐주얼 -상품추천-MD추천상품  1 영역
		List mainItemList5 = ch3DMgr.listMainPageItemB(1005, "07", "1", 6);
		model.put("mainItemList5",mainItemList5);
		
		//명품화장품 -상품추천-MD추천상품  2 영역
		List mainItemList6 = ch3DMgr.listMainPageItemB(1005, "07", "2", 6);
		model.put("mainItemList6",mainItemList6);
		
		//핸드백/지갑 -상품추천-MD추천상품  3 영역
		List mainItemList7 = ch3DMgr.listMainPageItemB(1005, "07", "3", 6);
		model.put("mainItemList7",mainItemList7);
		
		//구두/잡화 -상품추천-MD추천상품  4영역
		List mainItemList8 = ch3DMgr.listMainPageItemB(1005, "07", "4", 6);
		model.put("mainItemList8",mainItemList8);		

		//아동/유아 -상품추천-특가상품 1 영역 
		List mainItemList9 = ch3DMgr.listMainPageItemB(1005, "02", "1", 6);
		model.put("mainItemList9",mainItemList9);
		
		//스포츠/아웃도어 -상품추천-특가상품 2 영역 
		List mainItemList10 = ch3DMgr.listMainPageItemB(1005, "02", "2", 6);
		model.put("mainItemList10",mainItemList10);
		
		//홈리빙 -상품추천-특가상품 3영역 
		List mainItemList11 = ch3DMgr.listMainPageItemB(1005, "02", "3", 6);
		model.put("mainItemList11",mainItemList11);
		
		//식품 -상품추천-특가상품 4 영역
		List mainItemList12 = ch3DMgr.listMainPageItemB(1005, "02", "4", 6);
		model.put("mainItemList12",mainItemList12);		
		
		//TV현대홈쇼핑
		//상품추천-HOT ITEM 1 영역 
		List mainItemList13 = ch3DMgr.listMainPageItemB(110559, "01", "1", 9);
		model.put("mainItemList13",mainItemList13);	
			
		//하단 영역 공통 ////////////////////////////////////////////////////////
	    //패션의류 - 기획전영역1(3개)
		List listSectEvent21 = ch3DMgr.listSectEvent(1000, "10", 3);
		model.put("listSectEvent21", listSectEvent21);
		
		//패션의류 - 추천상품  -HOT ITEM 1 영역(6개)
		List mainItemList21 = ch3DMgr.listMainPageItemB(1000, "01", "1", 6);
		model.put("mainItemList21",mainItemList21);
		
		//명품/잡화 - 기획전영역2(3개)
		List listSectEvent22 = ch3DMgr.listSectEvent(1000, "20", 3);
		model.put("listSectEvent22", listSectEvent22);
		
		//명품/잡화 - 추천상품  -HOT ITEM 2 영역(6개)
		List mainItemList22 = ch3DMgr.listMainPageItemB(1000, "01", "2", 6);
		model.put("mainItemList22",mainItemList22);
			
		//화장품 - 기획전영역3(3개)
		List listSectEvent23 = ch3DMgr.listSectEvent(1000, "30", 3);
		model.put("listSectEvent23", listSectEvent23);
		
		//화장품 - 추천상품  -HOT ITEM 3 영역(6개)
		List mainItemList23 = ch3DMgr.listMainPageItemB(1000, "01", "3", 6);
		model.put("mainItemList23",mainItemList23);

		//유아동/문화 - 기획전영역4(3개)
		List listSectEvent24 = ch3DMgr.listSectEvent(1000, "40", 3);
		model.put("listSectEvent24", listSectEvent24);
		
		//유아동/문화 - 추천상품  -HOT ITEM 4 영역(6개)
		List mainItemList24 = ch3DMgr.listMainPageItemB(1000, "01", "4", 6);
		model.put("mainItemList24",mainItemList24);
				
		//스포츠/레저 - 기획전영역5(3개)
		List listSectEvent25 = ch3DMgr.listSectEvent(1000, "50", 3);
		model.put("listSectEvent25", listSectEvent25);
		
		//스포츠/레저 - 추천상품  -MD추천상품 1 영역(6개)
		List mainItemList25 = ch3DMgr.listMainPageItemB(1000, "07", "1", 6);
		model.put("mainItemList25",mainItemList25);
						
		//식품/주방 - 기획전영역6(3개)
		List listSectEvent26 = ch3DMgr.listSectEvent(1000, "60", 3);
		model.put("listSectEvent26", listSectEvent26);
		
		//식품/주방 - 추천상품  -MD추천상품 2 영역(6개)
		List mainItemList26 = ch3DMgr.listMainPageItemB(1000, "07", "2", 6);
		model.put("mainItemList26",mainItemList26);
		
		//가구/생활 - 기획전영역7(3개)
		List listSectEvent27 = ch3DMgr.listSectEvent(1000, "70", 3);
		model.put("listSectEvent27", listSectEvent27);
		
		//가구/생활 - 추천상품  -MD추천상품 3 영역(6개)
		List mainItemList27 = ch3DMgr.listMainPageItemB(1000, "07", "3", 6);
		model.put("mainItemList27",mainItemList27);
		
		//가전/컴퓨터 - 기획전영역8(3개)
		List listSectEvent28 = ch3DMgr.listSectEvent(1000, "80", 3);
		model.put("listSectEvent28", listSectEvent28);
				
		//가구/컴퓨터 - 추천상품  -MD추천상품 4 영역(6개)
		List mainItemList28 = ch3DMgr.listMainPageItemB(1000, "07", "4", 6);
		model.put("mainItemList28",mainItemList28);
		
	} 
	
	private ModelAndView loadLocalMainPageItemList(Map model)
	{
		return new ModelAndView("mainPageItemLocalL", model);
	}

	private boolean checkLocalMainPageItemFile()
	{
		boolean result = false;
		File file = new File(HttpUtils.getProperty("ch3d_category_path") + "mainF.xml");
		return file.exists();
	}	

	private void sendMainPageItemXmlFile()
	{
		//FTP로 전송한다.
		String[] server = new String[5];
		String[] user = new String[5];
		String[] password = new String[5];
		String[] path = new String[5];

		server[0] = HttpUtils.getProperty("etv_srv_1");
	    user[0] = HttpUtils.getProperty("etv_userid_1");
	    password[0] = HttpUtils.getProperty("etv_passwd_1");
	    path[0] = HttpUtils.getProperty("xml_path_1");
	    
	    server[1] = HttpUtils.getProperty("etv_srv_2");
	    user[1] = HttpUtils.getProperty("etv_userid_2");
	    password[1] = HttpUtils.getProperty("etv_passwd_2");
	    path[1] = HttpUtils.getProperty("xml_path_2");
	    
	    server[2] = HttpUtils.getProperty("etv_srv_3");
	    user[2] = HttpUtils.getProperty("etv_userid_3");
	    password[2] = HttpUtils.getProperty("etv_passwd_3");
	    path[2] = HttpUtils.getProperty("xml_path_3");
	    
	    server[3] = HttpUtils.getProperty("etv_srv_4");
	    user[3] = HttpUtils.getProperty("etv_userid_4");
	    password[3] = HttpUtils.getProperty("etv_passwd_4");
	    path[3] = HttpUtils.getProperty("xml_path_4");
	    
	    server[4] = HttpUtils.getProperty("etv_srv_5");
	    user[4] = HttpUtils.getProperty("etv_userid_5");
	    password[4] = HttpUtils.getProperty("etv_passwd_5");
	    path[4] = HttpUtils.getProperty("xml_path_5");
	    
	    String filename = HttpUtils.getProperty("ch3d_category_path") + "mainF.xml";
	    

/**
* FTP 파일 업로드
*/
	    
	    try 
		{
	    	for(int j = 0 ; j < 5 ; j++)
	    	{
		    	if(server[j] != null && !server[j].equals(""))
		    	{
		    		FtpClient ftpClient=new FtpClient();
			    	ftpClient.openServer(server[j]);
			    	ftpClient.login(user[j], password[j]);	
			    	if (path[j].length()!=0) ftpClient.cd(path[j]);	
			    	//ftpClient.binary();
			    	ftpClient.ascii();		
			    		
			    	// 개편될 FTP 
			    	TelnetOutputStream os=ftpClient.put("mainF.xml");
			    	File file_in=new File(HttpUtils.getProperty("ch3d_category_path") + "mainF.xml");
			    	FileInputStream is=new FileInputStream(file_in);
			    	byte[] bytes=new byte[1024];
			    	int c;
			    	while ((c=is.read(bytes))!=-1){
			    		os.write(bytes,0,c);
			    	}
			    	is.close();
			    	os.close();
			    	
			    	
			    	ftpClient.closeServer();
		    	}
	    	}
		} catch (IOException ex) 
		{log.debug(ex);}
	}	
	
	/**
	 * 총나타낼수 있는 쿠키 테스트.
	 * 작성일시 : 2008.06.10 
	 * 작성자   : 한성훈 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	public ModelAndView cookieTest(HttpServletRequest request, HttpServletResponse response)throws Exception {

		log.debug("entering 'cookieTest' method...");
		Map model = new HashMap();
		String mode = request.getParameter("mode");
		if(mode == null || mode.equals("")) { mode = "local"; }
		
		loadMainPageItemList(mode, model);
		
		WebLogicUtil.setCookie(response, "Cookie01", "데이터01", -1);
		WebLogicUtil.setCookie(response, "Cookie02", "데이터02", -1);
		WebLogicUtil.setCookie(response, "Cookie03", "데이터03", -1);
		WebLogicUtil.setCookie(response, "Cookie04", "데이터04", -1);
		WebLogicUtil.setCookie(response, "Cookie05", "데이터05", -1);
		WebLogicUtil.setCookie(response, "Cookie06", "데이터06", -1);
		WebLogicUtil.setCookie(response, "Cookie07", "데이터07", -1);
		WebLogicUtil.setCookie(response, "Cookie08", "데이터08", -1);
		WebLogicUtil.setCookie(response, "Cookie09", "데이터09", -1);
		WebLogicUtil.setCookie(response, "Cookie10", "데이터10", -1);
		WebLogicUtil.setCookie(response, "Cookie11", "데이터11", -1);
		WebLogicUtil.setCookie(response, "Cookie12", "데이터12", -1);
		WebLogicUtil.setCookie(response, "Cookie13", "데이터13", -1);
		WebLogicUtil.setCookie(response, "Cookie14", "데이터14", -1);
		WebLogicUtil.setCookie(response, "Cookie15", "데이터15", -1);
		WebLogicUtil.setCookie(response, "Cookie16", "데이터16", -1);
		WebLogicUtil.setCookie(response, "Cookie17", "데이터17", -1);
		WebLogicUtil.setCookie(response, "Cookie18", "데이터18", -1);
		WebLogicUtil.setCookie(response, "Cookie19", "데이터19", -1);
		WebLogicUtil.setCookie(response, "Cookie20", "데이터20", -1);
		WebLogicUtil.setCookie(response, "Cookie21", "데이터21", -1);
		WebLogicUtil.setCookie(response, "Cookie22", "데이터22", -1);
		WebLogicUtil.setCookie(response, "Cookie23", "데이터23", -1);
		WebLogicUtil.setCookie(response, "Cookie24", "데이터24", -1);
		WebLogicUtil.setCookie(response, "Cookie25", "데이터25", -1);
		WebLogicUtil.setCookie(response, "Cookie26", "데이터26", -1);
		WebLogicUtil.setCookie(response, "Cookie27", "데이터27", -1);
		WebLogicUtil.setCookie(response, "Cookie28", "데이터28", -1);
		WebLogicUtil.setCookie(response, "Cookie29", "데이터29", -1);
		WebLogicUtil.setCookie(response, "Cookie30", "데이터30", -1);
		WebLogicUtil.setCookie(response, "Cookie31", "데이터31", -1);
		WebLogicUtil.setCookie(response, "Cookie32", "데이터32", -1);
		WebLogicUtil.setCookie(response, "Cookie33", "데이터33", -1);
		WebLogicUtil.setCookie(response, "Cookie34", "데이터34", -1);
		WebLogicUtil.setCookie(response, "Cookie35", "데이터35", -1);
		WebLogicUtil.setCookie(response, "Cookie36", "데이터36", -1);
		WebLogicUtil.setCookie(response, "Cookie37", "데이터37", -1);
		WebLogicUtil.setCookie(response, "Cookie38", "데이터38", -1);
		WebLogicUtil.setCookie(response, "Cookie39", "데이터39", -1);
		WebLogicUtil.setCookie(response, "Cookie40", "데이터40", -1);		
		
		return new ModelAndView("mainPageItemL", model);
	}	
	
	/**
	 * 3D매장 전체 카테고리 리스트를 불러온다.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView ch3DCategoryL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'ch3DCategoryL' method...");
		Map model = new HashMap();
		int sectId = Integer.parseInt(HttpUtils.getProperty("ch3d_root_sectid"));
		String mode = request.getParameter("mode");
		if(mode == null || mode.equals("")) { mode = "local"; }
		
		if(mode.equals("preview"))
		{
			//DB에 파일을 만들고, 읽어온다.
			loadDBCategory(sectId, model);
		}else if(mode.equals("local")){
			if(checkLocalCateFile() == false)
			{
				//DB에 파일을 만들고, 읽어온다.
				loadDBCategory(sectId, model);
			}else{
				//local xml을 로드한다.
				return loadLocalCategory(model);
			}
		}else if(mode.equals("send")){
			sendXmlFile();
		}
		
		return new ModelAndView("ch3DCategoryL", model);
		
	}

	/**
	 * 메인 공지사항 영역
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView ch3DNoticeL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'ch3DNoticeL' method...");
		String requestsectId = request.getParameter("sectid");
		int sectId = 0;
		if(requestsectId == null || requestsectId.equals("")) { 
			sectId = Integer.parseInt(HttpUtils.getProperty("ch3d_root_sectid")); 
		} else {
			sectId = Integer.parseInt(requestsectId); 
		}
		Map model = new HashMap();
		List sectNoticeList = ch3DMgr.list3DRootNotice(sectId);
		
		model.put("sectNoticeList",sectNoticeList);
		return new ModelAndView("ch3DNoticeL", model);
	}
		
	/**
	 * 메인 EVENT 영역
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView ch3DSectEventL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'ch3DSectEventL' method...");

		String sectId = request.getParameter("sectid");
		String gubun = request.getParameter("gubun");		
		if(sectId == null || sectId.equals("")) { sectId = HttpUtils.getProperty("ch3d_root_sectid"); }
		Map model = new HashMap();
		List sectEventList = ch3DMgr.listSectEvent(Long.parseLong(sectId),gubun,-1);
		
		model.put("sectEventList",sectEventList);
		return new ModelAndView("ch3DSectEventL", model);
	}
	
	/**
	 * 매장 상품 리스트 (하위매장상품까지 모두)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView ch3DSectItemL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'ch3DSectItemL' method...");
		Map model = new HashMap();
		String sectId = request.getParameter("sectid");
		String brandId = request.getParameter("brandid");
		String sort = request.getParameter("sort");
		
		if(sectId == null || sectId.equals("")) 		
		{
			throw new XmlException("매장아이디가 없습니다.");
		}

		Sect sect = shopMgr.readSect(Integer.parseInt(sectId));
		if(sect == null)
		{
			throw new XmlException("매장이 존재하지 않습니다.");
		}
		
		List list3DSectItem = ch3DMgr.list3DSectItem(sectId, brandId, sort);
		
		model.put("list3DSectItem",list3DSectItem);
		model.put("sectid", sectId);
		model.put("brandid", brandId);
		model.put("sort", sort);
		
		return new ModelAndView("ch3DSectItemL", model);
	}

	/**
	 * 3D 베스트상품 (바이어추천)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView ch3DRecomItemL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'ch3DRecomItemL' method...");
		String sectId = request.getParameter("sectid");
		Map model = new HashMap();

		if(sectId == null || sectId.equals("")) 		
		{
			throw new XmlException("매장아이디가 없습니다.");
		}

		Sect sect = shopMgr.readSect(Integer.parseInt(sectId));
		if(sect == null)
		{
			throw new XmlException("매장이 존재하지 않습니다.");
		}
		
		List recommendItemList = ch3DMgr.listRecommendItem(sect.getSect_id(), "01", 0, sect.getDepth(), 100);

		model.put("recommendItemList", recommendItemList);

		return new ModelAndView("ch3DRecomItemL", model);
	}
		
	
	/**
	 * 메인 인기상품 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView htBestItemL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'htSectEventL' method...");
		String sectId = request.getParameter("sectid");
		if(sectId == null || sectId.equals("")) { sectId = "" + htvMgr.readHtvRootSect().getSect_id(); }
		
		//임시
		Map model = new HashMap();
		Sect sect = shopMgr.readSect(Integer.parseInt(sectId));
		
		/*
		* Weekly Best Seller (BEST10)
		*/
		List bestItemList = htvMgr.listBestItem(sect.getSect_id(), 5);
		model.put("bestItemList", bestItemList);
		model.put("bestItemListSize", new Integer(bestItemList.size()));
		return new ModelAndView("htBestItemL", model);
	}
	
	
	public ModelAndView ch3DItemDetailR(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'htItemDetailRL' method...");
		Map model = new HashMap();
		String itemCode = null;
		itemCode = request.getParameter("itemcode");
		String sectId = request.getParameter("sectid");
		
		if(itemCode == null || itemCode.equals("")) 
		{ 
			if(sectId == null || sectId.equals("")) 
			{ 
				throw new XmlException("상품코드 및 매장아이디가 존재하지 않습니다."); 
			} else {
				itemCode = ch3DMgr.select3DSectItem(Integer.parseInt(sectId));
			}
		}
		
		//상품상세
		//ItemDetail item = ch3DMgr.read3DlItemDetail(itemCode); // 직접ItemDtail에 받을경우 배송일(Ars_cost)등 값이 안오는 경우 생김 - 아래로 변경
		Product product = (Product) ch3DMgr.read3DlItemDetail(itemCode);
		if (product == null) {
			throw new ServiceException("해당 상품이 없습니다.");
		}
		
		ItemDetail item = product.getItemDetail();
		ItemPrice itemPrice = product.getItemPrice();

		//System.out.println("==============getArs_cost==================================");
		//Integer x = new Integer(7); 
		//item.setArs_cost(x);	
		//System.out.println(""+item.getArs_cost());
		//System.out.println("==============getArs_cost==================================");

		/*
		 * 사은품
		List giftPromomList = eventMgr.listGiftPromom(itemCode);
		model.put("giftPromomList", giftPromomList);
		model.put("giftPromomListSize", new Integer(giftPromomList.size()));		
		 */

		/*
		 * 상품 동영상
		 */
		Mpeg mpeg = product.getMpeg();
		model.put("mpeg", mpeg);
		
		//상품이미지
		List textImgList = shopMgr.listTextImg(itemCode);

		/* ---------------------------------------------------- */	
		/*
		 * 고객평가고객  갯수, 점수
		 */
		ItemStatus itemStatus = shopMgr.readItemStatus(itemCode);
		model.put("itemStatus", itemStatus);

		/*
		 * 이 상품을 등록한 블로그
		 */
		int itemBlogCount = shopMgr.readItemBlogCount(itemCode);
		model.put("itemBlogCount", new Integer(itemBlogCount));
		
		//블로그 별그리기
		List blogStarList = shopMgr.listBlogStarList(itemCode);
		model.put("blogStarList", blogStarList);
		
		int ratingCount = shopMgr.readRatingUserCount(itemCode, null);
		model.put("ratingCount", new Integer(ratingCount));		
		
		/* ---------------------------------------------------- */		
		
		/*
		 * 상품평 갯수, Q/A 갯수
		 */
		Map itemcountdata = shopMgr.readItemReviewKnowledgeCount(itemCode);
		item.setEntryCount("" + itemcountdata.get("reviewcount"));
		item.setKinEntryCount("" + itemcountdata.get("knowledgecount"));
		
		model.put("textImgList", textImgList);
		model.put("textImgListSize", new Integer(textImgList.size()));
		model.put("item",item);
		model.put("itemPrice",itemPrice);		
		
		return new ModelAndView("ch3DItemDetailR", model);
	}

	/**
	 * 보험 방송예정 상품 xml
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView insuMainL(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'insuMainL' method...");
		String mode = RequestUtils.getStringParameter(request, "mode", "cate"); // mode값을 가져왔을때 없으면 기본값 cate
		String group = RequestUtils.getStringParameter(request, "hidGroup", "15"); // hidGroup을 가져왔을때 없으면 기본값 15
		int page = RequestUtils.getIntParameter(request, "hidPage", 1); // 현재 페이지 번호
		int lineSize = RequestUtils.getIntParameter(request, "lineSize", 100); // 페이지당 라인 수
		int pageSize = RequestUtils.getIntParameter(request, "pageSize", 1); // 그룹당 페이지 수

		/*
		 * 미리 방송 상품 리스트를 가지고 온다.PARAM:1 (1:생방상품,2:금일방송상품,3:기타상품) PARAM:2 (검색 시작일) PARAM:3 (검색 종료일) PARAM:4 (페이지 사이즈) PARAM:5 (페이지)
		 */
		List weekDate = new ArrayList();
		Calendar today = null;
		// today = new GregorianCalendar(2005, 5 - 1, 01);
		today = new GregorianCalendar();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		String srvTime = null;
		for (int i = 1; i <= 7; i++) {
			today.add(Calendar.DAY_OF_MONTH, 1);
			if (i == 1) {
				srvTime = fmt.format(today.getTime());
			}
			Date tmpDate = today.getTime();
			weekDate.add(tmpDate);
		}
		String sDate = null;
		String eDate = null;
		if (mode.equals("day")) {
			sDate = RequestUtils.getStringParameter(request, "hidSDate", srvTime);
			eDate = RequestUtils.getStringParameter(request, "hidEDate", srvTime);
		} else if (mode.equals("cate")) {
			sDate = RequestUtils.getStringParameter(request, "hidSDate", srvTime);
			// 오픈시 풀어줄것
			eDate = RequestUtils.getStringParameter(request, "hidEDate", fmt.format(today.getTime()));
			// eDate = RequestUtils.getStringParameter(request, "hidEDate", "20050502");
		}

		int type = RequestUtils.getIntParameter(request, "hidType", 3);
		Map model = new HashMap();
		model.put("sDate", sDate);
		model.put("eDate", eDate);
		model.put("group", group);
		model.put("mode", mode);
		model.put("weekDate", weekDate);

		Calendar calendar = BaseUtils.getCalendar(Integer.parseInt(sDate.substring(0, 4)), Integer.parseInt(sDate.substring(4, 6)),
				Integer.parseInt(sDate.substring(6, 8)));
		Date curDate = calendar.getTime();

		model.put("curDate", curDate);
		List broadProdList = null;
		if (mode.equals("day")) {
			broadProdList = homeShopMgr.listBroadProd(type, sDate, eDate, 1000, 1);
			model.put("broadProdList", broadProdList);
			model.put("totalSize", "" + broadProdList.size());
		} else if (mode.equals("cate")) {
			broadProdList = homeShopMgr.listBroadProd(type, sDate, eDate, group, lineSize, page);
			PageHolder pageHolder = new PageHolder(lineSize, pageSize, homeShopMgr.listBroadProdCount(type, sDate, eDate, group),
					page);
			model.put("pageHolder", pageHolder);
			model.put("lineSize", new Integer(lineSize));
			model.put("page", new Integer(page));
			model.put("broadProdList", broadProdList);
			model.put("totalSize", "" + pageHolder.getTotalRows());
		}
		
		log.debug("검색날짜:" + srvTime);
		model.put("returnUrl", "/tv/tvScheduleR.do");
		model.put("noBuyBttn", "Y");
		model.put("nowDate", new Date());
		
		return new ModelAndView("insuMainL", model);
	}	
	
	private void loadDBCategory(int sectId, Map model) throws Exception
	{
		//왼쪽 카테고리를 가져온다.
		List leftCatalogList = ch3DMgr.listGrandChildSect(sectId); 
		model.put("leftCatalogList", leftCatalogList);
	
	}
	
	private ModelAndView loadLocalCategory(Map model)
	{
		/**
		1:   SAXBuilder builder = new SAXBuilder();
		2:   Document doc = builder.build(new File(args[0]));
		3:   DocType docType = doc.getDocType();
		4:   Element root = doc.getRootElement();
		5:   trace("Input File : " + args[0]);
		*/
		return new ModelAndView("ch3DLocalCategoryL", model);
	}
	
	private boolean checkLocalCateFile()
	{
		boolean result = false;
		File file = new File(HttpUtils.getProperty("ch3d_category_path") + HttpUtils.getProperty("ch3d_category_name"));
		return file.exists();
	}
	
	private void sendXmlFile()
	{
		//FTP로 전송한다.
		String[] server = new String[5];
		String[] user = new String[5];
		String[] password = new String[5];
		String[] path = new String[5];
		
		server[0] = HttpUtils.getProperty("etv_srv_1");
	    user[0] = HttpUtils.getProperty("etv_userid_1");
	    password[0] = HttpUtils.getProperty("etv_passwd_1");
	    path[0] = HttpUtils.getProperty("etv_path_1");
	    
	    server[1] = HttpUtils.getProperty("etv_srv_2");
	    user[1] = HttpUtils.getProperty("etv_userid_2");
	    password[1] = HttpUtils.getProperty("etv_passwd_2");
	    path[1] = HttpUtils.getProperty("etv_path_2");
	    
	    server[2] = HttpUtils.getProperty("etv_srv_3");
	    user[2] = HttpUtils.getProperty("etv_userid_3");
	    password[2] = HttpUtils.getProperty("etv_passwd_3");
	    path[2] = HttpUtils.getProperty("etv_path_3");
	    
	    server[3] = HttpUtils.getProperty("etv_srv_4");
	    user[3] = HttpUtils.getProperty("etv_userid_4");
	    password[3] = HttpUtils.getProperty("etv_passwd_4");
	    path[3] = HttpUtils.getProperty("etv_path_4");
	    
	    server[4] = HttpUtils.getProperty("etv_srv_5");
	    user[4] = HttpUtils.getProperty("etv_userid_5");
	    password[4] = HttpUtils.getProperty("etv_passwd_5");
	    path[4] = HttpUtils.getProperty("etv_path_5");
	    
	    String filename = HttpUtils.getProperty("ch3d_category_path") + HttpUtils.getProperty("ch3d_category_name");
	    

/**
* FTP 파일 업로드
*/
	    
/*	    
	    public void doUpLoad() {

	    FtpClient ftpClient = new FtpClient();

	    try {
	    ftpClient.openServer(server);
	    ftpClient.login(user, password);

	    if (path.length() != 0) ftpClient.cd(path);

	    ftpClient.binary();
	    TelnetOutputStream os = ftpClient.put(filename);
	    File file_in = new File(filename);
	    FileInputStream is = new FileInputStream(file_in);
	    byte[] bytes = new byte[1024];
	    int c;

	    while( (c = is.read(bytes)) != -1) {
	    os.write(bytes,0,c);
	    }

	    is.close();
	    os.close();
	    ftpClient.closeServer();

	    } catch (IOException ie) {
	    ie.printStackTrace();
	    }
	    }
*/	    
	    
	    
	    try 
		{
	    	for(int j = 0 ; j < 5 ; j++)
	    	{
		    	if(server[j] != null && !server[j].equals(""))
		    	{
		    		FtpClient ftpClient=new FtpClient();
			    	ftpClient.openServer(server[j]);
			    	ftpClient.login(user[j], password[j]);
			    	if (path[j].length()!=0) ftpClient.cd(path[j]);
			    	//ftpClient.binary();
			    	ftpClient.ascii();			    	
			    	TelnetOutputStream os=ftpClient.put(HttpUtils.getProperty("ch3d_category_name"));
			    	File file_in=new File(HttpUtils.getProperty("ch3d_category_path") + HttpUtils.getProperty("ch3d_category_name"));
			    	FileInputStream is=new FileInputStream(file_in);
			    	byte[] bytes=new byte[1024];
			    	int c;
			    	while ((c=is.read(bytes))!=-1){
			    		os.write(bytes,0,c);
			    	}
			    	is.close();
			    	os.close();
			    	ftpClient.closeServer();
		    	}
	    	}
		} catch (IOException ex) 
		{log.debug(ex);}
	}
	
	public ModelAndView htError(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'htMyVodC' method...");
		Map model = new HashMap();
		throw new XmlException("테스트 에러 MSG입니다. throw new XmlException으로 처리합니다.");
	}
	
	/**
	 *	스트링 치환 함수
	 *	
	 *	주어진 문자열(buffer)에서 특정문자열('src')를 찾아 특정문자열('dst')로 치환
	 *
	 */
	private String ReplaceAll(String buffer, String src, String dst){
		if(buffer == null) return null;
		if(buffer.indexOf(src) < 0) return buffer;
		
		int bufLen = buffer.length();
		int srcLen = src.length();
		StringBuffer result = new StringBuffer();

		int i = 0; 
		int j = 0;
		for(; i < bufLen; ){
			j = buffer.indexOf(src, j);
			if(j >= 0) {
				result.append(buffer.substring(i, j));
				result.append(dst);
				
				j += srcLen;
				i = j;
			}else break;
		}
		result.append(buffer.substring(i));
		return result.toString();
	}
	
	public ModelAndView ch_main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("entering 'ch_main' method...");
		String sectId = request.getParameter("sectid");
		String itemcode = request.getParameter("itemcode");
		String ch = "";
		if(sectId == null || sectId.equals(""))
		{
			if(itemcode != null && !itemcode.equals(""))
			{
				Sect sect = htvMgr.readSectFromSectItem(itemcode, "11");
				if(sect != null)
				{
					sectId = "" + sect.getSect_id();
				}
				
				if(sectId == null || sectId.equals(""))
				{
					ItemDetail item = htvMgr.readChannelItemDetail(itemcode);
					if(item != null)
					{
						sectId = item.getDefault_sect_id().toString();
					}
				}
			}
		}
		
		if(sectId != null && !sectId.equals(""))
		{
			Sect depth3 = shopMgr.readParentSect(Integer.parseInt(sectId),3);
			ch = "" + depth3.getPriority();
		}
		Map model = new HashMap();
		model.put("itemcode",itemcode);
		model.put("sectid",sectId);
		model.put("ch",ch);
		return new ModelAndView("/htv/ch_main",model);
	}
	
}
