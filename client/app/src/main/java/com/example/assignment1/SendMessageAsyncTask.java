package com.example.assignment1;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

public class SendMessageAsyncTask extends AsyncTask<Void,Void,Void> {
    private int chatRoomId;
    private int userId;
    private String name;
    private String message;
    private boolean failed = false;
    private SendMessageAsyncTaskListener sendMessageAsyncTaskListener;

    public SendMessageAsyncTask(int chatRoomId, int userId, String name, String message,
                                SendMessageAsyncTaskListener sendMessageAsyncTaskListener) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.name = name;
        this.message = message;
        this.sendMessageAsyncTaskListener = sendMessageAsyncTaskListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String responseString = APIUtils.sendMessage(chatRoomId, userId, name, message);
            JSONObject response = new JSONObject(responseString);
            String status = response.getString(JSONUtils.STATUS_KEY).toUpperCase();
            if (!status.equals(JSONUtils.STATUS_OK)) {
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
            sendMessageAsyncTaskListener.success();
        } else {
            sendMessageAsyncTaskListener.failed();
        }
    }

    public interface SendMessageAsyncTaskListener {
        void success();
        void failed();
    }
}
