package org.fundaciobit.web.chat;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.util.logging.Logger;

public class JsonChatMessageDecoder implements Decoder.Text<ChatMessage> {

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
    public ChatMessage decode(String message) throws DecodeException {
        log.info("decode: " + message);
        try {
            final ChatMessage chatMessage = JSONB.fromJson(message, ChatMessage.class);
            log.info("decoded: " + chatMessage.getContent());
            return chatMessage;
        } catch (JsonbException e) {
            throw new DecodeException(message, "JSON ERROR", e);
        }
    }

    @Override
    public boolean willDecode(String message) {
        log.info("willDecode: " + message);
        if (message == null) return false;

        final String trimmedMessage = message.trim();
        return (trimmedMessage.startsWith("{") && trimmedMessage.endsWith("}"));
    }
}
