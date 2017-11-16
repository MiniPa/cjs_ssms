/**
 * package-info: Controller
 * select 通用select查询, text='text' value='value'
 * 适用于miniui-combobox
 *
 * ############################### 1.controller绑定参数方式 ###############################
 * http://www.cnblogs.com/HD/p/4107674.html
 * http://www.cnblogs.com/xiepeixing/p/4243288.html
 *
 * 1. @RequestMapping(value = "/showUser/{userId}", method = RequestMethod.GET)
 *  public String showUser( @PathVariable("userId") Integer userId, ModelMap modelMap ){
 * 2.primary value: 表单中变量name要和controller参数名一致,或@RequestParam来绑定
 *
 * 3.object value: 于基本类型不同,表单中无此变量,或变量为"",则参数值为null
 * 4.自定义对象: 表单各name和对象参数名一致, public void test(User user)
 * 5.自定义复合对象：表单中用"field.field"方式来命名inputName,对象中有对象
 * 6.List：要浆List<User>绑定在对象里如 ListForm里有属性List<User> users
 *
 *    表单里用 users[0].name来绑定,----不好使
 *    spring会以最大下标创建List对象,在页面动态变动后,会导致下标不一致
 * 7.Set类似List,需先行add对应数量的模型----不好使
 * 8.Map：需绑定在对象中,相对灵活 绑定方式users['x'].firstName ---- 可用
 *
 * ############################### 2.controller注解参数 ###############################
 * ModelAndView modelAndView = new ModelAndView(); return modelAndView;
 *
 * 1.ModelMap model 页面${attributeName}
 *
 * 2.HttpServletRequest request, HttpServletResponse response
 * 3.@RequestParam(value = "method") String method
 *   @RequestParam(required=false) String name, //从HttpServletRequest中绑定name参数
 *   @RequestParam ( "age" ) int age
 * 4.@PathVariable String name
 *
 * 5.@CookieValue 绑定cookie值到Controller
 *   testCookieValue( @CookieValue ( "hello" ) String cookieValue, @CookieValue String hello) {
 * 6.testRequestHeader( @RequestHeader ( "Host" ) String hostAddr, @RequestHeader String Host, @RequestHeader String host )
 *   绑定HttpServletRequest头信息到Controller方法参数 --大小写不敏感,其他绑定都是敏感的
 *
 * 7.URI模板
 * Controller上的：@RequestMapping ( "/test/{variable1}" )
 * Method上的：@RequestMapping ( "/showView/{variable2}" )
 * Method里取参数：showView( @PathVariable String variable1, @PathVariable ( "variable2" ) int variable2)
 *    两种参数方式 前者只能在debug模式使用, 后者固定按照名称去绑定url中的变量,
 * 支持通配符"*"
 *
 * ############################### 3.创建json数据 ###############################
 * public @ResponseBody User getUser(@PathVariable String name)
 * retrun user;
 *
 * ############################### 4.controller映射处理 ###############################
 * ControllerClassNameHandlerMapping
 * 1./helloWorld.html或 /hello{任何字母}.html，DispatcherServlet将请求转发到HelloController
 * SimpleUrlHandlerMapping
 * 2.可显示的匹配转发请求 http://www.yiibai.com/spring_mvc/springmvc_simpleurlhandlermapping.html
 * ############################### mvc视图解析器 ###############################
 * 1.InternalResourceViewResolver
 * 2.XmlViewResolver http://www.yiibai.com/spring_mvc/springmvc_xmlviewresolver.html
 * 3.ResourceBundleViewResolver
 * 4.可配置多视图解析器并定义顺序
 *
 * ############################### 5.@RequestMapping高级应用 ###############################
 * 1.@RequestMapping (value= "testParams" , params={ "param1=value1" , "param2" , "!param3" })
 *   满足 param1=value1 且 param2存在 且 param3不存在 则能进入请求
 * 2.@RequestMapping (value= "testMethod" , method={RequestMethod. GET , RequestMethod. DELETE })
 *   只有 GET/DELETE 方法能够访问此方法
 * 3.RequestMapping (value= "testHeaders" , headers={ "host=localhost" , "Accept" })
 *   只有请求头包含 Accept 信息 且 host为localhost 才能正确访问此方法
 *
 * ############################### 6.@标记的处理器方法支持的 方法参数 和 返回值类型 ###############################
 * ############ 参数类型：
 * 1.HttpServlet对象: HttpServletRequest 、HttpServletResponse 和HttpSession
 * 2.WebRequest(spring的对象)：可访问HttpServletRequest 和HttpSession的属性值
 * 3.InputStream 、OutputStream 、Reader 和Writer
 * 4.@PathVariable 、@RequestParam 、@CookieValue 和@RequestHeader 标记的参数
 * 5.@ModelAttribute
 * 6.java.util.Map 、Spring 封装的Model 和ModelMap 。 这些都可以用来封装模型数据，用来给视图做展示
 * 7.实体类 接收上传的参数
 * 8.Spring 封装的MultipartFile, 用来接收上传文件的
 * 9.Spring 封装的Errors 和BindingResult 对象。 这两个对象参数必须紧接在需要验证的实体对象参数之后，它里面包含了实体对象的验证结果。
 * ############ 返回值类型：
 * 1.ModelAndView
 * 2.模型对象：包括Spring 封装好的Model 和ModelMap ，以及java.util.Map ，
 *   当没有视图返回的时候视图名称将由RequestToViewNameTranslator 来决定
 * 3.视图对象：在渲染视图的过程中模型的话就可以给处理器方法定义一个模型参数，然后在方法体里面往模型中添加值
 * 4.String：往往是一个视图的名称
 * 5.void：此情况一般直接把结果写到HttpServeletRequest中,
 *   若没写,spring将会利用RequestToViewNameTranslator来返回一个视图的名称
 * 6.@ResponseBody：任何返回值类型将经过HttpMessageConverters转换之后再写到HttpServletResponse中
 * 7.其他任何返回值类型都会被当做模型中的一个属性来处理,返回的视图还是由RequestToViewNameTranslator来决定
 *   添加属性到模型可在方法上,@ModelAttribute(“attributeName”),否则使用返回值类名称首字母小写形式表示
 *
 * ############################### 7.传递保存数据 ###############################
 * 1.@ModelAttribute 和 @SessionAttributes 在不同模型和控制器间共享数据
 * 1.1@ModelAttribute: 在方法上,该方法将在处理器方法执行前执行,并将结果放在session或model属性中
 *
 * @return
 */
package com.chengjs.cjsssmsweb.controller;