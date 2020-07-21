package client.model;

import client.model.enums.MessageType;

import java.util.Date;

public class Message {
    private String sender;
    private String content;
    private MessageType type;
    private String receiver;
    private Date time;


    public Message(String sender, String content, String receiver, MessageType type) {
        this.sender = sender;
        this.content = content;
        this.type = type;
        time = new Date();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
