package com.stream.reactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stream.reactions.databinding.ItemReactionsBinding

typealias OnReactionClick = (ReactionModel) -> Unit

class ReactionsAdapter(private val onReactionClick: OnReactionClick): ListAdapter<ReactionModel, ReactionsAdapter.ReactionsViewHolder>(ReactionsDiffUtil) {

    class ReactionsViewHolder(private val binding: ItemReactionsBinding, private val onReactionClick: OnReactionClick): RecyclerView.ViewHolder(binding.root){
        fun bind(reactionModel: ReactionModel) {
            binding.reaction = reactionModel
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onReactionClick(reactionModel)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReactionsViewHolder =
        ReactionsViewHolder(
            ItemReactionsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onReactionClick
        )

    override fun onBindViewHolder(holder: ReactionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object ReactionsDiffUtil: DiffUtil.ItemCallback<ReactionModel>(){
        override fun areItemsTheSame(oldItem: ReactionModel, newItem: ReactionModel): Boolean =
            oldItem == newItem


        override fun areContentsTheSame(oldItem: ReactionModel, newItem: ReactionModel): Boolean =
            oldItem.name == newItem.name

    }
}