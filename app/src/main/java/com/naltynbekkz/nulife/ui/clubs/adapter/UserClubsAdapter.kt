package com.naltynbekkz.nulife.ui.clubs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naltynbekkz.nulife.databinding.ItemClubBinding
import com.naltynbekkz.nulife.model.UserClub

class UserClubsAdapter(
    private val click: (String, View) -> Unit
) : ListAdapter<UserClub, UserClubsAdapter.ClubViewHolder>(UserClubDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        return ClubViewHolder(
            ItemClubBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        holder.bind(getItem(position), click)
    }

    class ClubViewHolder(val binding: ItemClubBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userClub: UserClub, click: (String, View) -> Unit) {
            binding.userClub = userClub

            binding.root.setOnClickListener {
                click(userClub.id, binding.logo)
            }
        }
    }
}

private class UserClubDiffCallback : DiffUtil.ItemCallback<UserClub>() {

    override fun areItemsTheSame(oldItem: UserClub, newItem: UserClub): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserClub, newItem: UserClub): Boolean {
        return oldItem == newItem
    }
}