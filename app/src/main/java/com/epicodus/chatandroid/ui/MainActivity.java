package com.epicodus.chatandroid.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.epicodus.chatandroid.Constants;
import com.epicodus.chatandroid.R;
import com.epicodus.chatandroid.adapters.FirebaseChatViewHolder;
import com.epicodus.chatandroid.models.Chat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private DatabaseReference mChatReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    private String name;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.messageInput) EditText mMessageInput;
    @Bind(R.id.sendButton) ImageButton mSendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mChatReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CHATS);
        setupFirebaseAdapter();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                    name = user.getDisplayName();
                } else {

                }
            }
        };

        mSendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mSendButton) {
            String message = mMessageInput.getText().toString();
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            mMessageInput.setText("");
            Chat chat = new Chat(name, message, dateFormat.format(date));
            mChatReference.push().setValue(chat);
        }
    }

    private void setupFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Chat, FirebaseChatViewHolder>(Chat.class, R.layout.chat_list_item,
                FirebaseChatViewHolder.class, mChatReference) {
            @Override
            protected void populateViewHolder(FirebaseChatViewHolder viewHolder, Chat model, int position) {
                viewHolder.bindChat(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
