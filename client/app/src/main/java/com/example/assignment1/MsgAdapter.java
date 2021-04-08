package com.example.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MsgAdapter extends ArrayAdapter<Message> {
    private int resourceId;

    public MsgAdapter(Context context, int textViewResourceId, List<Message> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.chatObjectSide = (LinearLayout)view.findViewById(R.id.chatObjectSide);
            viewHolder.userSide = (LinearLayout)view.findViewById(R.id.userSide);
            viewHolder.msg_chatObj = (TextView)view.findViewById(R.id.msg_chatObj);
            viewHolder.msg_user = (TextView)view.findViewById(R.id.msg_user);
            viewHolder.time = (TextView)view.findViewById(R.id.time);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(message.getUserId() != 1155145805) {
            viewHolder.chatObjectSide.setVisibility(View.VISIBLE);
            viewHolder.userSide.setVisibility(View.GONE);
            viewHolder.msg_chatObj.setText(message.getMessage());
            viewHolder.time.setText(message.getMessageTime());
        } else {
            viewHolder.userSide.setVisibility(View.VISIBLE);
            viewHolder.chatObjectSide.setVisibility(View.GONE);
            viewHolder.msg_user.setText(message.getMessage());
            viewHolder.time.setText(message.getMessageTime());
        }
        return view;
    }

    class ViewHolder {
        LinearLayout chatObjectSide;
        LinearLayout userSide;
        TextView msg_chatObj;
        TextView msg_user;
        TextView time;
    }
}