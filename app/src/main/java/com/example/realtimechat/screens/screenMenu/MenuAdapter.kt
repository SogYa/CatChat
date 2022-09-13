package com.example.realtimechat.screens.screenMenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimechat.R
import com.example.realtimechat.data.model.MenuItem
import de.hdodenhof.circleimageview.CircleImageView

class MenuAdapter(onClickListener: OnMenuItemClickListener) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    private val menuItems: ArrayList<MenuItem> = ArrayList()
    private val onMenuItemClickListener: OnMenuItemClickListener = onClickListener

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelText: TextView = itemView.findViewById(R.id.menuLabelText)
        val menuIcon: CircleImageView = itemView.findViewById(R.id.menuIconImageIVew)
        val menuMainText: TextView = itemView.findViewById(R.id.menuMainText)

    }

    interface OnMenuItemClickListener {
        fun onClick(menuItem: MenuItem, position: Int)
    }

    fun updateMenuList(menuItemList: ArrayList<MenuItem>) {
        this.menuItems.clear()
        notifyItemChanged(1)
        this.menuItems.addAll(menuItemList)
        notifyItemRangeChanged(0, menuItems.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.menu_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItems = menuItems[position]
        holder.menuIcon.setImageResource(menuItems.iconId)
        holder.menuMainText.text = menuItems.text
        holder.labelText.text = menuItems.title
        holder.itemView.setOnClickListener {
            onMenuItemClickListener.onClick(menuItems, position)
        }
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }
}