package org.fundaciobit.web.chat;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

@ServerEndpoint(value = "/chat/{username}")
public class ChatEndpoint {

   @Inject
   private Logger logger;

   private int nombreMissatges = 0;

   @OnOpen
   public void onOpen(Session session, @PathParam("username") String username) throws IOException {
      logger.info("Open " + username);
      // Temps màxim sense enviar res, 60 segons.
      session.setMaxIdleTimeout(60_000L);
      session.getBasicRemote().sendText("Benvingut " + username);
   }

   @OnMessage
   public void onMessage(Session session, String message) throws IOException {
      nombreMissatges++;
      logger.info("Message: " + message);
      session.getBasicRemote().sendText("Rebut missatge número " + nombreMissatges + ": [" + message + "]");
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