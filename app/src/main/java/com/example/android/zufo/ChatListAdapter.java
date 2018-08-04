package com.example.android.zufo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Lokesh Gidds on 23-07-2018.
 */

public class ChatListAdapter extends BaseAdapter {


    private Activity myActivity;
    private DatabaseReference myDatabaseReference;
    private String myUserName;
    private ArrayList<DataSnapshot> mySnapShot;
    private ChildEventListener myListener=new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            mySnapShot.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public  ChatListAdapter(Activity activity,DatabaseReference reference,String username){
        myActivity=activity;
        myDatabaseReference=reference.child("chats");
        myUserName=username;
        mySnapShot=new ArrayList<>();

        myDatabaseReference.addChildEventListener(myListener);

    }
    // static class
    static class ViewHolder{
        TextView senderName,chatBody;
        LinearLayout.LayoutParams layoutParams;
    }




    @Override
    public int getCount() {
        return mySnapShot.size();
    }

    @Override
    public instantMessage getItem(int i) {
        DataSnapshot snapshot=mySnapShot.get(i);
        return snapshot.getValue(instantMessage.class);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            LayoutInflater inflater= (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.chat_msg_single_row,viewGroup,false);

            final ViewHolder holder=new ViewHolder();
            holder.senderName=view.findViewById(R.id.author);
            holder.chatBody=view.findViewById(R.id.message);
            holder.layoutParams=(LinearLayout.LayoutParams) holder.senderName.getLayoutParams();
            view.setTag(holder);

        }


        final instantMessage message=getItem(i);
        final ViewHolder holder= (ViewHolder) view.getTag();

        //Styling

        boolean isMe=message.getAuthor().equals(myUserName);

        chatRowStyling(isMe,holder);


        String author=message.getAuthor();
        holder.senderName.setText(author);
        String msg=message.getMessage();
        holder.chatBody.setText(msg);



        return view;
    }
    private void chatRowStyling(boolean isItMe,ViewHolder holder){
        if(isItMe){
            holder.layoutParams.gravity= Gravity.END;
            holder.senderName.setTextColor(Color.BLUE);
            holder.chatBody.setBackgroundResource(R.drawable.speech_bubble_green);
        }
        else{
            holder.layoutParams.gravity= Gravity.START;
            holder.senderName.setTextColor(Color.BLUE);
            holder.chatBody.setBackgroundResource(R.drawable.speech_bubble_orange);

        }
        holder.senderName.setLayoutParams(holder.layoutParams);
        holder.chatBody.setLayoutParams(holder.layoutParams);
    }
    public void freeUpResources(){
        myDatabaseReference.removeEventListener(myListener);
    }
}
