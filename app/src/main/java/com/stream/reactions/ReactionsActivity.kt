package com.stream.reactions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.stream.reactions.databinding.ActivityReactionsBinding
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.client.api.models.QuerySort
import io.getstream.chat.android.client.channel.ChannelClient
import io.getstream.chat.android.client.models.*
import io.getstream.chat.android.livedata.ChatDomain

class ReactionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReactionsBinding
    private var sentMessage = Message()
    private val reactionsAdapter = ReactionsAdapter { reactionModel ->
        onReactionClick(reactionModel)
    }
    private lateinit var channelClient: ChannelClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reactions)

        binding = ActivityReactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = ChatClient.Builder("b67pax5b2wdq", applicationContext).build()
        ChatDomain.Builder(client, applicationContext).build()

        binding.imgReactOnMessage.setOnClickListener {
            showReactions()
        }

        val user = User(
            id = "tutorial-droid",
            extraData = mutableMapOf(
                "name" to "Stream Reactions",
                "image" to "https://bit.ly/2TIt8NR",
            ),
        )
        client.connectUser(
            user = user,
            token = BuildConfig.TOKEN
        ).enqueue()

        channelClient = client.channel(channelType = "messaging", channelId = "general")

        channelClient.create().enqueue { result ->
            if (result.isSuccess) {
                val newChannel: Channel = result.data()
                Log.d("Channel",newChannel.name)
            } else {
                showSnackBar("Adding channels Failed")
            }
        }

        val request = QueryChannelsRequest(
                filter = Filters.and(
                        Filters.eq("members", listOf("tutorial-droid")),
                ),
                offset = 0,
                limit = 10,
                querySort = QuerySort.desc("last_message_at")
        ).apply {
            watch = true
            state = true
        }


        client.queryChannels(request).enqueue { result ->
            if (result.isSuccess) {
                val channels: List<Channel> = result.data()
                Log.d("Channel",channels.size.toString())
            } else {
                showSnackBar("Querying channel Failed")
            }
        }

        val message = Message( text = "Sample message text" )

        channelClient.sendMessage(message).enqueue { result ->
            if (result.isSuccess) {
                sentMessage = result.data()
                binding.tvMessage.text = sentMessage.text
            } else {
                showSnackBar("Adding message Failed")
            }
        }


    }

    private fun showReactions() {
        binding.rvReactions.visibility = View.VISIBLE
        reactionsAdapter.submitList(getReactions())
        binding.rvReactions.adapter = reactionsAdapter

    }

    private fun hideReactions(sentReaction: Reaction) {
        binding.rvReactions.visibility = View.GONE
        binding.layoutReactionCount.visibility = View.VISIBLE
        if (sentReaction.type == "clap"){
            binding.imgReactionCount.setImageResource(R.drawable.ic_clapping)
        } else {
            binding.imgReactionCount.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        binding.tvReactionCount.text = sentReaction.score.toString()
        binding.imgDeleteReaction.setOnClickListener {
            deleteReaction(sentReaction)
        }
    }

    private fun onReactionClick(reactionModel: ReactionModel){
        val reaction = Reaction(
            messageId = sentMessage.id,
            type = reactionModel.name,
            score = reactionModel.score,
            extraData = mutableMapOf("customField" to 1),
        )

        if (reactionModel.name == "like"){
            likeReaction(reaction)
        }

        if (reactionModel.name == "clap"){
            clapReaction(reaction)
        }

    }

    private fun likeReaction(reaction: Reaction) {
        channelClient.sendReaction(reaction).enqueue { result ->
            if (result.isSuccess) {
                val sentReaction = result.data()
                hideReactions(sentReaction)
            } else {
                showSnackBar("Adding like reaction Failed")
            }
        }
    }

    private fun clapReaction(reaction: Reaction) {
        channelClient.sendReaction(reaction).enqueue { result->
            if (result.isSuccess) {
                val sentReaction = result.data()
                hideReactions(sentReaction)
            } else {
                showSnackBar("Adding clap reaction Failed")
            }
        }
    }

    private fun deleteReaction(reaction: Reaction){
        channelClient.deleteReaction(
            messageId = reaction.messageId,
            reactionType = reaction.type,
        ).enqueue { result ->
            if (result.isSuccess) {
                hideReactionCount()
            } else {
                showSnackBar("Delete Failed")
            }
        }

    }

    private fun hideReactionCount() {
        binding.layoutReactionCount.visibility = View.GONE
        showSnackBar("Reaction deleted")
    }


    private fun getReactions() = listOf(
        ReactionModel("like",R.drawable.ic_baseline_favorite_border_24, 10),
        ReactionModel("clap",R.drawable.ic_clapping, 25)
    )

    private fun showSnackBar(message: String){
        Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}