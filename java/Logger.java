/*
제목 : 널포인터역참조
내용 : 1. 오버라이딩 함수 처리 - 함수 연결시 함수명, 파라미터수, 파라미터타입이 동일할 경우만 함수 호출 연결 시킴
          - 이전 엔진에서는 동일한 함수명으로 인해 무한루프에 빠져서 취약점 분석 완료가 되지 않았음 
          - 취약점 분석이 완료되는지 확인

실제 소스
*/

package jspeed.admin.log;

/*
 * @(#)Logger.java
 *
 * Copyright (c) 1998-2003 ITPlus Co., Ltd. All Rights Reserved.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import jspeed.base.util.DateHelper;

/**
 * Logger.
 *
 * @version	3.0 2002.11
 * @since	3.0
 * @author	kiki (Kyoung Gu. LEE)
 */
public class Logger //extends PrintStream
{
	/**
	 * not logging.
	 */
	public final static int NOT_LOGGING = 0; //00

	/**
	 * Write a log message to the console.
	 */
	public final static int ONLY_CONSOLE = 1; //01

	/**
	 * Write a log message to the log file.
	 */
	public final static int ONLY_FILE = 2; //10

	/**
	 * Write a log message to the console and file.
	 */
	public final static int CONSOLE_FILE = 3; //11

	//==============================================================

	private File m_logFile = null; // *.log 파일

	private Properties m_logConfig = null;
	private PrintStream m_out = null; // log 파일 Stream
	private String m_logLevel = null; // 2003.08.27 added

	private String m_currDate = null;
	private String m_currFileDate = null;

	private String m_logFileName = null;
	private String m_logDirectory = null;
	private int m_maxLogSize = 1024;

	//==============================================================

	public Logger(Properties config) {
		m_logConfig = config;

		m_logDirectory = m_logConfig.getProperty("log.directory", "/LOGS/sysadm/");

		String sysUid = config.getProperty("system.uid");
		if (sysUid != null)
			m_logDirectory += "/" + sysUid;

		m_maxLogSize = Integer.parseInt(m_logConfig.getProperty("log.maxSize", "1024")) * 1024;

	}

	//==============================================================

	public int getLevel() {
		return isDummy() ? LogLevel.DEBUG : Integer.parseInt(m_logConfig.getProperty("log.level", "128"));
	}

	public static String levelNumToString(int i) {
		return LogLevel.levelNumToString(i);
	}

	public static int levelStringToNum(String s) {
		return LogLevel.levelStringToNum(s);
	}

	public static String levelStringToSymbol(String s) {
		return LogLevel.levelStringToSymbol(s);
	}

	//--

	public boolean isLogging() {
		return isDummy() ? false : Boolean.getBoolean(m_logConfig.getProperty("log.verbose", "true"));
	}

	public boolean isDummy() {
		return m_logConfig == null;
	}

	public boolean isShowPrefix() {
		return true;
		//System.out.println("isShowPrefix = " + m_logConfig.getProperty("log.isShowPrefix", "true"));
		//return isDummy() ? false : Boolean.getBoolean(m_logConfig.getProperty("log.isShowPrefix", "true"));
	}

	//==============================================================

	public synchronized void println() {
		m_logLevel = levelNumToString(getLevel()); // 2003.08.27 added

		if (openFileStream()) {
			if (isShowPrefix()) {
				m_out.print(getPrefix(null, m_logLevel));
			}
			m_out.println();
		}
	}

	/** 2003.08.29 added
	 * 
	 * @param		x - type : String
	 * @return		void
	 */
	public synchronized void print(String x) {
		m_logLevel = "Debug";

		if (LogLevel.levelStringToNum(m_logLevel) <= getLevel()) // Logging level check
		{
			if (openFileStream()) {
				if (isShowPrefix()) {
					m_out.print(getPrefix(null, m_logLevel));
				}
				m_out.print(x);
			}
		}
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * 
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : String
	 * @return		void
	 */
	public synchronized void print(String level, Object caller, String x) {
		m_logLevel = level; // 2003.08.27 added

		if (LogLevel.levelStringToNum(level) <= getLevel()) // Logging level check
		{
			if (openFileStream()) {
				if (isShowPrefix()) {
					m_out.print(getPrefix(caller, level));
				}
				m_out.print(x);
			}
		}
	}

	/** 2003.08.26 added
	 * 
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : String
	 * @return		void
	 */
	public synchronized void print(int level, Object caller, String x) {
		m_logLevel = levelNumToString(level); // 2003.08.27 added

		if (level <= getLevel()) // Logging level check
		{
			if (openFileStream()) {
				if (isShowPrefix()) {
					m_out.print(getPrefix(caller, m_logLevel));
				}
				m_out.print(x);
			}
		}
	}

	/** 2003.08.29 added
	 * 
	 * @param		x - type : String
	 * @return		void
	 */
	public synchronized void println(String x) {
		m_logLevel = "Debug";

		if (LogLevel.levelStringToNum(m_logLevel) <= getLevel()) // Logging level check
		{
			if (openFileStream()) {
				if (isShowPrefix()) {
					m_out.print(getPrefix(null, m_logLevel));
				}
				m_out.print(x);
				m_out.println();
			}
		}
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * 
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : String
	 * @return		void
	 */
	public synchronized void println(String level, Object caller, String x) {
		m_logLevel = level; // 2003.08.27 added

		if (LogLevel.levelStringToNum(level) <= getLevel()) // Logging level check
		{
			if (openFileStream()) {
				if (isShowPrefix()) {
					m_out.print(getPrefix(caller, m_logLevel));
				}
				m_out.print(x);
				m_out.println();
			}
		}
	}

	/** 2003.08.26 added
	 * 
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : String
	 * @return		void
	 */
	public synchronized void println(int level, Object caller, String x) {
		m_logLevel = levelNumToString(level); // 2003.08.27 added

		if (level <= getLevel()) // Logging level check
		{
			if (openFileStream()) {
				if (isShowPrefix()) {
					m_out.print(getPrefix(caller, m_logLevel));
				}
				m_out.print(x);
				m_out.println();
			}
		}
	}

	//==============================================================

	/** 2003.10.17 added
	 * 
	 * @param	level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param	caller - type : Object
	 * @param	x - type : String
	 * @param	ex - type : Exception Object
	 * @return		void
	 */
	public synchronized void print(String level, Object caller, String x, Object ex) {
		m_logLevel = level;
		//System.out.println("LogLevel.levelStringToNum(level)=" + LogLevel.levelStringToNum(level));
		//System.out.println("getLevel()=" + getLevel());

		if (LogLevel.levelStringToNum(level) <= getLevel()) // Logging level check
		{
			if (openFileStream()) {

				if (isShowPrefix()) {
					m_out.print(getPrefix(caller, m_logLevel));
				}

				m_out.print(x);

				Exception out_ex = (Exception) ex;

				out_ex.printStackTrace(m_out);

			}
		}
	}

	/** 2003.10.17 added
	 * 
	 * @param	level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param	caller - type : Object
	 * @param	x - type : String
	 * @param	ex - type : Exception Object
	 * @return		void
	 */
	public synchronized void print(int level, Object caller, String x, Object ex) {
		m_logLevel = levelNumToString(level);

		if (level <= getLevel()) // Logging level check
		{
			if (openFileStream()) {
				if (isShowPrefix()) {
					m_out.print(getPrefix(caller, m_logLevel));
				}
				m_out.print(x);

				Exception out_ex = (Exception) ex;
				out_ex.printStackTrace(m_out);

			}
		}
	}

	/** 2003.10.17 added
	 * 
	 * @param	level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param	caller - type : Object
	 * @param	x - type : String
	 * @param	ex - type : Exception Object
	 * @return		void
	 */
	public synchronized void println(String level, Object caller, String x, Object ex) {
		m_logLevel = level;

		if (LogLevel.levelStringToNum(level) <= getLevel()) // Logging level check
		{
			if (openFileStream()) {
				if (isShowPrefix()) {
					m_out.print(getPrefix(caller, m_logLevel));
				}
				m_out.print(x);

				Exception out_ex = (Exception) ex;
				out_ex.printStackTrace(m_out);

				m_out.println();

			}
		}
	}

	/** 2003.10.17 added
	 * 
	 * @param	level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param	caller - type : Object
	 * @param	x - type : String
	 * @param	ex - type : Exception Object
	 * @return		void
	 */
	public synchronized void println(int level, Object caller, String x, Object ex) {
		m_logLevel = levelNumToString(level); // 2003.08.27 added

		if (level <= getLevel()) // Logging level check
		{
			if (openFileStream()) {
				if (isShowPrefix()) {
					m_out.print(getPrefix(caller, m_logLevel));
				}
				m_out.print(x);

				Exception out_ex = (Exception) ex;
				out_ex.printStackTrace(m_out);

				m_out.println();

			}
		}
	}

	//==============================================================

	public void close() {
		if (m_out != null) {
			m_out.close();
			m_out = null;
			m_logFile = null;
		}
	}

	public void flush() {
		m_out.flush();
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Write Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		b - type : byte[]
	 * @param		off - type : int
	 * @param		len - type : int
	 * @return		void
	 */
	public void write(String level, byte[] b, int off, int len) {
		print(level, null, b == null ? null : new String(b, off, len));
	}

	/** 2003.08.26 added
	 * Write Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		b - type : byte[]
	 * @param		off - type : int
	 * @param		len - type : int
	 * @return		void
	 */
	public void write(int level, byte[] b, int off, int len) {
		print(level, null, b == null ? null : new String(b, off, len));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Write Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		b - type : int
	 * @return		void
	 */
	public void write(String level, int b) {
		print(level, null, String.valueOf(b));
	}

	/** 2003.08.26 added
	 * Write Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		b - type : int
	 * @return		void
	 */
	public void write(int level, int b) {
		print(level, null, String.valueOf(b));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Write Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		b - type : byte[]
	 * @return		void
	 */
	public void write(String level, byte[] b) {
		print(level, null, b == null ? null : String.valueOf(b));
	}

	/** 2003.08.26 added
	 * Write Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		b - type : byte[]
	 * @return		void
	 */
	public void write(int level, byte[] b) {
		print(level, null, b == null ? null : String.valueOf(b));
	}

	//--------------------------------------------------------------------------------

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : boolean
	 * @return		void
	 */
	public void print(String level, boolean x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : boolean
	 * @return		void
	 */
	public void print(int level, boolean x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : char
	 * @return		void
	 */
	public void print(String level, char x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : char
	 * @return		void
	 */
	public void print(int level, char x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : char[]
	 * @return		void
	 */
	public void print(String level, char[] x) {
		print(level, null, x == null ? "null" : String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : char[]
	 * @return		void
	 */
	public void print(int level, char[] x) {
		print(level, null, x == null ? "null" : String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : int
	 * @return		void
	 */
	public void print(String level, int x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : int
	 * @return		void
	 */
	public void print(int level, int x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : long
	 * @return		void
	 */
	public void print(String level, long x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : long
	 * @return		void
	 */
	public void print(int level, long x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : float
	 * @return		void
	 */
	public void print(String level, float x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : float
	 * @return		void
	 */
	public void print(int level, float x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : double
	 * @return		void
	 */
	public void print(String level, double x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : double
	 * @return		void
	 */
	public void print(int level, double x) {
		print(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : String
	 * @return		void
	 */
	public void print(String level, String x) {
		print(level, null, x);
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : String
	 * @return		void
	 */
	public void print(int level, String x) {
		print(level, null, x);
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : Object
	 * @return		void
	 */
	public void print(String level, Object x) {
		print(level, null, x == null ? "null" : x.toString());
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : Object
	 * @return		void
	 */
	public void print(int level, Object x) {
		print(level, null, x == null ? "null" : x.toString());
	}

	//==============================================================

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : boolean
	 * @return		void
	 */
	public void println(String level, boolean x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : boolean
	 * @return		void
	 */
	public void println(int level, boolean x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : char
	 * @return		void
	 */
	public void println(String level, char x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : char
	 * @return		void
	 */
	public void println(int level, char x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : char[]
	 * @return		void
	 */
	public void println(String level, char[] x) {
		println(level, null, x == null ? "null" : String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : char[]
	 * @return		void
	 */
	public void println(int level, char[] x) {
		println(level, null, x == null ? "null" : String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : int
	 * @return		void
	 */
	public void println(String level, int x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : int
	 * @return		void
	 */
	public void println(int level, int x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : long
	 * @return		void
	 */
	public void println(String level, long x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : long
	 * @return		void
	 */
	public void println(int level, long x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : float
	 * @return		void
	 */
	public void println(String level, float x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : float
	 * @return		void
	 */
	public void println(int level, float x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : double
	 * @return		void
	 */
	public void println(String level, double x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : double
	 * @return		void
	 */
	public void println(int level, double x) {
		println(level, null, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : String
	 * @return		void
	 */
	public void println(String level, String x) {
		println(level, null, x);
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : String
	 * @return		void
	 */
	public void println(int level, String x) {
		println(level, null, x);
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		x - type : Object
	 * @return		void
	 */
	public void println(String level, Object x) {
		println(level, null, x == null ? "null" : x.toString());
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		x - type : Object
	 * @return		void
	 */
	public void println(int level, Object x) {
		println(level, null, x == null ? "null" : x.toString());
	}

	//==============================================================

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : boolean
	 * @return		void
	 */
	public void print(String level, Object caller, boolean x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : boolean
	 * @return		void
	 */
	public void print(int level, Object caller, boolean x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : char
	 * @return		void
	 */
	public void print(String level, Object caller, char x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : char
	 * @return		void
	 */
	public void print(int level, Object caller, char x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : char[]
	 * @return		void
	 */
	public void print(String level, Object caller, char[] x) {
		print(level, caller, x == null ? "null" : String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : char[]
	 * @return		void
	 */
	public void print(int level, Object caller, char[] x) {
		print(level, caller, x == null ? "null" : String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : int
	 * @return		void
	 */
	public void print(String level, Object caller, int x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : int
	 * @return		void
	 */
	public void print(int level, Object caller, int x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : long
	 * @return		void
	 */
	public void print(String level, Object caller, long x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : long
	 * @return		void
	 */
	public void print(int level, Object caller, long x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : float
	 * @return		void
	 */
	public void print(String level, Object caller, float x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : float
	 * @return		void
	 */
	public void print(int level, Object caller, float x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : double
	 * @return		void
	 */
	public void print(String level, Object caller, double x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : double
	 * @return		void
	 */
	public void print(int level, Object caller, double x) {
		print(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Print Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : Object
	 * @return		void
	 */
	public void print(String level, Object caller, Object x) {
		print(level, caller, x == null ? "null" : x.toString());
	}

	/** 2003.08.26 added
	 * Print Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : Object
	 * @return		void
	 */
	public void print(int level, Object caller, Object x) {
		print(level, caller, x == null ? "null" : x.toString());
	}

	//==============================================================

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : boolean
	 * @return		void
	 */
	public void println(String level, Object caller, boolean x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : boolean
	 * @return		void
	 */
	public void println(int level, Object caller, boolean x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : char
	 * @return		void
	 */
	public void println(String level, Object caller, char x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : char
	 * @return		void
	 */
	public void println(int level, Object caller, char x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : char[]
	 * @return		void
	 */
	public void println(String level, Object caller, char[] x) {
		println(level, caller, x == null ? "null" : String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : char[]
	 * @return		void
	 */
	public void println(int level, Object caller, char[] x) {
		println(level, caller, x == null ? "null" : String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : int
	 * @return		void
	 */
	public void println(String level, Object caller, int x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : int
	 * @return		void
	 */
	public void println(int level, Object caller, int x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : long
	 * @return		void
	 */
	public void println(String level, Object caller, long x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : long
	 * @return		void
	 */
	public void println(int level, Object caller, long x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : float
	 * @return		void
	 */
	public void println(String level, Object caller, float x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : float
	 * @return		void
	 */
	public void println(int level, Object caller, float x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : double
	 * @return		void
	 */
	public void println(String level, Object caller, double x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : double
	 * @return		void
	 */
	public void println(int level, Object caller, double x) {
		println(level, caller, String.valueOf(x));
	}

	/** 2003.08.26 modify parameter (String type parameter added)
	 * Println Log file method
	 *
	 * @param		level	- type : String ("Debug", "Info", "Warning", "Error")
	 * @param		caller - type : Object
	 * @param		x - type : Object
	 * @return		void
	 */
	public void println(String level, Object caller, Object x) {
		println(level, caller, x == null ? "null" : x.toString());
	}

	/** 2003.08.26 added
	 * Println Log file method
	 *
	 * @param		level	- type : int(LogLevel.DEBUG, LogLevel.INFO, LogLevel.WARNING, LogLevel.ERROR)
	 * @param		caller - type : Object
	 * @param		x - type : Object
	 * @return		void
	 */
	public void println(int level, Object caller, Object x) {
		println(level, caller, x == null ? "null" : x.toString());
	}

	//==============================================================

	public static String parseLocation(int loc) {
		switch (loc) {
			case NOT_LOGGING:
				return "NOT_LOGGING";

			case ONLY_CONSOLE:
				return "ONLY_CONSOLE";

			case ONLY_FILE:
				return "ONLY_FILE";

			case CONSOLE_FILE:
				return "CONSOLE_FILE";

			default:
				return null;
		}
	}

	public static int parseLocation(String loc) {
		if (loc == null)
			return NOT_LOGGING;

		else if (loc.equalsIgnoreCase("ONLY_CONSOLE"))
			return ONLY_CONSOLE;

		else if (loc.equalsIgnoreCase("ONLY_FILE"))
			return ONLY_FILE;

		else if (loc.equalsIgnoreCase("CONSOLE_FILE"))
			return CONSOLE_FILE;

		else
			return NOT_LOGGING;
	}

	public static String parseClass(Object o) {
		if (o == null)
			return null;

		else if (o instanceof Class)
			return ((Class) o).getName();

		else if (o instanceof String)
			return (String) o;

		return o.getClass().getName();
	}

	//==============================================================

	/**
	 * Log 파일저장
	 */
	private boolean openFileStream() {
		synchronized (this) {
			try {
				String currFileDate = DateHelper.currentTime("yyyyMMdd");

				if (!currFileDate.equals(m_currFileDate)) {
					m_currFileDate = currFileDate;

					StringBuffer sb = new StringBuffer();
					sb.append(m_logDirectory);

					File aFile = new File(sb.toString());
					if (!aFile.exists())
						aFile.mkdirs();

					sb.append("/");
					sb.append(m_logConfig.getProperty("log.filename", "System") + "_");

					if (m_out != null)
						m_out.close();

					m_logFile = new File(sb.toString() + m_currFileDate + ".log");
					m_out = new PrintStream(new FileOutputStream(m_logFile.getCanonicalPath(), true), true);
				} else {
					if (m_logFile != null && m_logFile.length() < m_maxLogSize)
						return true;

					StringBuffer sb = new StringBuffer();
					sb.append(m_logDirectory);

					File aFile = new File(sb.toString());
					if (!aFile.exists())
						aFile.mkdirs();

					sb.append("/");
					sb.append(m_logConfig.getProperty("log.filename", "System") + "_");

					if (m_out != null)
						m_out.close();

					int i = 0;

					while ((aFile = new File(sb.toString() + m_currFileDate + "_" + padNumber(4, ++i) + ".log")).exists())
						;

					if (m_logFile != null) {
						if (!m_logFile.renameTo(aFile)) {
							throw new IOException("Failed to rename log file on attempt to rotate logs");
						}
					}

					m_logFile = new File(sb.toString() + m_currFileDate + ".log");
					m_out = new PrintStream(new FileOutputStream(m_logFile.getCanonicalPath(), true), true);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	private String padNumber(int size, long num) {
		String s = "";
		for (int j = size - 1; j >= 0; j--) {
			long l1 = (long) Math.pow(10D, j);
			long l2 = num / l1;
			s = s + l2;
			num -= l2 * l1;
		}

		return s;
	}

	/**
	 * Log File Format Prefix
	 *
	 * [Log Date]Tab
	 *
	 * @param	caller	Object
	 * @return		String	Prefix format String
	 */
	private String getPrefix(Object caller, String level) {
		m_currDate = DateHelper.currentTime("[yyyy-MM-dd  HH:mm:ss]");

		String className = caller.getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1, className.length());

		StringBuffer sb = new StringBuffer();
		sb.append(m_currDate);
		sb.append("\t");
		sb.append(level);
		sb.append("\t[");
		sb.append(className);
		sb.append("] ");
		sb.append("\t");

		return sb.toString();
	}

	/**
	 * Log Index File Format Prefix
	 *
	 * 1. [Log Date][채널구분][UUID] : Key
	 * 2. Log Date
	 * 3. 채널구분
	 * 4. UUID
	 * 5. 로그파일 경로
	 * 6. 사용자 필드
	 *
	 * @return		String	Prefix format String
	 */
	private String getPrefix() {
		StringBuffer sb = new StringBuffer();
		sb.append("");

		return sb.toString();
	}

}