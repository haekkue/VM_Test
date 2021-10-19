/*
 * @(#) UserMap.java 2.0,  2009. 04. 01.
 * 
 * Copyright (c) 2009 Envision Tech All rights reserved.
 */
 
package kr.co.envision.fw;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import net.sourceforge.jtds.jdbc.ClobImpl;

import org.apache.log4j.Logger;

/**
 * @author Envision Tech (rnd@cubesec.co.kr)
 * @version 2.0,  2009. 04. 01.
 * 
 */
public class UserMap extends java.util.HashMap {

	private static final long serialVersionUID = -3671210048221588090L;
	private NumberFormat nf = NumberFormat.getInstance();
	private NumberFormat cf = NumberFormat.getCurrencyInstance(Locale.KOREA);

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserMap.class);

	private boolean isSet = false;
	/**
	 * 
	 */
	public UserMap() {
		super();
	}

	/**
	 * @param arg0
	 */
	public UserMap(int arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public UserMap(int arg0, float arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public UserMap(Map arg0) {
		super(arg0);
	}
	
	public void set(){
		this.isSet = true;
	}
	
	public boolean isSet(){
		return this.isSet;
	}
  
  public String getHTML(String key) {
    return Utility.toHTML(getString(key));
  }
  
  public String getHTML(String key, String def) {
    return Utility.toHTML(getString(key, def));
  }
  
  public String getTextarea(String key) {
    return Utility.toTextarea(getString(key));
  }
  
  public String getTextarea(String key, String def) {
    return Utility.toTextarea(getString(key, def));
  }
  
	public String getString( String key ){
		Object obj = super.get(key.toUpperCase());
		ClobImpl clob = null;
		String str = "";
		try {
			if( obj instanceof ClobImpl ){
				clob = (ClobImpl)obj;
				Reader reader = clob.getCharacterStream();
				char[] c = new char[1024];
				while( reader.read(c) > -1 ){
					str += new String(c);
				}
				return str;			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if( obj == null ) return "";
		else return obj.toString();
	}
	
	public String getString( String key, String def ){
		String str = this.getString(key.toUpperCase());
		if( str.trim().equals("") ) str = def;
		return str;
	}
	
	public int getInt( String key ){
		Object obj = super.get(key.toUpperCase());
		int ret = 0;
		if( obj instanceof java.lang.Number ){
			ret = ((Number)obj).intValue();
		}else{
			try{
				ret = Integer.parseInt(obj.toString());
			}catch(Exception ex){
				ret = 0;
			}
		}
		return ret;
	}
	
	public long getLong( String key ){
		Object obj = super.get(key.toUpperCase());
		long ret = 0;
		if( obj instanceof java.lang.Number ){
			ret = ((Number)obj).longValue();
		}else{
			try{
				ret = Long.parseLong(obj.toString());
			}catch(Exception ex){
				ret = 0;
			}
		}
		return ret;
	}
	
	public Integer getInteger( String key ){
		return new Integer( this.getInt(key.toUpperCase()) );
	}

	public double getDouble( String key ){
		double ret = 0;
		Object obj = super.get(key.toUpperCase());
		if( obj == null ) return 0;
		try{
			ret = Double.parseDouble(obj.toString());
		}catch(Exception ex){
			return 0;
		}
		return ret;
	}
	
	public String getPersent( String key ){
		String str = "";
		NumberFormat format = NumberFormat.getPercentInstance();
		double value = this.getDouble( key );
		str = format.format( value );
		return str;
	}
	
	
	public String getDateFormat( String key, String type ){
		String str = "";
		Object o = null;
		SimpleDateFormat in = new SimpleDateFormat("yyyyMMddkkmmss");
		SimpleDateFormat out = new SimpleDateFormat(type);
		
		o = this.get( key.toUpperCase() );
		if( o == null || o.toString().trim().equals("") ) return "-";
		try {
			str = o instanceof String? out.format( in.parse((String)o) ) : out.format((Date)o);
		} catch (ParseException e) {
			logger.error("type="+ type + ",value=" + o, e );
			return "-";
		}
		
		return str;
	}

	public String getDateFormat( String key, String fType, String tType ){
		String str = "";
		String s = null;
		SimpleDateFormat in = new SimpleDateFormat(fType);
		SimpleDateFormat out = new SimpleDateFormat(tType);
		
		s = this.getString( key.toUpperCase() ).trim();
		if( s.equals("") ) return "-";
		try {
			str = out.format( in.parse(s) );
		} catch (ParseException e) {
			logger.error("type="+ tType + ",value=" + s, e );
			return "-";
		}
		
		return str;
	}

	public String getNumberFormat( String key ){
		String str = "";
		str = nf.format( this.getDouble( key.toUpperCase() ) );
		return str;
	}

	public String getCurrencyFormat( String key ){
		String str = "";
		double d = this.getDouble( key.toUpperCase() );
		if( d == 0 ) return "-";
		str = cf.format( d );
		return str;
	}
	
	public Object get(String key){
		return super.get(key.toUpperCase());
	}

}


