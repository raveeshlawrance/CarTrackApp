package com.cartrack.main.ui.dashboard

import android.content.Context
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cartrack.main.R
import com.cartrack.main.data.dashboard.UserListResponseItem
import com.cartrack.main.listener.OnUserItemClickListener
import kotlinx.android.synthetic.main.list_item_user.view.*
import java.util.Collections.addAll

class UserListAdapter(val activity: Context?, val userListResponse: ArrayList<UserListResponseItem>, val onUserItemClickListener: OnUserItemClickListener) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    class UserListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(userListResponseItem: UserListResponseItem) {
            itemView.apply {
                txtViewUserName.text = userListResponseItem.name
                txtViewUserEmail.text = userListResponseItem.email
                txtViewWebsite.text = userListResponseItem.website
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(userListResponse.get(position))
        holder.itemView.setOnClickListener {
            onUserItemClickListener.onItemClick(userListResponse.get(position))
        }
    }

    override fun getItemCount(): Int {
        return userListResponse.size
    }

    fun refreshUserList(userList: List<UserListResponseItem>) {
        this.userListResponse.apply {
            addAll(userList)
            notifyDataSetChanged()
        }
    }
}
