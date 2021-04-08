package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ChatRoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.chatRoom_listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChatRoom chatRoom = (ChatRoom) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra(ChatActivity.NAME, chatRoom.getName());
                intent.putExtra(ChatActivity.CHAT_ROOM_ID, chatRoom.getId());
                startActivity(intent);
            }
        });
        new GetChatRoomAsyncTask(new GetChatRoomAsyncTask.GetChatRoomAsyncTaskListener() {
            @Override
            public void success(ArrayList<ChatRoom> chatRooms) {
                adapter = new ChatRoomAdapter(MainActivity.this, R.layout.chat_room_item, chatRooms);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failed() {

            }
        }).execute();
    }
}