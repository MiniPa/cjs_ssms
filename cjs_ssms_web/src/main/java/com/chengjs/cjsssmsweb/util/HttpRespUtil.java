package com.chengjs.cjsssmsweb.util;

import com.chengjs.cjsssmsweb.enums.StatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * HttpRespUtil:
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 11:41
 */
public class HttpRespUtil {

  /**
   * 将对象和返回状态以json形式返回给浏览器
   *
   * @param status
   * @param response
   */
  public static void respJson(StatusEnum status, HttpServletResponse response) {
    responseBuildJson(response, parseJson(status, null));
  }

  /**
   * 将对象和返回状态以json形式返回给浏览器
   *
   * @param status
   * @param data
   * @param response
   */
  public static void respJson(StatusEnum status, Object data, HttpServletResponse response) {
    if (null == data) {
      respJson(status, response);
    } else {
      responseBuildJson(response, parseJson(status, data));
    }
  }

  /**
   * 加工HttpResponse,处理返回状态
   *
   * @param status @see com.chengjs.cjsssmsweb.enums.{@link StatusEnum}
   * @param data   返回数据
   * @return
   */
  public static JSONObject parseJson(StatusEnum status, Object data) {
    JSONObject jo = new JSONObject();
    jo.put("code", status.code());
    jo.put("msg", status.message());
    jo.put("data", data);
    return jo;
  }

  /**
   * 构建Jsonobject并返回response, 配置response通用属性
   *
   * @param response
   * @param jo
   */
  public static void responseBuildJson(HttpServletResponse response, Object jo) {
    String json = "";
    if (jo instanceof JSONObject) {
      json = JSONUtils.valueToString(jo);
    } else {
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(jo);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }

    //response.setContentType("text/plain");
    response.setContentType("application/json");
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setCharacterEncoding("UTF-8");
    PrintWriter writer = null;
    try {
      writer = response.getWriter();
      writer.print(json);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != writer) {
        writer.flush();
        writer.close();
      }
    }
  }

  /**
   * 构建http响应消息
   * @param msg 响应消息
   * @param map
   * @param re_status
   */
  public static void buildRespStatus(String msg, Map<String, Object> map, StatusEnum re_status) {
    map.put("msg", msg); // 请求自定义相应信息
    map.put("code", re_status.getCode()); // http请求响应code
    map.put("meg", re_status.getMessage()); // http请求响应消息message
  }

  /**
   * 生成日志实体工具
   *
   * @param objectFrom
   * @param objectTo
   */
  public static void setLogValueModelToModel(Object objectFrom, Object objectTo) {
    Class<? extends Object> clazzFrom = objectFrom.getClass();
    Class<? extends Object> clazzTo = objectTo.getClass();

    for (Method toSetMethod : clazzTo.getMethods()) {
      String mName = toSetMethod.getName();
      if (mName.startsWith("set")) {
        String field = mName.substring(3);//字段名
        Object value;//获取from值
        try {
          if ("LogId".equals(field)) {
            continue;
          }
          Method fromGetMethod = clazzFrom.getMethod("get" + field);
          value = fromGetMethod.invoke(objectFrom);
          toSetMethod.invoke(objectTo, value);//设置值
        } catch (NoSuchMethodException e) {
          throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
          throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

}
