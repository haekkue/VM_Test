<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>프로젝트 정보관리</title>
</head>
<body>
<h4>프로젝트 정보 수정한 후 수정 버튼을 누르십시오.</h4>
<form action=Updater.jsp method="post">
코드:<input type="text" name="code" size="5" value='${CODE}' readonly="true"><br>
//코드는 readonly="true" 옵션으로 인해 수정이 불가능합니다.
제목:<input type="text" name="title" size="5" value='${TITLE}'><br>
작가:<input type="text" name="writer" size="5" value='${WRITER}'><br>
가격:<input type="text" name="price" size="5" value='${PRICE}'>원<br>
<input type="submit" value="수정">
//이 녀석을 수정 하려면 수정 버튼을 눌러야 겠지요?
<a href="Init.html"><input type="submit" value="조회페이지"></a>
//이건 첫 화면으로 가기 위한 버튼 입니다.
</form>
</body>
</html>


