package com.example.assignment1;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ChatActivity extends AppCompatActivity {
    public static final String NAME = "name";
    public static final String CHAT_ROOM_ID = "chatRoomId";

    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    String chatRoomName = "";
    private int chatRoomId = 0;

    private List<Message> messageList;
    private WebSocketIO socketIO = null;
    private Object MenuItem;
//    String url = "http://18.216.12.222:8001";
    String url = "http://3.138.32.139:8001";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_chat);
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        chatRoomId = getIntent().getIntExtra(CHAT_ROOM_ID, 0);
        chatRoomName = getIntent().getStringExtra(NAME);
        socketIO = new WebSocketIO(chatRoomId, url, new WebSocketIO.MessageListener() {
            @Override
            public void onMessage(String respone) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject msg = new JSONObject(respone);
                            String message = msg.getString("message");
                            NotifyUtils.createNotification(ChatActivity.this, chatRoomName, message);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        refresh();
                    }
                });
            }
        });
        messageList = new ArrayList<>();
        adapter = new MsgAdapter(ChatActivity.this, R.layout.msg_item, messageList);

        inputText = findViewById(R.id.input_area);
        send = findViewById(R.id.send);
        msgListView = findViewById(R.id.msg_listView);
        msgListView.setAdapter(adapter);

        refresh();

        send.setOnClickListener(view -> {
            String content = inputText.getText().toString();
            if(!"".equals(content)) {
                new SendMessageAsyncTask(chatRoomId, 1155145805, "Strong man", content,
                        new SendMessageAsyncTask.SendMessageAsyncTaskListener() {
                    @Override
                    public void success() {
                        long time = System.currentTimeMillis();
                        String messageTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
                        Message message = new Message(chatRoomId, 1155145805, "Strong man", content, messageTime);
                        messageList.add(message);
                        adapter.notifyDataSetChanged();
                        msgListView.setSelection(messageList.size());
                        inputText.setText("");
                    }

                    @Override
                    public void failed() {

                    }
                }).execute();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void refresh() {
        new GetMessageAsyncTask(chatRoomId, 1, new GetMessageAsyncTask.GetMessageAsyncTaskListener() {

            @Override
            public void success(ArrayList<Message> messages, int currentPage, int totalPage) {
                messageList.addAll(messages);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failed() {

            }
        }).execute();
    }


}