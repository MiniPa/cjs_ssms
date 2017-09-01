<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>显示用户</title>
</head>
<body>
    ${webUser.username}
<br/>
<p>
    自行修改user/queryWebUser/{id}这里的ID参数来获取不同账户的信息。
</p>
<a href="<%=path%>/webUser/queryWebUser/0000">0000</a>
<a href="<%=path%>/webUser/queryWebUser/0001">0001</a>
<a href="<%=path%>/webUser/queryWebUser/0000">0002</a>

</body>
</html> 

