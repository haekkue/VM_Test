/*
 프로그램 명	:MultiPartParser.java
 설명		:ehyundai 파일 업로드를위한 Multipart
 작성일자	:2002년 4월 10일		작성자	:오인탁
 수정일자	:
 */

package com.hmall.util.ewrap;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

public class MultiPartParser {

	private Vector parameters = new Vector();

	private DataInputStream servletIn = null; // 서블릿으로 받아오는 데이타 스트림(여기에는 인자값도 포함되어 있음)

	private FileOutputStream fileOut = null; // 파일에 저장할 스트림

	private final int fileBufferMax = 1024; // 한번에 읽어드릴 버퍼의 최대크기

	private final int fileBufferMin = 64; // 한번에 읽어 드릴 버퍼의 최소크기

	private byte[] fileBuffer = new byte[fileBufferMax]; // 한번에 읽어 드릴 버퍼

	private int fileBufferLength = 0; // 버퍼의 크기

	private boolean endOfFile = false; // 버퍼의 끝을 체크하는 플레그

	private int leadBufferLength = 0;

	private byte[] leadBuffer = new byte[100];

	private byte[] delimiter = new byte[35];

	private byte[] nameHeader = new String("Content-Disposition: form-data; name=").getBytes();

	private byte oneByte = 0;

	private long maxFileSize = 0; // 업로드 되는 최대 파일 크기

	private final byte NEWLINE = (byte) '\n';

	private final byte CR = (byte) '\r';

	private final byte DASH = (byte) '-';

	private final byte EQUAL = (byte) '=';

	private final byte SPACE = (byte) ' ';

	private final byte SEMICOLON = (byte) ';';

	private final byte DOUBLEQUOTE = (byte) '\"';

	private String targetDir = "."; // 기본 다운로드 디렉토리

	private String parmName;

	private String parmValue;

	private File saveFile;

	private ERDictionary dictionary = null;

	public MultiPartParser() {
	}

	/*******************************************************************************************************************************
	 * 생성자(넘어오는 값을 파싱하면서, 업로드 되는 파일이 저장될 디렉토리를 설정한다.)
	 * @param
	 * @return
	 ******************************************************************************************************************************/
	public MultiPartParser(ERDictionary dictionary, HttpServletRequest req, String dir, long size) throws Exception {

		this.dictionary = dictionary;
		setTargetDir(dir); // 다운로드 디록토리 설정
		setMaxFileSize(size); // 최대 파일 사이즈 설정
		getRequest(req); // 서블릿에서 넘어온 데이타를 읽어 드린다.
		if ((String) dictionary.get("ActorError") != null && ((String) dictionary.get("ActorError")).equals("true"))
			return;
		if ((String) dictionary.get("FileError") != null && ((String) dictionary.get("FileError")).equals("true"))
			return;
	}

	/*******************************************************************************************************************************
	 * 생성자
	 * @param
	 * @return
	 ******************************************************************************************************************************/
	public void getRequest(HttpServletRequest req) throws Exception {
		servletIn = new DataInputStream(req.getInputStream()); // 서블릿에서 넘어온 데이타를 스트림에 저장
		makeParameters(); // 그 스트림을 가지고 파라미너를 파싱한다.
		if ((String) dictionary.get("ActorError") != null && ((String) dictionary.get("ActorError")).equals("true"))
			return;
		if ((String) dictionary.get("FileError") != null && ((String) dictionary.get("FileError")).equals("true"))
			return;
	}

	private void setMaxFileSize(long size) {
		maxFileSize = size;
	}

	/*******************************************************************************************************************************
	 * 파라미터를 만든다.
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private void makeParameters() throws Exception {
		
		// delimiter cut
		if (servletIn.read(delimiter) != delimiter.length) {
			return;
		}

		while (!endOfFile) {

			parmName = null;
			parmValue = null;
			saveFile = null;

			try {

				while (servletIn.readByte() != NEWLINE)
					;// new line skip

				// parm name make
				getParmName();

				// parm value make
				if (oneByte == NEWLINE) { // field value

					while (servletIn.readByte() != NEWLINE)
						;// new line skip

					getParmValue(); // parm value process

				} else if (oneByte == SEMICOLON) { // file value

					servletIn.readByte(); // space line skip
					

					getFileName(); // file value process
					
					if ((String) dictionary.get("ActorError") != null && ((String) dictionary.get("ActorError")).equals("true"))
						return;
					if ((String) dictionary.get("FileError") != null && ((String) dictionary.get("FileError")).equals("true"))
						return;
				}
			} catch (EOFException e) {
				endOfFile = true;
			}

			addParameter(); // add parameter

		}

	}

	/*******************************************************************************************************************************
	 * 기존의 파라미터집합에 또 하나의 파라미터를 더한다.
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private void addParameter() {

		if (parmName == null)
			return;

		MultiPartParameter mp = new MultiPartParameter(parmName);

		mp.setParmValue(parmValue);
		mp.setSaveFile(saveFile);

		parameters.addElement(mp);

	}

	/*******************************************************************************************************************************
	 * parameter name make function - read until eof, space, semicolon 파라미터의 이름을 얻어 온다.
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private void getParmName() throws Exception {

		byte[] data = new byte[100];
		int length;

		// field name header skip
		while (servletIn.readByte() != EQUAL)
			; // '='이 아닐때까지 커서를 이동 시킨다. 앞 부분의 헤너를 스킵

		// name make unitl newline or semicolon
		for (length = 0; true; length++) {

			oneByte = servletIn.readByte();

			if (oneByte == NEWLINE || oneByte == SEMICOLON) {
				break;
			} else {
				data[length] = oneByte;
			}

		}

		parmName = trimParm(new String(data, 0, length));

	}

	/*******************************************************************************************************************************
	 * parameter value make function - read until eof, delimiter
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private void getParmValue() throws Exception {

		byte[] data = new byte[1024];
		int length = 0;

		while (true) {

			oneByte = servletIn.readByte();

			if (oneByte == DASH) {
				if (isDelimiter(oneByte)) {
					break;
				} else {
					for (int i = 0; i < leadBufferLength; i++) {
						data[length] = leadBuffer[i];
						length++;
						data = checkBuffer(data, length);
					}
				}
			} else {
				data[length] = oneByte;
				length++;
				data = checkBuffer(data, length);

			}
		}

		parmValue = trimParm(new String(data, 0, length));
	}

	/*******************************************************************************************************************************
	 * buffer check
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private byte[] checkBuffer(byte[] buff, int length) {
		if (buff == null) {
			return null;
		}

		if ((buff.length - 10) <= length) {
			byte[] newBuff = new byte[buff.length * 2];
			System.arraycopy(buff, 0, newBuff, 0, buff.length);

			return newBuff;
		}

		return buff;

	}

	/*******************************************************************************************************************************
	 * 파라미터의 이름들을 얻어온다.
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private Enumeration getMultiPartParameters() {
		return (parameters.elements());
	}

	/*******************************************************************************************************************************
	 * file save and name make function read until eof, delimiter
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private void getFileName() throws Exception {
		byte[] data = new byte[1000];
		int length = 0;
		

		// skip filename header
		while (servletIn.readByte() != EQUAL)
			;

		// get file name
		while ((oneByte = servletIn.readByte()) != NEWLINE) {
			data[length] = oneByte;
			length++;
		}
		
		parmValue = makeFileName(trimParm(new String(data, 0, length)));
		if ((String) dictionary.get("ActorError") != null && ((String) dictionary.get("ActorError")).equals("true"))
			return;

		// null file skip
		if (parmValue == null || parmValue.length() == 0) {
			while (true) {
				while (true) {
					oneByte = servletIn.readByte();
					if (oneByte != CR && oneByte != NEWLINE) {
						break;
					}
				}
				if (isDelimiter(oneByte)) {
					return;
				}
			}
		}

		// target file open
		fileOpen();

		// content-type skip
		while ((oneByte = servletIn.readByte()) != NEWLINE) {
		}
		;
		// new line skip
		servletIn.readByte();
		servletIn.readByte();

		// file write
		length = 0;
		long filelen = 0L;

		while (true) {

			oneByte = servletIn.readByte();

			// 파일 크기 체크
			if (filelen > maxFileSize) {
				dictionary.put("FileError", "true");
				dictionary.put("ErrorMessage", "최대 파일 크기를 초과 하였습니다.");

				// 파일을 지운다.
				deleteFile(targetDir, parmValue);
				return;
			}
			filelen++;

			if (oneByte == NEWLINE) {

				data[length] = oneByte;
				length++;

				oneByte = servletIn.readByte();
				data[length] = oneByte;
				length++;

				if (oneByte == DASH) {

					if (isDelimiter(oneByte)) {
						break;
					} else {
						if (length > 0) {
							fileWrite(data, 0, length);
							length = 0;
						}
						fileWrite(leadBuffer, 1, leadBufferLength - 1);
					}
				} else {
					if (length > 0) {
						fileWrite(data, 0, length);
						length = 0;
					}
				}
			}
			/* modify */
			else if (oneByte == DASH) {

				data[length] = oneByte;
				length++;

				if (isDelimiter(oneByte)) {
					break;
				} else {
					if (length > 0) {
						fileWrite(data, 0, length);
						length = 0;
					}
					fileWrite(leadBuffer, 1, leadBufferLength - 1);
				}
			}

			/* end modify */
			else {

				if (length > 0) {
					fileWrite(data, 0, length);
					length = 0;
				}

				data[length] = oneByte;
				length++;

			}

		}

		length--; // dash skip
		length--; // newline skip

		if (length > 0) {
			if (data[length - 1] == CR) {
				length--;
			}
			fileWrite(data, 0, length);
		}
		fileClose();
	}

	/*******************************************************************************************************************************
	 * 업로드된 파일일 삭제 한다.
	 * @param String,String,REDictionary
	 * @return void
	 ******************************************************************************************************************************/
	private void deleteFile(String dir, String filename) throws Exception {
		StringBuffer file_name = new StringBuffer(20);

		try {

			if (file_name != null) {
				file_name.delete(0, file_name.length());
			}
			file_name.append(dir).append(String.valueOf((System.getProperty("file.separator")).charAt(0)));
			file_name.append(filename);
			File file = new File(file_name.toString());
			if (file.exists()) {
				file.delete();
			}

			dictionary.put("ActorError", "false");
		} catch (Exception e) {
			dictionary.put("ActorError", "true");
			dictionary.put("ErrorMessage", "파일 삭제에 실패 했습니다.");
		}
	}

	/*******************************************************************************************************************************
	 * 파일명을 만든다. (현재의 시간+파일이름)
	 * @param String
	 * @return String
	 ******************************************************************************************************************************/
	private String makeFileName(String tempfile) throws Exception {
		String retStr = null;
		String filename = null;

		try {
			if (tempfile == null)
				return null;
			
			filename = tempfile;
			String filename2 = (filename.substring(filename.indexOf('.') + 1, filename.length())).toUpperCase();
			

			if (filename2.equals("JPG") || filename2.equals("GIF") || filename2.equals("JPEG")) {

				if (filename != null && !filename.equals(""))
					retStr = new String(filename);
				else
					return "";

				// char sep = (System.getProperty("file.separator")).charAt(0);
				char sep = '\\';
				retStr = retStr.substring(retStr.lastIndexOf(sep) + 1);
				retStr = Long.toString(System.currentTimeMillis()) + retStr;
			} else {
				dictionary.put("ActorError", "true");
				dictionary.put("ErrorMessage", "이미지 등록은 확장자가 .jpg .jpeg .gif인 파일만 가능합니다.");
				return null;
			}

		} catch (Exception e) {
			dictionary.put("ActorError", "true");
			dictionary.put("ErrorMessage", "파일이름을 만드는데 실패 했습니다.");
			return null;
		}

		return retStr;
	}

	/*******************************************************************************************************************************
	 * file buffer open
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private void fileOpen() throws Exception {

		saveFile = getTargetFile();
		fileOut = new FileOutputStream(saveFile);
		fileBufferLength = 0;

	}

	/*******************************************************************************************************************************
	 * file bufferedWrite
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private void fileWrite(byte[] inData, int startIndex, int inLength) throws Exception {

		for (int i = 0; i < inLength; i++) {

			if (fileBufferLength == fileBufferMax) {

				fileBufferFlush();

			}

			fileBuffer[fileBufferLength++] = inData[startIndex++];

		}

	}

	/*******************************************************************************************************************************
	 * file buffer half flush
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private void fileBufferFlush() throws Exception {

		fileOut.write(fileBuffer, 0, fileBufferMin);

		fileBufferLength = 0;

		for (int i = fileBufferMin; i < fileBufferMax; i++) {
			fileBuffer[fileBufferLength++] = fileBuffer[i];
		}

	}

	/*******************************************************************************************************************************
	 * File Close
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private void fileClose() throws Exception {
		if (fileBufferLength > 0) { // buffer clear

			if (fileBuffer[fileBufferLength - 1] == CR) {
				fileBufferLength--;
			}

			fileOut.write(fileBuffer, 0, fileBufferLength);
			fileBufferLength = 0;
		}

		fileOut.close();

	}

	/*******************************************************************************************************************************
	 * is delimiter ? function
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private boolean isDelimiter(byte initByte) throws Exception {

		leadBufferLength = 0;

		leadBuffer[leadBufferLength] = initByte;
		leadBufferLength++;

		while (true) {

			oneByte = servletIn.readByte();
			leadBuffer[leadBufferLength] = oneByte;
			leadBufferLength++;

			if (compareBytes(delimiter, leadBuffer, leadBufferLength)) {

				if (leadBufferLength == delimiter.length) {
					return true;
				}
			} else {
				return false;
			}

		}

	}

	/*******************************************************************************************************************************
	 * compare byte arrays function
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private boolean compareBytes(byte[] source, byte[] target, int length) throws Exception {

		if (source == null || target == null)
			return false;

		if (source.length < length || target.length < length)
			return false;

		for (int i = 0; i < length; i++) {
			if (source[i] != target[i])
				return false;
		}

		return true;

	}

	/*******************************************************************************************************************************
	 * trim parameter function
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private String trimParm(String s) throws Exception {

		String str;

		if (s == null)
			return null;

		byte[] src = (s.trim()).getBytes();

		if (src.length == 0)
			return null;

		if (src[0] == DOUBLEQUOTE)
			src[0] = SPACE;
		if (src[src.length - 1] == DOUBLEQUOTE)
			src[src.length - 1] = SPACE;

		// str = ((new String(src)).trim());

		str = (new String(src, "8859_1")).trim();

		str = new String(str.getBytes("8859_1"), "KSC5601");

		if (str != null && str.length() == 0) {
			str = null;
		}

		return (str);

	}

	/*******************************************************************************************************************************
	 * set target dir
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	public void setTargetDir(String dir) throws Exception {
		if (dir == null || !(new File(dir)).isDirectory()) {
			throw new Exception("invalid Directory");
		}
		targetDir = dir;
	}

	public String getTargetDir() throws Exception {
		return targetDir;
	}

	/*******************************************************************************************************************************
	 * get target file
	 * @param
	 * @return void
	 ******************************************************************************************************************************/
	private File getTargetFile() throws Exception {

		String fileName, surfix, prefix, serialSurfix;
		int pointIndex, serial = 0;
		File file;

		char otherSeparator;

		otherSeparator = File.separatorChar == '/' ? '\\' : '/';

		String tmpName = parmValue;

		tmpName = tmpName.replace(otherSeparator, File.separatorChar);

		fileName = (new File(tmpName)).getName();

		pointIndex = fileName.lastIndexOf('.');
		if (pointIndex == -1 || pointIndex == (fileName.length() - 1)) {
			prefix = fileName;
			surfix = null;
		} else {
			prefix = fileName.substring(0, pointIndex);
			surfix = fileName.substring(pointIndex + 1);
		}

		fileName = surfix == null ? prefix : prefix + "." + surfix;
		file = new File(targetDir, fileName);

		while (file.exists()) {

			serialSurfix = String.valueOf(serial);
			fileName = surfix == null ? prefix + serialSurfix : prefix + serialSurfix + "." + surfix;
			file = new File(targetDir, fileName);

			serial++;

		}

		return file;

	}

	public String getParameter(String name) {

		MultiPartParameter parm;

		Enumeration enumParms = getMultiPartParameters();

		while (enumParms.hasMoreElements()) {

			parm = (MultiPartParameter) enumParms.nextElement();

			if (name.equals(parm.getParmName())) {
				return (parm.getParmValue());
			}

		}

		return null;

	}

	public File getFile(String name) {

		MultiPartParameter parm;

		Enumeration enumParms = getMultiPartParameters();

		while (enumParms.hasMoreElements()) {

			parm = (MultiPartParameter) enumParms.nextElement();

			if (name.equals(parm.getParmName())) {
				return (parm.getSaveFile());
			}

		}

		return null;

	}

	public String[] getParameterValues(String name) {
		Vector values = new Vector();
		String[] stringValues;
		int length;
		MultiPartParameter parm;

		Enumeration enumParms = getMultiPartParameters();

		while (enumParms.hasMoreElements()) {

			parm = (MultiPartParameter) enumParms.nextElement();

			if (name.equals(parm.getParmName())) {
				values.addElement(parm.getParmValue());
			}

		}

		length = values.size();
		stringValues = new String[length];

		for (int i = 0; i < length; i++) {
			stringValues[i] = (String) values.elementAt(i);
		}

		return (stringValues);

	}

	public Enumeration getParameterNames() {
		Vector names = new Vector();

		MultiPartParameter parm;

		Enumeration enumParms = getMultiPartParameters();

		while (enumParms.hasMoreElements()) {

			parm = (MultiPartParameter) enumParms.nextElement();

			names.addElement(parm.getParmName());

		}

		return (names.elements());
	}

	/*
	 * inner class MutiPartParameter
	 */

	public class MultiPartParameter {

		String parmName;

		String parmValue;

		File saveFile;

		public MultiPartParameter(String name) {
			parmName = name;
		}

		public void setParmName(String name) {
			parmName = name;
		}

		public void setParmValue(String value) {
			parmValue = value;
		}

		public void setSaveFile(File file) {
			saveFile = file;
		}

		public String getParmName() {
			return parmName;
		}

		public String getParmValue() {
			return parmValue;
		}

		public File getSaveFile() {
			return saveFile;
		}

	}
}