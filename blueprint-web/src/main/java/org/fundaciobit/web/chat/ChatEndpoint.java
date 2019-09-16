package org.fundaciobit.web.chat;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value = "/chat/{username}",
        encoders = JsonChatMessageEncoder.class,
        decoders = JsonChatMessageDecoder.class)
public class ChatEndpoint {

   private static final Logger LOG = Logger.getLogger(ChatEndpoint.class.getName());

   private static final ConcurrentMap<String, Session> USER_SESSION_MAP = new ConcurrentHashMap<>();

   private int nombreMissatges = 0;

   @OnOpen
   public void onOpen(Session session, @PathParam("username") String username)
           throws EncodeException, IOException {
      LOG.info("Open " + session.getId() + ", " + username);
      Session existingSession = USER_SESSION_MAP.putIfAbsent(username, session);
      if (existingSession != null) {
         session.close(
                 new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY,
                         username + " username already in use"));
      }

      // Temps màxim sense enviar res, 60 segons.
      session.setMaxIdleTimeout(60_000L);
      session.getBasicRemote().sendObject(new ChatMessage("Benvingut " + username));
   }

   @OnMessage
   public void onMessage(Session session, @PathParam("username") String username, ChatMessage message)
           throws EncodeException, IOException {
      nombreMissatges++;
      LOG.info("Message on " + session.getId() + " from " + username + ": " + message.getContent());
      String response = "Rebut missatge número " + nombreMissatges + ": [" + message.getContent() + "]";
       final ChatMessage responseMessage = new ChatMessage(response);
       session.getBasicRemote().sendObject(responseMessage);
   }

   @OnClose
   public void onClose(Session session, @PathParam("username") String username) {
      LOG.info("Close " + session.getId() + ", " + username);
      USER_SESSION_MAP.remove(username);
   }

   @OnError
   public void onError(Session session, Throwable throwable) throws EncodeException, IOException {
      LOG.log(Level.SEVERE, "Error on " + session.getId() + ": " + throwable.getMessage(), throwable);
      if (session.isOpen()) {
         // Enviar un missatge amb el message concret de l'error no és lo seu, però per provar.
          ChatMessage errorMessage = new ChatMessage("Error: " + throwable.getMessage());
          session.getBasicRemote().sendObject(errorMessage);
      }
   }
}
