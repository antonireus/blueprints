package org.fundaciobit.web.chat;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

@ServerEndpoint(value = "/chat/{username}")
public class ChatEndpoint {

   @Inject
   private Logger logger;

   @OnOpen
   public void onOpen(Session session) throws IOException {
      logger.info("Open");
      // Get session and WebSocket connection
   }

   @OnMessage
   public void onMessage(Session session, String message) throws IOException {
      logger.info("Message: " + message);
      session.getBasicRemote().sendText("Rebut " + message);
      // Handle new messages
   }

   @OnClose
   public void onClose(Session session) throws IOException {
      logger.info("Close");
      // WebSocket connection closes
   }

   @OnError
   public void onError(Session session, Throwable throwable) {
      logger.info("Error");
      // Do error handling here
   }
}