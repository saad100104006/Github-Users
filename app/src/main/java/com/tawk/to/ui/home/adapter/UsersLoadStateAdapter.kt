package com.tawk.to.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tawk.to.databinding.ItemLoadStateBinding
import com.tawk.to.ktx.visibleIfOrGone

class UsersLoadStateAdapter : LoadStateAdapter<UsersLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: UsersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): UsersLoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLoadStateBinding.inflate(layoutInflater, parent, false)
        return UsersLoadStateViewHolder(binding)
    }
}


class UsersLoadStateViewHolder(private val binding: ItemLoadStateBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        binding.progressBar.visibleIfOrGone(loadState is LoadState.Loading)
    }
}

