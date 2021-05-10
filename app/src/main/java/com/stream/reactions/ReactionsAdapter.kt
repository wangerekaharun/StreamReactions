package com.stream.reactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stream.reactions.databinding.ItemReactionsBinding
import io.getstream.chat.android.client.models.Reaction

typealias OnReactionClick = (Reaction) -> Unit

class ReactionsAdapter(private val onReactionClick: OnReactionClick): ListAdapter<Reaction, ReactionsAdapter.ReactionsViewHolder>(ReactionsDiffUtil) {

    class ReactionsViewHolder(private val binding: ItemReactionsBinding, private val onReactionClick: OnReactionClick): RecyclerView.ViewHolder(binding.root){
        fun bind(reactionModel: Reaction) {
            binding.reaction = reactionModel
            binding.executePendingBindings()
            binding.tvReactionCount.text = reactionModel.score.toString()

            if (reactionModel.type == "like"){
                binding.imgReaction.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.imgReaction.setImageResource(R.drawable.ic_clapping)
            }
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

    object ReactionsDiffUtil: DiffUtil.ItemCallback<Reaction>(){
        override fun areItemsTheSame(oldItem: Reaction, newItem: Reaction): Boolean =
            oldItem == newItem


        override fun areContentsTheSame(oldItem: Reaction, newItem: Reaction): Boolean =
            oldItem.updatedAt == newItem.updatedAt

    }
}