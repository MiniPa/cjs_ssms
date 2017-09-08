package com.chengjs.cjsssmsweb.controller;

import com.chengjs.cjsssmsweb.pojo.SocketContent;
import com.chengjs.cjsssmsweb.service.master.ISocketContentService;
import com.chengjs.cjsssmsweb.common.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocketController:
 *
 * 类似Servlet的注解mapping。无需在web.xml中配置。
 * configurator = SpringConfigurator.class是为了使该类可以通过Spring注入。
 *
 * 该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 16:58
 */
@ServerEndpoint(value = "/websocket", configurator = SpringConfigurator.class)
public class WebSocketController {

  private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);

  /**
   * TODO 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
   */
  private static int onlineCount = 0;

  /**
   * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
   * 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
   */
  private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<WebSocketController>();

  /**
   * 与客户端的连接会话，需要通过它来给客户端发送数据
   */
  private Session session;

  @Autowired
  private ISocketContentService contentService;

  public WebSocketController() {
  }

  /**
   * 连接建立成功调用的方法
   *
   * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
   */
  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    webSocketSet.add(this);     //加入set中
    addOnlineCount();           //在线数加1
    log.debug("有新连接加入！当前在线人数为" + getOnlineCount());
  }

  /**
   * 连接关闭调用的方法
   */
  @OnClose
  public void onClose() {
    webSocketSet.remove(this);  //从set中删除
    subOnlineCount();           //在线数减1
    log.debug("有一连接关闭！当前在线人数为" + getOnlineCount());
  }

  /**
   * 收到客户端消息后调用的方法
   *
   * @param message 客户端发送过来的消息
   * @param session 可选的参数
   */
  @OnMessage
  public void onMessage(String message, Session session) {
    log.debug("来自客户端的消息:" + message);
    /*群发消息*/
    for (WebSocketController item : webSocketSet) {
      try {
        Principal principal = session.getUserPrincipal();
        if (null == principal) {
          log.debug("群发消息，未获取到当前用户认证信息。");
          continue;
        }
        item.serializeMessage(message,principal);
      } catch (IOException e) {
        e.printStackTrace();
        continue;
      }
    }
  }

  /**
   * 发生错误时调用
   *
   * @param session
   * @param error
   */
  @OnError
  public void onError(Session session, Throwable error) {
    log.debug("发生错误");
    error.printStackTrace();
  }

  /**
   * 自定义方法: 聊天内容保存到数据库
   *
   * @param message
   * @param username
   * @throws IOException
   */
  public void serializeMessage(String message, Principal username) throws IOException {
    SocketContent content = new SocketContent();

    content.setContentid(UUIDUtil.uuid());
    content.setContentsender(username.getName());
    content.setContent(message);
    content.setCreatetime(new Date());
    contentService.insertSelective(content);
    if (log.isDebugEnabled()) {
      log.debug("聊天内容入库：\"" + content.toString() + "\"");
    }

    this.session.getBasicRemote().sendText(message);
    //this.session.getAsyncRemote().sendText(message);

  }

  public static synchronized int getOnlineCount() {
    return onlineCount;
  }

  public static synchronized void addOnlineCount() {
    WebSocketController.onlineCount++;
  }

  public static synchronized void subOnlineCount() {
    WebSocketController.onlineCount--;
  }

}
