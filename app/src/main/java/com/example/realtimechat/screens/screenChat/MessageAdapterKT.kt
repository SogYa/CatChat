package com.example.realtimechat.screens.screenChat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimechat.R
import com.example.realtimechat.data.SPControl
import com.example.realtimechat.data.model.Message
import com.example.realtimechat.instruments.Constants
import com.example.realtimechat.instruments.DifUtilsCallbacks
//This adapter created for future DiffUtils methods
class MessageAdapterKT(context: Context, messages: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
     var messages: List<Message> = messages
        set(value) {
            val difUtilCallback =  DifUtilsCallbacks.MessageDuffUtilCallback(field,value)
            val difResult =DiffUtil.calculateDiff(difUtilCallback)
            field = value
            difResult.dispatchUpdatesTo(this)
            notifyItemChanged(1)

        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)
        return MessageAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        val message = messages[position]
        if (message.uid == SPControl.getInstance().getStringPrefs(Constants.APP_PREFS_USER_ID)) {
            holder.userView.visibility = View.VISIBLE
            holder.receiverView.visibility = View.GONE
            holder.timeView.text = message.time
            holder.messageView.text = message.messageText
        } else {
            holder.userView.visibility = View.GONE
            holder.receiverView.visibility = View.VISIBLE
            holder.receiverNameView.text = message.name
            holder.receiverTimeView.text = message.time
            holder.receiverMessageView.text = message.messageText
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}
