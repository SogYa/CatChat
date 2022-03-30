package com.example.realtimechat.instruments

import androidx.recyclerview.widget.DiffUtil
import com.example.realtimechat.datalayer.model.Message
import com.example.realtimechat.datalayer.model.User

class DifUtilsCallbacks {

    class UserDiffUtilCallback(
        private val oldList: List<User>,
        private val newList: List<User>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldUser = oldList[oldItemPosition]
            val newUser = newList[newItemPosition]
            return oldUser.getUid() == newUser.getUid()
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldUser = oldList[oldItemPosition]
            val newUser = newList[newItemPosition]
            return oldUser.getName() == newUser.getName() && oldUser.getPhotoUrl() == newUser.getPhotoUrl() && oldUser.getStatus() == newUser.getStatus()
        }
    }

    class MessageDuffUtilCallback(
        private val oldList: List<Message>,
        private val newList: List<Message>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldMessage = oldList[oldItemPosition]
            val newMessage = newList[newItemPosition]
            return oldMessage.uid == newMessage.uid
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldMessage = oldList[oldItemPosition]
            val newMessage = newList[newItemPosition]
            return oldMessage.name == newMessage.name
                    && oldMessage.messageText == newMessage.messageText
                    && oldMessage.time == newMessage.time
        }

    }
}