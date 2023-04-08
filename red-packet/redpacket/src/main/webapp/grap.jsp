<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019-06-09
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>参数</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.js">
    </script>
    <script type="text/javascript">
        $(document).ready(function(){
            var max=30000;
            for(var i=1;i<=max;i++){
                $.post({
                    //根据自己请求修改对应的url和大红包编号
                   url: "./userRedPacket/grabRedPacket.html?redPacketId=1&userId="+i,
                   success: function (result) {
                       document.write(i+"\n");
                   }
                });
            }
        });
    </script>
</head>
<body>

</body>
</html>
