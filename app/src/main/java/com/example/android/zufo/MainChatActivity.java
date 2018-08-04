package com.example.android.zufo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainChatActivity extends AppCompatActivity {

   private ListView myChatlistView;
    private String myUserName;
    private EditText myChatText;
    private ImageButton mySendChatButton;
   private DatabaseReference mydatabaseReference;
    private ChatListAdapter myadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        myChatlistView=findViewById(R.id.chat_list_view);
        myChatText=findViewById(R.id.messageInput);
        mySendChatButton=findViewById(R.id.SendButton);
        setUpDisplayName();
        mydatabaseReference= FirebaseDatabase.getInstance().getReference();
        mySendChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushChatToFirebase();
            }
        });
        // call push method on keyboard event
        myChatText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                pushChatToFirebase();
                return true;
            }
        });
    }



    private void pushChatToFirebase(){
        String ChatInput=myChatText.getText().toString();
        if(!ChatInput.equals("")){
            instantMessage chat=new instantMessage(ChatInput,myUserName);
            mydatabaseReference.child("chats").push().setValue(chat);
            myChatText.setText("");
        }
    }
    // set user name

    private void setUpDisplayName(){
        SharedPreferences prefs=getSharedPreferences(RegisterActivity.CHAT_PREF,MODE_PRIVATE);
        myUserName=prefs.getString(RegisterActivity.DISPLAY_NAME,"null");

        if(myUserName==null){
            myUserName="user";
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myadapter=new ChatListAdapter(this,mydatabaseReference,myUserName);
        myChatlistView.setAdapter(myadapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myadapter.freeUpResources();
    }
}
