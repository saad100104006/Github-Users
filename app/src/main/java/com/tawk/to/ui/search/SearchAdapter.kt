package com.tawk.to.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import com.tawk.to.R
import com.tawk.to.databinding.ItemUserListBinding
import com.tawk.to.db.diffcalback.UserDetailsDiffCallback
import com.tawk.to.db.entity.UserDetails
import com.tawk.to.ktx.goneIfOrVisible
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.subjects.PublishSubject

class SearchAdapter : ListAdapter<UserDetails, SearchViewHolder>(UserDetailsDiffCallback()) {

    private val clickSubject = PublishSubject.create<UserDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserListBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data, clickSubject)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        clickSubject.onComplete()
    }

    fun getClickSubject(): PublishSubject<UserDetails> = clickSubject
}

class SearchViewHolder(private val binding: ItemUserListBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(userDetails: UserDetails?, clickSubject: @NonNull PublishSubject<UserDetails>) {
        binding.userNameTv.text = "${userDetails?.user?.userName}"
        binding.noteImageView.goneIfOrVisible(userDetails?.note?.note.isNullOrEmpty())

        Glide.with(binding.root.context)
            .load(userDetails?.user?.avatarUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_github)
            .into(binding.avatarImageView)

        // publish userDetails on item click
        binding.root.clicks()
            .map {
                userDetails
            }
            .subscribe(clickSubject)
    }
}


