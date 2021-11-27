package com.example.realtimechat.screens.screenChat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realtimechat.R;
import com.example.realtimechat.datalayer.SPControl;
import com.example.realtimechat.datalayer.model.Message;
import com.example.realtimechat.instruments.Constants;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<Message> messages;

    MessageAdapter(Context context, ArrayList<Message> messages) {
        this.layoutInflater = LayoutInflater.from(context);
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (message.getUid().equals(SPControl.getInstance().getStringPrefs(Constants.APP_PREFS_USER_ID))) {
            holder.userView.setVisibility(View.VISIBLE);
            holder.receiverView.setVisibility(View.GONE);
            holder.timeView.setText(message.getTime());
            holder.messageView.setText(message.getMessageText());
        } else {
            holder.userView.setVisibility(View.GONE);
            holder.receiverView.setVisibility(View.VISIBLE);
            holder.receiverNameView.setText(message.getName());
            holder.receiverTimeView.setText(message.getTime());
            holder.receiverMessageView.setText(message.getMessageText());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView messageView, timeView;
        final LinearLayout receiverView;
        final ConstraintLayout userView;
        final TextView receiverMessageView, receiverTimeView, receiverNameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView = itemView.findViewById(R.id.message_style);

            userView = itemView.findViewById(R.id.user_message);
            messageView = itemView.findViewById(R.id.user_message_text);
            timeView = itemView.findViewById(R.id.user_message_time);

            receiverView = itemView.findViewById(R.id.receiver_message);
            receiverMessageView = itemView.findViewById(R.id.receiver_message_text);
            receiverTimeView = itemView.findViewById(R.id.receiver_message_time);
            receiverNameView = itemView.findViewById(R.id.receiver_name);

        }

    }
}
