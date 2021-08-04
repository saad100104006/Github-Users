package com.tawk.to.db.diffcalback

import androidx.recyclerview.widget.DiffUtil
import com.tawk.to.db.entity.UserDetails


class UserDetailsDiffCallback : DiffUtil.ItemCallback<UserDetails>() {
    //Checking if new and old items are same or not
    override fun areItemsTheSame(
        oldItem: UserDetails,
        newItem: UserDetails
    ): Boolean {
        return oldItem.user.id == newItem.user.id
    }

    //comparing old userDetails item with new one
    override fun areContentsTheSame(
        oldItem: UserDetails,
        newItem: UserDetails
    ): Boolean {
        return oldItem == newItem
    }
}
