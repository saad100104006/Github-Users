package com.tawk.to.db.diffcalback

import androidx.recyclerview.widget.DiffUtil
import com.tawk.to.db.entity.UserDetails


class UserDetailsDiffCallback : DiffUtil.ItemCallback<UserDetails>() {
    override fun areItemsTheSame(
        oldItem: UserDetails,
        newItem: UserDetails
    ): Boolean {
        return oldItem.user.id == newItem.user.id
    }

    override fun areContentsTheSame(
        oldItem: UserDetails,
        newItem: UserDetails
    ): Boolean {
        return oldItem == newItem
    }
}
