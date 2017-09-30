<%--
  User: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>
  Date: 2017/9/12 Time: 21:08 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"
      + request.getServerName() + ":" + request.getServerPort()
      + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <!--========================= css script =========================-->

  <link href="<%=basePath%>miniui/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css"/>

  <script src="<%=basePath%>js/Globals.js" type="text/javascript"></script>
  <script src="<%=basePath%>miniui/boot.js" type="text/javascript"></script>

  <!--========================= css script =========================-->
  <script src="../js/user/usergrid.js" type="text/javascript"></script>
  <title>用户表格 grid测试</title>
</head>
<body>
<h1>WebUser数据表格</h1>

<!--toolform-->
<div id="datagrid1_form" >
  <table width="100%" border=0 cellPadding=0 cellSpacing=0>
    <tbody>
    <tr>
      <th width="15%">角色</th>
      <td width="35%">
        <input id="role" name="rolename" type="text" textField="text" valueField="val"
               url="<%=basePath%>select/comSelect?method=roles" onvaluechanged="onRoleChanged"
               class="mini-combobox" style="width: 200px" emptytext="全部" shownullitem="true" nullItemText=" 全部" />
      </td>
      <th width="15%">用户名称</th>
      <td width="35%">
        <input id="user" name="username" type="text" textField="text" valueField="val"
               class="mini-combobox" style="width: 200px" emptytext="全部" shownullitem="true" nullItemText=" 全部" />
      </td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
      <td colspan="4">
        <input type="button" value="查询" onclick="search()" />
        <input type="button" value="重置" onclick="reSet()" />
    </tr>
    </tfoot>
  </table>
</div>
<!--toolbar-->
<div style="width:100%;">
  <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
    <table style="width:100%;">
      <tr>
        <td style="width:100%;">
          <a class="mini-button" iconCls="icon-add" onclick="add()">增加</a>
          <a class="mini-button" iconCls="icon-add" onclick="edit()">编辑</a>
          <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
        </td>
        <td style="white-space:nowrap;">
          <input id="username" class="mini-textbox" emptyText="请输入姓名" style="width:150px;" onenter="onKeyEnter"/>
          <a class="mini-button" onclick="search()">查询</a>
        </td>
      </tr>
    </table>
  </div>
</div>
<!--grid-->
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="true" idField="id" multiSelect="true" >
  <div property="columns">
    <div type="indexcolumn"></div>
    <div type="checkcolumn"></div>
    <div field="id" width="120" headerAlign="center" allowSort="true">用户id</div>
    <div field="username" width="120" headerAlign="center" allowSort="true">用户名</div>
    <div field="password" width="120" headerAlign="center" allowSort="true">密码</div>
    <div field="description" width="120" headerAlign="center" allowSort="true">描述</div>
    <div field="createtime" width="100" headerAlign="center" dateFormat="yyyy-MM-dd" allowSort="true">创建日期</div>
    <div field="modifytime" width="100" headerAlign="center" dateFormat="yyyy-MM-dd" allowSort="true">修改日期</div>
    <div field="discard" width="120" headerAlign="center" allowSort="true">有效标志</div>
  </div>
</div>

</body>
</html>
