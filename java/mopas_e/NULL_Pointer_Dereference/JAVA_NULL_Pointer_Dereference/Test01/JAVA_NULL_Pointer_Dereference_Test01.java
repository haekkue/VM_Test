<%!
  String replaceStr(String src, String oldStr, String newStr) {
    	      int idx = 0;
	         int curIdx = 0;
	         StringBuffer result = new StringBuffer();

		      curIdx = src.indexOf(oldStr, idx);
	         while ( curIdx >= 0 ) {
			                      // Replace string and append string.
    		         result.append(src.substring(idx, curIdx));
			         result.append(newStr);
			// Increment search the string index.
    		         idx = curIdx + oldStr.length();
			         curIdx = src.indexOf(oldStr, idx);	// Add this line, this is the fixed point.
	        }

		// After replace all of the oldStr, then if the string remains...
		// append it to result string.
    	      if (idx <= src.length())
	    	         result.append(src.substring(idx, src.length()));	// this..

    	    return result.toString();
      }

     String toKR(String s){
            try{
                  if(s == null || s.equals("")){
                        return s;
                  }else{
                        return (new String(s.getBytes("8859_1"), "EUC_KR"));
                  }
            }catch (Exception e){
                  return "Error";
            }         
      }

String CharToDB(String src){
      src = replaceStr(src,"'","''");
      src = replaceStr(src,"\\a","#%a");
      src = replaceStr(src,"\\b","#%b");
      src = replaceStr(src,"\\c","#%c");
      src = replaceStr(src,"\\d","#%d");
      src = replaceStr(src,"\\e","#%e");
      src = replaceStr(src,"\\f","#%f");
      src = replaceStr(src,"\\g","#%g");
      src = replaceStr(src,"\\h","#%h");
      src = replaceStr(src,"\\i","#%i");
      src = replaceStr(src,"\\j","#%j");
      src = replaceStr(src,"\\k","#%k");
      src = replaceStr(src,"\\l","#%l");
      src = replaceStr(src,"\\m","#%m");
      src = replaceStr(src,"\\o","#%o");
      src = replaceStr(src,"\\p","#%p");
      src = replaceStr(src,"\\q","#%q");
      src = replaceStr(src,"\\r","#%r");
      src = replaceStr(src,"\\s","#%s");
      src = replaceStr(src,"\\t","#%t");
      src = replaceStr(src,"\\u","#%u");
      src = replaceStr(src,"\\v","#%v");
      src = replaceStr(src,"\\w","#%w");
      src = replaceStr(src,"\\x","#%x");
      src = replaceStr(src,"\\y","#%y");
      src = replaceStr(src,"\\z","#%z");
      
      src = replaceStr(src,"\\A","#%A");
      src = replaceStr(src,"\\B","#%B");
      src = replaceStr(src,"\\C","#%C");
      src = replaceStr(src,"\\D","#%D");
      src = replaceStr(src,"\\E","#%E");
      src = replaceStr(src,"\\F","#%F");
      src = replaceStr(src,"\\G","#%G");
      src = replaceStr(src,"\\H","#%H");
      src = replaceStr(src,"\\I","#%I");
      src = replaceStr(src,"\\J","#%J");
      src = replaceStr(src,"\\K","#%K");
      src = replaceStr(src,"\\L","#%L");
      src = replaceStr(src,"\\M","#%M");
      src = replaceStr(src,"\\O","#%O");
      src = replaceStr(src,"\\P","#%P");
      src = replaceStr(src,"\\Q","#%Q");
      src = replaceStr(src,"\\R","#%R");
      src = replaceStr(src,"\\S","#%S");
      src = replaceStr(src,"\\T","#%T");
      src = replaceStr(src,"\\U","#%U");
      src = replaceStr(src,"\\V","#%V");
      src = replaceStr(src,"\\W","#%W");
      src = replaceStr(src,"\\X","#%X");
      src = replaceStr(src,"\\Y","#%Y");
      src = replaceStr(src,"\\Z","#%Z");
      return src;
}

String DBToChar(String src){
           
      src = replaceStr(src,"<","&lt;");
      src = replaceStr(src,">","&gt;");     
      src = replaceStr(src," ","&nbsp;");
      src = replaceStr(src,"''","'");
      src = replaceStr(src,"\n","<br>");
      src = replaceStr(src,"#%a","\\a");
      src = replaceStr(src,"#%b","\\b");
      src = replaceStr(src,"#%c","\\c");
      src = replaceStr(src,"#%d","\\d");
      src = replaceStr(src,"#%e","\\e");
      src = replaceStr(src,"#%f","\\f");
      src = replaceStr(src,"#%g","\\g");
      src = replaceStr(src,"#%h","\\h");
      src = replaceStr(src,"#%i","\\i");
      src = replaceStr(src,"#%j","\\j");
      src = replaceStr(src,"#%k","\\k");
      src = replaceStr(src,"#%l","\\l");
      src = replaceStr(src,"#%m","\\m");
      src = replaceStr(src,"#%o","\\o");
      src = replaceStr(src,"#%p","\\p");
      src = replaceStr(src,"#%q","\\q");
      src = replaceStr(src,"#%r","\\r");
      src = replaceStr(src,"#%s","\\s");
      src = replaceStr(src,"#%t","\\t");
      src = replaceStr(src,"#%u","\\u");
      src = replaceStr(src,"#%v","\\v");
      src = replaceStr(src,"#%w","\\w");
      src = replaceStr(src,"#%x","\\x");
      src = replaceStr(src,"#%y","\\y");
      src = replaceStr(src,"#%z","\\z");
      
      src = replaceStr(src,"#%A","\\A");
      src = replaceStr(src,"#%B","\\B");
      src = replaceStr(src,"#%C","\\C");
      src = replaceStr(src,"#%D","\\D");
      src = replaceStr(src,"#%E","\\E");
      src = replaceStr(src,"#%F","\\F");
      src = replaceStr(src,"#%G","\\G");
      src = replaceStr(src,"#%H","\\H");
      src = replaceStr(src,"#%I","\\I");
      src = replaceStr(src,"#%J","\\J");
      src = replaceStr(src,"#%K","\\K");
      src = replaceStr(src,"#%L","\\L");
      src = replaceStr(src,"#%M","\\M");
      src = replaceStr(src,"#%O","\\O");
      src = replaceStr(src,"#%P","\\P");
      src = replaceStr(src,"#%Q","\\Q");
      src = replaceStr(src,"#%R","\\R");
      src = replaceStr(src,"#%S","\\S");
      src = replaceStr(src,"#%T","\\T");
      src = replaceStr(src,"#%U","\\U");
      src = replaceStr(src,"#%V","\\V");
      src = replaceStr(src,"#%W","\\W");
      src = replaceStr(src,"#%X","\\X");
      src = replaceStr(src,"#%Y","\\Y");
      src = replaceStr(src,"#%Z","\\Z");
      return src;
}
String DBToChar2(String src){
           
      src = replaceStr(src,"#%a","\\a");
      src = replaceStr(src,"#%b","\\b");
      src = replaceStr(src,"#%c","\\c");
      src = replaceStr(src,"#%d","\\d");
      src = replaceStr(src,"#%e","\\e");
      src = replaceStr(src,"#%f","\\f");
      src = replaceStr(src,"#%g","\\g");
      src = replaceStr(src,"#%h","\\h");
      src = replaceStr(src,"#%i","\\i");
      src = replaceStr(src,"#%j","\\j");
      src = replaceStr(src,"#%k","\\k");
      src = replaceStr(src,"#%l","\\l");
      src = replaceStr(src,"#%m","\\m");
      src = replaceStr(src,"#%o","\\o");
      src = replaceStr(src,"#%p","\\p");
      src = replaceStr(src,"#%q","\\q");
      src = replaceStr(src,"#%r","\\r");
      src = replaceStr(src,"#%s","\\s");
      src = replaceStr(src,"#%t","\\t");
      src = replaceStr(src,"#%u","\\u");
      src = replaceStr(src,"#%v","\\v");
      src = replaceStr(src,"#%w","\\w");
      src = replaceStr(src,"#%x","\\x");
      src = replaceStr(src,"#%y","\\y");
      src = replaceStr(src,"#%z","\\z");
      
      src = replaceStr(src,"#%A","\\A");
      src = replaceStr(src,"#%B","\\B");
      src = replaceStr(src,"#%C","\\C");
      src = replaceStr(src,"#%D","\\D");
      src = replaceStr(src,"#%E","\\E");
      src = replaceStr(src,"#%F","\\F");
      src = replaceStr(src,"#%G","\\G");
      src = replaceStr(src,"#%H","\\H");
      src = replaceStr(src,"#%I","\\I");
      src = replaceStr(src,"#%J","\\J");
      src = replaceStr(src,"#%K","\\K");
      src = replaceStr(src,"#%L","\\L");
      src = replaceStr(src,"#%M","\\M");
      src = replaceStr(src,"#%O","\\O");
      src = replaceStr(src,"#%P","\\P");
      src = replaceStr(src,"#%Q","\\Q");
      src = replaceStr(src,"#%R","\\R");
      src = replaceStr(src,"#%S","\\S");
      src = replaceStr(src,"#%T","\\T");
      src = replaceStr(src,"#%U","\\U");
      src = replaceStr(src,"#%V","\\V");
      src = replaceStr(src,"#%W","\\W");
      src = replaceStr(src,"#%X","\\X");
      src = replaceStr(src,"#%y","\\y");
      src = replaceStr(src,"#%z","\\z");
      return src;
}

String replaceNull(String s){
      if(s == null){
            return "";
      }else{
            return s;
      }

}
String getDateString(String pattern) {
       java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (pattern, java.util.Locale.KOREA);
       return formatter.format(new java.util.Date());
}

String removeComma(String s){
      if(s == null || s.equals("")){
            return "";      
      }else{
            while(s.indexOf(",") != -1){
                  s = s.substring(0,s.indexOf(","))+s.substring(s.indexOf(",")+1,s.length());	
      
            }
            return s;          
      }  
      
      
}

String getComma(String a){
            
      if((a == null)||(a.equals(""))){
            return "";
      }else{	
      String an = null;
      an="";
      int j =0;
      for(int i=a.length()-1; i>=0; i--){
            j++;
            if((j%3) == 0){
                  
                  an = "," + a.charAt(i)+ an; 	
            }else{
                  an = a.charAt(i)+an;
            }	
      }
      if((a.length()%3)==0){
            an = an.substring(1,an.length());  
      }
            return an;
      }
}

String getComma(int aa){
      String a = String.valueOf(aa);      
      if((a == null)||(a.equals(""))){
            return "";
      }else{	
      String an = null;
      an="";
      int j =0;
      for(int i=a.length()-1; i>=0; i--){
            j++;
            if((j%3) == 0){
                  
                  an = "," + a.charAt(i)+ an; 	
            }else{
                  an = a.charAt(i)+an;
            }	
      }
      if((a.length()%3)==0){
            an = an.substring(1,an.length());  
      }
            return an;
      }
}

String getPayType(String s){
      String ss = null;
      if(s == null || s.equals("")){
            ss = ""; 
      }else{
            if(s.equals("1")){
                ss =  "무통장";
            }else if(s.equals("2")){
                ss = "카드";
            }else{
                ss = "핸드폰";
            }
      }
      return ss;
}

String getStatus(String s){
      String ss = null;
      if(s == null || s.equals("")){
            ss = "";
      }else{
            if(s.equals("1")){
                ss = "구매요청";
            }else if(s.equals("2")){
                ss = "결제완료";
            }else if(s.equals("3")){
                ss = "배송완료";
            }else if(s.equals("4")){
                ss = "반품처리";
            }else if(s.equals("5")){
                ss = "결제취소";
            }else if(s.equals("9")){
                ss = "처리완료";
            }
     }
     return ss;       
}

String getStatusSelect(String s){
     String ss = null;
     if(s == null || s.equals("")){
           s = "";
     }
     ss = "<option value=1";
     if(s.equals("1")){ 
       ss = ss + " selected";
     } 
     ss =  ss + " >구매요청</option>";
     ss = ss+"<option value=2";
     if(s.equals("2")){ 
       ss = ss + " selected";
     } 
     ss =  ss + " >결제완료</option>";
     ss = ss+"<option value=3";
     if(s.equals("3")){ 
       ss = ss + " selected";
     } 
     ss =  ss + " >배송완료</option>";
     ss = ss+"<option value=4";
     if(s.equals("4")){ 
       ss = ss + " selected";
     } 
     ss =  ss + " >반품처리</option>";
     ss = ss+"<option value=5";
     if(s.equals("5")){ 
       ss = ss + " selected";
     } 
     ss =  ss + " >결제취소</option>";
     ss = ss+"<option value=9";
     if(s.equals("9")){ 
       ss = ss + " selected";
     } 
     ss = ss + " >처리완료</option>";
       
     return ss;   
}

%>
<%
String filePath = null;
String realPath = config.getServletContext().getRealPath("/");
if(realPath == null){
      filePath = "/MYWEB/DATA01/s/e/serverpage.com/shop/images/goods/";
}else{
      filePath = realPath+"/shop/images/goods/"; 
}
%>      