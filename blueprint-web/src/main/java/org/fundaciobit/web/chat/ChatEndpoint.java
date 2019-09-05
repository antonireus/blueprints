package org.fundaciobit.web.chat;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

@ServerEndpoint(value = "/chat/{username}")
public class ChatEndpoint {

   @Inject
   private Logger logger;

   private static ConcurrentMap<String, Session> userSessionMap = new ConcurrentHashMap<>();

   private int nombreMissatges = 0;

   @OnOpen
   public void onOpen(Session session, @PathParam("username") String username) throws IOException {
      logger.info("Open " + username);
      Session existingSession = userSessionMap.putIfAbsent(username, session);
      if (existingSession != null) {
         session.close(
                 new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY,
                         username + " username already in use"));
      }

      // Temps màxim sense enviar res, 60 segons.
      session.setMaxIdleTimeout(60_000L);
      session.getBasicRemote().sendText("Benvingut " + username);
   }

   @OnMessage
   public void onMessage(Session session, @PathParam("username") String username, String message)
           throws IOException {
      nombreMissatges++;
      logger.info("Message from " + username + ": " + message);
      session.getBasicRemote().sendText("Rebut missatge número " + nombreMissatges + ": [" + message + "]");
   }

   @OnClose
   public void onClose(Session session, @PathParam("username") String username) throws IOException {
      logger.info("Close " + username);
      userSessionMap.remove(username);
   }

   @OnError
   public void onError(Session session, Throwable throwable) throws IOException {
      logger.info("Error: " + throwable.getMessage());
      if (session.isOpen()) {
         session.getBasicRemote().sendText("Error: " + throwable.getMessage());
      }
   }
}