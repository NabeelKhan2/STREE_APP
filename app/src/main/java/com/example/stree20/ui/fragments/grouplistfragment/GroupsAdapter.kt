package com.example.stree20.ui.fragments.grouplistfragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.stree20.data.local.StreeItem
import com.example.stree20.databinding.GroupsItemBinding

class GroupsAdapter : RecyclerView.Adapter<GroupsAdapter.StreeItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<StreeItem>() {
        override fun areItemsTheSame(oldItem: StreeItem, newItem: StreeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StreeItem, newItem: StreeItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var streeItem: List<StreeItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreeItemViewHolder {
        val binding =
            GroupsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StreeItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return streeItem.size
    }

    override fun onBindViewHolder(holder: StreeItemViewHolder, position: Int) {
        holder.bind(streeItem[position])
    }


    class StreeItemViewHolder(private val binding: GroupsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: StreeItem) {
            binding.apply {
                tvGroupName.text = item.groupName
                tvChannelName.text = "Channel: ${item.channel}"
                tvSmsSource.text = "Source: ${item.source}"

                rootLayout.setOnClickListener {
                    val action =
                        GroupListFragmentDirections.actionGroupListFragmentToUpdateGroupFragment(
                            item
                        )
                    binding.root.findNavController().navigate(action)
                }
            }
        }
    }
}