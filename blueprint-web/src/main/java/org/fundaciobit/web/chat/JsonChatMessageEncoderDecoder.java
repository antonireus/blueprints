package org.fundaciobit.web.chat;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class JsonChatMessageEncoderDecoder implements Encoder.Text<ChatMessage>, Decoder.Text<ChatMessage> {

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
        try {
            return JSONB.toJson(chatMessage);
        } catch (JsonbException e) {
            throw new EncodeException(chatMessage, "JSON ERROR", e);
        }
    }

    @Override
    public ChatMessage decode(String message) throws DecodeException {
        try {
            return JSONB.fromJson(message, ChatMessage.class);
        } catch (JsonbException e) {
            throw new DecodeException(message, "JSON ERROR", e);
        }
    }

    @Override
    public boolean willDecode(String message) {
        if (message == null) return false;

        final String trimmedMessage = message.trim();
        return (trimmedMessage.startsWith("{") && trimmedMessage.endsWith("}"));
    }
}
