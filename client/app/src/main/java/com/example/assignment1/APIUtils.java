package com.example.assignment1;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class APIUtils {

    public static String fetchChatRooms() {
        StringBuilder results = new StringBuilder();
        HttpURLConnection conn = null;
        try{
//            URL url = new URL("http://18.216.12.222/api/a3/get_chatrooms");
            URL url = new URL("http://3.138.32.139/api/a3/get_chatrooms");
            conn = (HttpURLConnection) url.openConnection();

            InputStream is = conn.getInputStream();
            InputStreamReader isw = new InputStreamReader(is);

            int data = isw.read();
            while (data != -1) {
                results.append((char) data);
                data = isw.read();
            }
            return results.toString();
        } catch(Exception e) {
            e.printStackTrace();
        } finally{
            if (conn != null) {
                conn.disconnect();
            }
        }

        return "";
    }

    public static String fetchMessages(int chatRoomId, int page) {
        StringBuilder results = new StringBuilder();
        HttpURLConnection conn = null;
        try{
//            String urlString = "http://18.216.12.222/api/a3/get_messages?chatroom_id=" + chatRoomId + "&page=" + page;
            String urlString = "http://13.138.32.139/api/a3/get_messages?chatroom_id=" + chatRoomId + "&page=" + page;
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();

            InputStream is = conn.getInputStream();
            InputStreamReader isw = new InputStreamReader(is);

            int data = isw.read();
            while (data != -1) {
                results.append((char) data);
                data = isw.read();
            }
            return results.toString();
        } catch(Exception e) {
            e.printStackTrace();
        } finally{
            if (conn != null) {
                conn.disconnect();
            }
        }

        return "";
    }

    public static String sendMessage(int chatRoomId, int userId, String name, String message) {
        StringBuilder results = new StringBuilder();
        HttpURLConnection conn = null;
        try{
//            URL url = new URL("http://18.216.12.222/api/a3/send_message");
            URL url = new URL("http://3.138.32.139/api/a3/send_message");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            String str =  "chatroom_id=" + chatRoomId + "&user_id=" + userId + "&name=" + name + "&message=" + message;
            byte[] outputInBytes = str.getBytes(StandardCharsets.UTF_8);
            OutputStream os = conn.getOutputStream();
            os.write(outputInBytes);
            os.close();

            InputStream is = conn.getInputStream();
            InputStreamReader isw = new InputStreamReader(is);

            int data = isw.read();
            while (data != -1) {
                results.append((char) data);
                data = isw.read();
            }
            return results.toString();
        } catch(Exception e) {
            e.printStackTrace();
        } finally{
            if (conn != null) {
                conn.disconnect();
            }
        }

        return "";
    }
}
