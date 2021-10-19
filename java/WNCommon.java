/*
실제 소스
*/

package ezweb.search.html.korea.common;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.io.UnsupportedEncodingException;
import java.text.*;
import java.util.*;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jaxen.XPath;
import org.jaxen.dom4j.Dom4jXPath;
import java.io.*;
import java.net.*;

import javax.servlet.jsp.JspWriter;

import org.dom4j.io.SAXReader;

import QueryAPI400.Search;

public class WNCommon {
	private Search search = null;
    private JspWriter out = null;
    boolean isDebug = false;

    /**
     * SF-1 QueryAPI Search 클래스 객체를 생성하는 WNCommon의 생성자 함수이다.
     */
    public WNCommon() {
        this.search = new Search();
    }

    /**
     * WNCommon 오버로딩(overloading) 함수이다.
     * WNCommon을 단독으로 사용할 경우 debug 사용유무를 지정할 수 있다.
     * @param out
     * @param isDubug
     */
    public WNCommon(JspWriter out, boolean isDubug) {
        this.search = new Search();
        this.out = out;
        this.isDebug = isDubug;
    }

    /**
     * 검색 질의 키워드와 질의나 출력에 사용될 문자집합을 정한다.
     * @param query
     * @param charSet
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int setCommonQuery(String query, String charSet) {
        int ret = 0;
        ret = search.w3SetCodePage(charSet);
        ret = search.w3SetQueryLog(1);
        ret = search.w3SetCommonQuery(query);
        return ret;
    }

    /**
     * setCommonQuery의 오버로딩(overloading) 함수로
     * session정보를 부가기능으로 사용할 수 있다.
     * @param query
     * @param charSet
     * @param logInfo
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int setCommonQuery(String query, String charSet, String[] logInfo) {
        int ret = 0;
        if(logInfo != null && logInfo.length > 2) {
            ret = search.w3SetSessionMgr(logInfo[0],logInfo[1],logInfo[2]);
        }
        setCommonQuery(query, charSet);
        return ret;
    }
	
    /**
     * 컬렉션 별로 검색어 지정
     * @param collectionName
     * @param query
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int setCollectionQuery(String collectionName, String query) {
        return search.w3SetCollectionQuery(collectionName, query);
    }
    
    /**
     * 검색하고자 하는 컬렉션의 UID를 설정한다.
     * @param collectionName
     * @param values
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int setUid(String collectionName, String[] values) {
        int ret = 0;
        for(int i=0;i<values.length; i++) {
            int uid = 0;
			try{
				uid= Integer.parseInt(values[i]);
			}catch(NumberFormatException e){
				return -1;
			}
            ret = search.w3AddUid(collectionName, uid);
        }
        return ret;
    }

    /**
     * 검색대상 컬렉션, 언어분석기 사용유무, 대소문자 구분유무를 설정한다.
     * @param collectionName
     * @param useKma
     * @param isCase
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int addCollection(String collectionName, int useKma, int isCase) {
        int ret = 0;
        ret = search.w3AddCollection(collectionName);
        ret =  search.w3SetQueryAnalyzer(collectionName, useKma, isCase );
        //systemOut("[w3AddCollection] "+collectionName);
        return ret;
    }
    
    /**
     * 추상 컬렉션 추가
     * @param aliasName 설정한 별칭 컬렉션명
     * @param collectionName 실제 컬렉션
     * @param useKma
     * @param isCase
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int addAliasCollection(String aliasName, String collectionName, int useKma, int isCase){
    	int ret = 0;
    	ret = search.w3AddAliasCollection(aliasName, collectionName);
    	ret =  search.w3SetQueryAnalyzer(aliasName, useKma, isCase );
  		return ret;
    }
    
    /**
     * 검색기 연결
     * @param ip
     * @param port
     * @param timeOut
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int getConnection(String ip, int port, int timeOut) {
        int ret = search.w3ConnectServer(ip, port, timeOut);
        return ret;
    }

    /**
     * 해당 컬렉션의 검색 대상 필드를 여러 개를 설정한다.
     * @param collectionName
     * @param fields
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int setSearchField(String collectionName, String[] fields) {
        int ret = 0;
        for(int i=0;i<fields.length; i++) {
            ret = search.w3AddSearchField(collectionName, fields[i]);
            systemOut("[w3AddSearchField] " + collectionName + " / " + fields[i]);
        }
        return ret;
    }

    /**
     * 검색결과의 정렬필드를 설정한다.
     * @param collectionName
     * @param sortField
     * @param sortOrder
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int setSortField(String collectionName, String sortField, int sortOrder) {
        int ret = 0;
        ret = search.w3AddSortField(collectionName, sortField, sortOrder);
        systemOut("[w3AddSortField] " + collectionName + " / " + sortField);
        return ret;
    }

    /**
     *
     * @param collectionName
     * @param fieldNameValues
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int setExQuery(String collectionName, String fieldNameValues)  {
        int ret = search.w3AddExQuery(collectionName, fieldNameValues);
        systemOut("[w3AddExQuery]" + fieldNameValues);
        return ret;
    }

    /**
     *
     * @param collectionName
     * @param fieldNameValues
     * @return  성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int setFilterOperation(String collectionName, String fieldNameValues)  {
        int ret = search.w3AddFilterOperation(collectionName, fieldNameValues);
        systemOut("[w3AddFilterOperation]" + fieldNameValues);
        return ret;
    }

   /**
    *  setResultField에서 지정한 결과 필드들의 값을 얻는 함수이다.
    * @param collectionName
    * @param fields
    * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
    */
    public int setResultField(String collectionName, String[] fields) {
        int ret = 0;
        for(int i=0; i< fields.length; i++) {
            ret = search.w3AddDocumentField(collectionName, fields[i]);
            systemOut("[w3AddDocumentField] " + collectionName + " / " + fields[i]);
        }
        return ret;
    }

    /**
     * 해당 컬렉션의 몇 번째 검색 결과부터 몇 개를
     * 가져올 것인지를 지정하고 하이라이트 기능과 요약기능을 지정하는 함수이다.
     * 검색 API v3.5에서는 w3SetHighlight의 파라미터가 2개이지만 v3.7에서는 3개이다.
     * @param collName
     * @param highlight
     * @param startPos
     * @param resultCnt
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int setPageInfo(String collName,  int highlight, int startPos, int resultCnt) {
        int ret = 0;
        ret = search.w3SetHighlight(collName, highlight, 5);
        // 페이지, 기본정렬 설정
        ret = search.w3SetPageInfo(collName, startPos, resultCnt);

        return ret;
    }

   /**
    * 검색한 결과 날짜/시간 범위를 지정하고
    * 시작날짜와 종료날짜의 형식이 YYYY/MM/DD가 아니라면
    * 변경할 문자를 인자로 지정한다.
    * @param collectionName
    * @param startDate
    * @param endDate
    * @param replaceChr
    * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
    */
    public int setDateRange(String collectionName, String startDate, String endDate, String replaceChr) {
        // 날짜 조건 세팅
        int ret = 0;
        if(!startDate.equals("") && !endDate.equals("")){
            startDate = replace(startDate, replaceChr, "/");
            endDate = replace(endDate,replaceChr, "/");
            ret = search.w3SetDateRange(collectionName, startDate, endDate);
        }
        return ret;
    }

    /**
    *
    * @param collectionName
    * @param field
    * @param matchType
    * @param boostID
    * @param boostKeyword
    * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
    */
	public int setCategoryBoost(String collectionName, String field, String matchType, String boostID, String boostKeyword) {
	    int ret = 0;
	    ret = search.w3SetCategoryBoost(collectionName, field, matchType, boostID, boostKeyword);
	    return ret ;
	}   
   
    /**
     * v3.7에서는 2개의 전달인자 v4.0에서는 3개의 전달인자
     * @param collectionName
     * @param field
     * @param docCount
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int setGroupBy(String collectionName, String field, int docCount) {
        int ret = 0;
        ret = search.w3SetGroupBy(collectionName, field, docCount);
        return ret ;
    }
    
    /**
     * 그룹 검색결과의 정렬필드를 설정한다.
     * @param collectionName
     * @param sortField
     * @param sortOrder
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int addSortFieldInGroup(String collectionName, String sortField, int sortOrder) {
        int ret = 0;
        ret = search.w3AddSortFieldInGroup(collectionName, sortField, sortOrder);
        systemOut("[w3AddSortFieldInGroup] " + collectionName + " / " + sortField);
        return ret;
    }
    
    /**
    *
    * @param collectionName
    * @return 전체 그룹 개수
    */
	public int getGroupByCount(String collectionName) {
		return search.w3GetGroupByCount(collectionName);
	}
    
	/**
    *
    * @param collectionName
    * @return 가져온 그룹 개수
    */
	public int getGroupCount(String collectionName) {
		return search.w3GetGroupCount(collectionName);
	}
	
	/**
    *
    * @param collectionName
    * @parma groupIndex
    * @return 그룹에 속하는 전체문서 개수
    */
	public int getTotalCountInGroup(String collectionName, int groupIndex) {
		return search.w3GetTotalCountInGroup(collectionName, groupIndex);
	}
	
	/**
    *
    * @param collectionName
    * @parma groupIndex
    * @return 그룹에 속하는 문서 중 가져온 문서 개수
    */
	public int getCountInGroup(String collectionName, int groupIndex) {
		return search.w3GetCountInGroup(collectionName, groupIndex);
	}
	
	/**
     *
     * @param collectionName
     * @param groupIndex
     * @param docIndex
     * @return 그룹에 속하는 문서중 원하는 문서의 필드값
     */
	public String getFieldInGroup(String collectionName, String fieldName, int groupIndex, int docIndex) {
		return search.w3GetFieldInGroup(collectionName, fieldName, groupIndex, docIndex);
	}
	
	/**
    *
    * @param collectionName
    * @param groupIndex
    * @param docIndex
    * @return 그룹에 속하는 문서중 원하는 문서의 uid값
    */
	public long getUidInGroup(String collectionName, int groupIndex, int docIndex) {
		return search.w3GetUidInGroup(collectionName, groupIndex, docIndex);
	}
	
	/**
    *
    * @param collectionName
    * @param groupIndex
    * @param docIndex
    * @return 그룹에 속하는 문서중 원하는 문서의 가중치 값
    */
	public long getWeightInGroup(String collectionName, int groupIndex, int docIndex) {
		return search.w3GetWeightInGroup(collectionName, groupIndex, docIndex);
	}
	
	/**
    *
    * @param collectionName
    * @param groupIndex
    * @param docIndex
    * @return 그룹에 속하는 문서중 원하는 문서의 날짜 정보
    */
	public String getDateInGroup(String collectionName, int groupIndex, int docIndex) {
		return search.w3GetDateInGroup(collectionName, groupIndex, docIndex);
	}
	
	/**
     * 하이라이팅될 문자열을 보여주는 함수이다.
     * @return 하이라이팅될 문자열
     */
    public String getHighlightKeyword() {
        String keyWord = this.search.w3GetHighlightKeyword().trim();
        return keyWord;
    }

    /**
     * 형태소 분석된 결과 문자열을 보여주는 함수이다.
     * @param colName
     * @param field
     * @return 하이라이팅될 문자열
     */
    public String getHighlightKeywordByField(String colName, String searchField) {
         String keyWord = this.search.w3GetHighlightKeywordByField(colName, searchField);
         return keyWord;
     }
    
    /**
     *
     * @param collectionName
     * @return 검색결과 개수
     */
    public int getResultCount(String collectionName) {
        return search.w3GetResultCount(collectionName);
    }

    /**
     *
     * @param collectionName
     * @return 검색결과 총 개수
     */
    public int getResultTotalCount(String collectionName) {
        return search.w3GetResultTotalCount(collectionName);
    }


    /**
     * 최근에 검색된 키워드리스트를 반환한다.
     * @param count
     * @return 최근에 검색된 키워드리스트
     */
    public String[] recvRealTimeSearchKeywordList(int mode, int count) {
    	String realKeyword = search.w3RecvRealTimeSearchKeywordList(mode, count);
    	String[] keyList = null;
    	if(realKeyword != null){
	        StringTokenizer token = new StringTokenizer(realKeyword, ",");
	        keyList = new String[token.countTokens()];
	        for(int i=0; token.hasMoreTokens(); i++) {
	            keyList[i] = token.nextToken();
	        }
    	}
    	return keyList;
    }

    /**
     *
     * @param mode
     * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
     */
    public int recvResult(int mode) {
        int ret = search.w3RecvResult(mode);
        return ret;
    }

   /**
    * UID 검색을 검색기에 전달하고, 결과를 받는 함수이다.
    * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
    */
    public int recvDocument(int mode) {
        int ret = search.w3RecvDocument(mode);
        return ret;
    }
   
    /**
    *
    * @return 성공이면 0을 반환한다. 실패면 0이 아닌 값을 반환한다.
    */
    public int recvAnalyzedQuery(int mode) {
        int ret = search.w3RecvAnalyzedQuery(mode);
        return ret;
    }
    
    /**
     *
     * @param coll
     * @param field
     * @param idx
     * @return FIELD VALUE
     */
    public String getField(String coll, String field, int idx) {
        return search.w3GetField(coll,field,idx);
    }

   /**
    *
    * @param coll
    * @param idx
    * @return DATE
    */
    public String getDate(String coll, int idx) {
        return search.w3GetDate(coll,idx);
    }
	
    /**
    *
    * @param coll
    * @param idx
    * @return DATE
    */
    public long getWeight(String coll, int idx) {
        return search.w3GetWeight(coll,idx);
    }
   
    /**
     *
     * @param coll
     * @param idx
     * @return RANK
     */
    public  long getRank(String coll, int idx) {
        return search.w3GetRank(coll,idx);
    }

   /**
    *
    * @param coll
    * @param idx
    * @return UID
    */
    public  long getUid(String coll, int idx) {
        return search.w3GetUid(coll,idx);
    }

    /**
    * 네트워크 종료
    */
    public void closeServer() {
        if (search != null) {
            search.w3CloseServer();
            search = null;
        }
    }

    /**
     *
     * @param ret
     * @return
     */
    public String getErrorInfo() {
        String errorMsg = "";
        int errorCode = search.w3GetLastError();
        if( errorCode != 0){
            errorMsg = search.w3GetErrorInfo(errorCode);
            errorMsg = errorMsg + "(<b>code:<font color='red'>"+errorCode+"</font></b>)";
        }
        return errorMsg;
    }

    /**
     * 에러 코드에 대한 에러정보를 web application의 standard out log에 출력한다.
     * @param msg
     */
    public void systemOut(String msg) {
        if(out != null && isDebug) {
            try {
                out.println(msg+"<br>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     *  file: WNUtils.jsp
     *  subject: 검색 구현에 필요한 일반 메소드를 구현한다.
     *  ------------------------------------------------------------------------
     *  @original author: KoreaWISEnut
     *  @edit author: KoreaWISEnut
     *  @update date 2006.12.03
     *  ------------------------------------------------------------------------
     */
        /*
        *	문자셋 관련 설정
        */
        final static String ENCODE_ORI = "EUC-KR";
        final static String ENCODE_NEW = "UTF-8";

    	/**
         * 문자 배열 값을 검색하여 키 값을 리턴
         * @param fieldName
         * @param value
         * @param operation
         * @return
         */
        static int findArrayValue(String find, String[] arr) {
          if( arr == null) return -1;
    		int findKey = -1;
            for (int i = 0; i < arr.length; i++) {
                if (find.equals(arr[i])){
                    findKey = i;
                    break;
                }
            }
            return findKey;
        }

        /**
         *
         * @param s
         * @param findStr
         * @param replaceStr
         * @return
         */
        public static String replace(String s, String findStr, String replaceStr){
            int   pos;
            int   index = 0;

            while ((pos = s.indexOf(findStr, index)) >= 0) {
                s = s.substring(0, pos) + replaceStr + s.substring(pos + findStr.length());
                index = pos + replaceStr.length();
            }

            return s;
        }

        /**
         *
         * @param s
         * @return
         */
        public static String trimDuplecateSpace(String s){
            StringBuffer sb = new StringBuffer();
            for(int i=0; i<s.length(); i++){
                char c = s.charAt(i);
                if(i < s.length()-1) {
                    if( c == ' ' && s.charAt(i+1)==' '){
                        continue;
                    }
                }
                sb.append(c);
            }
            return sb.toString().trim();
        }

        public static String parseDate(String input, String inFormat, String outFormat) {
            String retStr = "";
            Date date = null;
            SimpleDateFormat formatter = null;
            try {
                date = (new SimpleDateFormat(inFormat)).parse(input.trim());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            formatter = new SimpleDateFormat(outFormat);
            retStr = formatter.format(date);
            return retStr;
        }

        public static String getCurrentDate() {
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat
                    ("yyyy/MM/dd", java.util.Locale.KOREA);
            return dateFormat.format(new java.util.Date());
        }

        /**
         *
         * @param strNum
         * @param def
         * @return
         */
        public static int parseInt(String strNum, int def){
            if(strNum == null) return def;
            try{
                return Integer.parseInt(strNum);
            }catch(Exception e){
                return def;
            }
        }

        /**
         * String의 값이 null일 경우 ""로 변환하여 리턴한다.
         * @param temp
         * @return
         */
        public static String checkNull(String temp) {
            if (temp != null) {
                temp = temp.trim();
            } else {
                temp = "";
            }
            return temp;
        }

        /**
         * 1차원 배열의 값중 null인 값을 ""로 변환하여 리턴한다.
         * @param temp
         * @return
         */
        public static String[] checkNull(String[] temp){
            for(int i=0; i<temp.length; i++) {
                temp[i] = checkNull(temp[i]);
            }
            return temp;
        }

        /**
         * 2차원 배열의 값중 null인 값을 ""로 변환하여 리턴한다.
         * @param temp
         * @return
         */
        public static String[][] checkNull(String[][] temp) {
            for(int i=0; i<temp.length; i++) {
                temp[i][0] = checkNull(temp[i][0]);
                temp[i][1] = checkNull(temp[i][1]);
            }
            return temp;
        }

        /**
         * 스트링을 format 에 맞게 변환을 한다.
         * convertFormat("1", "00") return "01" 로 입력 값을 리턴한다.
         * @param inputStr
         * @param format
         * @return String
         */
        public static String convertFormat(String inputStr, String format){
            int _input = Integer.parseInt(inputStr);
            StringBuffer result = new StringBuffer();
            DecimalFormat df = new DecimalFormat(format);
            df.format( _input, result, new FieldPosition(1) );
            return result.toString();
        }

        /**
         *
         * @param str
         * @param outFormat
         * @return
         */
        public static String numberFormat(String str, String outFormat) {
            return new DecimalFormat(outFormat).format(str);
        }

        /**
         *
         * @param str
         * @param oriEncode
         * @param newEncode
         * @return
         */
        public static String encoding(String str, String oriEncode, String newEncode) {
            str = checkNull(str);
            if(str.length() > 0) {
                try {
                    str = new String(str.getBytes(oriEncode), newEncode);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return str;
        }


        /**
         * 구분자를 가지고 있는 문자열을 구분자를 기준으로 나누어주는 메소드
         * @param splittee 구분자를 가진 문자열
         * @param splitChar 구분자
         * @return
         */
        public static String[] split(String splittee, String splitChar){
            String taRetVal[] = null;
            StringTokenizer toTokenizer = null;
            int tnTokenCnt = 0;

            try {
                toTokenizer = new StringTokenizer(splittee, splitChar);
                tnTokenCnt = toTokenizer.countTokens();
                taRetVal = new String[tnTokenCnt];

                for(int i=0; i<tnTokenCnt; i++) {
                    if(toTokenizer.hasMoreTokens())	taRetVal[i] = toTokenizer.nextToken();
                }
            } catch (Exception e) {
                taRetVal = new String[0];
            }
            return taRetVal ;
        }

        /**
         * String 을 받아 UTF-8 범위내 문자가 이닌경우 공백(0x0020) 으로 치환
         * @param str
         * @return String
         */
        public static String validate(String str) {
            StringBuffer buf = new StringBuffer();

            char ch;
            for(int i=0; i < str.length(); i++) {
                ch = str.charAt(i);
                if(Character.isLetterOrDigit(ch)) {
                } else {
                    if(Character.isWhitespace(ch)) {
                    } else {
                        if(Character.isISOControl(ch)) {
                            // UTF-8 에서 지원하지 않는 문자 제거
                            ch = (char)0x0020;
                        }
                    }
                }

                buf.append(ch);
            }

            return buf.toString();
        }

        /**
         * request null체크
         **/
        public String getCheckReq(javax.servlet.http.HttpServletRequest req, String parameter, String default_value) {
            String req_value = req.getParameter(parameter)!=null ? req.getParameter(parameter):default_value;
            return req_value;
        }

        /**
         * request Array null체크
         **/
        public String[] getCheckReqs(javax.servlet.http.HttpServletRequest req, String parameter, String[] default_value) {
            String[] req_value = req.getParameterValues(parameter);
            String[] tmp = null;
            int c = 0;
            if(req_value!=null) {
                tmp = new String[req_value.length];
                for(int i=0; i<req_value.length; i++) {
                    tmp[c] = req_value[i];
                    c++;
                }
            }
            req_value = req.getParameterValues(parameter)!=null ? tmp : default_value;
            return req_value;
        }

        /**
         * request null체크, uncoding
         **/
        public String getCheckReqUnocode(javax.servlet.http.HttpServletRequest req, String parameter, String default_value) {
            String req_value = req.getParameter(parameter)!=null ? encoding(req.getParameter(parameter), ENCODE_ORI, ENCODE_NEW):default_value;
            return req_value;
        }

        /**
         * request Array null체크, uncoding
         **/
        public String[] getCheckReqsUnocode(javax.servlet.http.HttpServletRequest req, String parameter, String[] default_value) {
            String[] req_value = req.getParameterValues(parameter);
            String[] tmp = null;
            int c = 0;
            if(req_value!=null) {
                tmp = new String[req_value.length];
                for(int i=0; i<req_value.length; i++) {
                    tmp[c] = encoding(req_value[i], ENCODE_ORI, ENCODE_NEW);
                    c++;
                }
            }
            req_value = req.getParameterValues(parameter)!=null ? tmp : default_value;
            return req_value;
        }


        public String getCategoryBoard(String nid){
        //	"고객센터 &gt; 고객상담 &gt; 고객의 소리"     qna
    	// 고객센터  &gt; 고객상담  &gt; 고객칭찬         qna_p
    	// 주식/선물옵션&gt; 온라인 투자상담 &gt;  Smart 투자상담
    	//
    	/*
    주식/선물옵션 &gt; 온라인 투자상담&gt; Smart 투자정보 &gt; Smart Today,
    주식/선물옵션 &gt; 온라인 투자상담&gt; Smart 투자정보 &gt; 특징주 정보
    금융상품상담  고객센터 &gt; 고객상담 &gt; 금융상품상담
    고객센터 &gt; 공지사항
    고객센터 &gt; 이벤트
    주식/선물옵션 > 원칙투자 > 원칙투자 체험
    회사소개> PRESS > 한화증권뉴스


    	*/

    	  return "";
    	}
    	public String getBoardLinkurl(String param, String nid){
    	   /*
    	   faq			    /FAQBoardServlet/center/board/faq/view.jsp?cmd=faqView&vc_bid=faq&mode=center&nn_id=	K
           qna	            /QNABoardServlet/center/board/qna/view.jsp?cmd=qnaView&vc_bid=qna&mode=&nn_id=	K
           qna_p	        /QNABoardServlet/center/board/qna/view.jsp?cmd=qnaView&vc_bid=qna_p&mode=&nn_id=	K
           soc_consult	    /QNABoardServlet/smartonclub/board/qna/view.jsp?cmd=qnaView&vc_bid=soc_consult&mode=&nn_id=	K
           soc_smarttoday	/GeneralBoardServlet/smartonclub/board/general/view.jsp?cmd=gview&vc_bid=soc_smarttoday&nn_id=	K
           soc_specialinfo	/GeneralBoardServlet/smartonclub/board/general/view.jsp?cmd=gview&vc_bid=soc_specialinfo&nn_id=	K
           finance1     	/QNABoardServlet/center/board/consult/qna/view.jsp?cmd=qnaView&vc_bid=finance1&nn_id=	S
           notice	/NoticeBoardServlet/center/board/notice/view.jsp?cmd=noticeView&vc_bid=notice&type=type1&mode=center&cc_gubun=all&nn_id=	K
           event	/NoticeBoardServlet/center/board/notice/view.jsp?cmd=noticeView&vc_bid=event&type=&&nn_id=	K
           invest	/ContentBoardServlet/wts/board/content/view.jsp?cmd=contentView&vc_bid=invest&nn_id=	K
           hanwha1	/ContentBoardServlet/hanwha/board/content/view.jsp?cmd=contentView&vc_bid=hanwha1&nn_id=	K


    	   */
    	   String tmplinkurl="";
    	   String korea="www.koreastock.co.kr",smart="www.smartcma.co.kr";

          if(param.equals("faq") ){
                 tmplinkurl="/center/board/faq/listNew.jsp?vc_bid=faq&nn_id=";

    	  }else if(param.equals("qna") ){
    				tmplinkurl="/QNABoardServlet/center/board/qna/view.jsp?cmd=qnaView&vc_bid=qna&mode=&nn_id=";
    	  }else if(param.equals("qna_p") ){
    				tmplinkurl="/QNABoardServlet/center/board/qna/view.jsp?cmd=qnaView&vc_bid=qna&mode=&nn_id=";
    	  }else if(param.equals("soc_consult") ){
    				tmplinkurl="/QNABoardServlet/smartonclub/board/qna/view.jsp?cmd=qnaView&vc_bid=soc_consult&mode=&nn_id=";

    	  }else if(param.equals("soc_smarttoday") ){
    	 			tmplinkurl="/GeneralBoardServlet/smartonclub/board/general/view.jsp?cmd=gview&vc_bid=soc_smarttoday&nn_id=";
    	  }else if(param.equals("soc_specialinfo") ){
    	  			tmplinkurl="/GeneralBoardServlet/smartonclub/board/general/view.jsp?cmd=gview&vc_bid=soc_specialinfo&nn_id=";
    	  }else if(param.equals("finance1") ){
    	  			tmplinkurl="http://www.smartcma.co.kr/n_frame.html?/QNABoardServlet/center/board/consult/qna/view.jsp?cmd=qnaView&vc_bid=finance1&nn_id=";
    	  }else if(param.equals("notice") ){
    	  	tmplinkurl="/NoticeBoardServlet/center/board/notice/view.jsp?cmd=noticeView&vc_bid=notice&type=type1&mode=center&cc_gubun=all&nn_id=";
    	  }else if(param.equals("event") ){
    	  			tmplinkurl="/NoticeBoardServlet/center/board/notice/view.jsp?cmd=noticeView&vc_bid=event&type=&&nn_id=";
    	  }else if(param.equals("invest") ){
    	  			tmplinkurl="/ContentBoardServlet/wts/board/content/view.jsp?cmd=contentView&vc_bid=invest&nn_id=";
    	  }else if(param.equals("hanwha1") ){
    	  			tmplinkurl="/ContentBoardServlet/hanwha/board/content/view.jsp?cmd=contentView&vc_bid=hanwha1&nn_id=";
    	  }

         return tmplinkurl+nid;


    	}

    	public String getResearchLinkurl(String param, String id,String site){

              	if(site.equals("korea"))
    			 return "/research/board/listNew.jsp?depth3_id="+param+"&seq="+id;

    			else
                 return "/n_frame.jsp?/research/board/listNew.jsp?depth3_id="+param+"&seq="+id;




    /*
    anls15_1	/research/board/listNew.jsp?depth3_id=anls15_1&seq=
    anls15_2	/research/board/listNew.jsp?depth3_id=anls15_2&seq=
    anls18	/research/board/listNew.jsp?depth3_id=anls18&seq=
    anls7	/research/board/listNew.jsp?depth3_id=anls7&seq=
    anls5	/research/board/listNew.jsp?depth3_id=anls5&seq=
    rpt1	/research/board/listNew.jsp?depth3_id=rpt1&seq=
    rpt2	/research/board/listNew.jsp?depth3_id=rpt2&seq=
    anls_d	/research/board/listNew.jsp?depth3_id=anls_d&seq=
    bond_m	/research/board/listNew.jsp?depth3_id=bond_m&seq=
    istn1	/research/board/listNew.jsp?depth3_id=istn1&seq=
    anls16_4	/research/board/listNew.jsp?depth3_id=anls16_4&seq=
    brief1	/research/board/listNew.jsp?depth3_id=brief1&seq=
    anls2	/research/board/listNew.jsp?depth3_id=anls2&seq=
    anls1	/research/board/listNew.jsp?depth3_id=anls1&seq=
    anls3	/research/board/listNew.jsp?depth3_id=anls3&seq=
    rpt3	/research/board/listNew.jsp?depth3_id=rpt3&seq=
    rp9	/research/board/listNew.jsp?depth3_id=rp9&seq=
    anls_e	/research/board/listNew.jsp?depth3_id=anls_e&seq=
    ch_e	/research/board/listNew.jsp?depth3_id=ch_e&seq=
    ch_issue	/research/board/listNew.jsp?depth3_id=ch_issue&seq=
    ch_daily	/research/board/listNew.jsp?depth3_id=ch_daily&seq=
    anls_f	/research/board/listNew.jsp?depth3_id=anls_f&seq=

    	*/


    	}


    /*
    [정정이요]
    펀드상세정보
    http://172.21.203.175:81/n_frame.html?/finance/info/sc2100_1.htm?item_cd=0325001

    채권상세보기
    http://172.21.203.175:81
    /n_frame.html?/finance/bond/sc2201_01.jsp?bdtypegbn=%25&txongbn=%25&bndcode=C015014M3&ivstriskgbn=%25&ivstterm1=%25&lastdata=&move=
    http://172.21.203.175:81
    /n_frame.html?/finance/bond/sc2201_01.jsp?bdtypegbn=%25&txongbn=%25&bndcode=C015014N8&ivstriskgbn=%25&ivstterm1=%25&lastdata=&move=


    ELS/DLS

    http://172.21.203.175:81/n_frame.html?/finance/els/els01.htm?mode=view&item_cd=KR6003534V64&toff_status=청약중


    */




    	public String getJongmoc(String code,String title) {
    		//String trurl ="http://203.239.57.101/ksgate/run/hts/tr/11100000.xml?jongcode="+code;
    		//String trurl ="http://localhost/ksgate/run/hts/tr/11100000.xml?jongcode="+code;
    		//String trurl ="http://172.21.203.175/ksgate/run/hts/tr/11100000.xml?jongcode="+code;
    		String trurl ="http://203.239.57.116/ksgate/run/hts/tr/11100000.xml?jongcode="+code;
    		
         // 현재가/전일 -- out_val_14 / out_val_26
    	  String  out_val_14="" , out_val_26="" ;
//         매도/매수  --- out_val_18 / out_val_19
          String out_val_18="" , out_val_19="";
    	 //거래량
    	  String  out_val_17 ="",out_val_16="";
    	  //등락폭
    	  String out_val_13="", out_val_20 ="",out_val_21="",out_val_23="", out_val_22 ="" ,out_val_15="";
    	                       //시가 out_val_20   고가 - out_val_21

            StringBuffer scdBuff = new StringBuffer();

    	     Document doc =null;

    		 int val15=0,val16=0,percent=0, val14=0,val26=0,val13=0;

        	try{
        	 SAXReader reader = new SAXReader();

              URLConnection conn = new URL(trurl).openConnection();
              BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"euc-kr"));

              reader.setEncoding("euc-kr");
              doc = reader.read(br);


            XPath sourceXPath = new Dom4jXPath("/dataset/result/data-block");

            List results = sourceXPath.selectNodes( doc );

            Iterator resultIter = results.iterator();


            Node node = (Node)resultIter.next();
    		     node = (Node)resultIter.next();


    		out_val_14    = node.valueOf("out_val_14");
    		out_val_26    = node.valueOf("out_val_26");


    		out_val_18    = node.valueOf("out_val_18");
    		out_val_19    = node.valueOf("out_val_19");
    		out_val_17    = node.valueOf("out_val_17");
    		out_val_13    = node.valueOf("out_val_13");
    		out_val_20    = node.valueOf("out_val_20");
    		out_val_21    = node.valueOf("out_val_21");
    		out_val_22    = node.valueOf("out_val_22");
    		out_val_15    = node.valueOf("out_val_15");
    		out_val_16    = node.valueOf("out_val_16");
    		out_val_23    = node.valueOf("out_val_23");



             val14 =  Integer.parseInt(out_val_14.replaceAll(",",""));
    	     val26 =  Integer.parseInt(out_val_26.replaceAll(",",""));





    		 /*
    		 //---> 종목정보 조회
    		http://172.21.203.175/ksgate/run/hts/tr/11100000.xml?jongcode=003530
    		
    		현재가/전일 -- out_val_14 / out_val_26
    			(*현재가 색깔결정) == > getColorText(out_val_26,out_val_14)
    		
    		
    		매도/매수  --- out_val_18 / out_val_19
    		
    		 거래량 -- out_val_17
    		 거래대금  - out_val_23
    		 등락폭  -  getCol(Number(out_val_13)) + out_val_15   / out_val_16 %
    		 시가 - out_val_20
    		 고가 - out_val_21
    		 저가 - out_val_22
    		
    		
    		.table_box_data1{color:#044cee; text-align:right} blu
    		.table_box_data2{color:#FF3300; text-align:right} red
    		.table_box_data3{color:#339933; text-align:right} gree
    		
    		
    		
    		 */
    		
    		 String rightnowcolor ="", upanddown="",arrowupndown="";
    		 String rightnowcolor_new = "", rightnowcolor_drp_new = "", arrowupndown_new = "";;
    		if(val14 > val26 ) {
    		   rightnowcolor="<td style='color:#FF3300;'>";
    		   rightnowcolor_new = "c_up";
    		   rightnowcolor_drp_new = "up1r";  //class='up1r(업), down1r(다운)'
    		}else {
    		   rightnowcolor="<td style='color:#044cee;'>";
    		   rightnowcolor_new = "c_down";
    		   rightnowcolor_drp_new = "down1r"; 
    		}






            if (out_val_13.equals("1")) {
            	//기세상한
                arrowupndown ="<img src=\"/img/common/ico/ico_up02.png\"  alt='기세상한'>";
                arrowupndown_new = "up1r";
    		} else if (out_val_13.equals("2")) {
    			//시세상승
    			  arrowupndown ="<img src=\"/img/common/ico/ico_up02.png\" alt='시세상승'>";
    			  arrowupndown_new = "up1r";
    		} else if (out_val_13.equals("7")) {
    			//상한
    			  arrowupndown ="<img src=\"/img/common/ico/ico_up02.png\" alt='상한'>";
    			  arrowupndown_new = "up1r";
    		} else if (out_val_13.equals("6")) {
    			//상승
    		    arrowupndown ="<img src=\"/img/common/ico/ico_up02.png\" alt='상승'>";
    		    arrowupndown_new = "up1r";
    		} else if (out_val_13.equals("5")) {
    			//보합
                arrowupndown="";
                arrowupndown_new = "";
    		} else if (out_val_13.equals("3")) {
    			//하한
                arrowupndown ="<img src=\"/img/common/ico/ico_down02.png\" alt='하한'>";
                arrowupndown_new = "down1r";
    		} else if (out_val_13.equals("4")) {
    			//하락
    			 arrowupndown ="<img src=\"/img/common/ico/ico_down02.png\" alt='하락'>";
    			 arrowupndown_new = "down1r";
    		} else if (out_val_13.equals("8")) {
    			//기세하한
    			 arrowupndown ="<img src=\"/img/common/ico/ico_down02.png\" alt='기세하한'>";
    			 arrowupndown_new = "down1r";
    		} else if (out_val_13.equals("9")) {
    			//기세하락
    			 arrowupndown ="<img src=\"/img/common/ico/ico_down02.png\" alt='기세하락'>";
    			 arrowupndown_new = "down1r";
    		} else  {
    			arrowupndown="";
    			arrowupndown_new = "";
    		}

    		scdBuff.append("  <div class='search_info'>                                                                                    	\n");
    		scdBuff.append("  <div class='box13'>                                                                                    	\n");
    		scdBuff.append("    <div class='topr'></div><div class='topl'></div><div class='btml'></div><div class='btmr'></div>      \n");
    		scdBuff.append("    <div class='leftArea'>                                                                            	\n");
    		scdBuff.append("  	<div class='in'>                                                                            	\n");
    		scdBuff.append("	<div class='title'>                                                                                          \n");
    		scdBuff.append("		<h3>"+title+"</h3><span class='num'>"+code+"</span>                                                        \n");
    		scdBuff.append("		<span class='btn'>                                                                                       \n");
    		scdBuff.append("			<a href='javascript:fn_gomenu(0);' class='rBtn2'>주문하기</a>                          \n");
    		scdBuff.append("			<a href='javascript:fn_gomenu(6);' class='pibtn'><span>관심종목등록</span></a>                \n");
    		scdBuff.append("		</span>                                                                                                  \n");
    		scdBuff.append("	</div>                                                                                                       \n");
    		scdBuff.append("	<div class='company_Info'>                                                                                  \n");
    		scdBuff.append("		<table summary='"+title+"시세정보' class='vbrd'>                                                                     \n");
    		scdBuff.append("			<caption>" + title+ "시세정보</caption>                                                                              \n");
    		scdBuff.append("			<colgroup>                                                                                           \n");
    		scdBuff.append("				<col width='15%'>                                                                                 \n");
    		scdBuff.append("				<col width='20%'>                                                                                \n");
    		scdBuff.append("				<col width='15%'>                                                                                 \n");
    		scdBuff.append("				<col width='20%'>                                                                                \n");
    		scdBuff.append("				<col width='15%'>                                                                                 \n");
    		scdBuff.append("				<col width='*'>                                                                                \n");
    		scdBuff.append("			</colgroup>                                                                                          \n");
    		scdBuff.append("			<tbody>                                                                                              \n");
    		scdBuff.append("				<tr>                                                                                             \n");
    		scdBuff.append("					<th>현재가</th>                          \n");
    		scdBuff.append("					<td class='price'><strong class='"+rightnowcolor_new+"'>"+out_val_14+"</strong></td>                                                \n");
    		scdBuff.append("					<th>등락폭</th>                          \n");
    		scdBuff.append("					<td colspan='3'>                                                                             \n");
    		scdBuff.append("						<!-- class='up1r(업), down1r(다운)' -->                                                  \n");
    		scdBuff.append("						<strong class='"+arrowupndown_new+"'>"+arrowupndown+"&nbsp;&nbsp;"+out_val_13+"</strong> / <span class='"+rightnowcolor_new+"'>"+out_val_16+"%</span> <!--<span class='day'>(08/04 장종료 기준)</span>-->\n");
    		scdBuff.append("					</td>                                                                                        \n");
    		scdBuff.append("				</tr>                                                                                            \n");
    		scdBuff.append("				<tr>                                                                                             \n");
    		scdBuff.append("					<th>거래량</th>                           \n");
    		scdBuff.append("					<td class='price'><span class='fs'>"+out_val_17+"</span></td>                                                    \n");
    		scdBuff.append("					<th>거래대금</th>                          \n");
    		scdBuff.append("					<td colspan='3' class='price'><span class='fs'>"+out_val_23+"&nbsp;백만</span></td>                                        \n");
    		scdBuff.append("				</tr>                                                                                            \n");
    		scdBuff.append("				<tr>                                                                                             \n");
    		scdBuff.append("					<th>전일종가</th>                     \n");
    		scdBuff.append("					<td class='green price'>"+out_val_26+"</td>                                                                \n");
    		scdBuff.append("					<th>매도호가</th>                    \n");
    		scdBuff.append("					<td class='c_up price'>"+out_val_18+"</td>                                                                 \n");
    		scdBuff.append("					<th>매수호가</th>                       \n");
    		scdBuff.append("					<td class='c_down price'>"+out_val_19+"</td>                                                               \n");
    		scdBuff.append("				</tr>                                                                                            \n");
    		scdBuff.append("				<tr>                                                                                             \n");
    		scdBuff.append("					<th>시가</th>                              \n");
    		scdBuff.append("					<td class='price'>"+out_val_20+"</td>                                                                              \n");
    		scdBuff.append("					<th>고가</th>                         \n");
    		scdBuff.append("					<td class='c_up price'>"+out_val_21+"</td>                                                                 \n");
    		scdBuff.append("					<th>저가</th>                          \n");
    		scdBuff.append("					<td class='c_down price'>"+out_val_22+"</td>                                                               \n");
    		scdBuff.append("				</tr>                                                                                            \n");
    		scdBuff.append("			</tbody>                                                                                             \n");
    		scdBuff.append("		</table>                                                                                                 \n");
    		scdBuff.append("		</div>                                                                                                   \n");
    		scdBuff.append("	</div>                                                                                                   \n");
    		scdBuff.append("	</div>                                                                                                   \n");
    		scdBuff.append("		<!-- 관련종목 -->                                                                                        \n");
    		scdBuff.append("		<div class='rightArea'>                                                                                    \n");
    		scdBuff.append("			<strong class='tit'>관련종목</strong>                                                                            \n");
    		scdBuff.append("			<ul class='cbtn'>                                                                                    \n");
    		scdBuff.append("				<li class='link1'><a href='javascript:fn_gomenu(3);'>차트보기</a></li>                       \n");
    		scdBuff.append("				<li class='link2'><a href='javascript:fn_gomenu(5);'>종목지표</a></li>                   \n");
    		scdBuff.append("				<li class='link3'><a href='javascript:fn_gomenu(2);'>기업정보</a></li>                     \n");
    		scdBuff.append("			</ul>                                                                                               \n");
    		scdBuff.append("		</div>                                                                                                   \n");
    		scdBuff.append("	<div class='clear'></div>                                                                                                      \n");
    		scdBuff.append("	</div>                                                                                                       \n");
    		scdBuff.append("	</div>                                                                                                       \n");
    		
    		



     }catch (Exception e){
                 e.printStackTrace();
     }



        return scdBuff.toString();

      }  // getJongmoc End 




	public String getPrintStackTrace(Exception e){
		StringBuffer sb = new StringBuffer();
		String ln = System.getProperty( "line.separator" );
		sb.append(e.toString());
		sb.append(ln);

		StackTraceElement stackTraceElement[] = e.getStackTrace();
		int len = stackTraceElement.length;

		for (int i = 0; i < len ; i++ ) {
		sb.append(stackTraceElement[i].toString()+ln);
		}

		return sb.toString();
	}

    public String  getColorText (int junil, int price){

    		String  cText = "";

    		if (junil > price ) {
    			cText = "<font color=#0041C4>" + price + "</font>";
    		} else if (junil < price){
    			cText = "<font color=#FE000B>" + price + "</font>";
    		} else {
    			cText =Integer.toString(price);
    		}

    		return cText;

    	}



    	public String getCol(String val) {
    		String  pm = "";
    		if        (val.equals("1") ) {		pm="<font color=red>";		//기세상한
    		} else if (val.equals("2") ) {		pm="<font color=red>";		//시세상승
    		} else if (val.equals("7") ) {		pm="<font color=red>";		//상한
    		} else if (val.equals("6") ) {		pm="<font color=red>";		//상승
    		} else if (val.equals("5") ) {		pm="&nbsp;";		        //보합
    		} else if (val.equals("3") ) {		pm="<font color=blue>";		//하한
    		} else if (val.equals("4") ) {	    pm="<font color=blue>";		//하락
    		} else if (val.equals("8") ) {		pm="<font color=blue>";		//기세하한
    		} else if (val.equals("9") ) {	     pm="<font color=blue>";		//기세하락
    		} else  { pm = "&nbsp;";
    		}
    		return pm;
    	}
}


