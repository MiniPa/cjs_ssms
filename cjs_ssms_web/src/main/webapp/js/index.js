window.onload = function () {


};











/*登录*/
function login() {
  /*===== tpjs ajax 向controller 传值方式一 简单参数 =====*/
  /*params.XXX XXX必须要和controller 中参数名称一致*/
  var params = {};
  params.username = $("#username").val();
  params.password = $("#password").val();

  if (params.username == '' || params.password == '') {
    note("用户名或密码不能为空~");
    return false;
  } else {
    var reg = /^[0-9A-Za-z]+$/;
    if (!reg.exec(params.username)) {
      note("用户名错误");
      return false;
    }
  }

  $.ajax({
    url: "/user/loginUser",
    type: "POST",
    async: true,
    dataType: "json",
    data: params,
    success: function (data) {
      $('#login').modal('hide');
      note(data.data.msg);
    },
    error: function (data) {
      note(data.data.msg);
    },
    beforeSend: function () {
    },
    complete: function () {
    }
  });

}

/*注册*/
function register() {
  /*===== tpjs ajax 向controller 传值方式一 javaBean参数 =====*/
  /*params js对象 和 javaBean 的field名称相同*/
  var params = Globals.getForm($("#registerUserForm"));
  var password2 = $("#register-password2").val();

  if (params.username == '' || params.password == '' || params.email == '' || password2 == '') {
    note("用户名,密码,邮件不能为空~");
  } else {
    var reg_username = /^[0-9A-Za-z]+$/;
    var reg_email = /^([a-zA-Z0-9_-])+([.]){1}([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    if (!reg_username.exec(params.username)) {
      note("用户名错误");
      return false;
    } else if(!reg_email.exec(params.email)) {
      note("邮箱错误");
      return false;
    }
  }
  if(params.password != password2) {
    note("两次密码不相同");
    is_show();
    return false;
  }

  $.ajax({
    url: "/user/register",
    type: "POST",
    async: true,
    dataType: "json",
    data: params,
    success: function (data) {
      note(data.data.msg);
      $('#register').modal('hide');
    },
    error: function (data) {
      note(data.data.msg);
    },
    beforeSend: function () {
    },
    complete: function () {
    }
  });



}

function note(msg) {
  $("#ts").html(msg);
  is_show();
  return false;
}

function is_hide() {
  $(".alert").animate({"top": "-40%"}, 300)
}
function is_show() {
  $(".alert").show().animate({"top": "45%"}, 300)
}




