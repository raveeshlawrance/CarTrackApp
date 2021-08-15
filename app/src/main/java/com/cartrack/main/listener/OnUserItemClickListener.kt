package com.cartrack.main.listener

import com.cartrack.main.data.dashboard.UserListResponseItem

interface OnUserItemClickListener {
    fun onItemClick(userListResponseItem: UserListResponseItem)
}