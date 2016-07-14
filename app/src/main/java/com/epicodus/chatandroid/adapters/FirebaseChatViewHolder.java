package com.epicodus.chatandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.epicodus.chatandroid.models.Chat;

/**
 * Created by Guest on 7/14/16.
 */
public class FirebaseChatViewHolder extends RecyclerView.ViewHolder {

    View mView;
    Context mContext;

    public FirebaseChatViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindChat(Chat chat) {

    }

}
