package com.example.assignment1;

public class Message {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;

    private int id;
    private int chatRoomId;
    private int userId;
    private String name;
    private String message;
    private String messageTime;

    public Message(int id, int chatRoomId, int userId, String name, String message, String messageTime) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.name = name;
        this.message = message;
        this.messageTime = messageTime;
    }

    public Message(int chatRoomId, int userId, String name, String message, String messageTime) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.name = name;
        this.message = message;
        this.messageTime = messageTime;
    }

    public int getId() {
        return id;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTime() {
        return messageTime;
    }
}