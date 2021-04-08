package com.example.assignment1;

public class GetMessage {
    private int id;
    private int chatRoom_id;
    private int user_id;
    private String name;
    private String message;
    private String message_time;

    public GetMessage(int id,int chatRoom_id,int user_id, String name, String message, String message_time) {
        this.id = id;
        this.chatRoom_id=chatRoom_id;
        this.user_id=user_id;
        this.name = name;
        this.message = message;
        this.message_time= message_time;
    }
}
