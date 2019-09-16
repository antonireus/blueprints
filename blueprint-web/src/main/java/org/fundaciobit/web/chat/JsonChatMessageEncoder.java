package org.fundaciobit.web.chat;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.logging.Logger;

public class JsonChatMessageEncoder implements Encoder.Text<ChatMessage> {

    private static final Logger log = Logger.getLogger(JsonChatMessageEncoder.class.getName());

    // Les instàncies de Jsonb són threadsafe i poden ser compartides per tota l'aplicació
    private static final Jsonb JSONB = JsonbBuilder.create();

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(ChatMessage chatMessage) throws EncodeException {
        log.info("endode: " + chatMessage.getContent());
        try {
            final String jsonMessage = JSONB.toJson(chatMessage);
            log.info("encoded: " + jsonMessage);
            return jsonMessage;
        } catch (JsonbException e) {
            throw new EncodeException(chatMessage, "JSON ERROR", e);
        }
    }
}
