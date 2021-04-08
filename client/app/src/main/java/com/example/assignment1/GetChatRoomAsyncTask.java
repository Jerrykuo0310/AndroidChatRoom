package com.example.assignment1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetChatRoomAsyncTask extends AsyncTask<Void, Void, Void> {
    private ArrayList<ChatRoom> chatRooms;
    private boolean failed = false;
    private GetChatRoomAsyncTaskListener getChatRoomAsyncTaskListener;

    public GetChatRoomAsyncTask(GetChatRoomAsyncTaskListener getChatRoomAsyncTaskListener) {
        this.getChatRoomAsyncTaskListener = getChatRoomAsyncTaskListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String responseString = APIUtils.fetchChatRooms();
            JSONObject response = new JSONObject(responseString);
            String status = response.getString(JSONUtils.STATUS_KEY).toUpperCase();
            if (status.equals(JSONUtils.STATUS_OK)) {
                JSONArray chatRoomArrays = response.getJSONArray(JSONUtils.DATA_KEY);
                chatRooms = new ArrayList<>();
                for (int i = 0; i < chatRoomArrays.length(); i++) {
                    JSONObject chatRoom = chatRoomArrays.getJSONObject(i);
                    chatRooms.add(new ChatRoom(chatRoom.getInt(JSONUtils.ID_KEY),
                            chatRoom.getString(JSONUtils.NAME_KEY)));
                }
            } else {
                failed = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            failed = true;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (!failed) {
            getChatRoomAsyncTaskListener.success(chatRooms);
        } else {
            getChatRoomAsyncTaskListener.failed();
        }
    }

    public interface GetChatRoomAsyncTaskListener {
        void success(ArrayList<ChatRoom> chatRooms);
        void failed();
    }
}