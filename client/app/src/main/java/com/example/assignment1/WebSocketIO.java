package com.example.assignment1;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

public class WebSocketIO {
    public interface MessageListener {
        void onMessage(final String respone);
    }
    private MessageListener messageListener = null;
    private String url = "";
    private Socket socket = null;
    private int chatroom_id = 0;
    public WebSocketIO(int chatroom_id, String url, MessageListener listener) {
        this.chatroom_id = chatroom_id;
        this.url = url;
        this.messageListener = listener;
        this.init();
    }
    public void disconnect() {
        if(socket != null) {
            // leave room
            try {
                JSONObject object = new JSONObject();
                object.put("chatroom_id", this.chatroom_id);
                socket.emit("leave", object);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            socket.disconnect();
            socket = null;
        }
    }
    void init() {
        try {
            IO.Options options = IO.Options.builder()
                    // Manager options
                    .setReconnection(true)
                    .setReconnectionAttempts(5)
                    .setReconnectionDelay(1_000)
                    .setReconnectionDelayMax(5_000)
                    .setRandomizationFactor(0.5)
                    .setTimeout(20_000)
                    .setAuth(null)
                    .build();
            options.transports=new String[]{WebSocket.NAME};
            socket = IO.socket(url, options);
            // connect event
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    try {
                        JSONObject object = new JSONObject();
                        object.put("chatroom_id", chatroom_id);
                        socket.emit("join", object);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            // connect error event
            socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("connect err:" + args[0]);
                }
            });
            socket.connect();
            System.out.println("start connect:" + url);
            socket.on("message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("message:" + args[0]);
                    if(messageListener != null) {
                        messageListener.onMessage((String)(args[0]));
                    }
                }
            });
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            socket = null;
        }
    }

}
