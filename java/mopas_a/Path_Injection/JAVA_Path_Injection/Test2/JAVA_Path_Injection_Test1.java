 package com.hmall.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Decoder;

import com.hmall.exception.ServiceException;
import com.hmall.model.cart.EHUBasketList;
import com.hmall.model.common.ItemTagCode;
import com.hmall.model.display.Sect;
import com.hmall.model.item.Item;
import com.hmall.service.CommonMgr;
import com.hmall.service.ShopMgr;
import com.hmall.util.ewrap.CustomerUtil;
import com.hmall.util.ewrap.ERCookie;
import com.initech.iniplugin.IniPlugin;


public class HttpUtils implements MessageSourceAware {

	private static Log log = LogFactory.getLog(HttpUtils.class);

	private static MessageSource messageSource;

	private static CommonMgr commonMgr;

	private static ShopMgr shopMgr;

	/**
	 * ������Ƽ ���� ����
	 */
	private static Properties properties = null;

	/**
	 * ��ǰ�±� �ڵ� ���
	 */
	private static Map itemTagCodeMap = null;

	public void setMessageSource(MessageSource messageSource) {
		HttpUtils.messageSource = messageSource;
	}

	public void setCommonMgr(CommonMgr commonMgr) {
		HttpUtils.commonMgr = commonMgr;
	}

	public void setShopMgr(ShopMgr shopMgr) {
		HttpUtils.shopMgr = shopMgr;
	}

	public void setLocations(Resource[] locations) {
		try {
			if (locations != null) {
				Properties result = new Properties();
				for (int i = 0; i < locations.length; i++) {
					Properties props = new Properties();
					Resource location = locations[i];
					props.load(location.getInputStream());
					for (Enumeration en = props.propertyNames(); en.hasMoreElements();) {
						String key = (String) en.nextElement();
						result.setProperty(key, props.getProperty(key));
					}
				}
				HttpUtils.properties = result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �޽��� ���
	 * @param code �޽��� �ڵ�
	 * @return �޽��� ����
	 */
	public static String getMessage(String code) {
		return messageSource.getMessage(code, null, Locale.getDefault());
	}

	/**
	 * �޽��� ���
	 * @param code �޽��� �ڵ�
	 * @param args �Ű�����
	 * @return �޽��� ����
	 */
	public static String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, Locale.getDefault());
	}

	/**
	 * ȯ�溯�� ���
	 * @param key ������Ƽ Ű
	 * @return ������Ƽ ��
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * ��ǰ�̹��� ���� ��� ���
	 * @param s ��ǰ�̹��� ���� �̸�
	 * @return ��ǰ�̹��� ���� ���
	 */
	public static String getItemImgPath(String s) {
		String ItemImgPath = "";
		try {
	
			if (s != null && s.length() > 0) {
				int imgSize = 0;
				
				//[start]�̹����� �߸��Ȱ�� ���°ɷ� ó���Ѵ�.ex)D:\.....	
				s = s.replaceAll("\n","");//enter������
				if(s.indexOf(':')>0){
					return "etc/img_noimg_300.gif";
				}
				//[end]�̹����� �߸��Ȱ�� ���°ɷ� ó���Ѵ�.ex)D:\.....
				
				if (s.startsWith("small") && s.length() >= 15) {
					imgSize = "small".length();
				} else if (s.startsWith("ext") && s.length() >= 13) {
					imgSize = "ext".length();
				} else if (s.startsWith("middle") && s.length() >= 16) {
					imgSize = "middle".length();
				} else if (s.startsWith("large") && s.length() >= 15) {
					imgSize = "large".length();
				} else if (s.startsWith("note") && s.length() >= 15) {
					imgSize = "note".length();
				} else if (s.startsWith("icon") && s.length() >= 15) {
					imgSize = "icon".length();
				} else if (s.startsWith("ecody") && s.length() >= 15) {
					imgSize = "ecody".length();
				}
				if (imgSize == 0) {
					ItemImgPath = "etc/" + s;
				} else {
					String path = s.substring(imgSize, imgSize + 4) + s.substring(imgSize + 9, imgSize + 10);
					try {
						if (Integer.parseInt(path) > 0) {
							ItemImgPath = path + "/" + s;
						}
					} catch (NumberFormatException nfe) {
						ItemImgPath = "etc/" + s;
					}
				}
			}
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return ItemImgPath;
	}

	/**
	 * ��ǰ �±� ����� �����´�.
	 * @return ��ǰ �±� ���
	 */
	public static Map getItemTagCodes() throws Exception {
		if (itemTagCodeMap == null) {
			Map tempItemTagCodeMap = new HashMap();
			List list = commonMgr.listItemTagCode();
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				ItemTagCode tagCode = (ItemTagCode) iterator.next();
				tempItemTagCodeMap.put(String.valueOf(tagCode.getCode_bit()), tagCode);
			}
			itemTagCodeMap = tempItemTagCodeMap;
		}
		return itemTagCodeMap;
	}
	
	/**
	 * @Noasoft_renewal(����) 2007-10-10, ��뼮, �ؿܹ��/�����ȭ�� ���� ����
	 * �� ���������� ǥ���� ��ǰ �±� 3�� ����� �����´�.
	 * @param item
	 * @return ��ǰ �±� 3�� ���
	 */
	public static List getItemTags(Item item) throws Exception {
		List itemTags = new ArrayList();
		int tagCount = 0;

		//Map tagCodeMap = getItemTagCodes();
		// 2007.11.9 :: �Ӻ���:: tag�� �ھƹ���.
		// ǰ��
		if (BaseUtils.equals(item.getTag8_yn(), "1")) {
			//itemTags.add(tagCodeMap.get("8"));
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("noproduct");
			tagCode.setTag_url("ic_outof.gif");
			itemTags.add(tagCode);
			tagCount++;
		}

		//@Noasoft_renewal(����) 2007-10-10, ��뼮, �����ȭ�� ���� ����
		// �����ȭ��
		/*
		String hyundaiDeptVenCode = HttpUtils.getProperty("hd_dept_ven_code");
		if (BaseUtils.indexOf(hyundaiDeptVenCode, item.getVen_code()) >= 0) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("Hyundai Department");
			tagCode.setTag_url("button_sec_01.gif");
			itemTags.add(tagCode);
			tagCount++;
		}
		*/
		/******************************
		 *   �����ȭ�� �±� �̹��� ����
		 1��[�̾���] : 003766,006323,003021
		 2��[õȣ��] : 004502
		 3��[�λ���] : 007562
		 4��[������] : 004504
		 5��[������] : 004503
		 6��[����] : 004505, [�ƿ﷿] : 007799
		 7��[��û��] : 011054
		 *  ���� : Tag2_yn=1 �̸� ��ȭ�� �±� �̳���		
		 *  HM/20130409/������/HttpUtils.java/��û�� �߰�  
		 *  */ 		
		if ( ("003766".equals(item.getVen_code())
			|| "006323".equals(item.getVen_code())
			|| "003021".equals(item.getVen_code())
			|| "004502".equals(item.getVen_code())
			|| "007562".equals(item.getVen_code())
			|| "004503".equals(item.getVen_code())
			|| "004505".equals(item.getVen_code())
			|| "007799".equals(item.getVen_code())
			|| "004504".equals(item.getVen_code())
			|| "011054".equals(item.getVen_code()))
			&& !BaseUtils.equals(item.getTag2_yn(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("Hyundai Department");
			
			if ("011054".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart8.gif");
			} else if ("007799".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart7.gif");
			} else if ("004505".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart6.gif");
			} else if ("004503".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart5.gif");
			} else if ("004504".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart4.gif");
			} else if ("007562".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart3.gif");
			} else if ("004502".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart2.gif");
			}else {
				tagCode.setTag_url("ic_depart1.gif");
			}			
			itemTags.add(tagCode);
			tagCount++;
		}

		/* ����Ȩ����*/
		if (BaseUtils.equals(item.getTag3_yn(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("homeshop");
			tagCode.setTag_url("ic_hs.gif");
			itemTags.add(tagCode);
			//itemTags.add(tagCodeMap.get("3"));
			tagCount++;
		}


		// ����ȭ��ǰ
		String cosmeticVenCode = HttpUtils.getProperty("cosmetic_ven_code");
		if (BaseUtils.isEmpty(item.getVen_code()) == false && BaseUtils.equals(item.getVen_code(), cosmeticVenCode)) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("Cosmetic");
			tagCode.setTag_url("ic_c_cos.gif");
			itemTags.add(tagCode);
			tagCount++;
		}

		// �ؿܹ��
		if (BaseUtils.equals(item.getTag4_yn(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("broad");
			tagCode.setTag_url("ic_ems.gif");
			itemTags.add(tagCode);
			//itemTags.add(tagCodeMap.get("4"));
			tagCount++;
		}


		// ����ǰ
		if (item.isExistGift() == true) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("Gift");
			tagCode.setTag_url("ic_gift.gif");
			itemTags.add(tagCode);
			tagCount++;
		}

		// �Ż�ǰ
		int newtag_days = BaseUtils.toInt(HttpUtils.getProperty("newtag_days"));
		if (item.getNewcheck() != null && item.getNewcheck().intValue() <= newtag_days) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("New");
			tagCode.setTag_url("ic_new.gif");
			itemTags.add(tagCode);
			tagCount++;
		}

		/* Hit��ǰ*/
		/* 2007.10.31 �ǿ�â������ ��û�� ���� �ּ�ó��.
		if (BaseUtils.equals(item.getTag2_yn(), "1")) {
			itemTags.add(tagCodeMap.get("2"));
			tagCount++;
		}
		*/

		/* sale��ǰ*/
		/* 2007.11.04 �ǿ�â������ ��û�� ���� �ּ�ó��. 
		if (BaseUtils.equals(item.getTag5_yn(), "1")) {
			itemTags.add(tagCodeMap.get("5"));
			tagCount++;
		}
		*/
		
		/* event��ǰ*/
		if (BaseUtils.equals(item.getTag5_yn(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("New");
			tagCode.setTag_url("ic_event.gif");
			itemTags.add(tagCode);
			tagCount++;
		}
		
		
		/* md��õ��ǰ*/
		/*
		if (BaseUtils.equals(item.getTag7_yn(), "1")) {
			itemTags.add(tagCodeMap.get("7"));
			tagCount++;
		}
		*/


		// �����ڰ�����
		/* ������.
		if (item.getNorest_allot_month() != null && item.getNorest_allot_month().intValue() > 0) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("Gift");
			tagCode.setTag_url("ic_no_interest1.jpg");
			itemTags.add(tagCode);
			tagCount++;
		}





		 �ؿܹ�� ����
		if ("1".equals(item.getTag4_yn())) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("Oversea");
			tagCode.setTag_url("button_sec_02.gif");
			itemTags.add(tagCode);
			tagCount++;
		}




		// ����
		if (item.getCoupon_dc_amt() > 0) {

			String UnitEventNo = ""; 		// �̳��������� �ƴҰ�츸 ����
			if (BaseUtils.isEmpty(item.getUnit()) == false) { // null�� ó�� �� �ؾ���.
				UnitEventNo = item.getUnit();
			}
			// 2007.05.16:: ������������ ������:: �Ӻ���
			int as_persent = 0;
			 if (UnitEventNo.equals("00003551"))  as_persent = 1;
			  else if (UnitEventNo.equals("00003552")) as_persent = 2;
			  else if (UnitEventNo.equals("00003553"))	as_persent = 3;
			  else if (UnitEventNo.equals("00003554")) 	as_persent = 4;
			  else if (UnitEventNo.equals("00003555")) 	as_persent = 5;
			  else if (UnitEventNo.equals("00003556")) 	as_persent = 6;
			  else if (UnitEventNo.equals("00003557")) 	as_persent = 7;
			  else if (UnitEventNo.equals("00003558")) 	as_persent = 8;
			  else if (UnitEventNo.equals("00003559")) 	as_persent = 9;
			  else if (UnitEventNo.equals("00003560")) 	as_persent = 10;
			  else if (UnitEventNo.equals("00004157")) 	as_persent = 11;
			  else if (UnitEventNo.equals("00004158")) 	as_persent = 12;
			  else if (UnitEventNo.equals("00004159")) 	as_persent = 13;
			  else if (UnitEventNo.equals("00004160")) 	as_persent = 14;
			  else if (UnitEventNo.equals("00004161")) 	as_persent = 15;
			  else if (UnitEventNo.equals("00004162")) 	as_persent = 16;
			  else if (UnitEventNo.equals("00004163")) 	as_persent = 17;
			  else if (UnitEventNo.equals("00004164")) 	as_persent = 18;
			  else if (UnitEventNo.equals("00004165")) 	as_persent = 19;
			  else if (UnitEventNo.equals("00004166")) 	as_persent = 20;

			if (as_persent >0){
				//ItemTagCode tagCode = new ItemTagCode();
				//tagCode.setCode_bit(0);
				//tagCode.setCode_name("Cosmetic");
				//tagCode.setTag_url("ic_"+as_persent+"_coupon.jpg");
				//itemTags.add(tagCode);
				//tagCount++;
			}

		}
		*/

		if (itemTags.size() > 3) {
			return itemTags.subList(0, 3);
		}

		return itemTags;
	}
	
	
	/**
	 * @Noasoft_renewal(����) 2007-10-10, ��뼮, �ؿܹ��/�����ȭ�� ���� ����
	 * �� ���������� ǥ���� ��ǰ �±� 3�� ����� �����´�.
	 * @param item
	 * @return ��ǰ �±� 3�� ���
	 */
	public static List getBasketItemTags(EHUBasketList item) throws Exception {
		List itemTags = new ArrayList();
		int tagCount = 0;

//		//Map tagCodeMap = getItemTagCodes();
//		// 2007.11.9 :: �Ӻ���:: tag�� �ھƹ���.
//		// ǰ��
//		if (BaseUtils.equals(item.getTag8Yn(), "1")) {
//			ItemTagCode tagCode = new ItemTagCode();
//			tagCode.setCode_bit(0);
//			tagCode.setCode_name("noproduct");
//			tagCode.setTag_url("ic_outof.gif");
//			itemTags.add(tagCode);
//			tagCount++;
//		}

		/******************************
		 *   �����ȭ�� �±� �̹��� ����
			1��[�̾���] : 003766,006323,003021
			2��[õȣ��] : 004502
			3��[�λ���] : 007562
			4��[������] : 004504
			5��[������] : 004503
			6��[����] : 004505, [�ƿ﷿] : 007799
			7��[��û��] : 011054
		 *  ���� : Tag2_yn=1 �̸� ��ȭ�� �±� �̳���		
		 *  HM/20130409/������/HttpUtils.java/��û�� �߰�  
		 *  */ 		
		if ( ("003766".equals(item.getVen_code())
			|| "006323".equals(item.getVen_code())
			|| "003021".equals(item.getVen_code())
			|| "004502".equals(item.getVen_code())
			|| "007562".equals(item.getVen_code())
			|| "004503".equals(item.getVen_code())
			|| "004505".equals(item.getVen_code())
			|| "007799".equals(item.getVen_code())
			|| "004504".equals(item.getVen_code())
			|| "011054".equals(item.getVen_code()))
			&& !BaseUtils.equals(item.getTag2Yn(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("Hyundai Department");
			if ("011054".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart8.gif");
			}else if ("007799".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart7.gif");
			}else if ("004505".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart6.gif");
			}else if ("004503".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart5.gif");
			}else if ("004504".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart4.gif");
			}else if ("007562".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart3.gif");
			}else if ("004502".equals(item.getVen_code())){
				tagCode.setTag_url("ic_depart2.gif");
			}else {
				tagCode.setTag_url("ic_depart1.gif");
			}			
			itemTags.add(tagCode);
			tagCount++;
		}

//		/* ����Ȩ����*/
//		if (BaseUtils.equals(item.getTag3Yn(), "1")) {
//			ItemTagCode tagCode = new ItemTagCode();
//			tagCode.setCode_bit(0);
//			tagCode.setCode_name("homeshop");
//			tagCode.setTag_url("ic_hs.gif");
//			itemTags.add(tagCode);
//			//itemTags.add(tagCodeMap.get("3"));
//			tagCount++;
//		}


//		// ����ȭ��ǰ
//		String cosmeticVenCode = HttpUtils.getProperty("cosmetic_ven_code");
//		if (BaseUtils.isEmpty(item.getVen_code()) == false && BaseUtils.equals(item.getVen_code(), cosmeticVenCode)) {
//			ItemTagCode tagCode = new ItemTagCode();
//			tagCode.setCode_bit(0);
//			tagCode.setCode_name("Cosmetic");
//			tagCode.setTag_url("ic_c_cos.gif");
//			itemTags.add(tagCode);
//			tagCount++;
//		}

//		// �ؿܹ��
//		if (BaseUtils.equals(item.getTag4Yn(), "1")) {
//			ItemTagCode tagCode = new ItemTagCode();
//			tagCode.setCode_bit(0);
//			tagCode.setCode_name("broad");
//			tagCode.setTag_url("ic_ems.gif");
//			itemTags.add(tagCode);
//			//itemTags.add(tagCodeMap.get("4"));
//			tagCount++;
//		}


		// ����ǰ
//		if (item.isExistGift() == true) {
//			ItemTagCode tagCode = new ItemTagCode();
//			tagCode.setCode_bit(0);
//			tagCode.setCode_name("Gift");
//			tagCode.setTag_url("ic_gift.gif");
//			itemTags.add(tagCode);
//			tagCount++;
//		}
//
//		// �Ż�ǰ
//		int newtag_days = BaseUtils.toInt(HttpUtils.getProperty("newtag_days"));
//		if (item.getNewcheck() != null && item.getNewcheck().intValue() <= newtag_days) {
//			ItemTagCode tagCode = new ItemTagCode();
//			tagCode.setCode_bit(0);
//			tagCode.setCode_name("New");
//			tagCode.setTag_url("ic_new.gif");
//			itemTags.add(tagCode);
//			tagCount++;
//		}

		
//		/* event��ǰ*/
//		if (BaseUtils.equals(item.getTag5Yn(), "1")) {
//			ItemTagCode tagCode = new ItemTagCode();
//			tagCode.setCode_bit(0);
//			tagCode.setCode_name("New");
//			tagCode.setTag_url("ic_event.gif");
//			itemTags.add(tagCode);
//			tagCount++;
//		}
		
		

		if (itemTags.size() > 3) {
			return itemTags.subList(0, 3);
		}

		return itemTags;
	}

	/**
	 * @Noasoft_renewal(����) 2011-10-24, ȫ����, 
	 * �� ���������� ǥ���� ��ǰ �±� 5�� ����� �����´�.(trend)
	 * @param item
	 * @return ��ǰ �±� 5�� ���
	 */
	public static List getTrendItemTags(Item item) throws Exception {
		List itemTags = new ArrayList();
		int tagCount = 0;
		
		// �Ż�ǰ
		int newtag_days = BaseUtils.toInt(HttpUtils.getProperty("newtag_days"));
		if (item.getNewcheck() != null && item.getNewcheck().intValue() <= newtag_days) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("New");
			tagCode.setTag_url("icon_new");
			itemTags.add(tagCode);
			tagCount++;
		}

		//Map tagCodeMap = getItemTagCodes();
		// 2007.11.9 :: �Ӻ���:: tag�� �ھƹ���.
		// ǰ��
		
		if (BaseUtils.equals(item.getTag8_yn(), "1")) {
			//itemTags.add(tagCodeMap.get("8"));
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("noproduct");
			tagCode.setTag_url("ic_outof.gif");
			itemTags.add(tagCode);
			tagCount++;
		}

		  //Ʈ���� H�� hit��ǰ
		if (BaseUtils.equals(item.getTrend_hit(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("hit");
			tagCode.setTag_url("icon_hit");
			itemTags.add(tagCode);
			//itemTags.add(tagCodeMap.get("4"));
			tagCount++;
		}
		
		  //Ʈ���� H�� sale��ǰ
		if (BaseUtils.equals(item.getTrend_sale(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("sale");
			tagCode.setTag_url("icon_sale");
			itemTags.add(tagCode);
			//itemTags.add(tagCodeMap.get("4"));
			tagCount++;
		}
		
		  //Ʈ���� H�� only��ǰ
		if (BaseUtils.equals(item.getTrend_only(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("only");
			tagCode.setTag_url("icon_only");
			itemTags.add(tagCode);
			//itemTags.add(tagCodeMap.get("4"));
			tagCount++;
		}
		
		  //Ʈ���� H�� md��õ��ǰ
		if (BaseUtils.equals(item.getTag7_yn(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("trend_md");
			tagCode.setTag_url("icon_md");
			itemTags.add(tagCode);
			//itemTags.add(tagCodeMap.get("4"));
			tagCount++;
		}

		return itemTags;
	}

	/**
	 * @Noasoft_renewal(�߰�) 2007-11-15, ��뼮, �˻� ��� �±� ���
	 * getItemTags �����ؼ� ����
	 * @param item
	 * @return ��ǰ �±� 3�� ���
	 */
	public static List getSearchItemTags(com.hmall.util.search.SearchItem item) throws Exception {
		List itemTags = new ArrayList();
		int tagCount = 0;

		if (BaseUtils.equals(item.getTag8_yn(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("noproduct");
			tagCode.setTag_url("ic_outof.gif");
			itemTags.add(tagCode);
			tagCount++;
		}

		/*
		// �����ȭ��
		if ("003766".equals(item.getVen_code())
			|| "003021".equals(item.getVen_code())
			|| "006323".equals(item.getVen_code())
			|| "004505".equals(item.getVen_code())
			|| "004503".equals(item.getVen_code())) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("Hyundai Department");
			tagCode.setTag_url("ic_depart.gif");
			itemTags.add(tagCode);
			tagCount++;
		}

		// ����Ȩ����
		if (BaseUtils.equals(item.getTag3_yn(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("homeshop");
			tagCode.setTag_url("ic_hs.gif");
			itemTags.add(tagCode);
			//itemTags.add(tagCodeMap.get("3"));
			tagCount++;
		}

		// ����ȭ��ǰ
		String cosmeticVenCode = HttpUtils.getProperty("cosmetic_ven_code");
		if (BaseUtils.isEmpty(item.getVen_code()) == false && BaseUtils.equals(item.getVen_code(), cosmeticVenCode)) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("Cosmetic");
			tagCode.setTag_url("ic_c_cos.gif");
			itemTags.add(tagCode);
			tagCount++;
		}

		// �ؿܹ��
		if (BaseUtils.equals(item.getTag4_yn(), "1")) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("broad");
			tagCode.setTag_url("ic_ems.gif");
			itemTags.add(tagCode);
			//itemTags.add(tagCodeMap.get("4"));
			tagCount++;
		}


		// ����ǰ
		if (item.isExistGift() == true) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("Gift");
			tagCode.setTag_url("ic_gift.gif");
			itemTags.add(tagCode);
			tagCount++;
		}
		*/

		// �Ż�ǰ
		int newtag_days = BaseUtils.toInt(HttpUtils.getProperty("newtag_days"));
		if (item.getNewcheck() != null && item.getNewcheck().intValue() <= newtag_days) {
			ItemTagCode tagCode = new ItemTagCode();
			tagCode.setCode_bit(0);
			tagCode.setCode_name("New");
			tagCode.setTag_url("ic_new.gif");
			itemTags.add(tagCode);
			tagCount++;
		}

		if (itemTags.size() > 3) {
			return itemTags.subList(0, 3);
		}

		return itemTags;
	}

	/**
	 * @Noasoft_renewal(�߰�) 2007-10-17, �ǿ���, �߰� �Ķ���͸� ������ �� �ֵ��� ����
	 * ���ø��� ������ ��(Model)�� ��(View)�� ��´�. �信 �߰� �Ķ���͸� ������ �� �ִ�.
	 * @param model �� ��ü
	 * @param pageId ���ø� �ڵ�
	 * @param extraParam �߰� �Ķ����
	 * @return ModelAndView
	 */
	public static ModelAndView getView(Map model, String pageId, String extraParam) {
		String[] msg = StringUtils.commaDelimitedListToStringArray(HttpUtils.getMessage(pageId));
		model.put("pageId", pageId);
		model.put("pageTitle", msg[1]);
		// ������ ���� ������ �������ش�.
		if(msg[2] == null || msg[2].equals("")) {
			model.put("contentPage", "/jsp/plan/"+pageId+".jsp");
		} else {
			model.put("contentPage", msg[2]);
		}
		return new ModelAndView("forward:" + msg[0]+extraParam, model);
	}

	/**
	 * ���ø��� ������ ��(Model)�� ��(View)�� ��´�.
	 * @param model �� ��ü
	 * @param pageId ���ø� �ڵ�
	 * @return ModelAndView
	 */
	public static ModelAndView getView(Map model, String pageId) {
		String[] msg = StringUtils.commaDelimitedListToStringArray(HttpUtils.getMessage(pageId));
		model.put("pageId", pageId);
		model.put("pageTitle", msg[1]);
		// ������ ���� ������ �������ش�.
		if(msg[2] == null || msg[2].equals("")) {
			model.put("contentPage", "/jsp/plan/"+pageId+".jsp");
		} else {
			model.put("contentPage", msg[2]);
		}
		return new ModelAndView("forward:" + msg[0], model);
	}

	/**
	 * ��Ű���� ��´�.
	 * @param request ��û
	 * @param value ��Ű��
	 * @return ��Ű��
	 * @throws UnsupportedEncodingException
	 */
	public static String getCookie(HttpServletRequest request, String value) {
		return ERCookie.getCookie3(request, value);
	}

	/**
	 * ���ȸ�� ��ü ���
	 * @param request ��û
	 * @param response ����
	 * @return ���ȸ�� ��ü
	 */
	public static IniPlugin getIniPlugin(HttpServletRequest request, HttpServletResponse response) {
		try {
			IniPlugin iniPlugin = new IniPlugin(request, response, HttpUtils.getProperty("initech_prop"));
			iniPlugin.init(false);
			return iniPlugin;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param request ReferCode�� tc�ڵ� ����
	 * @param response ���� ����
	 * @return refercode
	 */
	public static void setTcCode(HttpServletRequest request, HttpServletResponse response, String ReferCode, String TcCode) {
		// Setting
		if (ReferCode != null) {
			ERCookie.addCookie2(response, "EHReferCode", ReferCode);			
		}
		if (TcCode != null) {
			ERCookie.addCookie2(response, "EHTcCode", TcCode); // Tc�ڵ� ����::�Ӻ���::2008.11.26
		} 
	}	
	
	/**
	 * @param request ���� ��û
	 * @param response ���� ����
	 * @return refercode
	 */
	public static String getReferCode(HttpServletRequest request, HttpServletResponse response) {
		String referCode = request.getParameter("ReferCode");
		String nv_refer = request.getParameter("nv_refer");
		String u_id = request.getParameter("u_id");//imbc
		if ( "shopping_tv".equals(nv_refer) ) {	//���̹� E-TV ���� ���� ��� refer_code �� ���� ����
			referCode = "544";
		}
		String cookSet = null;	

		// Setting
		if (referCode != null) {
			ERCookie.addCookie2(response, "EHReferCode", referCode);			
			ERCookie.addCookie2(response, "EHTcCode", "0000000"+referCode+"|0000000000"); // Tc�ڵ� ����::�Ӻ���::2008.11.26
			
			//��Ű���ý� Linkprice�� �����ڵ尡 �ƴϰ�, LPINFO��Ű�� �����ϸ�  LPINFO��Ű ������Ų��.
			if(referCode.equals("024")){
				String linkprice = ERCookie.getCookie3(request, "LPINFO");
				if(linkprice!=null) {
					ERCookie.removeCookie2(response, "LPINFO");
				}
			}
			
			
			if (u_id != null){//imbc
				ERCookie.addCookie2(response, "u_id", u_id);
			}
			
			cookSet = referCode;			
		} 
		// Get
		else {
			cookSet = ERCookie.getCookie3(request, "EHReferCode");

			if (cookSet == null || cookSet.equals("")) {    //trendH �� ���� ������Ű ����...(������Ű�� ���� ��쿡�� �������� üũ trendh ��� ��Ű�� 777�� ���´�.)
				if(request.getServerName().indexOf("trendh") >= 0){
					referCode = "777";
					
					ERCookie.addCookie2(response, "EHReferCode", referCode);			
					ERCookie.addCookie2(response, "EHTcCode", "0000000"+referCode+"|0000000000");
				}else{
					referCode = "";
				}   
			}
			if (referCode != null && referCode.trim().length() == 0) { // ���ü� ���� ����� ��!!!,���߿� ��������::�Ӻ���
				// linkprice
				String lpInfo = ERCookie.getCookie3(request, "LPINFO");
				if(lpInfo==null) {
					lpInfo = "";
				}
				// Ad Banner
				String adbanner = ERCookie.getCookie3(request, "ADBanner");
				if(adbanner==null) {
					adbanner = "";
				}
				// Setting Cookie(Cookie�� 2�� �� �����Ҷ� Banner�� �켱����..)
				if (adbanner.trim().length() != 0 && lpInfo.trim().length() != 0) {
					ERCookie.addCookie2(response, "EHReferCode", adbanner);
					ERCookie.addCookie2(response, "EHTcCode", "0000000"+adbanner+"|0000000000"); // Tc�ڵ� ����
					cookSet = adbanner;
				} 
				else if (adbanner.trim().length() != 0) {
					ERCookie.addCookie2(response, "EHReferCode", adbanner);
					ERCookie.addCookie2(response, "EHTcCode", "0000000"+adbanner+"|0000000000"); // Tc�ڵ� ����
					cookSet = adbanner;
				} 
				else if (lpInfo.trim().length() != 0) {
					ERCookie.addCookie2(response, "EHReferCode", "024");
					ERCookie.addCookie2(response, "EHTcCode", "0000000024|0000000000"); // Tc�ڵ� ����
					cookSet = "024";
				}
			} 
		}
		return cookSet;
	} 

	/**
	 * ��ǰ�� �̹��� ����Ʈ�� ��ȸ�Ѵ�.
	 * @param item_code ��ǰ�ڵ�
	 * @return �̹��� ����Ʈ
	 */
	public static List listTextImg(String item_code) {
		try {
			return shopMgr.listTextImg(item_code);
		} catch (ServiceException e) {
			return new ArrayList();
		}
	}

	/**
	 * ������ �з��� �÷� �׷��� �����´�.
	 * @param sectId ������̵�
	 * @return �÷� �׷�
	 */
	public static String getSectColorGroup(int sectId) {
		  /*
		   * ������̵𰣿� ���ڰ� ���Եɼ� �����Ƿ� ���ʿ� �޸��� �ٿ� ��
		   */
		  String group1 = "," + getProperty("group1") + ",";
		  String group2 = "," + getProperty("group2") + ",";
		  String group3 = "," + getProperty("group3") + ",";
		  String group4 = "," + getProperty("group4") + ",";

		  log.debug("group1 : " + group1);
		  log.debug("group2 : " + group2);
		  log.debug("group3 : " + group3);
		  log.debug("group4 : " + group4);

		  String compareString = "," + sectId + ",";
		  if (group1.indexOf(compareString) >= 0) {
		   return "1";
		  }
		  if (group2.indexOf(compareString) >= 0) {
		   return "2";
		  }
		  if (group3.indexOf(compareString) >= 0) {
		   return "3";
		  }
		  if (group4.indexOf(compareString) >= 0) {
		   return "4";
		  }
		  // �迬��:: 2007.04.13:: apprication.properties�� �������� �ʰ� ���⿡�� Į������
		  if(sectId == 62162) {
		   return "2";
		  }
		  // �Ӻ���:: 2007.04.27:: apprication.properties�� �������� �ʰ� ���⿡�� Į������
		  if(sectId == 117881) {
			   return "6";
		  }
		  return "1";
		 }

	/**
	 * ������ �α��� �������� Ȯ���Ѵ�.
	 * @param request HTTP��û
	 * @return �α����̸� true, �ƴϸ� false
	 */
	public static boolean isLogin(HttpServletRequest request) {
		String userCookie = ERCookie.getCookie3(request, "EHUserID");

		if (userCookie == null || BaseUtils.isEmpty(userCookie)
				|| BaseUtils.equals(userCookie, "_ERAnonymousUser")) {
			return false;
		} else {
			return true;
		}
	}

	public static String replaceAmpersandAndDoubleQuote(String source) {
		if (source == null)
			return null;
		source = StringUtils.replace(source, "&", "��");
		return StringUtils.replace(source, "\"", "��");
	}

	public static List listRecentViewItem(HttpServletRequest request) throws UnsupportedEncodingException {
		//log.debug("listRecentViewItem ..");
		List items = new ArrayList();

		//String itemCodeCookieString = getCookie(request, "RECENT_VIEW_ITEM_CODE");
		//String itemImageCookieString = getCookie(request, "RECENT_VIEW_ITEM_IMG");
		String itemImageString = getCookie(request, "RECENT_VIEW_ITEM");
		//  2007e00429|
		//   20079/icon2007e00429.jpg|
		//   2007f65260|
		//   20070/icon2007f65260.jpg|
		//   2004555799|
		//   20078/icon2007f26398.jpg;
		//	2007.11.11::  �ֱٺ���ǰ ����::�Ӻ���
		/*
		String cookieString = null;
		if (itemImageString != null && itemImageString != null) {
			String[] itemSplitedString = itemImageString.split("\\|");
			for (int i = 0; i < (itemSplitedString.length/2); i++) {
				cookieString = itemSplitedString[(i*2)]+"|"+ itemSplitedString[(i * 2) + 1];
				cookieString = cookieString.trim();
				Item item = new Item();
				item.setItem_code(cookieString);
				items.add(item);
			}
		}
		*/
		// 2011.03.11:: �ֱٺ���ǰ �̹��� ��� ����
		//log.debug("itemImageString:["+itemImageString+"]");
		
		String cookieString = null;
		if (itemImageString != null && !"".equals(itemImageString)) {
			String[] itemSplitedString = itemImageString.split("\\|");
			for (int i = 0; i < itemSplitedString.length; i++) {
				if ( itemSplitedString[i].toUpperCase().indexOf("JPG") >= 0 ) {//���߿� �̷����� ���ֹ�����!![2011.06.16]
					//log.debug("itemSplitedString[i]:["+itemSplitedString[i]+"]");
					continue;
				}
				cookieString = itemSplitedString[i];
				cookieString = cookieString.trim();
				Item item = new Item();
				item.setItem_code(cookieString);
				items.add(item);
				
				//log.debug("[i-->]:["+i+"]");
			}
		}
		
		//log.debug("[items.size()-->]:["+items.size()+"]");
		/*
		if (itemCodeCookieString != null && itemImageCookieString != null) {
			String[] itemCodeSplitedString = itemCodeCookieString.split("\\|");
			String[] itemImageSplitedString = itemImageCookieString.split("\\|");
			for (int i = 0; i < itemCodeSplitedString.length && i < itemImageSplitedString.length; i++) {
				Item item = new Item();
				item.setItem_code(itemCodeSplitedString[i]);
				item.setCookieIcon_img(itemImageSplitedString[i]);

				items.add(item);
			}
		}
		*/
		return items;
	}

	public static void setRecentViewItemCookie(HttpServletResponse response, List recentViewItems) {
		//log.debug("setRecentViewItemCookie..");
		
		StringBuffer itemCodeCookieString = new StringBuffer();
		//StringBuffer itemImageCookieString = new StringBuffer();

		//  2007e00429|
		//   20079/icon2007e00429.jpg|
		//   2007f65260|
		//   20070/icon2007f65260.jpg|
		//   2004555799|
		//   20078/icon2007f26398.jpg;
		//  2007f55773|20073/icon2007f55773.jpg|2007f60645|null|20075/icon2007f60645.jpg|null;
		// 2007.11.11::  �ֱٺ���ǰ ����
		// 2011.03.11:: �ֱٺ���ǰ �̹��� ����
		//log.debug("recentViewItems.size()["+recentViewItems.size()+"]");
		
		if(recentViewItems.size()>0){
			
			Iterator iterator = recentViewItems.iterator();
			String cookieString = null;
			int j = 1;
			//int check = 0;
			while (iterator.hasNext()) {
				Item item = (Item) iterator.next();
				//if(check==0){
				//	cookieString = item.getItem_code()+"|"+item.getIcon_img() + "|";
				//}
				//else {
				//	cookieString = item.getItem_code()+"|";
				//}
				//check++;
				cookieString = item.getItem_code();
				// ���� Ȯ���ڰ� �ҹ��� jpg �� �ƴ� �빮�ڰ� ���ԵǾ� ���� ���...[2011.06.16 ������..!!]
				/*if ( item.getIcon_img() != null && item.getIcon_img().indexOf("jpg") < 0 ) {
					cookieString += ( "|" + item.getIcon_img() );
				}*/
				if ( recentViewItems.size()!=j) {//�������� ������ ����.
					cookieString += "|";
				}
				cookieString = cookieString.trim();
				itemCodeCookieString.append(cookieString);
				j++;
				//log.debug("newcookie=="+cookieString+"]");
				//itemImageCookieString.append(item.getIcon_img() + "|");
			}
			//log.debug("itemCodeCookieString.length():"+itemCodeCookieString.length());
			
			if (itemCodeCookieString.length() > 0) {
				//log.debug("if itemCodeCookieString.length():"+itemCodeCookieString.length());
				//Cookie itemCodeCookie = new Cookie("RECENT_VIEW_ITEM", itemCodeCookieString.substring(0,itemCodeCookieString.length() - 1).toString());
				Cookie itemCodeCookie = new Cookie("RECENT_VIEW_ITEM", itemCodeCookieString.toString());
				itemCodeCookie.setPath("/");
				itemCodeCookie.setMaxAge(60 * 60 * 24 * 30);
				itemCodeCookie.setDomain(".hyundaihmall.com");
				response.addCookie(itemCodeCookie);
			}
		}
		

		/*
		if (itemImageCookieString.length() > 0) {
			Cookie itemImageCookie = new Cookie("RECENT_VIEW_ITEM_IMG", itemImageCookieString.substring(0,
					itemImageCookieString.length() - 1).toString());
			itemImageCookie.setPath("/");
			itemImageCookie.setMaxAge(60 * 60 * 24 * 30);
			itemImageCookie.setDomain("hyundaihmall.com");
			response.addCookie(itemImageCookie);
		}
		*/
	}

	/*--------------------------------------------------------------------*/
	// �����ð�����(return format : 2004. 04. 02(��) ���� 6��)
	//----------------------------------------------------------------------/
	public static String getPlainDateTime()
	{
		java.text.SimpleDateFormat simDate = new java.text.SimpleDateFormat("yyyy. MM. dd(EEE) a hh:mm:ss", java.util.Locale.KOREA);
		return simDate.format(new java.util.Date());
	}

	/*--------------------------------------------------------------------*/
	// �����ð�����(return format:yyyy.MM.dd.HH.mm.ss)
	//----------------------------------------------------------------------/
	private static String getPlainDateTime2()
	{
		java.text.SimpleDateFormat simDate = new java.text.SimpleDateFormat("yyyy.MM.dd.HH.mm.ss", java.util.Locale.KOREA);
		return simDate.format(new java.util.Date());
	}

	/*--------------------------------------------------------------------*/
	// �����ð�����(return format:yyyyMMddHHmmss)
	//----------------------------------------------------------------------/
	private static String getPlainDateTime3()
	{
		java.text.SimpleDateFormat simDate = new java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.KOREA);
		return simDate.format(new java.util.Date());
	}
	
	/*--------------------------------------------------------------------*/
	// �����ð�����(return format:yyyyMMddHHmmss)
	//----------------------------------------------------------------------/
	public static String getPlainDateTime4() 
	{
		java.text.SimpleDateFormat simDate = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
		return simDate.format(new java.util.Date());
	}	

	
	/**
	 * Ư�� URL�� ȣ���Ͽ� ����� ���ڿ��� �޾ƿ´�.
	 * @param url ��û URL
	 * @return ��� ��Ʈ��
	 */
	public static String getHtml(String url) {
		String content = "";
		try {
			URL source = new URL(url);
			URLConnection sconnection = source.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(sconnection.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null)
				content += inputLine;
			in.close();
		} catch (MalformedURLException me) {
			log.debug("URL�� �߸��Ǿ����ϴ�.\n" + me.toString());
			return null;
		} catch (IOException ioe) {
			log.debug("���� I/O�� ������ �߻��߽��ϴ�.\n" + ioe.toString());
			return null;
		} catch (Exception e) {
			log.debug("������ �߻��߽��ϴ�.\n" + e.toString());
			return null;
		}
		return content;
	}

	/**
	 * ���ο��θ� Ȯ����.(19���̻�)
	 * @param request HTTP��û
	 * @return �����̸� true, �ƴϸ� false
	 */
	public static boolean isAdultYn(HttpServletRequest request) {
		Cookie ssnCookie = null;
		String custOld = null;
		int customerYear = -1;
		
		String custSSN_total = ERCookie.getCookie3(request, "EHSSNTOTAL");
		String [] custSSN_imsi = custSSN_total.split("[|]");

		// �α��� ���� Ȯ��
		if(!isLogin(request)) {
			return false;
		}
		//log.warn("isLogin====>Y");
		// �ֹι�ȣ�� ������
		//ssnCookie = WebUtils.getCookie(request, "EHSSN");
		//String test = ERCookie.getCookie3(request, "EHSSN");
		String ssnCookie1 = custSSN_imsi[0];
		String ssnynCookie1 = custSSN_imsi[1];
		try {
			//custOld = ssnCookie.getValue();
			custOld = ssnCookie1;

//			***************************************
			String ssn_yn = null;
			//if ( ERCookie.getCookie3(request, "EHSSN_YN") == null )
			if ( ssnynCookie1 == null )
			{
				////log.warn("WebUtils.getCookie(request �� null ");
			}
			else
			{
				//ssn_yn = ERCookie.getCookie3(request, "EHSSN_YN");
				ssn_yn = ssnynCookie1;
				//log.warn("WebUtils.getCookie(request �� null �� �ƴ� ");
			}
			if ( "Y".equals(ssn_yn) )
			{
		        BASE64Decoder decoder = new BASE64Decoder();
		        String D_ssn = null;
		        String E_ssn = null;

		        D_ssn = custOld;

		        byte[] b2;

				b2 = decoder.decodeBuffer(D_ssn);
				E_ssn = new String(b2);

				custOld = E_ssn;

				//log.warn("D_ssn => ["+ D_ssn +"]");
				//log.warn("E_ssn => ["+ E_ssn +"]");
			}
//			****************************************

		} catch (NullPointerException e) {
			//log.warn("������ �߻��߽��ϴ�.\n" + e.toString());
			return false;
		} catch (IOException e) {
			//log.warn("������ �߻��߽��ϴ�.\n" + e.toString());
			return false;
		}
		if (ssnCookie1 == null || BaseUtils.isEmpty(custOld)) {
			return false;
		}
		//log.warn("custOld====>" + custOld);
		// ��Ű��ȿ�� ����
		//if (!BaseUtils.isNumeric(custOld) || custOld.length() !=13) {
		if (custOld.length() !=13) {
			return false;
		}
		//log.warn("����(0.6)==>"+custOld.substring(0,6));
		//log.warn("����(6)==>"+custOld.substring(6));
		// ���̰��
		customerYear = CustomerUtil.getCustomerYear(custOld.substring(0,6), custOld.substring(6));
		//log.warn("����==>"+customerYear);
		if (customerYear == -1) {
			return false;
		}
		if(customerYear >= 19) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * û�ҳ� ���θ� Ȯ����.(14~19�� �ش�)
	 * @param request HTTP��û
	 * @return û�ҳ��̸� true, �ƴϸ� false
	 */
	public static boolean isAdultNoYn(HttpServletRequest request) {
		Cookie ssnCookie = null;
		String custOld = null;
		int customerYear = -1;
		
		String custSSN_total = ERCookie.getCookie3(request, "EHSSNTOTAL");
		String [] custSSN_imsi = custSSN_total.split("[|]");

		// �α��� ���� Ȯ��
		if(!isLogin(request)) {
			return false;
		}
		//log.warn("isLogin====>Y");
		// �ֹι�ȣ�� ������
		//ssnCookie = WebUtils.getCookie(request, "EHSSN");
		//String test = ERCookie.getCookie3(request, "EHSSN");
		String ssnCookie1 = custSSN_imsi[0];
		String ssnynCookie1 = custSSN_imsi[1];
		try {
			//custOld = ssnCookie.getValue();
			custOld = ssnCookie1;

//			***************************************
			String ssn_yn = null;
			//if ( ERCookie.getCookie3(request, "EHSSN_YN") == null )
			if ( ssnynCookie1 == null )
			{
				//log.warn("WebUtils.getCookie(request �� null ");
			}
			else
			{
				//ssn_yn = ERCookie.getCookie3(request, "EHSSN_YN");
				ssn_yn = ssnynCookie1;
				//log.warn("WebUtils.getCookie(request �� null �� �ƴ� ");
			}
			if ( "Y".equals(ssn_yn) )
			{
		        BASE64Decoder decoder = new BASE64Decoder();
		        String D_ssn = null;
		        String E_ssn = null;

		        D_ssn = custOld;

		        byte[] b2;

				b2 = decoder.decodeBuffer(D_ssn);
				E_ssn = new String(b2);

				custOld = E_ssn;

				//log.warn("D_ssn => ["+ D_ssn +"]");
				//log.warn("E_ssn => ["+ E_ssn +"]");
			}
//			****************************************

		} catch (NullPointerException e) {
			//log.warn("������ �߻��߽��ϴ�.\n" + e.toString());
			return false;
		} catch (IOException e) {
			//log.warn("������ �߻��߽��ϴ�.\n" + e.toString());
			return false;
		}
		if (ssnCookie1 == null || BaseUtils.isEmpty(custOld)) {
			return false;
		}
		//log.warn("custOld====>" + custOld);
		// ��Ű��ȿ�� ����
		//if (!BaseUtils.isNumeric(custOld) || custOld.length() !=13) {
		if (custOld.length() !=13) {
			return false;
		}
		//log.warn("����(0.6)==>"+custOld.substring(0,6));
		//log.warn("����(6)==>"+custOld.substring(6));
		// ���̰��
		customerYear = CustomerUtil.getCustomerYear(custOld.substring(0,6), custOld.substring(6));
		//log.warn("����==>"+customerYear);
		if (customerYear == -1) {
			return false;
		}
		if(customerYear >= 14 && customerYear <= 19) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @Noasoft_renewal(�߰�) 2007-10-23, �ӱ��, ��Ű���� ����ID�� �����´�. ��α����̸� null �� ������.
	 */
	public static String getUserName(HttpServletRequest request)
	{
		if (isLogin(request)){
			return getCookie(request, "EHCustName");
		}
		else{
			return "";
		}
	}



	 /**
	  * HTML TAG�� ������. (�̽±�)
	  * ���� <style> *** </style> ������ ��Ÿ�� ���鵵 ������.
	  * @param orgString
	  * @return
	  */
	 public static String stripHTMLTags(String orgString){
	  String filteredString;

	  Pattern p;
	  Matcher m;
	  // �ҹ��ڷ� ����
	  orgString = orgString.toLowerCase();
	     // <style> aaa</style> ���̿� �ִ� aaa���� ����
	  p = Pattern.compile("<style\\b[^>]*>(.*?)</style>");
	  m = p.matcher(orgString);
	  filteredString =m.replaceAll("");
	  // <a> bbb</a>���� bbb�� ���ܳ��� <a> </a> ����
	     p = Pattern.compile("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>");
	  m = p.matcher(filteredString);

	     filteredString = m.replaceAll("");

	     filteredString = filteredString.trim();
	     return filteredString;
	 }

	 /**
	  * top �޴��� �׸�
	  * @param orgString
	  * @return
	  */
	 public static String getTopDepthSectDisp
	     (List depth2CatalogList, 
	      List leftCatalogList,
	      Sect sect, 
	      Sect depth2SectId, 
	      Sect depth3SectId,
	      Sect depth4SectId){

		 String strHtml2 = "";
		int forcnt = 0;
		String selectTitle = "";
		String strSubHtml = "";
		String strGubun = "";
		String UrlLink = "";
		String subUrlLink = "";
		Iterator depth2CatalogListL = null;
		Iterator leftCatalogListL = null;
		Iterator childiterator = null;
		Sect sect2 = null;
		Sect sect3 = null;
		List dataNodeChildList = null;
		Sect childSect = null;
		String rethtml = "";
		String strHtmlk = "";
		String strHtmlp = "";
		String strHtmlx = "";
			
		/*=============================
		 * TopMenuSelectBoxHome() Start
		 ==============================*/		
		 if (depth2SectId != null) {
			 // �Ϲݸ���
			 log.debug("depth2SectId.getSect_id()===>["+depth2SectId.getSect_id()+"]");	 
			 if(depth2SectId.getSect_id()!= 1003) {							 
				strHtml2 = "<div class='selectbox1'>";
				if(depth2CatalogList.size() > 0 && depth2CatalogList !=null){
					depth2CatalogListL = depth2CatalogList.iterator();	
					while (depth2CatalogListL.hasNext()) {						
						sect2 = (Sect) depth2CatalogListL.next(); // 2depth����						
						selectTitle = substring(sect2.getSect_name(),24); // title
						if("".equals(sect2.getLink_url()) || sect2.getLink_url()==null){
							UrlLink = "/front/shSectR.do?SectID="+sect2.getSect_id();
						}
						else{
							UrlLink = sect2.getLink_url();
						}						
						if(sect2.getSect_id() == depth2SectId.getSect_id()){
							strHtml2 += "<div id='CategoryNavi' style='position:relative;overflow:hidden;' onMouseover='view_div_view1(Lcategory)' onmouseleave='view_div_view1(Lcategory)' onclick='window.location.href(\""+UrlLink+"\");return false;'><p>"+selectTitle+"</p></div>\n";
							strHtml2 += "<div style='float:left;'><img src='"+HttpUtils.getProperty("img_root")+"/common/layout/top_input_right_03.gif' align='top' onmouseover='view_div_view1(Lcategory)' onmouseleave='view_div_view1(Lcategory)' class='img_hand' /></div>\n";
							strHtml2 += "<div style='clear:left;' >\n";
							strHtml2 += "<div id='Lcategory' onmouseover='this.style.display=\"block\"' onmouseleave='this.style.display=\"none\"'>\n";
							strHtml2 += "<div class='categorylist_div'>\n";
							strHtml2 += "<ul>\n";
						}
						strHtmlk += "<li onmouseover='javascript:navilist_over(this,\"over\");' onmouseout='javascript:navilist_over(this,\"out\");' onclick='window.location.href(\""+UrlLink+"\");return false;'><p>"+selectTitle+"</p></li>\n";
						forcnt ++;
					}	
				}
				strHtmlk += "</ul>\n";
				strHtmlk += "</div>\n";
				strHtmlk += "</div>\n";
				strHtmlk += "</div>\n";
				strHtmlk += "</div>\n";
		
				rethtml+= "<div id='depth1'>"+strHtml2+strHtmlk+"</div>\n"; 
				
				if (depth3SectId == null) {					
					if(leftCatalogList.size() > 0 && leftCatalogList !=null){
						strGubun =  "<div id='gubun2' class='selectbox1'><p class='bar'>&nbsp;>&nbsp;</p></div>\n";
						rethtml+= strGubun;
						strSubHtml += "<div class='selectbox1'>\n";
						strSubHtml += "<div id='CategoryNavi' style='position:relative;overflow:hidden;' onMouseover='view_div_view1(Mcategory)' onmouseleave='view_div_view1(Mcategory)' ><p>�����ϼ���</p></div>\n";
						strSubHtml += "<div style='float:left;'><img src='"+HttpUtils.getProperty("img_root")+"/common/layout/top_input_right_03.gif' align='top' onmouseover='view_div_view1(Mcategory)' onmouseleave='view_div_view1(Mcategory)' class='img_hand' /></div>\n";
						strSubHtml += "<div style='clear:left;' >\n";
						strSubHtml += "<div id='Mcategory' onmouseover='this.style.display=\"block\"' onmouseleave='this.style.display=\"none\"'>\n";
						strSubHtml += "<div class='categorylist_div'>\n";
						strSubHtml += "<ul>";
						leftCatalogListL = leftCatalogList.iterator(); // 3depth�� ������.
						while (leftCatalogListL.hasNext()) {
							sect3 = (Sect) leftCatalogListL.next();
							if(!"I".equals(sect3.getGubun())){
								selectTitle = substring(sect3.getSect_name(),24); // title
								if("".equals(sect3.getLink_url()) || sect3.getLink_url()==null){
									subUrlLink = "/front/shSectR.do?SectID="+sect3.getSect_id();
								}
								else{
									subUrlLink = sect3.getLink_url();
								}	
								log.debug("-----depth3SectId(null)--------"+sect3.getSect_id()+"---------------------"+subUrlLink);
								
								strSubHtml += "<li onmouseover='javascript:navilist_over(this,\"over\");' onmouseout='javascript:navilist_over(this,\"out\");' onclick='window.location.href(\""+subUrlLink+"\");return false;'><p>"+selectTitle+"</p></li>\n";
							}
						}
						strSubHtml += "</ul>\n";
						strSubHtml += "</div>\n";
						strSubHtml += "</div>\n";
						strSubHtml += "</div>\n";
						strSubHtml += "</div>\n";
						rethtml+="<div id='depth2'>"+strSubHtml+"</div>\n";
					}
				}				
			 }
			 // TVȨ����
			 else {
				 rethtml +="<div id='depth1'></div>\n";
				 rethtml +=	"<div id='gubun2' class='selectbox1'></div>\n";
				 rethtml = "<div id='depth2'>\n";
				 rethtml = "<select class='div_select' id='selectdepth2' style='height:20px;width:150;OVERFLOW-X:hidden');'>\n";
				 rethtml += "<option value='1003' selected> ����Ȩ����</option>\n";	
				 rethtml += "</select>\n";
				 rethtml +="</div>\n"; 
				 rethtml +="<div id='gubun3' class='selectbox1'></div>\n";
				 rethtml +=	"<div id='depth3'></div>\n";
				 return rethtml;
			 }
		 }
		 else {
			 rethtml +="<div id='depth1'></div>\n";
			 rethtml +=	"<div id='gubun2' class='selectbox1'></div>\n";
			 rethtml = "<div id='depth2'>\n";
			 rethtml +="</div>\n"; 
			 rethtml +="<div id='gubun3' class='selectbox1'></div>\n";
			 rethtml +=	"<div id='depth3'></div>\n";
			 return rethtml;
		 }
		 /*=============================
		 * TopMenuSelectBoxHome() End
		 ==============================*/

		 /*=============================
		 * TopMenuSelectBoxHome1() Start
		 ==============================*/
		 if (depth3SectId != null) {
			 forcnt = 0;
			 strHtml2 = "<div class='selectbox1'>\n";			
		
			if(leftCatalogList.size() > 0 && leftCatalogList !=null){
				leftCatalogListL = leftCatalogList.iterator(); // 3depth�� ������.
				while (leftCatalogListL.hasNext()) {
					sect3 = (Sect) leftCatalogListL.next();
					if(!"I".equals(sect3.getGubun())){
						selectTitle = substring(sect3.getSect_name(),24); // title
						if("".equals(sect3.getLink_url()) || sect3.getLink_url()==null){
							UrlLink = "/front/shSectR.do?SectID="+sect3.getSect_id();
						}
						else{
							UrlLink = sect3.getLink_url();
						}
						if(sect3.getSect_id() == depth3SectId.getSect_id()){
							strHtml2 += "<div id='CategoryNavi' style='position:relative;overflow:hidden;' onMouseover='view_div_view1(Mcategory_store)' onmouseleave='view_div_view1(Mcategory_store)' onclick='window.location.href(\""+UrlLink+"\");return false;'><p>"+selectTitle+"</p></div>\n";
							strHtml2 += "<div style='float:left;'><img src='"+HttpUtils.getProperty("img_root")+"/common/layout/top_input_right_03.gif' align='top' onmouseover='view_div_view1(Mcategory_store)' onmouseleave='view_div_view1(Mcategory_store)' class='img_hand' /></div>\n";
							strHtml2 += "<div style='clear:left;' >\n";
							strHtml2 += "<div id='Mcategory_store' onmouseover='this.style.display=\"block\"' onmouseleave='this.style.display=\"none\"'>\n";
							strHtml2 += "<div class='categorylist_div'>\n";
							strHtml2 += "<ul>";
							dataNodeChildList = sect3.getLnkChildSect();
						}
						log.debug("-----depth3SectId--------"+sect3.getSect_id()+"---------------------"+UrlLink);
						
						strHtmlx += "<li onmouseover='javascript:navilist_over(this,\"over\");' onmouseout='javascript:navilist_over(this,\"out\");' onclick='window.location.href(\""+UrlLink+"\");return false;'><p>"+selectTitle+"</p></li>\n";
						forcnt ++;
					}
				}
			}
			strHtmlx += "</ul>\n";
			strHtmlx += "</div>\n";
			strHtmlx += "</div>\n";
			strHtmlx += "</div>\n";
			strHtmlx += "</div>\n";
		
			strGubun = "<div id='gubun2' class='selectbox1'><p class='bar'>&nbsp;>&nbsp;</p></div>\n";
			rethtml	+= strGubun;
			rethtml += "<div id='depth2'>"+strHtml2+strHtmlx+"</div>\n"; 
			
			if (depth4SectId == null) {
				if(dataNodeChildList !=null && dataNodeChildList.size() > 0){	
					strGubun = "<div id='gubun3' class='selectbox1'><p class='bar'>&nbsp;>&nbsp;</p></div>\n";
					rethtml	+= strGubun;
					strSubHtml += "<div class='selectbox1'>\n";
					strSubHtml += "<div id='CategoryNavi' style='position:relative;overflow:hidden;' onMouseover='view_div_view1(Scategory)' onmouseleave='view_div_view1(Scategory)' ><p>�����ϼ���</p></div>\n";
					strSubHtml += "<div style='float:left;'><img src='"+HttpUtils.getProperty("img_root")+"/common/layout/top_input_right_03.gif' align='top' onmouseover='view_div_view1(Scategory)' onmouseleave='view_div_view1(Scategory)' class='img_hand' /></div>\n";
					strSubHtml += "<div style='clear:left;' >\n";
					strSubHtml += "<div id='Scategory' onmouseover='this.style.display=\"block\"' onmouseleave='this.style.display=\"none\"'>\n";
					strSubHtml += "<div class='categorylist_div'>\n";
					strSubHtml += "<ul>";
					
					childiterator = dataNodeChildList.iterator();
		
					while (childiterator.hasNext()) {
						childSect = (Sect)childiterator.next();
						log.debug("=============================================>>");
						log.debug("childSect.getSect_name():"+childSect.getSect_name());
						log.debug("selectTitle:"+selectTitle);
						
						log.debug("=============================================>>");
						if(childSect.getSect_name()==null){
							selectTitle = ""; // title
						}else{
							selectTitle = substring(childSect.getSect_name(),24); // title
						}
						
						if("".equals(childSect.getLink_url()) || childSect.getLink_url()==null){
							subUrlLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
						}
						else{
							subUrlLink = childSect.getLink_url();
						}
						log.debug("-----depth4SectId(null)--------"+childSect.getSect_id()+"---------------------"+subUrlLink);
						strSubHtml += "<li onmouseover='javascript:navilist_over(this,\"over\");' onmouseout='javascript:navilist_over(this,\"out\");' onclick='window.location.href(\""+subUrlLink+"\");return false;'><p>"+selectTitle+"</p></li>\n";
					}
					strSubHtml += "</ul>\n";
					strSubHtml += "</div>\n";
					strSubHtml += "</div>\n";
					strSubHtml += "</div>\n";
					strSubHtml += "</div>\n";
					
					rethtml += "<div id='depth3'>"+strSubHtml+"</div>\n"; 
				}	
			}
		 }
		/*=============================
		 * TopMenuSelectBoxHome1() End
		 ==============================*/

		/*=============================
		 * TopMenuSelectBoxHome2() Start
		 ==============================*/
		 if (depth4SectId != null) {
			 forcnt = 0;
			
			 strHtml2 = "<div class='selectbox1'>\n";
			 if(dataNodeChildList !=null && dataNodeChildList.size() > 0){					
				childiterator = dataNodeChildList.iterator();
	
				while (childiterator.hasNext()) {
					childSect = (Sect)childiterator.next();
					selectTitle = substring(childSect.getSect_name(),24); // title
					if("".equals(childSect.getLink_url()) || childSect.getLink_url()==null){
						subUrlLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
					}
					else{
						subUrlLink = childSect.getLink_url();
					}
					
					if(childSect.getSect_id() == depth4SectId.getSect_id()){
						strHtml2 += "<div id='CategoryNavi' style='position:relative;overflow:hidden;' onMouseover='view_div_view1(Scategory)' onmouseleave='view_div_view1(Scategory)' onclick='window.location.href(\""+subUrlLink+"\");return false;'><p>"+selectTitle+"</p></div>\n";
						strHtml2 += "<div style='float:left;'><img src='"+HttpUtils.getProperty("img_root")+"/common/layout/top_input_right_03.gif' align='top' onmouseover='view_div_view1(Scategory)' onmouseleave='view_div_view1(Scategory)' class='img_hand' /></div>\n";
						strHtml2 += "<div style='clear:left;' >\n";
						strHtml2 += "<div id='Scategory' onmouseover='this.style.display=\"block\"' onmouseleave='this.style.display=\"none\"'>\n";
						strHtml2 += "<div class='categorylist_div'>\n";
						strHtml2 += "<ul>\n";
					}
					strHtmlp += "<li onmouseover='javascript:navilist_over(this,\"over\");' onmouseout='javascript:navilist_over(this,\"out\");' onclick='window.location.href(\""+subUrlLink+"\");return false;'><p>"+selectTitle+"</p></li>\n";
		
					forcnt ++;
				}
					
			}				
			strHtmlp += "</ul>\n";
			strHtmlp += "</div>\n";
			strHtmlp += "</div>\n";
			strHtmlp += "</div>\n";
			strHtmlp += "</div>\n";
			strGubun = "<div id='gubun3' class='selectbox1'><p class='bar'>&nbsp;>&nbsp;</p></div>\n";
			rethtml	+= strGubun;
			
			rethtml += "<div id='depth3'>"+strHtml2+strHtmlp+"</div>\n"; 
		 }	
		 /*=============================
		 * TopMenuSelectBoxHome2() End
		 ==============================*/
		 
		 return rethtml;
	 }
	 
	 
	 public static String getTopDepthSectDispLayer(List depth2CatalogList,List leftCatalogList,Sect sect,Sect depth2SectId, Sect depth3SectId,Sect depth4SectId){

		int forcnt = 0;
		String selectTitle = "";
		String UrlLink = "";
		String subUrlLink = "";
		Iterator depth2CatalogListL = null;
		Iterator leftCatalogListL = null;
		Iterator childiterator = null;
		Sect sect2 = null;
		Sect sect3 = null;
		List dataNodeChildList = null;
		Sect childSect = null;
		String rethtml = "";
		
		int layerSize = 20;
		String strHtmlTop ="";
		String strLayer2 = "";
		String strLayer3 = "";
		String strLayer4 = "";
		int    depth2CatalogListSize = 0;
		int    depth3CatalogListSize = 0;
		int    depth4CatalogListSize = 0;
		

			
		/*=============================
		 * TopMenuSelectBoxHome() Start
		 ==============================*/		
		 if (depth2SectId != null) {
			 // �Ϲݸ���
			 log.debug("depth2SectId.getSect_id()===>["+depth2SectId.getSect_id()+"]");	 
			 if(depth2SectId.getSect_id()!= 1003) {
				 strHtmlTop += "<div id=\"loc_parent\">"; 
				 strHtmlTop += "<a href=\""+HttpUtils.getProperty("_ERUrlHmall")+"\">Home</a>&nbsp;"; 

				if(depth2CatalogList.size() > 0 && depth2CatalogList !=null){
					depth2CatalogListSize = depth2CatalogList.size();
					strLayer2 +="<div id=\"loc_2depth_div\" class=\"loc_child_box\">";
					depth2CatalogListL = depth2CatalogList.iterator();	
					while (depth2CatalogListL.hasNext()) {						
						sect2 = (Sect) depth2CatalogListL.next(); // 2depth����						
						// selectTitle = substring(sect2.getSect_name(),24); // title
						selectTitle = sect2.getSect_name();
						if("".equals(sect2.getLink_url()) || sect2.getLink_url()==null){
							UrlLink = "/front/shSectR.do?SectID="+sect2.getSect_id();
						}
						else{
							UrlLink = sect2.getLink_url();
						}						
						if(sect2.getSect_id() == depth2SectId.getSect_id()){
							strHtmlTop += "<img src='"+HttpUtils.getProperty("img_root")+"/common/category/loc_next.gif' alt =''/>\n";
							strHtmlTop += "<a id=\"loc_2depth\" href=\""+UrlLink+"\" class=\"loc_drop_chk loc_drop\"><span>"+selectTitle+"</span></a>\n"; 
						}
						if(forcnt % layerSize ==0){
							if(forcnt!=0 ){
								strLayer2 +="</div>";
							}
							if(forcnt + layerSize < depth2CatalogListSize ){
								strLayer2 += "<div class=\"loc_child_div\">"; 
							}else{
								strLayer2 += "<div class=\"loc_child_div loc_child_div_none\">";
							}
						}
						strLayer2 += "<a href=\""+UrlLink+"\" class=\"loc_child\">"+selectTitle+"</a>\n";
						forcnt ++;
					}
					strLayer2 +="</div></div>";
				}
				
				if (depth3SectId == null) {					
					if(leftCatalogList.size() > 0 && leftCatalogList !=null){
						forcnt =0;
						// 3depth Size
						leftCatalogListL = leftCatalogList.iterator();
						while (leftCatalogListL.hasNext()) {
							sect3 = (Sect) leftCatalogListL.next();
							if(!"I".equals(sect3.getGubun())){
								depth3CatalogListSize++;
							}
						}
						// 3depth�� ������
						strHtmlTop += "<img src='"+HttpUtils.getProperty("img_root")+"/common/category/loc_next.gif' alt =''/>\n";
						strHtmlTop += "<a id=\"loc_3depth\" href=\"javascript:;\" class=\"loc_drop_chk loc_drop\"><span>�����ϼ���</span></a>\n";
						
						strLayer3 +="<div id=\"loc_3depth_div\" class=\"loc_child_box\">";
						leftCatalogListL = leftCatalogList.iterator(); 
						while (leftCatalogListL.hasNext()) {
							sect3 = (Sect) leftCatalogListL.next();
							if(!"I".equals(sect3.getGubun())){
								// selectTitle = substring(sect3.getSect_name(),24); // title
								selectTitle = sect3.getSect_name();
								if("".equals(sect3.getLink_url()) || sect3.getLink_url()==null){
									subUrlLink = "/front/shSectR.do?SectID="+sect3.getSect_id();
								}
								else{
									subUrlLink = sect3.getLink_url();
								}
								
								if(forcnt % layerSize ==0){
									if(forcnt!=0 ){
										strLayer3 +="</div>";
									}
									if(forcnt + layerSize < depth3CatalogListSize ){
										strLayer3 += "<div class=\"loc_child_div\">"; 
									}else{
										strLayer3 += "<div class=\"loc_child_div loc_child_div_none\">";
									}
								}
								strLayer3 += "<a href=\""+subUrlLink+"\" class=\"loc_child\">"+selectTitle+"</a>\n";
								forcnt ++;
							}
						}
						strLayer3 +="</div></div>";
					}
				}				
			 }else { // TVȨ����
				 rethtml +="<div id='depth1'></div>\n";
				 rethtml +=	"<div id='gubun2' class='selectbox1'></div>\n";
				 rethtml = "<div id='depth2'>\n";
				 rethtml = "<select class='div_select' id='selectdepth2' style='height:20px;width:150;OVERFLOW-X:hidden');'>\n";
				 rethtml += "<option value='1003' selected> ����Ȩ����</option>\n";	
				 rethtml += "</select>\n";
				 rethtml +="</div>\n"; 
				 rethtml +="<div id='gubun3' class='selectbox1'></div>\n";
				 rethtml +=	"<div id='depth3'></div>\n";
				 return rethtml;
			 }
		 }
		 /*=============================
		 * TopMenuSelectBoxHome() End
		 ==============================*/
		 /*=============================
		 * TopMenuSelectBoxHome1() Start
		 ==============================*/
		 if (depth3SectId != null) {
			if(leftCatalogList.size() > 0 && leftCatalogList !=null){
				forcnt = 0;
				// 3depth Size
				leftCatalogListL = leftCatalogList.iterator();
				while (leftCatalogListL.hasNext()) {
					sect3 = (Sect) leftCatalogListL.next();
					if(!"I".equals(sect3.getGubun())){
						depth3CatalogListSize++;
					}
				}
				// 3depth�� ������.
				strLayer3 +="<div id=\"loc_3depth_div\" class=\"loc_child_box\">";
				leftCatalogListL = leftCatalogList.iterator(); 
				while (leftCatalogListL.hasNext()) {
					sect3 = (Sect) leftCatalogListL.next();
					if(!"I".equals(sect3.getGubun())){
						// selectTitle = substring(sect3.getSect_name(),24); // title
						selectTitle = sect3.getSect_name(); // title
						if("".equals(sect3.getLink_url()) || sect3.getLink_url()==null){
							subUrlLink = "/front/shSectR.do?SectID="+sect3.getSect_id();
						}
						else{
							subUrlLink = sect3.getLink_url();
						}
						if(sect3.getSect_id() == depth3SectId.getSect_id()){
							strHtmlTop += "<img src='"+HttpUtils.getProperty("img_root")+"/common/category/loc_next.gif' alt =''/>\n";
							strHtmlTop += "<a id=\"loc_3depth\" href=\""+subUrlLink+"\" class=\"loc_drop_chk loc_drop\"><span>"+selectTitle+"</span></a>\n";
							dataNodeChildList = sect3.getLnkChildSect();
						}
						if(forcnt % layerSize ==0){
							if(forcnt!=0 ){
								strLayer3 +="</div>";
							}
							if(forcnt + layerSize < depth3CatalogListSize ){
								strLayer3 += "<div class=\"loc_child_div\">"; 
							}else{
								strLayer3 += "<div class=\"loc_child_div loc_child_div_none\">";
							}
						}
						strLayer3 += "<a href=\""+subUrlLink+"\" class=\"loc_child\">"+selectTitle+"</a>\n";
						forcnt ++;
					}
				}
				strLayer3 +="</div></div>";
			}
			
			if (depth4SectId == null) {
				if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
					depth4CatalogListSize = dataNodeChildList.size();
					forcnt =0;
					// 3depth�� ������
					strHtmlTop += "<img src='"+HttpUtils.getProperty("img_root")+"/common/category/loc_next.gif' alt =''/>\n";
					strHtmlTop += "<a id=\"loc_4depth\" href=\"javascript:;\" class=\"loc_drop_chk loc_drop\"><span>�����ϼ���</span></a>\n";
					strLayer4 +="<div id=\"loc_4depth_div\" class=\"loc_child_box\">";
					childiterator = dataNodeChildList.iterator();
					while (childiterator.hasNext()) {
						childSect = (Sect)childiterator.next();
						if(!"I".equals(childSect.getGubun())){
							log.debug("=============================================>>");
							log.debug("childSect.getSect_name():"+childSect.getSect_name());
							log.debug("selectTitle:"+selectTitle);
							
							log.debug("=============================================>>");
							if(childSect.getSect_name()==null){
								selectTitle = ""; // title
							}else{
								// selectTitle = substring(childSect.getSect_name(),24); // title
								selectTitle = childSect.getSect_name(); // title
							}
							
							if("".equals(childSect.getLink_url()) || childSect.getLink_url()==null){
								subUrlLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
							}
							else{
								subUrlLink = childSect.getLink_url();
							}
							if(forcnt % layerSize ==0){
								if(forcnt!=0 ){
									strLayer4 +="</div>";
								}
								if(forcnt + layerSize < depth4CatalogListSize ){
									strLayer4 += "<div class=\"loc_child_div\">"; 
								}else{
									strLayer4 += "<div class=\"loc_child_div loc_child_div_none\">";
								}
							}
							strLayer4 += "<a href=\""+subUrlLink+"\" class=\"loc_child\">"+selectTitle+"</a>\n";
							forcnt ++;
						}
					}
					strLayer4 +="</div></div>";
				}	
			}
		 }
		/*=============================
		 * TopMenuSelectBoxHome1() End
		 ==============================*/
		
		/*=============================
		 * TopMenuSelectBoxHome2() Start
		 ==============================*/
		 if (depth4SectId != null) {
			 forcnt = 0;
			 if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
				depth4CatalogListSize = dataNodeChildList.size();
				childiterator = dataNodeChildList.iterator();
				strLayer4 +="<div id=\"loc_4depth_div\" class=\"loc_child_box\">";
				while (childiterator.hasNext()) {
					childSect = (Sect)childiterator.next();
					//selectTitle = substring(childSect.getSect_name(),24); // title
					if(!"I".equals(childSect.getGubun())){
						selectTitle = childSect.getSect_name(); // title
						if("".equals(childSect.getLink_url()) || childSect.getLink_url()==null){
							subUrlLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
						}
						else{
							subUrlLink = childSect.getLink_url();
						}
						
						if(childSect.getSect_id() == depth4SectId.getSect_id()){
							strHtmlTop += "<img src='"+HttpUtils.getProperty("img_root")+"/common/category/loc_next.gif' alt =''/>\n";
							strHtmlTop += "<a id=\"loc_4depth\" href=\""+subUrlLink+"\" class=\"loc_drop_chk loc_drop\"><span>"+selectTitle+"</span></a>\n";
						}
						
						if(forcnt % layerSize ==0){
							if(forcnt!=0 ){
								strLayer4 +="</div>";
							}
							if(forcnt + layerSize < depth4CatalogListSize ){
								strLayer4 += "<div class=\"loc_child_div\">"; 
							}else{
								strLayer4 += "<div class=\"loc_child_div loc_child_div_none\">";
							}
						}
						strLayer4 += "<a href=\""+subUrlLink+"\" class=\"loc_child\">"+selectTitle+"</a>\n";
						forcnt ++;
					}
				}
				strLayer4 +="</div></div>";	
			}				
			
		 }	
		 /*=============================
		 * TopMenuSelectBoxHome2() End
		 ==============================*/
		 log.debug("strLayer2 : " + strLayer2);
		 rethtml = 	strHtmlTop + "\n" + strLayer2 +  "\n" + strLayer3 +"\n" + strLayer4 +"\n";
		 rethtml +="</div>";	 
		 return rethtml;
	 }
	 
	 /**
	  * ��ȭ�� �귣����� ��ܸ޴�
	  * @param leftCatalogList
	  * @param code_grp
	  * @return
	  */
	 public static String brandTopMenuList(List leftCatalogList, String code_grp){
		  
		 String strHtmlLeft = "";
		 List dataNodeChildList = null;
		 Sect sect = null;
		 Sect childSect = null;
		 Iterator iterator = null;
		 Iterator childiterator = null;
		 String btrueLink = "";
		 String trueLink = "";
		 String tagImgS = "";
		 String tmpid = "";
		 String imgUrl = "";
		 
		 iterator = leftCatalogList.iterator();
		 
		 // ��ȭ�� �귣�� ����
		 if (code_grp.equals("BRAND")) {
			   strHtmlLeft += "<div id='category_07'>\n";  
							   		   
			   while (iterator.hasNext()) {
					// parentSect
					sect = (Sect) iterator.next();
										
					// childSect
					dataNodeChildList = sect.getLnkChildSect();
										
					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
									
					
					
					log.debug("####################imgUrl"+imgUrl);
					
					if("A".equals(sect.getGubun())){
						
						strHtmlLeft += "<dl>\n";
						strHtmlLeft += "<dt>\n";
						strHtmlLeft += "<img src='"+HttpUtils.getProperty("sect_img_root")+"/2008ca/ca"+sect.getSect_id()+".gif' alt='"+sect.getSect_name()+"' />\n";
						strHtmlLeft += "</dt>\n";
						
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "<dd>\n";
							strHtmlLeft += "<ul>\n";
							
							childiterator = dataNodeChildList.iterator();
							
							
							while (childiterator.hasNext()) {
								
								childSect = (Sect)childiterator.next();
								
								trueLink = "";
								tagImgS = "";
								
								if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
									trueLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
								}
								else{
									trueLink = childSect.getLink_url();
								}

								
								strHtmlLeft += "<li><a href="+trueLink+" onmouseover=\"this.className='on'\" onmouseout=\"this.className=''\"><span>"+childSect.getSect_name()+"</span></a></li>\n";
										
								
								
							}
							strHtmlLeft += "</ul>\n";
							strHtmlLeft += "</dd>\n";
							
						}
						strHtmlLeft += "</dl>\n";
						
						
						
					}
					
				}					
				
				strHtmlLeft += "</div>";			 
		 }
			 
	
		 log.debug("###########################strHtmlLeft"+strHtmlLeft);
		
		 		 
	     return strHtmlLeft;
	 }
	 
	 
	 
	 

	 /**
	  * 3,4depth�� ���� �޴��� �׸�
	  * @param orgString
	  * @return
	  */
	 public static String get34DepthSectDisp(List leftCatalogList, String code_grp){
	  
		 String strHtmlLeft = "";
		 List dataNodeChildList = null;
		 Sect sect = null;
		 Sect childSect = null;
		 Iterator iterator = null;
		 Iterator childiterator = null;
		 int j = 0;
		 String btrueLink = "";
		 int forcnt = 1;
		 String trueLink = "";
		 String tagImgS = "";
		 String tmpid = "";
		 String imgUrl = "";
		 int catalogRowSize= 23;
		 int catalogTotalSize = 92;
		 
		 iterator = leftCatalogList.iterator();
		 
		 // Hmall
		 if (code_grp.equals("LGROUP_HMALL")) {
			   strHtmlLeft += "<ul id='menu_type_01'>\n";  
				while (iterator.hasNext()) {
					// parentSect
					sect = (Sect) iterator.next();
					// childSect
					dataNodeChildList = sect.getLnkChildSect();
					if(j<9){
						tmpid = "0"+(j+1);
					}
					else{
						tmpid = (j+1)+"";
					}
					forcnt = 1;
					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
					// �ٸ�.	
					if(!"".equals(sect.getStore_attrb()) && sect.getStore_attrb()!=null){
						tagImgS = "<img src='"+HttpUtils.getProperty("img_root")+"/image/common/cate/"+sect.getStore_attrb()+"'/>";
					}
					
					if(sect.getTitle_img() != null && !"".equals(sect.getTitle_img())){
						if("B".equals(sect.getGubun())){
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/shopchance/"+ sect.getTitle_img();
						}
						else{
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/sect_group/"+ sect.getTitle_img();
						}
					}
					if("I".equals(sect.getGubun())){
						if("".equals(sect.getTitle_img())){
							strHtmlLeft += "<li class='menu_depth1'><p>"+sect.getSect_name()+"</p></li>\n";
						}
						else{
							strHtmlLeft += "<li class='menu_depth1'><p><img src='"+imgUrl+"' alt='"+sect.getSect_name()+"'/></p></li>\n";
						}
					}
					else if("A".equals(sect.getGubun())){
						if(j==0){
							strHtmlLeft += "<li class='menu_depth2'></li>\n";
						}
						strHtmlLeft += "<li class='menu_depth2'>\n";
						strHtmlLeft += "<div id='menu_01_depth2_"+tmpid+"' onmouseover='view_div_view1(menu_01_depth2_"+tmpid+")' onmouseout='view_div_view1(menu_01_depth2_"+tmpid+")' style='display:none;z-index:999;'>\n";
						strHtmlLeft += "<div id='menu_01_border'>\n";
						strHtmlLeft += "<table cellspacing='0' cellpadding='0'>\n";
						strHtmlLeft += "<tr>\n";
						strHtmlLeft += "<td valign=top>\n";
						
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "<ul id='menu_depth_01'>\n";
							
							childiterator = dataNodeChildList.iterator();
							boolean firstLineCheck = false;
							boolean firstLineCheck2 = true;
					
							while (childiterator.hasNext() && forcnt <= catalogTotalSize) {
								
								childSect = (Sect)childiterator.next();
								
								trueLink = "";
								tagImgS = "";
								
								if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
									trueLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
								}
								else{
									trueLink = childSect.getLink_url();
								}

								if(!"".equals(childSect.getStore_attrb()) && childSect.getStore_attrb()!=null){
									tagImgS = "<img src='"+HttpUtils.getProperty("img_root")+"/image/common/cate/"+childSect.getStore_attrb()+"'/>";
								}
								if("I".equals(childSect.getGubun())){
									if(firstLineCheck == true){
										strHtmlLeft += "<li class='depth2'><div style=\"width:147px; height:1px; zoom:1; line-height:1px; border-top:1px solid #E8E8E8; margin-top:-2px; margin-bottom:-8px;\"></div></li>";
									}
									firstLineCheck = true;
									strHtmlLeft += "<li class='depth_nohand'><strong class=\"orange\">"+childSect.getSect_name()+tagImgS+"</strong></li>\n";
								}else{
									if(childSect.getItem_priority().equals("0") && firstLineCheck2 && firstLineCheck ){
										strHtmlLeft += "<li class='depth2'><div style=\"width:147px; height:1px; zoom:1; line-height:1px; border-top:1px solid #E8E8E8; margin-top:-2px; margin-bottom:-8px;\"></div></li>";
										firstLineCheck2 = false;
									}
									strHtmlLeft += "<li class='depth2' onmouseover='menu_bg(this,\"#ff7800\",\"over\")' onmouseout='menu_bg(this,\"#ff7800\")' onClick='javascript:location.href=\""+trueLink+"\";'>"+childSect.getSect_name()+tagImgS+"</li>\n"; 
								}
								
								if((forcnt % catalogRowSize)==0){
									//2009.02.26::�ֿ뼺::�������  forcnt ���� ���  �±� ��ó����[�Ϲݸ���]
									if(dataNodeChildList.size()!=forcnt && forcnt != catalogTotalSize){
										//log.debug("�������  forcnt ���� ���  �±� ��ó����[�Ϲݸ���]========"+childSect+"===========================> dataNodeChildList.size()["+dataNodeChildList.size()+"]");
										strHtmlLeft += "</ul>\n";
										strHtmlLeft += "</td>\n";
										strHtmlLeft += "<td valign='top'>\n";
										strHtmlLeft += "<ul id='menu_depth_01'>\n";
									}
								}
								
								forcnt ++;
							}
							strHtmlLeft += "</ul>\n";
						}
						strHtmlLeft += "</td>\n";
						strHtmlLeft += "</tr>\n"; 
						strHtmlLeft += "</table>\n";
						strHtmlLeft += "</div>\n";
						strHtmlLeft += "</div>\n";
						if(sect.getLink_url_gb() == null || "".equals(sect.getLink_url_gb()) || "02".equals(sect.getLink_url_gb())){
							strHtmlLeft += "<p><a href='"+btrueLink+"' target='_self' ";
						}
						else{
							strHtmlLeft += "<p><a href='"+btrueLink+"' target='_blank' ";
						}					
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "onmouseover='view_div_view1(menu_01_depth2_"+tmpid+")' onmouseout='view_div_view1(menu_01_depth2_"+tmpid+")'";
						}
						strHtmlLeft += "><img src='"+HttpUtils.getProperty("sect_img_root")+"/2008ca/ca"+sect.getSect_id()+".gif'  width='164' alt='"+sect.getSect_name()+"' onmouseover='img_over1(this,\"ca"+sect.getSect_id()+"\",\""+HttpUtils.getProperty("sect_img_root")+"/2008ca/\",\"over\")' onmouseout='img_over1(this,\"ca"+sect.getSect_id()+"\",\""+HttpUtils.getProperty("sect_img_root")+"/2008ca/\")' /></a></p>";
						strHtmlLeft += "</li>";
					}
					j++;
				}					
				strHtmlLeft += "<li class='menu_block'></li>\n";
				strHtmlLeft += "</ul>";			 
		 }
		 // ��ȭ��
		 if (code_grp.equals("LGROUP_DEPART")) {	 
		   strHtmlLeft += "<ul id='menu_type_01'>\n";  
			
			while (iterator.hasNext()) {
				
				// parentSect
				sect = (Sect) iterator.next();
				// childSect
				dataNodeChildList = sect.getLnkChildSect();
				if(j<9){
					tmpid = "0"+(j+1);
				}
				else{
					tmpid = (j+1)+"";
				}
				
				forcnt = 1;
				
				if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
					btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
				}
				else{
					btrueLink = sect.getLink_url();
				}
				
				if(sect.getTitle_img() != null && !"".equals(sect.getTitle_img())){
					if("B".equals(sect.getGubun())){
						imgUrl = HttpUtils.getProperty("sect_img_root")+"/shopchance/"+ sect.getTitle_img();
					}
					else{
						imgUrl = HttpUtils.getProperty("sect_img_root")+"/sect_group/"+ sect.getTitle_img();
					}
				}

				if("I".equals(sect.getGubun())){
					if("".equals(sect.getTitle_img())){
						strHtmlLeft += "<li class='menu_depth1'><p>"+sect.getSect_name()+"</p></li>\n";
					}
					else{
						strHtmlLeft += "<li class='menu_depth1'><p><img src='"+imgUrl+"' alt='"+sect.getSect_name()+"'/></p></li>\n";
					}
				}
				else if("A".equals(sect.getGubun())){
					if(j==0){
						strHtmlLeft += "<li class='menu_depth2'></li>\n";
					}
					strHtmlLeft += "<li class='menu_depth2'>\n";
					strHtmlLeft += "<div id='menu_02_01_depth2_"+tmpid+"' onmouseover='view_div_view1(menu_02_01_depth2_"+tmpid+")' onmouseout='view_div_view1(menu_02_01_depth2_"+tmpid+")' style='display:none;z-index:999;'>\n";
					strHtmlLeft += "<div id='menu_02_01_border'>\n";
					strHtmlLeft += "<table cellspacing='0' cellpadding='0'>\n";
					strHtmlLeft += "<tr>\n";
					strHtmlLeft += "<td valign=top>\n";
					
					if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
						strHtmlLeft += "<ul id='menu_depth_01'>\n";
					
						childiterator = dataNodeChildList.iterator();				
						boolean firstLineCheck = false;
						boolean firstLineCheck2 = true;
						
						while (childiterator.hasNext() && forcnt <= catalogTotalSize) {
							childSect = (Sect)childiterator.next();
							
							trueLink = "";
							tagImgS = "";
							
							if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
								trueLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
							}
							else{
								trueLink = childSect.getLink_url();
							}

							if(!"".equals(childSect.getStore_attrb()) && childSect.getStore_attrb()!=null){
								tagImgS = "<img src='"+HttpUtils.getProperty("img_root")+"/image/common/cate/"+childSect.getStore_attrb()+"'/>";
							}
						 
							if("I".equals(childSect.getGubun())){
								if(firstLineCheck == true){
									strHtmlLeft += "<li class='depth2'><div style=\"width:147px; height:1px; zoom:1; line-height:1px; border-top:1px solid #E8E8E8; margin-top:-2px; margin-bottom:-8px;\"></div></li>";
								}
								firstLineCheck = true;
								strHtmlLeft += "<li class='depth_nohand'><strong class=\"orange\">"+childSect.getSect_name()+tagImgS+"</strong></li>\n";
							}else{
								if(childSect.getItem_priority().equals("0") && firstLineCheck2 && firstLineCheck ){
									strHtmlLeft += "<li class='depth2'><div style=\"width:147px; height:1px; zoom:1; line-height:1px; border-top:1px solid #E8E8E8; margin-top:-2px; margin-bottom:-8px;\"></div></li>";
									firstLineCheck2 = false;
								}
								strHtmlLeft += "<li class='depth2' onmouseover='menu_bg(this,\"#8b7264\",\"over\")' onmouseout='menu_bg(this,\"#8b7264\")' onClick='javascript:location.href=\""+trueLink+"\";'>"+childSect.getSect_name()+tagImgS+"</li>\n"; 
							}

							if((forcnt % catalogRowSize)==0){
								//2009.02.26::�ֿ뼺::�������  forcnt ���� ���  �±� ��ó����[��ȭ������]
								if(dataNodeChildList.size()!=forcnt && forcnt != catalogTotalSize){
									//log.debug("�������  forcnt ���� ���  �±� ��ó����[��ȭ��]========"+childSect+"===========================> dataNodeChildList.size()["+dataNodeChildList.size()+"]");
									strHtmlLeft += "</ul>\n";
									strHtmlLeft += "</td>\n";
									strHtmlLeft += "<td valign='top'>\n";
									strHtmlLeft += "<ul id='menu_depth_01'>\n";
								}
							}
							
							forcnt ++;
						}
						strHtmlLeft += "</ul>\n";
					}
					strHtmlLeft += "</td>\n";
					strHtmlLeft += "</tr>\n"; 
					strHtmlLeft += "</table>\n";
					strHtmlLeft += "</div>\n";
					strHtmlLeft += "</div>\n";
					if(sect.getLink_url_gb() == null || "".equals(sect.getLink_url_gb()) || "02".equals(sect.getLink_url_gb())){
						strHtmlLeft += "<p><a href='"+btrueLink+"' target='_self' ";
					}
					else{
						strHtmlLeft += "<p><a href='"+btrueLink+"' target='_blank' ";
					}					
					if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
						strHtmlLeft += "onmouseover='view_div_view1(menu_02_01_depth2_"+tmpid+")' onmouseout='view_div_view1(menu_02_01_depth2_"+tmpid+")'";
					}
					strHtmlLeft += "><img src='"+HttpUtils.getProperty("sect_img_root")+"/2008ca/ca"+sect.getSect_id()+".gif'  width='164' alt='"+sect.getSect_name()+"' onmouseover='img_over1(this,\"ca"+sect.getSect_id()+"\",\""+HttpUtils.getProperty("sect_img_root")+"/2008ca/\",\"over\")' onmouseout='img_over1(this,\"ca"+sect.getSect_id()+"\",\""+HttpUtils.getProperty("sect_img_root")+"/2008ca/\")' /></a></p>";
				}
				j++;
			}					
			strHtmlLeft += "<li class='menu_block'></li>\n";
			strHtmlLeft += "</ul>";
		 }
		 //Ŭ���ж�� �߰� - 2009.04.15 �̱⿬
		 if(code_grp.equals("CLUB_MILANO")){
			 strHtmlLeft += "<ul class='lx_Menu'>\n";  
				
				while (iterator.hasNext()) {
					
					// parentSect
					sect = (Sect) iterator.next();
					// childSect
					dataNodeChildList = sect.getLnkChildSect();
					if(j<9){
						tmpid = "0"+(j+1);
					}
					else{
						tmpid = (j+1)+"";
					}
					
					forcnt = 1;
					
					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
					
					if(sect.getSect_name() != null && !"".equals(sect.getSect_name())){
						if("B".equals(sect.getGubun())){
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/shopchance/"+ sect.getTitle_img();
						}
						else{
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/sect_group/"+ sect.getTitle_img();
						}
					}

					if("I".equals(sect.getGubun())){
						strHtmlLeft += "<li class='mn_title'>"+sect.getSect_name()+"</li>\n";
					}
					else if("A".equals(sect.getGubun())){
						if(j==0){
							strHtmlLeft += "<li></li>\n";
						}
						strHtmlLeft += "<li>\n";
						strHtmlLeft += "<div id='lx_menu_"+tmpid+"' onmouseover='view_div_view1(lx_menu_"+tmpid+")' onmouseout='view_div_view1(lx_menu_"+tmpid+")' >\n";
						
						
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "<ul id='hide_sub'>\n";
						
							childiterator = dataNodeChildList.iterator();

							while (childiterator.hasNext()) {
								childSect = (Sect)childiterator.next();
								
								trueLink = "";
								tagImgS = "";
								
								if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
									trueLink = "/front/shSectR.do?depth4SectID="+sect.getSect_id()+"&SectID="+childSect.getSect_id();
								}
								else{
									trueLink = childSect.getLink_url();
								}

								if(!"".equals(childSect.getStore_attrb()) && childSect.getStore_attrb()!=null){
									tagImgS = "<img src='"+HttpUtils.getProperty("img_root")+"/image/common/cate/"+childSect.getStore_attrb()+"'/>";
								}
								strHtmlLeft += "<li><p><a href=\""+trueLink+"\">"+childSect.getSect_name()+tagImgS+"</a></p></li>\n"; 
							
								
								forcnt ++;
							}
							strHtmlLeft += "</ul>\n";
						}
						
						
						strHtmlLeft += "</div>\n";
						
						strHtmlLeft += "<a href='"+btrueLink+"'  ";
											
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "onmouseover='view_div_view1(lx_menu_"+tmpid+")' onmouseout='view_div_view1(lx_menu_"+tmpid+")'";
						}
						strHtmlLeft += " class='mn_lx'>"+sect.getSect_name()+"</a></li>";
					}
					j++;
				}					
			//strHtmlLeft += "<li class=end></li>\n";
			strHtmlLeft += "</ul>";
			 
			 
			 
			 
			 
			 
		 }
		//��òٶٸ� �߰� - 2009.05.14 �̱⿬
		 if(code_grp.equals("JUICY_COUTURE")){
			 strHtmlLeft += "<ul class='jc_Menu'>\n";  
				
				while (iterator.hasNext()) {
					
					// parentSect
					sect = (Sect) iterator.next();
					// childSect
					dataNodeChildList = sect.getLnkChildSect();
					if(j<9){
						tmpid = "0"+(j+1);
					}
					else{
						tmpid = (j+1)+"";
					}
					
					forcnt = 1;
					
					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
					
					if(sect.getSect_name() != null && !"".equals(sect.getSect_name())){
						if("B".equals(sect.getGubun())){
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/shopchance/"+ sect.getTitle_img();
						}
						else{
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/sect_group/"+ sect.getTitle_img();
						}
						
					}

					if("I".equals(sect.getGubun())){
						strHtmlLeft += "<li class='mn_title'>"+sect.getSect_name()+"</li>\n";
					}
					else if("A".equals(sect.getGubun())){
						if(j==0){
							strHtmlLeft += "<li></li>\n";
						}
						strHtmlLeft += "<li>\n";
						strHtmlLeft += "<div id='jc_menu_"+tmpid+"' onmouseover='view_div_view1(jc_menu_"+tmpid+")' onmouseout='view_div_view1(jc_menu_"+tmpid+")' >\n";
						
						
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "<ul id='hide_sub' >\n";
						
							childiterator = dataNodeChildList.iterator();

							while (childiterator.hasNext()) {
								childSect = (Sect)childiterator.next();
								
								trueLink = "";
								tagImgS = "";
								
								if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
									trueLink = "/front/shSectR.do?depth4SectID="+sect.getSect_id()+"&SectID="+childSect.getSect_id();
								}
								else{
									trueLink = childSect.getLink_url();
								}

								if(!"".equals(childSect.getStore_attrb()) && childSect.getStore_attrb()!=null){
									tagImgS = "<img src='"+HttpUtils.getProperty("img_root")+"/image/common/cate/"+childSect.getStore_attrb()+"'/>";
								}
								strHtmlLeft += "<li><p><a href=\""+trueLink+"\">"+childSect.getSect_name()+tagImgS+"</a></p></li>\n"; 
							
								
								forcnt ++;
							}
							strHtmlLeft += "</ul>\n";
						}
						
						
						strHtmlLeft += "</div>\n";
						if(sect.getLink_url_gb() == null || "".equals(sect.getLink_url_gb()) || "02".equals(sect.getLink_url_gb())){
							strHtmlLeft += "<a href='"+btrueLink+"' target='_self' ";
						}
						else{
							strHtmlLeft += "<a href='"+btrueLink+"' target='_blank' ";
						}					
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "onmouseover='view_div_view1(jc_menu_"+tmpid+")' onmouseout='view_div_view1(jc_menu_"+tmpid+")' ";
						}
						strHtmlLeft += "class='mn_jc'>"+sect.getSect_name()+"</a></li>";
					}
					j++;
				}					
			strHtmlLeft += "<li class=end></li>\n";
			strHtmlLeft += "</ul>";
		 }

			//����������::2010.10.22::�Ӻ���
		 if(code_grp.equals("FURS")){
			 strHtmlLeft += "<ul class='furs_Menu'>\n";  
				
				while (iterator.hasNext()) {
					
					// parentSect
					sect = (Sect) iterator.next();
					// childSect
					dataNodeChildList = sect.getLnkChildSect();
					if(j<9){
						tmpid = "0"+(j+1);
					}
					else{
						tmpid = (j+1)+"";
					}
					
					forcnt = 1;
					
					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
					
					if(sect.getSect_name() != null && !"".equals(sect.getSect_name())){
						if("B".equals(sect.getGubun())){
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/shopchance/"+ sect.getTitle_img();
						}
						else{
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/sect_group/"+ sect.getTitle_img();
						}
						
					}

					if("I".equals(sect.getGubun())){
						strHtmlLeft += "<li class='mn_title'>"+sect.getSect_name()+"</li>\n";
					}
					else if("A".equals(sect.getGubun())){
						if(j==0){
							strHtmlLeft += "<li></li>\n";
						}
						strHtmlLeft += "<li>\n";
						strHtmlLeft += "<div id='furs_menu_"+tmpid+"' onmouseover='view_div_view1(furs_menu_"+tmpid+")' onmouseout='view_div_view1(furs_menu_"+tmpid+")' >\n";
						
						
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "<ul id='hide_sub' >\n";
						
							childiterator = dataNodeChildList.iterator();

							while (childiterator.hasNext()) {
								childSect = (Sect)childiterator.next();
								
								trueLink = "";
								tagImgS = "";
								
								if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
									trueLink = "/front/shSectR.do?depth4SectID="+sect.getSect_id()+"&SectID="+childSect.getSect_id();
								}
								else{
									trueLink = childSect.getLink_url();
								}

								if(!"".equals(childSect.getStore_attrb()) && childSect.getStore_attrb()!=null){
									tagImgS = "<img src='"+HttpUtils.getProperty("img_root")+"/image/common/cate/"+childSect.getStore_attrb()+"'/>";
								}
								strHtmlLeft += "<li><p><a href=\""+trueLink+"\">"+childSect.getSect_name()+tagImgS+"</a></p></li>\n"; 
							
								
								forcnt ++;
							}
							strHtmlLeft += "</ul>\n";
						}
						
						
						strHtmlLeft += "</div>\n";
						if(sect.getLink_url_gb() == null || "".equals(sect.getLink_url_gb()) || "02".equals(sect.getLink_url_gb())){
							strHtmlLeft += "<a href='"+btrueLink+"' target='_self' ";
						}
						else{
							strHtmlLeft += "<a href='"+btrueLink+"' target='_blank' ";
						}					
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "onmouseover='view_div_view1(furs_menu_"+tmpid+")' onmouseout='view_div_view1(furs_menu_"+tmpid+")' ";
						}
						strHtmlLeft += "class='mn_furs'>"+sect.getSect_name()+"</a></li>";
					}
					j++;
				}					
			strHtmlLeft += "<li class=end></li>\n";
			strHtmlLeft += "</ul>";
		 }
		 
			//GAP::2010.12.14::������
		 if(code_grp.equals("GAP")){
			 strHtmlLeft += "<ul class='pl_tpl_cate_ul'>\n";
				while (iterator.hasNext()) {
					// parentSect
					sect = (Sect) iterator.next();
					dataNodeChildList = sect.getLnkChildSect();

					if(j<9){
						tmpid = "0"+(j+1);
					}
					else{
						tmpid = (j+1)+"";
					}

					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
	
					if("I".equals(sect.getGubun())){
						if(j!=0){
							strHtmlLeft += "</ul>\n";
							strHtmlLeft += "<ul class='pl_tpl_cate_ul'>\n";
						}
						strHtmlLeft += "<li><strong>"+sect.getSect_name()+"</strong></li>\n";
					}
					else if("A".equals(sect.getGubun())){
						
						strHtmlLeft += "<li id='pl_tpl_s"+tmpid+"' onmouseover=\"sub_L_call('"+tmpid+"');\">\n";
						
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
						
							strHtmlLeft += "<div id='pl_tpl_s"+tmpid+"_L' class='sub_cate_style'>\n";	
							strHtmlLeft += "<ul>\n";
							
							childiterator = dataNodeChildList.iterator();

							while (childiterator.hasNext()) {
								childSect = (Sect)childiterator.next();
								
								trueLink = "";
								tagImgS = "";
								
								if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
									trueLink = "/front/shSectR.do?depth4SectID="+sect.getSect_id()+"&SectID="+childSect.getSect_id();
								}
								else{
									trueLink = childSect.getLink_url();
								}

								if(!"".equals(childSect.getStore_attrb()) && childSect.getStore_attrb()!=null){
									tagImgS = "<img src='"+HttpUtils.getProperty("img_root")+"/image/common/cate/"+childSect.getStore_attrb()+"'/>";
								}
								strHtmlLeft += "<li class='sub_cate_depth'><a href=\""+trueLink+"\">"+childSect.getSect_label()+tagImgS+"</a></li>\n"; 
							}
							strHtmlLeft += "</ul></div>\n";
						}
						strHtmlLeft += "<a href='"+btrueLink+"' class='mn_pl_tpl'><strong>"+sect.getSect_label()+"</strong></a></li>";
					}
					j++;
				}					
			strHtmlLeft += "</ul>";
		 }		 
		 
		//�������ø� �߰� - 2009.08.24 �̱⿬
		 if(code_grp.equals("FESTIVAL")){
			 strHtmlLeft += "<ul class='fs_Menu'>\n";  
				
				while (iterator.hasNext()) {
					
					// parentSect
					sect = (Sect) iterator.next();
					// childSect
					dataNodeChildList = sect.getLnkChildSect();
					if(j<9){
						tmpid = "0"+(j+1);
					}
					else{
						tmpid = (j+1)+"";
					}
					
					forcnt = 1;
					
					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
					
					if(sect.getSect_name() != null && !"".equals(sect.getSect_name())){
						if("B".equals(sect.getGubun())){
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/shopchance/"+ sect.getTitle_img();
						}
						else{
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/sect_group/"+ sect.getTitle_img();
						}
						
					}

					if("I".equals(sect.getGubun())){
						strHtmlLeft += "<li class='mn_title'>"+sect.getSect_name()+"</li>\n";
					}
					else if("A".equals(sect.getGubun())){
						if(j==0){
							strHtmlLeft += "<li></li>\n";
						}
						strHtmlLeft += "<li>\n";
						strHtmlLeft += "<div id='fs_menu_"+tmpid+"' onmouseover='view_div_view1(fs_menu_"+tmpid+")' onmouseout='view_div_view1(fs_menu_"+tmpid+")' >\n";
						
						
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "<ul id='hide_sub' >\n";
						
							childiterator = dataNodeChildList.iterator();

							while (childiterator.hasNext()) {
								childSect = (Sect)childiterator.next();
								
								trueLink = "";
								tagImgS = "";
								
								if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
									trueLink = "/front/shSectR.do?depth4SectID="+sect.getSect_id()+"&SectID="+childSect.getSect_id();
								}
								else{
									trueLink = childSect.getLink_url();
								}

								if(!"".equals(childSect.getStore_attrb()) && childSect.getStore_attrb()!=null){
									tagImgS = "<img src='"+HttpUtils.getProperty("img_root")+"/image/common/cate/"+childSect.getStore_attrb()+"'/>";
								}
								strHtmlLeft += "<li><p><a href=\""+trueLink+"\">"+childSect.getSect_name()+tagImgS+"</a></p></li>\n"; 
							
								
								forcnt ++;
							}
							strHtmlLeft += "</ul>\n";
						}
						
						
						strHtmlLeft += "</div>\n";
						if(sect.getLink_url_gb() == null || "".equals(sect.getLink_url_gb()) || "02".equals(sect.getLink_url_gb())){
							strHtmlLeft += "<a href='"+btrueLink+"' target='_self' ";
						}
						else{
							strHtmlLeft += "<a href='"+btrueLink+"' target='_blank' ";
						}					
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "onmouseover='view_div_view1(fs_menu_"+tmpid+")' onmouseout='view_div_view1(fs_menu_"+tmpid+")' ";
						}
						strHtmlLeft += "class='mn_jc'>"+sect.getSect_name()+"</a></li>";
					}
					j++;
				}					
			strHtmlLeft += "<li class=end></li>\n";
			strHtmlLeft += "</ul>";
			 
			 
			 
			 
			 
			 
		 }
		 
		 //��ȥ���ø� �߰� - 2009.09.22 �̱⿬
		 if(code_grp.equals("WEDDING")){
			 strHtmlLeft += "<ul class='wd_Menu'>\n";  
				
				while (iterator.hasNext()) {
					
					// parentSect
					sect = (Sect) iterator.next();
					// childSect
					dataNodeChildList = sect.getLnkChildSect();
					if(j<9){
						tmpid = "0"+(j+1);
					}
					else{
						tmpid = (j+1)+"";
					}
					
					forcnt = 1;
					
					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
					
					if(sect.getSect_name() != null && !"".equals(sect.getSect_name())){
						if("B".equals(sect.getGubun())){
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/shopchance/"+ sect.getTitle_img();
						}
						else{
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/sect_group/"+ sect.getTitle_img();
						}
						
					}

					if("I".equals(sect.getGubun())){
						strHtmlLeft += "<li class='mn_title'>"+sect.getSect_name()+"</li>\n";
					}
					else if("A".equals(sect.getGubun())){
						if(j==0){
							strHtmlLeft += "<li></li>\n";
						}
						strHtmlLeft += "<li>\n";
						strHtmlLeft += "<div id='wd_menu_"+tmpid+"' onmouseover='view_div_view1(wd_menu_"+tmpid+")' onmouseout='view_div_view1(wd_menu_"+tmpid+")' >\n";
						
						
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "<ul id='hide_sub' >\n";
						
							childiterator = dataNodeChildList.iterator();

							while (childiterator.hasNext()) {
								childSect = (Sect)childiterator.next();
								
								trueLink = "";
								tagImgS = "";
								
								if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
									trueLink = "/front/shSectR.do?depth4SectID="+sect.getSect_id()+"&SectID="+childSect.getSect_id();
								}
								else{
									trueLink = childSect.getLink_url();
								}

								if(!"".equals(childSect.getStore_attrb()) && childSect.getStore_attrb()!=null){
									tagImgS = "<img src='"+HttpUtils.getProperty("img_root")+"/image/common/cate/"+childSect.getStore_attrb()+"'/>";
								}
								strHtmlLeft += "<li><p><a href=\""+trueLink+"\">"+childSect.getSect_name()+tagImgS+"</a></p></li>\n"; 
							
								
								forcnt ++;
							}
							strHtmlLeft += "</ul>\n";
						}
						
						
						strHtmlLeft += "</div>\n";
						if(sect.getLink_url_gb() == null || "".equals(sect.getLink_url_gb()) || "02".equals(sect.getLink_url_gb())){
							strHtmlLeft += "<a href='"+btrueLink+"' target='_self' ";
						}
						else{
							strHtmlLeft += "<a href='"+btrueLink+"' target='_blank' ";
						}					
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "onmouseover='view_div_view1(wd_menu_"+tmpid+")' onmouseout='view_div_view1(wd_menu_"+tmpid+")' ";
						}
						strHtmlLeft += "class='mn_jc'>";
							
						if(sect.getSect_id()==289084 || sect.getSect_id() == 289085){// ���ݴ뺰 ����,�����뺰 ������  ���� ��Ÿ�� ����
							strHtmlLeft += "<strong style='color:#891F62;'>"+sect.getSect_name()+"</strong></a></li>";
						}else{
							strHtmlLeft += sect.getSect_name()+"</a></li>";
						}
							
							
							
					}
					j++;
				}					
			strHtmlLeft += "<li class=end></li>\n";
			strHtmlLeft += "</ul>";
			 	 			 
		 }
		 	 
		 //�������ø� �߰� - 2011.04.25 c.y.s
		 if(code_grp.equals("NONGHYUP")){
			 strHtmlLeft += "<p><img src=\"http://image.hyundaihmall.com/static/image/nonghyup/nh_left_bg_01.jpg\" alt=\"\"></p>\n";  
				
				while (iterator.hasNext()) {
					
					// parentSect
					sect = (Sect) iterator.next();
					// childSect
					dataNodeChildList = sect.getLnkChildSect();
					if(j<9){
						tmpid = "0"+(j+1);
					}
					else{
						tmpid = (j+1)+"";
					}
					
					forcnt = 1;
					
					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
					
					if(sect.getSect_name() != null && !"".equals(sect.getSect_name())){
						if("B".equals(sect.getGubun())){
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/shopchance/"+ sect.getTitle_img();
						}
						else{
							imgUrl = HttpUtils.getProperty("sect_img_root")+"/sect_group/"+ sect.getTitle_img();
						}
						
					}

					if("I".equals(sect.getGubun())){
						//strHtmlLeft += "<li>"+sect.getSect_name()+"</li>\n";//������ �׷��� ���ܽ�Ŵ
					}
					else if("A".equals(sect.getGubun())){

						strHtmlLeft += "<ul class=\"nh_category_ul\">\n";
						
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "<li id='pl_tpl_s"+tmpid+"' onmouseover=\"sub_L_call('"+tmpid+"');\" >\n";
						}else{
							strHtmlLeft += "<li id='pl_tpl_s"+tmpid+"'>\n";
						}
					
						strHtmlLeft += "<div id='pl_tpl_s"+tmpid+"_L' class=\"sub_cate_style\">\n";
						
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "<ul>\n";
						
							childiterator = dataNodeChildList.iterator();

							while (childiterator.hasNext()) {
								childSect = (Sect)childiterator.next();
								
								trueLink = "";
								tagImgS = "";
								
								if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
									trueLink = "/front/shSectR.do?depth4SectID="+sect.getSect_id()+"&SectID="+childSect.getSect_id();
								}
								else{
									trueLink = childSect.getLink_url();
								}

								if(!"".equals(childSect.getStore_attrb()) && childSect.getStore_attrb()!=null){
									tagImgS = "<img src='"+HttpUtils.getProperty("img_root")+"/image/common/cate/"+childSect.getStore_attrb()+"'/>";
								}
								
								strHtmlLeft += "<li class='sub_cate_depth'><a href=\""+trueLink+"\">"+childSect.getSect_name()+tagImgS+"</a></li>\n"; 
							
								
								forcnt ++;
							}
							strHtmlLeft += "</ul>\n";
						}
						
									
						strHtmlLeft += "</div>\n";
						strHtmlLeft += "<a href='"+btrueLink+"'><strong>"+sect.getSect_name()+"</strong></a>\n";					
						strHtmlLeft += "</li>\n";
						strHtmlLeft += "</ul>\n";
					}
					j++;
				}					
	 
		 }
		 
		 
		 
		 // CHANEL ���ø� �߰� - 2011.06.03 ������
		 int groupCount = 1;
		 int sgroupCount =1;
		 if(code_grp.equals("CHANEL")){
			 strHtmlLeft += "<div class=\"chanel_menubox\">\n";
			 strHtmlLeft += "<div id=\"chanel_lm_box\">\n";
				while (iterator.hasNext()) {
					// parentSect
					sect = (Sect) iterator.next();
					dataNodeChildList = sect.getLnkChildSect();

					tmpid = (j+1)+"";

					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
	
					if("I".equals(sect.getGubun())){
						if(groupCount !=1){
							strHtmlLeft += "</ul>\n";
						}
						strHtmlLeft += "<h3 class=\"lm_title\" onclick=\"show_parentmn('lm_group_', '"+groupCount+"'); fakearea('58082700"+groupCount+".gif');\">"+sect.getSect_name()+"</h3>\n";
						strHtmlLeft += "<ul id=\"lm_group_"+groupCount+"\" class=\"chanel_mn\" style=\"display:none;\">\n";
						groupCount++;
						j=0;
					}
					else if("A".equals(sect.getGubun())){
						
						
						if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
							strHtmlLeft += "<li><a href=\"javascript:void(0);\" onclick=\"show_hiddensub('lm_group_"+(groupCount-1)+"','"+tmpid+"');\">"+sect.getSect_name()+"</a></li>\n";
							strHtmlLeft += "<li id=\"lm_group_"+(groupCount-1)+"_sub_"+tmpid+"\" style=\"display:none;\">\n";	
							strHtmlLeft += "<ul>\n";
							
							childiterator = dataNodeChildList.iterator();

							while (childiterator.hasNext()) {
								childSect = (Sect)childiterator.next();
								
								trueLink = "";
								tagImgS = "";
								
								if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
									trueLink = "/front/shSectR.do?depth4SectID="+sect.getSect_id()+"&SectID="+childSect.getSect_id()+"&Lmenu="+(groupCount-1)+"&Mmenu="+tmpid;
								}
								else{
									trueLink = childSect.getLink_url();
								}

								if(!"".equals(childSect.getStore_attrb()) && childSect.getStore_attrb()!=null){
									tagImgS = "<img src='"+HttpUtils.getProperty("img_root")+"/image/common/cate/"+childSect.getStore_attrb()+"'/>";
								}
								
								strHtmlLeft += "<li><a id=\"lm_sgroup_"+sgroupCount+"\" href=\""+trueLink+"&Smenu="+sgroupCount+"\">"+childSect.getSect_label()+tagImgS+"</a></li>\n"; 
								sgroupCount++;
							}
							strHtmlLeft += "</ul></li>\n";
							j++;
						}else{
							strHtmlLeft += "<li><a id=\"lm_sgroup_"+sgroupCount+"\" href=\"/front/shSectR.do?SectID="+sect.getSect_id()+"&Lmenu="+(groupCount-1)+"&Smenu="+sgroupCount+"\">"+sect.getSect_name()+"</a></li>\n";
							sgroupCount++;
						}
						
					}
					
				}					
			strHtmlLeft += "</ul>";
			strHtmlLeft += "</div>";
		 }
		 
		 
		 
		 //2011.12.22::������::��ǰ�� ���ø�
		 if(code_grp.equals("FOOD")){
			 strHtmlLeft += "<div class=\"food_menubox\">\n";
		
			 String typeCheck = "1";
			 boolean firstCheck  = true;
			 int count =1;
			 
			 while (iterator.hasNext()) {
					// parentSect
					sect = (Sect) iterator.next();
					// childSect
					dataNodeChildList = sect.getLnkChildSect();


					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}

					if("I".equals(sect.getGubun())){
						if("2".equals(Integer.toString(sect.getSect_id()))){
							typeCheck ="2";
						}else{
							typeCheck ="1";
						}
					}else if("1".equals(typeCheck)){
						//�귣�弥
						if("663005".equals(Integer.toString(sect.getSect_id()))){
							if("A".equals(sect.getGubun())){
								strHtmlLeft += "<div class=\"menu_group\">\n";
								strHtmlLeft += "<ul class=\"food_brandshop\">\n";

								strHtmlLeft += "<li>\n";
								strHtmlLeft += "<img src='"+HttpUtils.getProperty("sect_img_root")+"/2008ca/fca_"+sect.getSect_id()+".gif'/></li>";
								
								if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
									childiterator = dataNodeChildList.iterator();
				
									while (childiterator.hasNext()) {
										childSect = (Sect)childiterator.next();
										trueLink = "";
										tagImgS = "";
										
										if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
											trueLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
										}
										else{
											trueLink = childSect.getLink_url();
										}
										
										if("A".equals(childSect.getGubun())){
											strHtmlLeft += "<li class=\"fca_"+childSect.getSect_id()+"_logo\">\n";
											strHtmlLeft += "<a href=\""+trueLink+"\";>\n";
											strHtmlLeft += "<img src='"+HttpUtils.getProperty("sect_img_root")+"/2008ca/fca_"+childSect.getSect_id()+".gif'/></a></li>";
										}
									}
								}
								strHtmlLeft += "</ul></div>\n";
							}
							strHtmlLeft += "<div class=\"clearL\"></div>\n";
						}else {
							if("A".equals(sect.getGubun())){
								strHtmlLeft += "<div class=\"menu_group\">\n";
								strHtmlLeft += "<ul>\n";
								
								if(sect.getLink_url()==null || "".equals(sect.getLink_url())){
									trueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
								}
								else{
									trueLink = sect.getLink_url();
								}
								
								strHtmlLeft += "<li>\n";
								strHtmlLeft += "<a href=\""+trueLink+"\";>\n";
								strHtmlLeft += "<img src='"+HttpUtils.getProperty("sect_img_root")+"/2008ca/fca_"+sect.getSect_id()+".gif'/></a></li>";
								
								if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
									childiterator = dataNodeChildList.iterator();
									boolean checkOneBanner = true;
									while (childiterator.hasNext()) {
										childSect = (Sect)childiterator.next();
										trueLink = "";
										tagImgS = "";
										
										if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
											trueLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
										}
										else{
											trueLink = childSect.getLink_url();
										}
										
										if("A".equals(childSect.getGubun())){
											strHtmlLeft += "<li>\n";
											strHtmlLeft += "<a href=\""+trueLink+"\";>\n";
											strHtmlLeft += "<img src='"+HttpUtils.getProperty("sect_img_root")+"/2008ca/fca_"+childSect.getSect_id()+".gif'/></a></li>";
										}else if("B".equals(childSect.getGubun()) && checkOneBanner){
											strHtmlLeft += "</ul>\n";
											strHtmlLeft += "<div class=\"floatbanner\">\n";
											strHtmlLeft += "<a href=\""+trueLink+"\";>\n";
											strHtmlLeft += "<img src='"+HttpUtils.getProperty("sect_img_root")+"/shopchance/"+ childSect.getTitle_img()+"' width =\"80\" height =\"155\"/></a></div>";
											checkOneBanner = false;
										}
									}
								}
								strHtmlLeft += "</div>\n";
								strHtmlLeft += "<div class=\"clearL\"></div>\n";
							}
						}
					}else if("2".equals(typeCheck)){
						if(firstCheck){
							strHtmlLeft += "<div class=\"menu_group menu_group_noneline\">\n";
							strHtmlLeft += "<ul class=\"food_otherstore\">\n";
							firstCheck = false;
						}
						if("A".equals(sect.getGubun())){
							if("".equals(sect.getLink_url())|| sect.getLink_url()==null){
								trueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
							}
							else{
								trueLink = sect.getLink_url();
							}
							if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
								strHtmlLeft += "<li class=\"fca_"+sect.getSect_id()+"_logo\" onmouseover=\"otherstore_sub_over('"+count+"');\" onmouseout=\"otherstore_sub_out('"+count+"');\">\n";
							}else{
								strHtmlLeft += "<li class=\"fca_"+sect.getSect_id()+"_logo\">\n";
							}
							
							strHtmlLeft += "<div id=\"food_otherstore_sub_"+count+"\" class=\"food_otherstore_sub\">\n";
							
							strHtmlLeft += "<ul class=\"food_otherstore_sub_ul\">\n";

							if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
								childiterator = dataNodeChildList.iterator();
			
								while (childiterator.hasNext()) {
									childSect = (Sect)childiterator.next();
									trueLink = "";
									tagImgS = "";
									
									if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
										trueLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
									}
									else{
										trueLink = childSect.getLink_url();
									}
									
									if("A".equals(childSect.getGubun())){
										strHtmlLeft += "<li>\n";
										strHtmlLeft += "<a href='"+trueLink+"'>"+childSect.getSect_name()+"</a>\n";
										strHtmlLeft += "</li>\n";
									}
								}
							}
							count++;
							strHtmlLeft += "</ul>\n";
							strHtmlLeft += "</div>\n";
							if("663008".equals(Integer.toString(sect.getSect_id()))){
								strHtmlLeft += "<a href=\""+btrueLink+"\" target =\"_blank\";>\n";
							}else{
								strHtmlLeft += "<a href=\""+btrueLink+"\";>\n";
							}
							strHtmlLeft += "<img src='"+HttpUtils.getProperty("sect_img_root")+"/2008ca/fca_"+sect.getSect_id()+".gif'/></a>";
							
							strHtmlLeft += "</li>\n";
						}
					}
			 
			 }
			 strHtmlLeft += "</ul>\n";
			 strHtmlLeft += "</div>\n";
			 strHtmlLeft += "</div>\n";
		 }
		 
		 String targetCheck ="";
			//LUXURY::2012.02.27::������ .. 2012.08.07::������ ::��������ó��
		 if(code_grp.equals("LUXURY") || code_grp.equals("BEANPOLE")){
			 strHtmlLeft += "<div class=\"premium_menu_box\">\n";
			 strHtmlLeft += "<ul id=\"premium_menu\" class=\"premium_menu\">\n";
			 
				while (iterator.hasNext()) {
					// parentSect
					sect = (Sect) iterator.next();
					dataNodeChildList = sect.getLnkChildSect();

					if(j<9){
						tmpid = "0"+(j+1);
					}
					else{
						tmpid = (j+1)+"";
					}
					j++;
					
					btrueLink ="";
					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
	
					if(sect.getLink_url_gb() == null || "".equals(sect.getLink_url_gb()) || "02".equals(sect.getLink_url_gb())){
						targetCheck= " target='_self'";
					}
					else{
						targetCheck=" target='_blank'";
					}
					
					if(code_grp.equals("BEANPOLE")){
						imgUrl = HttpUtils.getProperty("sect_img_root")+"/2008ca/pre_bp"+ tmpid+".gif";
					}else{
						imgUrl = HttpUtils.getProperty("sect_img_root")+"/2008ca/pre_mn"+ tmpid+".gif";
					}
					
					strHtmlLeft += "<li id=\"pre_mn"+tmpid+"\" class=\"bar\">\n";
					if(!"".equals(btrueLink)){
						strHtmlLeft += "<a href=\""+btrueLink+"\""+targetCheck+"><img src=\""+imgUrl+"\"></a>\n";
					}else{
						strHtmlLeft += "<img src=\""+imgUrl+"\">\n";
					}

					if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
						
						
						strHtmlLeft += "<div id=\"pre_mn"+tmpid+"_sub\" class=\"pre_mn_sublayer\">\n";
						strHtmlLeft += "<div id=\"pre_mn"+tmpid+"_sub_content\" class=\"pre_mn_sub_content\">\n";

						childiterator = dataNodeChildList.iterator();

						while (childiterator.hasNext()) {
							childSect = (Sect)childiterator.next();
							
							trueLink = "";
							tagImgS = "";
							
							if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
								trueLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
							}
							else{
								trueLink = childSect.getLink_url();
							}

							if(childSect.getLink_url_gb() == null || "".equals(childSect.getLink_url_gb()) || "02".equals(childSect.getLink_url_gb())){
								targetCheck= " target='_self'";
							}
							else{
								targetCheck=" target='_blank'";
							}
							
							
							strHtmlLeft += "<a href=\""+trueLink+"\""+targetCheck+">"+childSect.getSect_label()+"</a>\n"; 
						}
						strHtmlLeft += "</div>\n";
						strHtmlLeft += "<div id=\"pre_mn"+tmpid+"_sub_bar\" class=\"pre_mn_sub_bar\">\n";
						strHtmlLeft += "<div class=\"uparrow\"></div>\n";
						strHtmlLeft += "<div class=\"bar\"></div>\n";
						strHtmlLeft += "<div class=\"downarrow\"></div>\n";
						strHtmlLeft += "</div>\n";
						strHtmlLeft += "</div>\n";
					}
					strHtmlLeft += "</li>";
				}					
			strHtmlLeft += "</ul>";
			strHtmlLeft += "</div>";
		 }
		 
		 if(code_grp.equals("SPECIAL")){
			log.debug("SPECIAL ī�װ�");
			 strHtmlLeft += "<div class=\"float_parent\">\n";
			 strHtmlLeft += "<div class=\"float_tpl_bg\">\n";
			 strHtmlLeft += "<ul class=\"float_tpl_cate_ul\">\n";
			 
				while (iterator.hasNext()) {
					// parentSect
					sect = (Sect) iterator.next();
					dataNodeChildList = sect.getLnkChildSect();
					if(j==0){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_pid();
						strHtmlLeft += "<li><a href=\""+btrueLink+"\">��ü ��ǰ����</a></li>\n";
					}
					tmpid = j+1+"";
					j++;
					
					btrueLink ="";
					if("".equals(sect.getLink_url()) || sect.getLink_url()==null){
						btrueLink = "/front/shSectR.do?SectID="+sect.getSect_id();
					}
					else{
						btrueLink = sect.getLink_url();
					}
	
					if(sect.getLink_url_gb() == null || "".equals(sect.getLink_url_gb()) || "02".equals(sect.getLink_url_gb())){
						targetCheck= " target='_self'";
					}
					else{
						targetCheck=" target='_blank'";
					}
					
					strHtmlLeft += "<li id=\"float_tpl_s"+tmpid+"\" onmouseover=\"sub_L_call("+tmpid+");\">\n";
					strHtmlLeft += "<a href=\""+btrueLink+"\""+targetCheck+">"+sect.getSect_label()+"</a>\n";
					
					if(dataNodeChildList !=null && dataNodeChildList.size() > 0){
						
						strHtmlLeft += "<div id=\"float_tpl_s"+tmpid+"_L\" class=\"sub_cate_style\">\n";
						strHtmlLeft += "<ul>\n";
					
						childiterator = dataNodeChildList.iterator();

						while (childiterator.hasNext()) {
							childSect = (Sect)childiterator.next();
							
							trueLink = "";
							tagImgS = "";
							
							if("".equals(childSect.getLink_url())|| childSect.getLink_url()==null){
								trueLink = "/front/shSectR.do?SectID="+childSect.getSect_id();
							}
							else{
								trueLink = childSect.getLink_url();
							}

							if(childSect.getLink_url_gb() == null || "".equals(childSect.getLink_url_gb()) || "02".equals(childSect.getLink_url_gb())){
								targetCheck= " target='_self'";
							}
							else{
								targetCheck=" target='_blank'";
							}
							
							
							strHtmlLeft += "<li class=\"sub_cate_depth\"><a href=\""+trueLink+"\""+targetCheck+">"+childSect.getSect_label()+"</a></li>\n"; 
						}
						strHtmlLeft += "</ul>\n";
						strHtmlLeft += "</div>\n";
					}
					strHtmlLeft += "</li>";
				}					
			strHtmlLeft += "</ul>";
			strHtmlLeft += "</div>";
			strHtmlLeft += "</div>";
		 }
		 
		// log.debug("strHtmlLeft : " + strHtmlLeft);
	     return strHtmlLeft;
	 }

		/**
		 * @Noasoft_renewal(�߰�) 2007-10-11, ��뼮, byte ������ substring
		 * @param src
		 * @param size
		 * @return substring�� ���ڿ�
		 */
		public static String substring(String src, int size) {
			StringBuffer result = new StringBuffer("");
			int pos = 0;
			
			for (int i=0; i<src.length(); i++) {
				String tmp = src.substring(i, i+1);
				byte[] b = tmp.getBytes();
				if (b.length < 1)	break;
				pos = pos + b.length;
				result.append(tmp);
				if (pos >= size)
					break;
			}
			
			return result.toString();
		}
		/**
		 * @��ȭ����(I��,II��,III��,IV��,V��,VI��,VII��)
		 * @param src
		 * @param size
		 * @return
		 * HM/20130409/������/HttpUtils.java/��û�� �߰�
		 */
		public static boolean deptVenCheck(String venCode) {
			/* 	
			    1��[�̾���] : 003766,006323,003021
				2��[õȣ��] : 004502
				3��[�λ���] : 007562
				4��[������] : 004504
				5��[������] : 004503
				6��[����] : 004505, [�ƿ﷿] : 007799
				7��[��û��] : 011054
			*/
			
			if(venCode.equals("003766")||venCode.equals("006323")||
			   venCode.equals("003021")||venCode.equals("004502")||
		       venCode.equals("007562")||venCode.equals("004503")||
		       venCode.equals("004505")||venCode.equals("007799")||
		       venCode.equals("004504")||venCode.equals("011054")){
				return true;       
			
		    }else{
				return false;
			}
				
		}

	/**
	 * @parameter�� �м��ؼ� ����
	 * @param request
	 * @return String
	 */
	public static String getRequestParameter(HttpServletRequest request) {
		String getParam = "";
		String paramName = "";
		String paramValue = "";
		
		Enumeration enu = request.getParameterNames();//get/post��� ���
		while(enu.hasMoreElements()){
			paramName  = (String)enu.nextElement();
			paramValue = request.getParameter(paramName);
			getParam += paramName+"="+URLEncoder.encode(paramValue)+"&";
		}
		if(getParam!=null && !"".equals(getParam)){
			getParam = "?"+getParam;
		}
		return getParam;
	}		
}