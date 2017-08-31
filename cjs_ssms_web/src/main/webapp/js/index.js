/*
 function refresh(){
 $.ajax({
 type:'POST',
 url:getPath()+'/createAllIndex',
 success:function(data){
 if(data.result ==1){
 alert("生成索引成功！");
 }else {
 alert(data.msg) ;
 }
 }
 });
 }*/


window.onload = function () {


};


function login() {
  /*===== tpjs ajax 向controller 传值方式一 简单参数 =====*/
  /*params.XXX XXX必须要和controller 中参数名称一致*/
  var params = {};
  params.username = $("#username").val();
  params.password = $("#password").val();

  if (params.username == '' || params.password == '') {
    $("#ts").html("用户名或密码不能为空~");
    is_show();
    return false;
  } else {
    var reg = /^[0-9A-Za-z]+$/;
    if (!reg.exec(params.username)) {
      $("#ts").html("用户名错误");
      is_show();
      return false;
    }
  }

  $.ajax({
    url: "/sysUser/loginWebUser",
    type: "POST",
    async: true,
    dataType: "json",
    data: params,
    success: function (data) {
      alert("success");
    },
    error: function (data) {
      alert("error");
    },
    beforeSend: function () {
    },
    complete: function () {
    }
  });

}

function register() {
  /*===== tpjs ajax 向controller 传值方式一 javaBean参数 =====*/
  /*params js对象 和 javaBean 的field名称相同*/
  var params = $("#registerUserForm").serializeArray();

  var password2 = $("#register-password2").val();
  if (params.username == '' || params.password == '' || params.email == '' || password2 == '') {
    $("#ts").html("用户名,密码,邮件不能为空~");
    is_show();
    return false;
  } else {
    var reg_username = /^[0-9A-Za-z]+$/;
    var reg_email = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    if (!reg_username.exec(params.username)) {
      $("#ts").html("用户名错误");
      is_show();
      return false;
    } else if(!reg_email.exec(params.email)) {
      $("#ts").html("邮箱错误");
      is_show();
      return false;
    }
  }
  if(params.password != password2) {
    $("#ts").html("两次密码不相同");
    is_show();
    return false;
  }

  $.ajax({
    url: "/webUser/loginWebUser",
    type: "POST",
    async: true,
    dataType: "json",
    data: params,
    success: function (data) {
      alert("success");
    },
    error: function (data) {
      alert("error");
    },
    beforeSend: function () {
    },
    complete: function () {
    }
  });



}

function validate_register() {

}

function is_hide() {
  $(".alert").animate({"top": "-40%"}, 300)
}
function is_show() {
  $(".alert").show().animate({"top": "45%"}, 300)
}



