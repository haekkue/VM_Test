<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>������Ʈ ��������</title>
</head>
<body>
<h4>������Ʈ ���� ������ �� ���� ��ư�� �����ʽÿ�.</h4>
<form action=Updater.jsp method="post">
�ڵ�:<input type="text" name="code" size="5" value='${CODE}' readonly="true"><br>
//�ڵ�� readonly="true" �ɼ����� ���� ������ �Ұ����մϴ�.
����:<input type="text" name="title" size="5" value='${TITLE}'><br>
�۰�:<input type="text" name="writer" size="5" value='${WRITER}'><br>
����:<input type="text" name="price" size="5" value='${PRICE}'>��<br>
<input type="submit" value="����">
//�� �༮�� ���� �Ϸ��� ���� ��ư�� ������ ������?
<a href="Init.html"><input type="submit" value="��ȸ������"></a>
//�̰� ù ȭ������ ���� ���� ��ư �Դϴ�.
</form>
</body>
</html>


