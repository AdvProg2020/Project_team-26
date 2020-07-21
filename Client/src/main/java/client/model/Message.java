package client.model;

import client.model.enums.MessageType;
import client.model.enums.Role;

import java.util.Date;

public class Message {
    private String sender;
    private String content;
    private String receiver;
    private MessageType type;
    private Role role;
    private Date time;

    public Message(){

    }

    public Message(String sender, String content, String receiver, MessageType type, Role role) {
        this.sender = sender;
        this.content = content;
        this.receiver = receiver;
        this.type = type;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
