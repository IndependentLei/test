package com.shebao.test.service.impl;

import com.shebao.test.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description :
 * @ClassName : WebSocketService
 * @Author : jdl
 * @Create : 2022-08-20 11:27
 */
@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")
public class WebSocketService {

    @Autowired
    PersonService personService;

    private static final AtomicInteger onLineCount = new AtomicInteger(0);

    private static final Map<Long, Session> sessionMap = new ConcurrentHashMap<>(100);

    /**
     * 打开连接
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId){
        String[] split = userId.split("-");
        Long parseUserId = Long.parseLong(split[0]);
        Long parseArticleId = Long.parseLong(split[1]);
        if(!sessionMap.containsKey(parseUserId)) {
            sessionMap.put(parseUserId,session);
            onLineCount.incrementAndGet();
        }
        sendMessage(session,"连接成功");
        log.info("用户:{},加入连接，当前在线人数为:{}",parseUserId,onLineCount.get());
    }

    private void sendMessage(Session session,String msg){
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId){
        String[] split = userId.split("-");
        Long parseUserId = Long.parseLong(split[0]);
        Long parseArticleId = Long.parseLong(split[1]);
         Session userSession = sessionMap.get(parseUserId);
         if(Objects.isNull(userSession)){
             log.error("用户:{}不存在",userId);
         }
         sessionMap.remove(parseUserId);
         onLineCount.decrementAndGet();
        log.info("用户:{}退出,当前在线人数为:{}",userId,onLineCount.get());
    }
//
//    /**
//     * 发送消息
//     * @param message
//     */
//    @OnMessage
//    public void onMessage(String message){
//        if(StringUtils.isNotBlank(message)){
//            JSONObject jsonObject = JSON.parseObject(message);
//            Long toUserId = jsonObject.getLong("toUserId");
//            Session toUsrSession = sessionMap.get(toUserId);
//            if(!Objects.isNull(toUsrSession)){
//                sendMessage(toUsrSession,message);
//            }
//        }
//    }

    /**
     * 发送时候错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        log.error("发生错误");
    }
}
