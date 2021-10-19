function removeComma(x){ 
      var str = x.value;
      var cnt = x.value.length;
      var an = "";

      for(k=0; k<cnt; k++){
            if(str.charAt(k)==","){
                  continue;
            }
      an = an+str.charAt(k);    
      }
      x.value = an;
      x.select();
}

function addComma(x){
    var str = x.value;
    var tmp = str.split(".");
    
    if (parseInt(x.value)==0) x.value = 0;
    else {
           if( tmp.length > 1 ){
                 var strInit = str.split(".")[0];
                 var strFloat = "." + str.split(".")[1];
           }else{
                 var strInit = str;
                 var strFloat = "";
           }    
           var cnt = strInit.length;
           var an1 ="";
           var tag = "0";
           for(k=0; k<cnt; k++){
              if(strInit.charAt(k)=="0"&&tag=="0"){
              }else{
                an1 = an1+strInit.charAt(k);
                tag = "1";
              }   
           }
           var cnt1 = an1.length;
           var an ="";
           var p=0;
           
           var aa ="";
             for(k=cnt1-1; k>=0; k--){
                   an = an1.charAt(k) + an;
                   if(((an.length-p)%3) == 0){
                         an = "," + an;
                         p++;
                   }
             }
            if(cnt1%3 == 0){ 
                  aa = an.substring(1,cnt1+p) + strFloat;
            }else{
                  aa = an  + strFloat;
            }
	         x.value = aa;
	 }
}

function addComma2(str){
    var tmp = str.split(".");
    
    if (parseInt(str)==0){
    	 return 0;
    }else {
           if( tmp.length > 1 ){
                 var strInit = str.split(".")[0];
                 var strFloat = "." + str.split(".")[1];
           }else{
                 var strInit = str;
                 var strFloat = "";
           }    
           var cnt = strInit.length;
           var an1 ="";
           var tag = "0";
           for(k=0; k<cnt; k++){
              if(strInit.charAt(k)=="0"&&tag=="0"){
              }else{
                an1 = an1+strInit.charAt(k);
                tag = "1";
              }   
           }
           var cnt1 = an1.length;
           var an ="";
           var p=0;
           
           var aa ="";
             for(k=cnt1-1; k>=0; k--){
                   an = an1.charAt(k) + an;
                   if(((an.length-p)%3) == 0){
                         an = "," + an;
                         p++;
                   }
             }
            if(cnt1%3 == 0){ 
                  aa = an.substring(1,cnt1+p) + strFloat;
            }else{
                  aa = an  + strFloat;
            }
	         return aa;
	 }
}

function removeComma2(str){ 
      var cnt = str.length;
      var an = "";

      for(k=0; k<cnt; k++){
            if(str.charAt(k)==","){
                  continue;
            }
      an = an+str.charAt(k);    
      }
      return an;
      
}

function onlyNumber(x){
    f = x.value;
    if(event.keyCode==16){
   	      alert("숫자만 입력가능합니다.");
          event.returnValue=false;
    } 
    if (keypress_integer(f)==false){
           //alert("숫자만 입력가능합니다.");
           event.returnValue=false;
    }
    return true;
}

function keypress_integer(obj){
    
     if ((event.keyCode!=8) //Backspace
          &&(event.keyCode!=9) // Tab
          &&(event.keyCode!=13) // Enter
          &&(event.keyCode!=16) // Enter                            
          &&(event.keyCode!=46)// delete
          &&(event.keyCode!=35)// .home
          &&(event.keyCode!=36)// .end
          &&(event.keyCode < 37 || event.keyCode > 40) //방향키
          &&(event.keyCode < 48 || event.keyCode > 57) //0-9
          &&(event.keyCode < 96 || event.keyCode > 105) //0-9
          ) {
         
           return false;
     }     
     return true;        
}

//AJAX관련 함수
function newXMLHttpRequest() {

  var xmlreq = false;

  if (window.XMLHttpRequest) {

    // Create XMLHttpRequest object in non-Microsoft browsers
    xmlreq = new XMLHttpRequest();

  } else if (window.ActiveXObject) {

    // Create XMLHttpRequest via MS ActiveX
    try {
      // Try to create XMLHttpRequest in later versions
      // of Internet Explorer
      xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e1) {

      // Failed to create required ActiveXObject

      try {
        // Try version supported by older versions
        // of Internet Explorer

        xmlreq = new ActiveXObject("Microsoft.XMLHTTP");

      } catch (e2) {

        // Unable to create an XMLHttpRequest with ActiveX
      }
    }
  }

  return xmlreq;
}

function getReadyStateHandler(req, responseXmlHandler) {

  // Return an anonymous function that listens to the 
  // XMLHttpRequest instance
  return function () {

    // If the request's status is "complete"
    if (req.readyState == 4) {
      
      // Check that a successful server response was received
      if (req.status == 200) {

        // Pass the XML payload of the response to the 
        // handler function
        responseXmlHandler(req.responseXML);

      } else {

        // An HTTP problem has occurred
        alert("HTTP error: "+req.status);
      }
    }
  }
}

function setAJAX(url,callFunction) {
    var req = newXMLHttpRequest();
    var handlerFunction = eval("getReadyStateHandler(req, "+callFunction+")");
    req.onreadystatechange = handlerFunction;
    req.open("POST",url, true);
    req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    req.send();
}