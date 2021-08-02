package com.tawk.to.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jakewharton.rxbinding4.view.clicks
import com.tawk.to.R
import com.tawk.to.databinding.ItemUserListBinding
import com.tawk.to.db.diffcalback.UserDetailsDiffCallback
import com.tawk.to.db.entity.UserDetails
import com.tawk.to.ktx.goneIfOrVisible
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.subjects.PublishSubject
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation

class UserAdapter : PagingDataAdapter<UserDetails, UserViewHolder>(UserDetailsDiffCallback()) {
    private val clickSubject = PublishSubject.create<UserDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserListBinding.inflate(layoutInflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data, clickSubject, holder.bindingAdapterPosition)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        clickSubject.onComplete()
    }

    fun getClickSubject(): PublishSubject<UserDetails> = clickSubject
}

class UserViewHolder(private val binding: ItemUserListBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(userDetails: UserDetails?, clickSubject: @NonNull PublishSubject<UserDetails>, adapterPosition: Int) {
        binding.userNameTv.text = userDetails?.user?.userName ?: ""
        binding.noteImageView.goneIfOrVisible(userDetails?.note?.note.isNullOrEmpty())

        var glideRequestBuilder = Glide.with(binding.root.context)
            .load(userDetails?.user?.avatarUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_github)

        // if the adapter position is every fourth then apply color transformation
        if ((adapterPosition + 1) % 4 == 0) {
            glideRequestBuilder = glideRequestBuilder.apply(RequestOptions.bitmapTransform(InvertFilterTransformation()))
        }

        glideRequestBuilder.into(binding.avatarImageView)


        // publish userDetails on item click
        binding.root.clicks()
            .map {
                userDetails
            }
            .subscribe(clickSubject)
    }
}
