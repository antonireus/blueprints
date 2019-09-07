package org.fundaciobit.web.chat;

@SuppressWarnings("unused")
public class ChatMessage {

    private String content;

    /**
     * Constructor per defecte necessari pel decoder Json
     */
    public ChatMessage() {
    }

    public ChatMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
