function genReceiptURL (baseURL){
  var randNum = Math.random(); 
  var receiptURL = baseURL + randNum + ".html";
  return receiptURL;
}