/* grid 标准示范 js*/
var grid;
var form;
var userComb;
var roleComb;

$(function () {
  mini.parse();
  grid = mini.get("datagrid");
  form = mini.get("form");
  userComb = mini.get("user");
  roleComb = mini.get("role");
});

function search() {
  var rolename = mini.get("role").getValue();
  var username = mini.get("user").getValue();
  var o = {};
  o.rolename = rolename;
  o.username = username;
  o.key = "UUserMapper_users";
  var data = JSON.stringify(o);
  gridSearch(data, "createtime");
}

/*后台使用POJO方式向mybatis传递参数*/
function search_pojo() {
  var rolename = mini.get("role").getValue();
  var username = mini.get("user").getValue();
  var o = {};
  o.rolename = rolename;
  o.username = username;
  o.key = "UUserMapper_gridUsers";
  var data = JSON.stringify(o);
  gridSearch(data, "createtime");
}
function search_users() {
  var rolename = mini.get("role").getValue();
  var username = mini.get("user").getValue();
  var o = {};
  o.rolename = rolename;
  o.username = username;
  o.key = "UUserMapper_gridUsers";
  var json = JSON.stringify(o);
  grid.setUrl("../select/userGridQuery");
  grid.load({data: json});
  grid.sortBy("createtime", "desc");
}
function grid_data() {
  grid.data;
}


function reSet() {
  form.clear();
  grid.clearRows();
}
function add_line() {//TODO
  var row = grid.getSelected();
  if (row) {
    mini.open({
      url: bootPATH + "../demo/CommonLibs/EmployeeWindow.html",
      title: "编辑员工", width: 600, height: 400,
      onload: function () {
        var iframe = this.getIFrameEl();
        var data = {action: "edit", id: row.id};
        iframe.contentWindow.SetData(data);

      },
      ondestroy: function (action) {
        grid.reload();

      }
    });
  } else {
    alert("请选中一条记录");
  }
}
function edit() {//TODO

}
function add_window() {//TODO
  mini.open({
    url: bootPATH + "../demo/CommonLibs/EmployeeWindow.html",
    title: "新增员工", width: 600, height: 400,
    onload: function () {
      var iframe = this.getIFrameEl();
      var data = {action: "new"};
      iframe.contentWindow.SetData(data);
    },
    ondestroy: function (action) {

      grid.reload();
    }
  });
}
function remove() {//TODO
  var rows = grid.getSelecteds();
  if (rows.length > 0) {
    if (confirm("确定删除选中记录？")) {
      var ids = [];
      for (var i = 0, l = rows.length; i < l; i++) {
        var r = rows[i];
        ids.push(r.id);
      }
      var id = ids.join(',');
      grid.loading("操作中，请稍后......");
      $.ajax({
        url: "../data/AjaxService.aspx?method=RemoveEmployees&id=" + id,
        success: function (text) {
          grid.reload();
        },
        error: function () {
        }
      });
    }
  } else {
    alert("请选中一条记录");
  }
}
function onKeyEnter(e) {
  search();
}
/////////////////////////////////////////////////
function onBirthdayRenderer(e) {
  var value = e.value;
  if (value) return mini.formatDate(value, 'yyyy-MM-dd');
  return "";
}
function onMarriedRenderer(e) {
  if (e.value == 1) return "是";
  else return "否";
}
var Genders = [{id: 1, text: '男'}, {id: 2, text: '女'}];
function onGenderRenderer(e) {
  for (var i = 0, l = Genders.length; i < l; i++) {
    var g = Genders[i];
    if (g.id == e.value) return g.text;
  }
  return "";
}

function onRoleChanged(e) {
  var roleid = roleComb.getValue();
  userComb.setValue("");

  var url = "../select/comSelect?method=users&roleid=" + roleid
  userComb.setUrl(url);

  userComb.select(0);
}
