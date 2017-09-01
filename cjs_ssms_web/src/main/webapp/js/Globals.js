var Globals = { // 全局函数
  getPath: getPath,
  getQueryString: getUrlParam,
  getCookie: getCookie,
  imgError: imgError,
  zero: zero,
  refresh: refresh,
  getForm: getForm

}

/*========================= function ===========================*/




//获取表单数据 jfrom：form的jquery对象
function getForm(jform) {
  var data = {};
  var formArr = jform.serializeArray();
  $.each(formArr, function() {
    data[this.name] = this.value;
  });
  return data;
}


//获取java路径
function getPath() {
  return $('body').attr('data');
}

//404情况下的图片或头像
function imgError(img, type) {
  img.src = getPath() + '/images/default/' + type + '.jpg';
  if (type == 'default_coach_list_img') {
    window.setTimeout(function () {
      $('#coachPic img').css('height', '' + $('.content_bg:eq(1) img').height() + 'px');
    }, 50);
  }
}

//获取请求URL参数
function getUrlParam(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r != null) {
    return unescape(r[2]);
  }
  return null;
}

//获取cookies参数
function getCookie(name) {
  var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
  if (arr != null) {
    return unescape(arr[2]);
  } else {
    return null;
  }
}

//倒计时时间大小判断
function zero(num) {
  return num < 10 ? ("0" + num) : num;
};

//刷新lucene索引
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
}

