package com.example.assignment1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetMessageAsyncTask  extends AsyncTask<Void, Void, Void>{
    private ArrayList<Message> messages;
    private int chatRoomId;
    private int page;
    private int totalPage;
    private boolean failed = false;
    private GetMessageAsyncTaskListener getMessageAsyncTaskListener;

    public GetMessageAsyncTask(int chatRoomId, int page,
                               GetMessageAsyncTaskListener getMessageAsyncTaskListener) {
        this.chatRoomId = chatRoomId;
        this.page = page;
        this.getMessageAsyncTaskListener = getMessageAsyncTaskListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String responseString = APIUtils.fetchMessages(chatRoomId, page);
            JSONObject response = new JSONObject(responseString);
            String status = response.getString(JSONUtils.STATUS_KEY).toUpperCase();
            if (status.equals(JSONUtils.STATUS_OK)) {
                JSONArray messageArrays = response.getJSONObject(JSONUtils.DATA_KEY).getJSONArray(JSONUtils.MESSAGES_KEY);
                messages = new ArrayList<>();
                for (int i = messageArrays.length() - 1; i >= 0; i--) {
                    JSONObject message = messageArrays.getJSONObject(i);
                    messages.add(new Message(message.getInt(JSONUtils.ID_KEY),
                            message.getInt(JSONUtils.CHATROOMID_KEY),
                            message.getInt(JSONUtils.USERID_KEY),
                            message.getString(JSONUtils.NAME_KEY),
                            message.getString(JSONUtils.MESSAGE_KEY),
                            message.getString(JSONUtils.MESSAGETIME_KEY)));
                }
                totalPage = response.getJSONObject(JSONUtils.DATA_KEY).getInt(JSONUtils.TOTALPAGE_KEY);
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
            getMessageAsyncTaskListener.success(messages, page, totalPage);
        } else {
            getMessageAsyncTaskListener.failed();
        }
    }

    public interface GetMessageAsyncTaskListener {
        void success(ArrayList<Message> messages, int currentPage, int totalPage);
        void failed();
    }
}
