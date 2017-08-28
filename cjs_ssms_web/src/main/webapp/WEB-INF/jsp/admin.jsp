<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>管理平台</title>
</head>
<body>
<shiro:hasRole name="admin">
    这是admin角色登录：<shiro:principal></shiro:principal>
</shiro:hasRole>

<br>
<shiro:hasPermission name="user:create">
    有user:create权限信息
</shiro:hasPermission>

<br>
<shiro:hasPermission name="sys_user:create">
    有sys_user:create权限信息
</shiro:hasPermission>

<br>
登录成功
</body>
</html>
