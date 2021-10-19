<html>
<head>
<title>문서의 제목</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<script language="javascript">
    
    //Math.random() : 0~1사이의 난수를 반환
    document.write("난수 : " + Math.random() + "<br>");

    // Math.floor(실수)
    // : 현재 실수에서 가장 가까운 작은 정수를 반환
    // 예) Math.floor(3.6) => 3
    //   Math.floor(-3.6) => -4
    document.write(Math.floor(2.4) + "<br>");
    document.write(Math.floor(2.5) + "<br>");
    document.write(Math.floor(2.6) + "<br>");

    //Math.ceil(실수)
   // : 현재 실수에서 가장 가까운 큰 정수를 반환
    // 예) Math.ceil(3.6) => 4
    //   Math.ceil(-3.6) => -3
    document.write(Math.ceil(2.4) + "<br>");
    document.write(Math.ceil(2.5) + "<br>");
    document.write(Math.ceil(2.6) + "<br>");

    // 가장 가까운 정수값을 반환한다. 소수점 반올림이 된다. 
    document.write(Math.round(2.4) + "<br>");
    document.write(Math.round(2.5) + "<br>");
    document.write(Math.round(2.6) + "<br>");    

</script>
</head>

<body></body>
</html>