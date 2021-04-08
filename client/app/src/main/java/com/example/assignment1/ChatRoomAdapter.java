package com.example.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatRoomAdapter extends ArrayAdapter<ChatRoom> {

    public ChatRoomAdapter(Context context, int textViewResourceId, List<ChatRoom> chatRooms) {
        super(context, textViewResourceId, chatRooms);
    }

    class ViewHolder {
        TextView chatRoomName;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ChatRoom cr = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.chat_room_item, null);
            viewHolder = new ViewHolder();
            viewHolder.chatRoomName = (TextView) view;
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.chatRoomName.setText(cr.getName());

        return view;
    }
}
